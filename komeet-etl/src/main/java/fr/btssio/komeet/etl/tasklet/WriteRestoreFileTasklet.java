package fr.btssio.komeet.etl.tasklet;

import fr.btssio.komeet.common.service.PathService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Slf4j
public class WriteRestoreFileTasklet implements Tasklet {

    private static final String RESTORE_FILENAME = "restore.sql";

    private final PathService pathService;

    public WriteRestoreFileTasklet(PathService pathService) {
        this.pathService = pathService;
    }

    @Override
    public RepeatStatus execute(@NotNull StepContribution contribution, @NotNull ChunkContext chunkContext) throws IOException {

        final File restoreFile = pathService.getTempFile(RESTORE_FILENAME);

        Optional<File[]> optional = Optional.ofNullable(pathService.getTempPath().toFile().listFiles());
        if (optional.isPresent()) {
            List<File> files = Arrays.stream(optional.get())
                    .sorted(Comparator.comparing(File::getName))
                    .toList();
            if (restoreFile.createNewFile()) {
                log.info("Created restore file {}", restoreFile);
            }
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(restoreFile))) {
                for (File file : files) {
                    writeInRestoreFile(file, bw);
                }
            }
        }

        return RepeatStatus.FINISHED;
    }

    private void writeInRestoreFile(final File file, final BufferedWriter bw) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                bw.write(line);
                bw.newLine();
            }
        }
    }
}
