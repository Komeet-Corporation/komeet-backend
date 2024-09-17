package fr.btssio.komeet.komeetapi.etl.tasklet;

import fr.btssio.komeet.komeetapi.service.PathService;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

public class DeleteOldZipTasklet implements Tasklet {

    private static final int NUMBER_OF_RESTORE_FILE = 10;

    private final PathService pathService;

    public DeleteOldZipTasklet(final PathService pathService) {
        this.pathService = pathService;
    }

    @Override
    @SuppressWarnings("all")
    public RepeatStatus execute(@NotNull StepContribution contribution, @NotNull ChunkContext chunkContext) {
        Optional<File[]> optional = Optional.ofNullable(pathService.getSavePath().toFile().listFiles());
        if (optional.isPresent() && optional.get().length > NUMBER_OF_RESTORE_FILE) {
            final File[] files = optional.get();
            Arrays.stream(files)
                    .sorted(Comparator.comparing(File::getName))
                    .sorted(Comparator.reverseOrder())
                    .skip(NUMBER_OF_RESTORE_FILE)
                    .forEach(File::delete);
        }

        return RepeatStatus.FINISHED;
    }
}
