package org.luisangel.msvcoperation.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateFormatApp {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static LocalDateTime stringToLocalDateTime(String date) {
        return LocalDateTime.parse(date, formatter);
    }

    public static String localDateTimeToString(LocalDateTime dateTime) {
        return dateTime.format(formatter);
    }

}
