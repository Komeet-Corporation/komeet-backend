package fr.btssio.komeet.etl.tasklet;

import fr.btssio.komeet.common.service.PathService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@Slf4j
public class DeleteTempFileTasklet implements Tasklet {

    private final PathService pathService;

    public DeleteTempFileTasklet(final PathService pathService) {
        this.pathService = pathService;
    }

    @Override
    public RepeatStatus execute(@NotNull StepContribution contribution, @NotNull ChunkContext chunkContext) throws IOException {

        Optional<File[]> optional = Optional.ofNullable(pathService.getTempPath().toFile().listFiles());
        if (optional.isPresent()) {
            final File[] files = optional.get();
            for (File file : files) {
                if (file.exists()) {
                    Files.delete(file.toPath());
                    log.info("Deleted temp file {}", file.getName());
                }
            }
        }
        return RepeatStatus.FINISHED;
    }
}
