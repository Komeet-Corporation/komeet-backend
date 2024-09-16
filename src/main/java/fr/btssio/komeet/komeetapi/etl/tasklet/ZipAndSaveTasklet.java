package fr.btssio.komeet.komeetapi.etl.tasklet;

import fr.btssio.komeet.komeetapi.service.PathService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Slf4j
public class ZipAndSaveTasklet implements Tasklet {

    private final PathService pathService;

    public ZipAndSaveTasklet(final PathService pathService) {
        this.pathService = pathService;
    }

    @Override
    public RepeatStatus execute(@NotNull StepContribution contribution, @NotNull ChunkContext chunkContext) throws IOException {

        Optional<File> optional = Optional.ofNullable(pathService.getTempFile("restore.sql"));
        if (optional.isPresent()) {
            final File restoreFile = optional.get();
            File zipFile = Path.of(pathService.getSavePath() + File.separator + getFullRestoreFilename()).toFile();
            File restoreInZipFile = Path.of(zipFile.getPath() + File.separator + getFullRestoreFilename()).toFile();
            if (zipFile.createNewFile() && restoreInZipFile.createNewFile()) {
                log.info("Zip file created: {}", zipFile.getAbsolutePath());
                log.info("Restore file created: {}", restoreInZipFile.getAbsolutePath());
                try (FileOutputStream fos = new FileOutputStream(restoreInZipFile)) {
                    try (FileInputStream fis = new FileInputStream(restoreFile)) {
                        final byte[] buffer = new byte[8192];
                        int read;
                        while ((read = fis.read(buffer)) != -1) {
                            fos.write(buffer, 0, read);
                        }
                    }
                }
            }
        }

        return RepeatStatus.FINISHED;
    }

    private String getFullRestoreFilename() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String dateTime = dtf.format(LocalDateTime.now());
        return "full-restore-" + dateTime + ".zip";
    }
}
