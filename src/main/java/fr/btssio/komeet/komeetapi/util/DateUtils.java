package fr.btssio.komeet.komeetapi.util;

import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    private static final String ZONE = "Europe/Paris";

    private DateUtils() {
    }

    public static @NotNull String formatMillisTime(long millis, String format) {
        Instant instant = Instant.ofEpochMilli(millis);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, java.time.ZoneId.of(ZONE));
        return localDateTime.format(DateTimeFormatter.ofPattern(format));
    }
}
