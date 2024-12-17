package fr.btssio.komeet.etl.tasklet;

import fr.btssio.komeet.common.service.PathService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class WriteRestoreFileTaskletTest {

    private final PathService pathService = mock(PathService.class);
    private final WriteRestoreFileTasklet writeRestoreFileTasklet = new WriteRestoreFileTasklet(pathService);

    @SuppressWarnings("all")
    @BeforeEach
    void setup() throws IOException {
        new File("src/test/resources/restore.sql").createNewFile();
    }

    @SuppressWarnings("all")
    @AfterEach
    void close() {
        new File("src/test/resources/restore.sql").delete();
        new File("src/test/resources/autoCreated.sql").delete();
    }

    @Test
    void executePathEmpty() throws IOException {
        final File mockFolder = mock(File.class);
        final Path mockPath = spy(Path.of("src", "test", "resources"));
        when(pathService.getTempPath()).thenReturn(mockPath);
        when(mockFolder.listFiles()).thenReturn(null);
        when(mockPath.toFile()).thenReturn(mockFolder);

        RepeatStatus result = writeRestoreFileTasklet.execute(mock(StepContribution.class), mock(ChunkContext.class));

        assertEquals(RepeatStatus.FINISHED, result);
    }

    @Test
    void execute() throws IOException {
        final File mockFolder = mock(File.class);
        final Path mockPath = spy(Path.of("src", "test", "resources"));
        when(pathService.getTempFile(any())).thenReturn(new File("src/test/resources/restore.sql"));
        when(pathService.getTempPath()).thenReturn(mockPath);
        when(mockFolder.listFiles()).thenReturn(new File[]{new File("src/test/resources/resource.sql")});
        when(mockPath.toFile()).thenReturn(mockFolder);

        RepeatStatus result = writeRestoreFileTasklet.execute(mock(StepContribution.class), mock(ChunkContext.class));

        assertEquals(RepeatStatus.FINISHED, result);
    }

    @Test
    void executeAndAutoFileCreated() throws IOException {
        final File mockFolder = mock(File.class);
        final Path mockPath = spy(Path.of("src", "test", "resources"));
        when(pathService.getTempFile(any())).thenReturn(new File("src/test/resources/autoCreated.sql"));
        when(pathService.getTempPath()).thenReturn(mockPath);
        when(mockFolder.listFiles()).thenReturn(new File[]{new File("src/test/resources/resource.sql")});
        when(mockPath.toFile()).thenReturn(mockFolder);

        RepeatStatus result = writeRestoreFileTasklet.execute(mock(StepContribution.class), mock(ChunkContext.class));

        assertEquals(RepeatStatus.FINISHED, result);
    }
}