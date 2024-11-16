package fr.btssio.komeet.komeetapi.service;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;

@Service
public class PathService {

    private static final String USER_HOME_LABEL = "user.home";

    private final String userHome = System.getProperty(USER_HOME_LABEL);

    public PathService() {
        /// No property(ies) needed
        /// If you need to add more, delete this comment
    }

    public Path getTempPath() {
        return Path.of(userHome + "/komeet/temp");
    }

    public File getTempFile(String fileName) {
        return Path.of(userHome + "/komeet/temp/" + fileName).toFile();
    }

    public Resource getTempFileAsResource(String filename) {
        return new FileSystemResource(getTempPath().resolve(filename).toFile());
    }

    public Path getSavePath() {
        return Path.of(userHome + "/komeet/save");
    }
}
