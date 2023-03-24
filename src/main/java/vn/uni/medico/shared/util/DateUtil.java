package vn.uni.medico.shared.util;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static final ZoneId DEFAULT_ZONE_ID = ZoneId.of("UTC");
    private static final SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
    private static final SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy-MM");
    private static final SimpleDateFormat weekFormat = new SimpleDateFormat("yyyy-ww");
    private static final SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static Date valueOf(Date value, Date defaultValue) {
        return value == null || value.getTime() == 0 ? defaultValue : value;
    }

    public static Date serverDateTime() {
        return new Date();
    }

    public static Date clientDateTime() {
        return toClientDateTime(serverDateTime());
    }

    public static Date toClientDateTime(final Date serverDateTime) {
        return DateUtils.addHours(serverDateTime, +7);
    }

    public static Date toServerDateTime(final Date clientDateTime) {
        return DateUtils.addHours(clientDateTime, -7);
    }

    public static Date truncateYear(final Date serverDateTime, boolean usingClientTimeZone) {
        if (usingClientTimeZone) {
            return toServerDateTime(DateUtils.truncate(toClientDateTime(serverDateTime), Calendar.YEAR));
        }
        return DateUtils.truncate(serverDateTime, Calendar.YEAR);
    }

    public static Date truncateMonth(final Date serverDateTime, boolean usingClientTimeZone) {
        if (usingClientTimeZone) {
            return toServerDateTime(DateUtils.truncate(toClientDateTime(serverDateTime), Calendar.MONTH));
        }
        return DateUtils.truncate(serverDateTime, Calendar.MONTH);
    }

    public static Date truncateWeek(final Date serverDateTime, boolean usingClientTimeZone) {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        if (usingClientTimeZone) {
            c.setTime(DateUtils.truncate(toClientDateTime(serverDateTime), Calendar.DATE));
        } else {
            c.setTime(serverDateTime);
        }
        c.add(Calendar.DAY_OF_WEEK, -c.get(Calendar.DAY_OF_WEEK) + Calendar.MONDAY);
        return usingClientTimeZone ? toServerDateTime(c.getTime()) : c.getTime();
    }

    public static Date truncateDate(final Date serverDateTime, boolean usingClientTimeZone) {
        if (usingClientTimeZone) {
            return toServerDateTime(DateUtils.truncate(toClientDateTime(serverDateTime), Calendar.DATE));
        }
        return DateUtils.truncate(serverDateTime, Calendar.DATE);
    }

    public static Date getMonthCycle(final Date value) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(value);
        calendar.set(Calendar.YEAR, 0);

        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getDayOfWeekCycle(final Date value) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(value);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.set(Calendar.YEAR, 0);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DATE, 0);

        calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);

        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getDateCycle(final Date value) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(value);
        calendar.set(Calendar.YEAR, 0);
        calendar.set(Calendar.MONTH, 0);

        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getTimeCycle(final Date value) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(value);
        calendar.set(Calendar.YEAR, 0);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DATE, 0);

        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static String formatYear(Date dateTime, boolean usingClientTimeZone) {
        return dateTime == null ? "" : yearFormat.format(usingClientTimeZone ? DateUtil.toClientDateTime(dateTime) : dateTime);
    }

    public static String formatMonth(Date dateTime, boolean usingClientTimeZone) {
        return dateTime == null ? "" : monthFormat.format(usingClientTimeZone ? DateUtil.toClientDateTime(dateTime) : dateTime);
    }

    public static String formatWeek(Date dateTime, boolean usingClientTimeZone) {
        return dateTime == null ? "" : weekFormat.format(usingClientTimeZone ? DateUtil.toClientDateTime(dateTime) : dateTime);
    }

    public static String formatDate(Date dateTime, boolean usingClientTimeZone) {
        return dateTime == null ? "" : dayFormat.format(usingClientTimeZone ? DateUtil.toClientDateTime(dateTime) : dateTime);
    }

    public static String formatDateTime(Date dateTime) {
        return formatDateTime(dateTime, false);
    }

    public static String formatDateTime(Date dateTime, boolean usingClientTimeZone) {
        return dateTime == null ? "" : dateTimeFormat.format(usingClientTimeZone ? DateUtil.toClientDateTime(dateTime) : dateTime);
    }

    public static LocalDateTime startOfMonth() {
        return LocalDateTime.now(DEFAULT_ZONE_ID)
                .with(LocalTime.MIN)
                .with(TemporalAdjusters.firstDayOfMonth());
    }

    public static Date string2Date(String date, String pattern) {
        try {
            return new SimpleDateFormat(pattern).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
