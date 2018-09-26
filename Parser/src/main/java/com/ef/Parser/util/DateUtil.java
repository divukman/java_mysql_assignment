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
    public static LocalDateTime validateLogDate(final String strDate) {
        return DateUtil.validateDate(strDate, "yyyy-MM-dd HH:mm:ss.SSS");
    }

    /**
     * Parses the date string timestamps.
     * @param strDate date string in yyyy-MM-dd.HH:mm:ss format
     * @return localized date object
     * @throws ArgumentException
     */
    public static LocalDateTime validateArgumentDate(final String strDate) {
       return DateUtil.validateDate(strDate, "yyyy-MM-dd.HH:mm:ss");
    }

    /**
     * Parses the date string timestamps.
     * @param strDate date string
     * @param dateFormat date format string
     * @return localized date object
     * @throws ArgumentException
     */
    public static LocalDateTime validateDate(final String strDate, final String dateFormat) {
        LocalDateTime result = null;

        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
        try {
            result = LocalDateTime.parse(strDate, formatter);
        } catch (Exception e) {
            throw new ArgumentException("date string format " + dateFormat);
        }

        return result;
    }


}
