package fr.btssio.komeet.komeetapi.etl.tasklet;

import fr.btssio.komeet.komeetapi.service.PathService;
import fr.btssio.komeet.komeetapi.util.DateUtils;
import fr.btssio.komeet.komeetapi.util.LogUtils;
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

public class ZipAndSaveTasklet implements Tasklet {

    private static final String FORMAT = "yyyyMMddHHmmssSSS";

    private final PathService pathService;

    public ZipAndSaveTasklet(final PathService pathService) {
        this.pathService = pathService;
    }

    @Override
    public RepeatStatus execute(@NotNull StepContribution contribution, @NotNull ChunkContext chunkContext) throws IOException {

        Optional<File> optional = Optional.ofNullable(pathService.getTempFile("restore.sql"));
        if (optional.isPresent()) {
            final File restoreFile = optional.get();
            final File zipFile = new File(pathService.getSavePath() + File.separator + getFullRestoreFilename());
            if (zipFile.createNewFile()) {
                LogUtils.logInfo(this.getClass(), "Zip file created: " + zipFile.getAbsolutePath());
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
        }

        return RepeatStatus.FINISHED;
    }

    private @NotNull String getFullRestoreFilename() {
        String dateTime = DateUtils.formatMillisTime(System.currentTimeMillis(), FORMAT);
        return "full-restore-" + dateTime + ".zip";
    }
}
