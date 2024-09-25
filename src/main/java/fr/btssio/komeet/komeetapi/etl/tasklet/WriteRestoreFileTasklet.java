package fr.btssio.komeet.komeetapi.etl.tasklet;

import fr.btssio.komeet.komeetapi.service.PathService;
import fr.btssio.komeet.komeetapi.util.LogUtils;
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

public class WriteRestoreFileTasklet implements Tasklet {

    private static final String restoreFilename = "restore.sql";

    private final PathService pathService;

    public WriteRestoreFileTasklet(PathService pathService) {
        this.pathService = pathService;
    }

    @Override
    public RepeatStatus execute(@NotNull StepContribution contribution, @NotNull ChunkContext chunkContext) throws IOException {

        final File restoreFile = new File(pathService.getTempPath() + File.separator + restoreFilename);

        Optional<File[]> optional = Optional.ofNullable(pathService.getTempPath().toFile().listFiles());
        if (optional.isPresent()) {
            List<File> files = Arrays.stream(optional.get())
                    .sorted(Comparator.comparing(File::getName))
                    .toList();
            if (restoreFile.createNewFile()) {
                LogUtils.logInfo(this.getClass(), "Created restore file " + restoreFile);
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
