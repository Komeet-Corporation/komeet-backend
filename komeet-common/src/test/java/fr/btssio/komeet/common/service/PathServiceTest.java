package fr.btssio.komeet.common.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PathServiceTest {

    private static final File tempFile = new File(System.getProperty("user.home") + "/komeet/temp/file-test.txt");
    private static final File saveDir = new File(System.getProperty("user.home") + "/komeet/save");

    private final PathService pathService = new PathService();

    @BeforeAll
    static void setUp() throws IOException {
        if (!saveDir.exists()) {
            boolean createdDir = saveDir.mkdirs();
            assertTrue(createdDir);
        }
        boolean createdFile = tempFile.createNewFile();
        assertTrue(createdFile);
    }

    @AfterAll
    static void close() {
        if (tempFile.exists()) {
            boolean deleted = tempFile.delete();
            assertTrue(deleted);
        }
    }

    @Test
    void getTempFile() {
        File file = pathService.getTempFile("file-test.txt");
        assertTrue(file.exists());
    }

    @Test
    void getSavePath() {
        String path = pathService.getSavePath().toString();
        assertTrue(path.endsWith("save"));
    }
}