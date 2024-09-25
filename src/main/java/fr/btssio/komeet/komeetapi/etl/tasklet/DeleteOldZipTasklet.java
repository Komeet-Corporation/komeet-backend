package fr.btssio.komeet.komeetapi.etl.tasklet;

import fr.btssio.komeet.komeetapi.service.PathService;
import fr.btssio.komeet.komeetapi.util.LogUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;
import java.util.regex.Pattern;

public class DeleteOldZipTasklet implements Tasklet {

    private static final int NUMBER_OF_RESTORE_FILE = 10;
    private static final Pattern FULL_RESTORE_FILENAME_PATTERN = Pattern.compile("^full-restore-\\d{17}\\.zip$");

    private final PathService pathService;

    public DeleteOldZipTasklet(final PathService pathService) {
        this.pathService = pathService;
    }

    @Override
    @SuppressWarnings("all")
    public RepeatStatus execute(@NotNull StepContribution contribution, @NotNull ChunkContext chunkContext) {

        Optional<File[]> optional = Optional.ofNullable(pathService.getSavePath().toFile().listFiles());
        if (optional.isPresent()) {
            final File[] files = optional.get();
            Arrays.stream(files)
                    .filter(file -> !FULL_RESTORE_FILENAME_PATTERN.matcher(file.getName()).matches())
                    .forEach(file -> {
                        if (file.delete()) {
                            LogUtils.logInfo(this.getClass(), "Deleted unknown file " + file.getName());
                        }
                    });
            if (files.length > NUMBER_OF_RESTORE_FILE) {
                Arrays.stream(files)
                        .sorted(Comparator.comparing(File::getName))
                        .sorted(Comparator.reverseOrder())
                        .skip(NUMBER_OF_RESTORE_FILE)
                        .forEach(File::delete);
            }
        }

        return RepeatStatus.FINISHED;
    }
}
