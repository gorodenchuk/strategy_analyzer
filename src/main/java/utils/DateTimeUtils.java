package utils;

import java.time.Instant;

public class DateTimeUtils {

    /**
     * Compares two ISO-8601 UTC datetime strings.
     *
     * @param dateTime1 ISO datetime string, e.g. 2025-04-30T16:00:00.000000000Z
     * @param dateTime2 ISO datetime string, e.g. 2025-04-30T15:59:59.999999999Z
     * @return -1 if dateTime1 < dateTime2, 0 if equal, 1 if dateTime1 > dateTime2
     */
    public int compare(String dateTime1, String dateTime2) {
        Instant instant1 = Instant.parse(dateTime1);
        Instant instant2 = Instant.parse(dateTime2);
        return instant1.compareTo(instant2);
    }
}
