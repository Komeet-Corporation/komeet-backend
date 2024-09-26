package fr.btssio.komeet.komeetapi.util;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtils {

    private static final Logger log = LoggerFactory.getLogger(LogUtils.class);

    private LogUtils() {
    }

    public static <T> void logInfo(@NotNull Class<T> tClass, String message) {
        log.info("{} | {}", tClass.getSimpleName(), message);
    }

    public static <T> void logWarn(@NotNull Class<T> tClass, String message) {
        log.warn("{} | {}", tClass.getSimpleName(), message);
    }

    public static <T> void logError(@NotNull Class<T> tClass, String message, Throwable e) {
        log.error("{} | {}", tClass.getSimpleName(), message, e);
    }
}
