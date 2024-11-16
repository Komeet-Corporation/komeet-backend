package fr.btssio.komeet.komeetapi.etl.tasklet;

import fr.btssio.komeet.komeetapi.service.PathService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;
import java.util.regex.Pattern;

@Slf4j
public class DeleteOldZipTasklet implements Tasklet {

    private static final int NUMBER_OF_RESTORE_FILE = 10;
    private static final Pattern FULL_RESTORE_FILENAME_PATTERN = Pattern.compile("^full-restore-\\d{17}\\.zip$");

    private final PathService pathService;

    public DeleteOldZipTasklet(final PathService pathService) {
        this.pathService = pathService;
    }

    @Override
    public RepeatStatus execute(@NotNull StepContribution contribution, @NotNull ChunkContext chunkContext) {
        Optional<File[]> optional = Optional.ofNullable(pathService.getSavePath().toFile().listFiles());
        if (optional.isPresent()) {
            final File[] files = optional.get();
            purgeUnknownFiles(files);
            keepTenMostRecentFiles(files);
        }
        return RepeatStatus.FINISHED;
    }

    @SuppressWarnings("all")
    private void keepTenMostRecentFiles(final File @NotNull [] files) {
        if (files.length > NUMBER_OF_RESTORE_FILE) {
            Arrays.stream(files)
                    .sorted(Comparator.comparing(File::getName).reversed())
                    .skip(NUMBER_OF_RESTORE_FILE)
                    .forEach(File::delete);
        }
    }

    private void purgeUnknownFiles(final File @NotNull [] files) {
        for (final File file : files) {
            if (!FULL_RESTORE_FILENAME_PATTERN.matcher(file.getName()).matches() && file.exists()) {
                try {
                    Files.delete(file.toPath());
                    log.info("Deleted unknown file {}", file.getName());
                } catch (IOException e) {
                    log.warn("Failed to delete unknown file {}", file.getName(), e);
                }
            }
        }
    }
}
