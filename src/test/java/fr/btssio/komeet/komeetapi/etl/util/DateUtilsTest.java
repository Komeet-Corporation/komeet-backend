package fr.btssio.komeet.komeetapi.etl.util;

import fr.btssio.komeet.komeetapi.util.DateUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DateUtilsTest {

    @Test
    void formatMillisTime() {
        long timeMillis = 1726655675608L;

        String format1 = DateUtils.formatMillisTime(timeMillis, "yyyy-MM-dd HH:mm:ss");
        String format2 = DateUtils.formatMillisTime(timeMillis, "yyyyMMddHHmmssSSS");

        assertEquals("2024-09-18 12:34:35", format1);
        assertEquals("20240918123435608", format2);
    }
}