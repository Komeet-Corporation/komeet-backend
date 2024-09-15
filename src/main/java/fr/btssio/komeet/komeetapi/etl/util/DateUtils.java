package fr.btssio.komeet.komeetapi.etl.util;

import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    private static final String ZONE = "Europe/Paris";
    private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";

    private DateUtils() {
    }

    public static @NotNull String formatMillisTime(long millis) {
        Instant instant = Instant.ofEpochMilli(millis);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, java.time.ZoneId.of(ZONE));
        return localDateTime.format(DateTimeFormatter.ofPattern(FORMAT));
    }
}
