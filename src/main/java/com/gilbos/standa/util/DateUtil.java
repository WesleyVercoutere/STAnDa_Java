package com.gilbos.standa.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Helper functions for handling dates.
 */
public class DateUtil {

    /**
     * The date pattern that is used for conversion. Change as you wish.
     */
    private static final String DATE_PATTERN = "yyyy-MM-dd-HH:mm:ss.SSS";
    private static final String DATE_PATTERN_VIEW = "yyyy-MM-dd\nHH:mm:ss.SSS";
    private static final String DATE_PATTERN_LABEL = "dd HH:mm:ss.SSS";

    /**
     * The date formatter.
     */
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern(DATE_PATTERN);

    /**
     * The date formatter.
     */
    private static final DateTimeFormatter DATE_FORMATTER_VIEW =
            DateTimeFormatter.ofPattern(DATE_PATTERN_VIEW);

    /**
     * The date formatter.
     */
    private static final DateTimeFormatter DATE_FORMATTER_LABEL =
            DateTimeFormatter.ofPattern(DATE_PATTERN_LABEL);

    /**
     * Returns the given date as a well formatted String. The above defined
     * {@link DateUtil#DATE_PATTERN} is used.
     *
     * @param date the date to be returned as a string
     * @return formatted string
     */
    public static String format(LocalDate date) {
        if (date == null) {
            return null;
        }
        return DATE_FORMATTER.format(date);
    }

    /**
     * Converts a String in the format of the defined {@link DateUtil#DATE_PATTERN}
     * to a {@link LocalDate} object.
     * <p>
     * Returns null if the String could not be converted.
     *
     * @param dateString the date as String
     * @return the date object or null if it could not be converted
     */
    public static LocalDate parse(String dateString) {
        try {
            return DATE_FORMATTER.parse(dateString, LocalDate::from);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Checks the String whether it is a valid date.
     *
     * @param dateString
     * @return true if the String is a valid date
     */
    public static boolean validDate(String dateString) {
        // Try to parse the String.
        return DateUtil.parse(dateString) != null;
    }

    /**
     * Returns the long from a date calculate from 1-1-1970
     *
     * @param dateString
     * @return long
     */
    public static long epochMilli(String dateString) {
        LocalDateTime time = LocalDateTime.parse(dateString, DATE_FORMATTER);
        return time.toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli();
    }

    public static String fromEpochMilli(long epochMilli) {
        LocalDateTime time = Instant.ofEpochMilli(epochMilli).atZone(ZoneOffset.ofTotalSeconds(0)).toLocalDateTime();

        return DATE_FORMATTER_VIEW.format(time);
    }

    public static String fromMilli(double durationInMillis) {

        double second = (durationInMillis / 1000) % 60;
        double minute = (durationInMillis / (1000 * 60)) % 60;
        double hour = (int) (durationInMillis / (1000 * 60 * 60));

        return String.format("%02.0f:%02.0f:%02.0f", hour, minute, second);
    }

}