package fr.btssio.komeet.komeetapi.etl.tasklet;

import fr.btssio.komeet.komeetapi.etl.util.DateUtils;
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
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
public class ZipAndSaveTasklet implements Tasklet {

    private static final String RESTORE_FILENAME = "restore.sql";
    private static final String FULL_RESTORE_FILENAME_PATTERN = "full-restore-%s.zip";
    private static final String DATE_FORMAT = "yyyyMMddHHmmssSSS";

    private final PathService pathService;

    public ZipAndSaveTasklet(final PathService pathService) {
        this.pathService = pathService;
    }

    @Override
    public RepeatStatus execute(@NotNull StepContribution contribution, @NotNull ChunkContext chunkContext) throws IOException {

        Optional<File> optional = Optional.ofNullable(pathService.getTempFile(RESTORE_FILENAME));
        if (optional.isPresent()) {
            final File restoreFile = optional.get();
            final File zipFile = new File(pathService.getSavePath() + File.separator + getFullRestoreFilename());
            if (zipFile.createNewFile()) {
                log.info("Zip file created: {}", zipFile.getAbsolutePath());
                writeFullRestoreZipFile(zipFile, restoreFile);
            }
        }

        return RepeatStatus.FINISHED;
    }

    private void writeFullRestoreZipFile(File zipFile, @NotNull File restoreFile) throws IOException {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile));
             FileInputStream fis = new FileInputStream(restoreFile)) {
            ZipEntry ze = new ZipEntry(restoreFile.getName());
            zos.putNextEntry(ze);
            byte[] buffer = new byte[8192];
            int read;
            while ((read = fis.read(buffer)) != -1) {
                zos.write(buffer, 0, read);
            }
            zos.closeEntry();
        }
    }

    private @NotNull String getFullRestoreFilename() {
        String dateTime = DateUtils.formatMillisTime(System.currentTimeMillis(), DATE_FORMAT);
        return String.format(FULL_RESTORE_FILENAME_PATTERN, dateTime);
    }
}
