package fr.btssio.komeet.common.service;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

class PathServiceTest {

    private final PathService pathService = spy(new PathService());

    @Test
    void getTempFile() {
        final File fileMock = new File("file-test.txt");
        when(pathService.getTempFile("file-test.txt")).thenReturn(fileMock);

        File file = pathService.getTempFile("file-test.txt");

        assertNotNull(file);
    }

    @Test
    void getSavePath() {
        final Path pathMock = Path.of("/komeet/save");
        when(pathService.getSavePath()).thenReturn(pathMock);

        String path = pathService.getSavePath().toString();

        assertTrue(path.endsWith("save"));
    }
}