package com.wildbeeslabs.api.rest.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * DateUtils implementation
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 */
public final class DateUtils {

    /**
     * Default private constructor
     */
    private DateUtils() {
    }

    /**
     * Default date format locale
     */
    public static final String DEFAULT_DATE_FORMAT_LOCALE = "en_GB";

    /**
     * Default date format pattern
     */
    public static final String DEFAULT_DATE_FORMAT_PATTERN_EXT = "yyyy-MM-dd HH:mm:ssZ";

    /**
     * Default date format pattern
     */
    public static final String DEFAULT_DATE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";//yyyy-MM-dd HH:mm:ss.SSSZ
    /**
     * Default time zone pattern
     */
    public static final String DEFAULT_TIMEZONE_PATTERN = Calendar.getInstance().getTimeZone().getID();

    public static Date strToDate(final String str) {
        return strToDate(str, DEFAULT_TIMEZONE_PATTERN);
    }

    public static Date strToDate(final String str, final String timezone) {
        return strToDate(str, timezone, DEFAULT_DATE_FORMAT_PATTERN);
    }

    public static Date strToDate(final String str, final String timezone, final String format) {
        try {
            DateFormat df = new SimpleDateFormat(format);
            df.setTimeZone(TimeZone.getTimeZone(timezone));
            return df.parse(str);
        } catch (ParseException ex) {
            return null;
        }
    }

    public static String dateToStr(final Date date) {
        return dateToStr(date, DEFAULT_TIMEZONE_PATTERN);
    }

    public static String dateToStr(final Date date, final String timezone) {
        return dateToStr(date, timezone, DEFAULT_DATE_FORMAT_PATTERN);
    }

    public static String dateToStr(final Date date, final String timezone, final String format) {
        DateFormat df = new SimpleDateFormat(format);
        df.setTimeZone(TimeZone.getTimeZone(timezone));
        return df.format(date);
    }
}
