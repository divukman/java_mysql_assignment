package com.ef.Parser.util;

import com.ef.Parser.exceptions.ArgumentException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    /**
     * Parses the date string timestamps.
     * @param strDate date string in yyyy-MM-dd.HH:mm:ss format
     * @return localized date object
     * @throws ArgumentException
     */
    public static LocalDateTime validateDate(final String strDate) {
        LocalDateTime result = null;

        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd.HH:mm:ss");
        try {
            result = LocalDateTime.parse(strDate, formatter);
        } catch (Exception e) {
            throw new ArgumentException("date string format yyyy-MM-dd.HH:mm:ss");
        }

        return result;
    }
}
