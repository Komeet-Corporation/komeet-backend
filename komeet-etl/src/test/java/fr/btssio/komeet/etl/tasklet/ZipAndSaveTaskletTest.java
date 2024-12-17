package fr.btssio.komeet.etl.tasklet;

import fr.btssio.komeet.common.service.PathService;
import fr.btssio.komeet.etl.util.DateUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

class ZipAndSaveTaskletTest {

    private final File zipFile = new File("src/test/resources/full-restore-20241211141101447.zip");
    private final PathService pathService = mock(PathService.class);
    private final ZipAndSaveTasklet zipAndSaveTasklet = new ZipAndSaveTasklet(pathService);

    private static MockedStatic<DateUtils> dateUtils;

    @BeforeEach
    void setup() {
        dateUtils = mockStatic(DateUtils.class);
    }

    @SuppressWarnings("all")
    @AfterEach
    void close() {
        if (zipFile.exists()) {
            zipFile.delete();
        }
        dateUtils.close();
    }

    @Test
    void executePathEmpty() throws IOException {
        when(pathService.getTempFile(any())).thenReturn(null);

        RepeatStatus result = zipAndSaveTasklet.execute(mock(StepContribution.class), mock(ChunkContext.class));

        assertEquals(RepeatStatus.FINISHED, result);
    }

    @Test
    void executeZipNotCreated() throws IOException {
        File file = new File("src/test/resources/resource.sql");
        when(pathService.getTempFile(any())).thenReturn(file);
        when(pathService.getSavePath()).thenReturn(Path.of("src", "test", "resources"));
        dateUtils.when(() -> DateUtils.formatMillisTime(anyLong(), any())).thenReturn("20241211141101446");

        RepeatStatus result = zipAndSaveTasklet.execute(mock(StepContribution.class), mock(ChunkContext.class));

        assertEquals(RepeatStatus.FINISHED, result);
    }

    @Test
    void execute() throws IOException {
        File file = new File("src/test/resources/resource.sql");
        when(pathService.getTempFile(any())).thenReturn(file);
        when(pathService.getSavePath()).thenReturn(Path.of("src", "test", "resources"));
        dateUtils.when(() -> DateUtils.formatMillisTime(anyLong(), any())).thenReturn("20241211141101447");

        RepeatStatus result = zipAndSaveTasklet.execute(mock(StepContribution.class), mock(ChunkContext.class));

        assertEquals(RepeatStatus.FINISHED, result);
    }
}