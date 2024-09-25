package fr.btssio.komeet.komeetapi.util;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

@Slf4j
public class LogUtils {

    private LogUtils() {
    }

    public static <T> void logInfo(@NotNull Class<T> tClass, String message) {
        log.info("{} | {}", tClass.getName(), message);
    }

    public static <T> void logWarn(@NotNull Class<T> tClass, String message) {
        log.warn("{} | {}", tClass.getName(), message);
    }

    public static <T> void logError(@NotNull Class<T> tClass, String message, Throwable e) {
        log.error("{} | {}", tClass.getName(), message, e);
    }
}
