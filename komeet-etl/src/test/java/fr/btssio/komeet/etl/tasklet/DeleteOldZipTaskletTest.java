package fr.btssio.komeet.etl.tasklet;

import fr.btssio.komeet.common.service.PathService;
import fr.btssio.komeet.etl.util.DateUtils;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.MockedStatic;
import org.mockito.stubbing.Answer;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DeleteOldZipTaskletTest {

    private final PathService pathService = mock(PathService.class);
    private final DeleteOldZipTasklet deleteOldZipTasklet = new DeleteOldZipTasklet(pathService);

    private static MockedStatic<Files> files;

    @BeforeEach
    void setup() {
        files = mockStatic(Files.class);
    }

    @AfterEach
    void close() {
        files.close();
    }

    @ParameterizedTest
    @CsvSource(value = {"'test.zip',false", "'full-restore-20241207213251501.zip',true",
            "'test.zip',true", "'full-restore-20241207213251501.zip',true"})
    void executePurgeUnknownFile(String filename, boolean exist) {
        final File unkownFile = mock(File.class);
        final File mockFolder = mock(File.class);
        final Path mockPath = spy(Path.of("src", "test", "resources"));
        when(pathService.getSavePath()).thenReturn(mockPath);
        when(mockFolder.listFiles()).thenReturn(new File[]{unkownFile});
        when(mockPath.toFile()).thenReturn(mockFolder);
        when(unkownFile.getName()).thenReturn(filename);
        when(unkownFile.exists()).thenReturn(exist);
        when(unkownFile.toPath()).thenReturn(mockPath);
        files.when(() -> Files.delete(any())).thenAnswer((Answer<Void>) invocation -> null);

        RepeatStatus result = deleteOldZipTasklet.execute(mock(StepContribution.class), mock(ChunkContext.class));

        assertEquals(RepeatStatus.FINISHED, result);
    }

    @Test
    void executePurgeUnknownFileError() {
        final File unkownFile = mock(File.class);
        final File mockFolder = mock(File.class);
        final Path mockPath = spy(Path.of("src", "test", "resources"));
        when(pathService.getSavePath()).thenReturn(mockPath);
        when(mockFolder.listFiles()).thenReturn(new File[]{unkownFile});
        when(mockPath.toFile()).thenReturn(mockFolder);
        when(unkownFile.getName()).thenReturn("test.zip");
        when(unkownFile.exists()).thenReturn(true);
        when(unkownFile.toPath()).thenReturn(mockPath);
        files.when(() -> Files.delete(any())).thenThrow(IOException.class);

        RepeatStatus result = deleteOldZipTasklet.execute(mock(StepContribution.class), mock(ChunkContext.class));

        assertEquals(RepeatStatus.FINISHED, result);
    }

    @Test
    void executePathNull() {
        final File mockFolder = mock(File.class);
        final Path mockPath = spy(Path.of("src", "test", "resources"));
        when(pathService.getSavePath()).thenReturn(mockPath);
        when(mockFolder.listFiles()).thenReturn(null);
        when(mockPath.toFile()).thenReturn(mockFolder);

        RepeatStatus result = deleteOldZipTasklet.execute(mock(StepContribution.class), mock(ChunkContext.class));

        assertEquals(RepeatStatus.FINISHED, result);
    }

    @Test
    void execute() {
        final File[] fileTable = createFileTable();
        final File mockFolder = mock(File.class);
        final Path mockPath = spy(Path.of("src", "test", "resources"));
        when(mockPath.toFile()).thenReturn(mockFolder);
        when(pathService.getSavePath()).thenReturn(mockPath);
        when(mockFolder.listFiles()).thenReturn(fileTable);

        RepeatStatus result = deleteOldZipTasklet.execute(mock(StepContribution.class), mock(ChunkContext.class));

        assertEquals(RepeatStatus.FINISHED, result);
    }

    private File @NotNull [] createFileTable() {
        File[] fileTable = new File[11];
        for (int i = 0; i < 11; i++) {
            fileTable[i] = createMockFile();
        }
        return fileTable;
    }

    private @NotNull File createMockFile() {
        String filename = "full-restore-" + DateUtils.formatMillisTime(System.currentTimeMillis(), "yyyyMMddHHmmssSSS") + ".zip";
        File file = mock(File.class);
        when(file.exists()).thenReturn(true);
        when(file.getName()).thenReturn(filename);
        return file;
    }
}