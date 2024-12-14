package fr.btssio.komeet.etl.tasklet;

import fr.btssio.komeet.common.service.PathService;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

class DeleteTempFileTaskletTest {

    private final PathService pathService = mock(PathService.class);
    private final DeleteTempFileTasklet tasklet = new DeleteTempFileTasklet(pathService);

    private static MockedStatic<Files> files;

    @BeforeEach
    void setUp() {
        files = mockStatic(Files.class);
    }

    @AfterEach
    void close() {
        files.close();
    }

    @Test
    void execute() throws IOException {
        final File mockFolder = mock(File.class);
        final File existFile = createMockFile(true, "existFile.txt");
        final File notExistFile = createMockFile(false, "notExistFile.txt");
        final Path mockPath = spy(Path.of("src", "test", "resources"));
        when(existFile.toPath()).thenReturn(mockPath);
        when(pathService.getTempPath()).thenReturn(mockPath);
        when(mockFolder.listFiles()).thenReturn(new File[]{existFile, notExistFile});
        when(mockPath.toFile()).thenReturn(mockFolder);
        files.when(() -> Files.delete(any())).thenAnswer((Answer<Void>) invocation -> null);

        RepeatStatus result = tasklet.execute(mock(StepContribution.class), mock(ChunkContext.class));

        assertEquals(RepeatStatus.FINISHED, result);
    }

    @Test
    void executePathNull() throws IOException {
        final File mockFolder = mock(File.class);
        final Path mockPath = spy(Path.of("src", "test", "resources"));
        when(pathService.getTempPath()).thenReturn(mockPath);
        when(mockFolder.listFiles()).thenReturn(null);
        when(mockPath.toFile()).thenReturn(mockFolder);

        RepeatStatus result = tasklet.execute(mock(StepContribution.class), mock(ChunkContext.class));

        assertEquals(RepeatStatus.FINISHED, result);
    }

    private @NotNull File createMockFile(boolean exist, String filename) {
        final File f = mock(File.class);
        when(f.exists()).thenReturn(exist);
        when(f.getName()).thenReturn(filename);
        return f;
    }
}
