package cn.zifangsky.mqwebsocket.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

/**
 * Date相关公共方法
 *
 * @author zifangsky
 * @date 2018/7/27
 * @since 1.0.0
 */
/**
 * Date相关公共方法
 *
 * @author zifangsky
 * @date 2018/7/27
 * @since 1.0.0
 */
public class DateUtils {
    private static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 返回当前的LocalDateTime
     * @author zifangsky
     * @date 2018/7/30 13:23
     * @since 1.0.0
     * @return java.time.LocalDateTime
     */
    public static LocalDateTime now(){
        return LocalDateTime.now();
    }

    /**
     * 返回当前时间字符串（格式化表达式：yyyy-MM-dd HH:mm:ss）
     * @author zifangsky
     * @date 2018/7/30 13:23
     * @since 1.0.0
     * @return java.lang.String
     */
    public static String nowStr(){
        return formatLocalDateTime(now());
    }

    /**
     * 返回当前时间字符串
     * @author zifangsky
     * @date 2018/7/30 13:23
     * @since 1.0.0
     * @param pattern 指定时间格式化表达式
     * @return java.lang.String
     */
    public static String nowStr(String pattern){
        return formatLocalDateTime(now(), pattern);
    }

    /**
     * 返回当前时间Instant
     * @author zifangsky
     * @date 2018/7/30 13:23
     * @since 1.0.0
     * @param zoneOffset 时区，不填默认为+8
     * @return java.time.Instant
     */
    public static Instant nowInstant(ZoneOffset zoneOffset){
        return toInstant(now(), zoneOffset);
    }

    /**
     * 返回当前精确到秒的时间戳
     * @author zifangsky
     * @date 2018/7/30 13:23
     * @since 1.0.0
     * @param zoneOffset 时区，不填默认为+8
     * @return java.lang.Long
     */
    public static Long nowSecondTimestamp(ZoneOffset zoneOffset){
        return getSecondTimestamp(nowInstant(zoneOffset));
    }

    /**
     * 返回当前精确到毫秒的时间戳
     * @author zifangsky
     * @date 2018/7/30 13:23
     * @since 1.0.0
     * @param zoneOffset 时区，不填默认为+8
     * @return java.lang.Long
     */
    public static Long nowMilliSecondTimestamp(ZoneOffset zoneOffset){
        return getMilliSecondTimestamp(nowInstant(zoneOffset));
    }

    /**
     * 返回当前日期
     * @author zifangsky
     * @date 2018/7/30 13:23
     * @since 1.0.0
     * @return java.time.LocalDate
     */
    public static LocalDate today(){
        return LocalDate.now();
    }

    /**
     * 返回当前日期字符串（格式化表达式：yyyy-MM-dd）
     * @author zifangsky
     * @date 2018/7/30 13:23
     * @since 1.0.0
     * @return java.lang.String
     */
    public static String todayStr(){
        return formatLocalDate(today());
    }

    /**
     * 返回当前日期字符串
     * @author zifangsky
     * @date 2018/7/30 13:23
     * @since 1.0.0
     * @param pattern 指定时间格式化表达式
     * @return java.lang.String
     */
    public static String todayStr(String pattern){
        return formatLocalDate(today(), pattern);
    }

    /**
     * 根据Instant返回精确到秒的时间戳
     * @author zifangsky
     * @date 2018/7/30 13:23
     * @since 1.0.0
     * @param instant 指定的Instant
     * @return java.lang.Long
     */
    public static Long getSecondTimestamp(Instant instant){
        return instant.getEpochSecond();
    }

    /**
     * 根据Instant返回精确到毫秒的时间戳
     * @author zifangsky
     * @date 2018/7/30 13:23
     * @since 1.0.0
     * @param instant 指定的Instant
     * @return java.lang.Long
     */
    public static Long getMilliSecondTimestamp(Instant instant){
        return instant.toEpochMilli();
    }

    /**
     * 返回几天之后的时间
     * @author zifangsky
     * @date 2018/7/30 13:48
     * @since 1.0.0
     * @param nextDays 天数
     * @return java.time.LocalDateTime
     */
    public static LocalDateTime nextDays(Long nextDays){
        return now().plusDays(nextDays);
    }

    /**
     * 返回几天之后的时间（精确到秒的时间戳）
     * @author zifangsky
     * @date 2018/7/30 13:48
     * @since 1.0.0
     * @param nextDays 天数
     * @param zoneOffset 时区，不填默认为+8
     * @return java.lang.Long
     */
    public static Long getNextDaysSecondTimestamp(Long nextDays, ZoneOffset zoneOffset){
        LocalDateTime dateTime = nextDays(nextDays);

        return getSecondTimestamp(toInstant(dateTime, null));
    }

    /**
     * 格式化LocalDateTime（格式化表达式：yyyy-MM-dd HH:mm:ss）
     * @author zifangsky
     * @date 2018/7/30 13:23
     * @since 1.0.0
     * @return java.lang.String
     */
    public static String formatLocalDateTime(LocalDateTime dateTime){
        return formatLocalDateTime(dateTime, DEFAULT_DATE_TIME_FORMAT);
    }

    /**
     * 格式化LocalDateTime
     * @author zifangsky
     * @date 2018/7/30 13:23
     * @since 1.0.0
     * @param pattern 指定时间格式化表达式
     * @return java.lang.String
     */
    public static String formatLocalDateTime(LocalDateTime dateTime, String pattern){
        return dateTime.format(DateTimeFormatter.ofPattern(pattern, Locale.SIMPLIFIED_CHINESE));
    }

    /**
     * 格式化LocalDate（格式化表达式：yyyy-MM-dd）
     * @author zifangsky
     * @date 2018/7/30 13:42
     * @since 1.0.0
     * @param date LocalDate
     * @return java.lang.String
     */
    public static String formatLocalDate(LocalDate date){
        return formatLocalDate(date, DEFAULT_DATE_FORMAT);
    }

    /**
     * 格式化LocalDate
     * @author zifangsky
     * @date 2018/7/30 13:42
     * @since 1.0.0
     * @param date LocalDate
     * @param pattern 指定日期格式化表达式
     * @return java.lang.String
     */
    public static String formatLocalDate(LocalDate date, String pattern){
        return date.format(DateTimeFormatter.ofPattern(pattern, Locale.SIMPLIFIED_CHINESE));
    }

    /**
     * 格式化Date（格式化表达式：yyyy-MM-dd HH:mm:ss）
     * @author zifangsky
     * @date 2018/7/30 13:42
     * @since 1.0.0
     * @param date Date
     * @return java.lang.String
     */
    public static String formatDate(Date date){
        return formatLocalDateTime(toLocalDateTime(date), DEFAULT_DATE_TIME_FORMAT);
    }

    /**
     * 格式化Date
     * @author zifangsky
     * @date 2018/7/30 13:42
     * @since 1.0.0
     * @param date Date
     * @param pattern 指定日期格式化表达式
     * @return java.lang.String
     */
    public static String formatDate(Date date, String pattern){
        return formatLocalDateTime(toLocalDateTime(date), pattern);
    }

    /**
     * 将LocalDateTime转换为Instant
     * @author zifangsky
     * @date 2018/7/30 13:23
     * @since 1.0.0
     * @param dateTime 指定的LocalDateTime
     * @param zoneOffset 时区，不填默认为+8
     * @return java.time.Instant
     */
    public static Instant toInstant(LocalDateTime dateTime, ZoneOffset zoneOffset){
        if(zoneOffset == null){
            return dateTime.toInstant(ZoneOffset.ofHours(8));
        }else{
            return dateTime.toInstant(zoneOffset);
        }
    }

    /**
     * 将Instant转换为LocalDateTime
     * @author zifangsky
     * @date 2018/7/30 13:23
     * @since 1.0.0
     * @param instant Instant类型时间
     * @param zoneOffset 时区，不填默认为+8
     * @return java.time.LocalDateTime
     */
    public static LocalDateTime toLocalDateTime(Instant instant, ZoneOffset zoneOffset){
        if(zoneOffset == null){
            return LocalDateTime.ofInstant(instant, ZoneOffset.ofHours(8));
        }else{
            return LocalDateTime.ofInstant(instant, zoneOffset);
        }
    }

    /**
     * 通过精确到秒的时间戳构造Instant
     * @author zifangsky
     * @date 2018/7/30 13:23
     * @since 1.0.0
     * @param second 精确到秒的时间戳
     * @return java.time.Instant
     */
    public static Instant toInstantOfSecondTimestamp(Long second){
        return Instant.ofEpochSecond(second);
    }

    /**
     * 通过精确到毫秒的时间戳构造Instant
     * @author zifangsky
     * @date 2018/7/30 13:23
     * @since 1.0.0
     * @param milliSecond 精确到毫秒的时间戳
     * @return java.time.Instant
     */
    public static Instant toInstantOfMilliSecondTimestamp(Long milliSecond){
        return Instant.ofEpochMilli(milliSecond);
    }

    /**
     * 通过精确到秒的时间戳构造LocalDateTime
     * @author zifangsky
     * @date 2018/7/30 13:23
     * @since 1.0.0
     * @param second 精确到秒的时间戳
     * @param zoneOffset 时区，不填默认为+8
     * @return java.time.LocalDateTime
     */
    public static LocalDateTime toLocalDateTimeOfSecondTimestamp(Long second, ZoneOffset zoneOffset){
        return toLocalDateTime(toInstantOfSecondTimestamp(second), zoneOffset);
    }

    /**
     * 通过精确到毫秒的时间戳构造LocalDateTime
     * @author zifangsky
     * @date 2018/7/30 13:23
     * @since 1.0.0
     * @param milliSecond 精确到毫秒的时间戳
     * @param zoneOffset 时区，不填默认为+8
     * @return java.time.LocalDateTime
     */
    public static LocalDateTime toLocalDateTimeOfMilliSecondTimestamp(Long milliSecond, ZoneOffset zoneOffset){
        return toLocalDateTime(toInstantOfMilliSecondTimestamp(milliSecond), zoneOffset);
    }

    /**
     * 将Date转化为LocalDateTime
     * @author zifangsky
     * @date 2018/7/30 13:23
     * @since 1.0.0
     * @return java.time.LocalDateTime
     */
    public static LocalDateTime toLocalDateTime(Date date){
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * 将Date转化为LocalDate
     * @author zifangsky
     * @date 2018/7/30 13:23
     * @since 1.0.0
     * @return java.time.LocalDate
     */
    public static LocalDate toLocalDate(Date date){
        LocalDateTime localDateTime = toLocalDateTime(date);
        return localDateTime.toLocalDate();
    }

    /**
     * 将Date转化为LocalTime
     * @author zifangsky
     * @date 2018/7/30 13:23
     * @since 1.0.0
     * @return java.time.LocalTime
     */
    public static LocalTime toLocalTime(Date date){
        LocalDateTime localDateTime = toLocalDateTime(date);
        return localDateTime.toLocalTime();
    }

    /**
     * 将LocalDateTime转化为Date
     * @author zifangsky
     * @date 2018/7/30 13:23
     * @since 1.0.0
     * @return java.util.Date
     */
    public static Date toDate(LocalDateTime localDateTime){
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    /**
     * 将LocalDate转化为Date
     * @author zifangsky
     * @date 2018/7/30 13:23
     * @since 1.0.0
     * @return java.util.Date
     */
    public static Date toDate(LocalDate localDate){
        Instant instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    /**
     * 将时间字符串转化为LocalDateTime
     * @author zifangsky
     * @date 2018/7/30 13:48
     * @since 1.0.0
     * @param dateTimeStr 时间字符串
     * @return java.time.LocalDateTime
     */
    public static LocalDateTime toLocalDateTime(String dateTimeStr){
        return toLocalDateTime(dateTimeStr, DEFAULT_DATE_TIME_FORMAT);
    }

    /**
     * 将时间字符串转化为LocalDateTime
     * @author zifangsky
     * @date 2018/7/30 13:48
     * @since 1.0.0
     * @param dateTimeStr 时间字符串
     * @param pattern 指定时间格式化表达式
     * @return java.time.LocalDateTime
     */
    public static LocalDateTime toLocalDateTime(String dateTimeStr, String pattern){
        return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern(pattern, Locale.SIMPLIFIED_CHINESE));
    }

    /**
     * 将日期字符串转化为LocalDate
     * @author zifangsky
     * @date 2018/7/30 13:48
     * @since 1.0.0
     * @param dateStr 日期字符串
     * @return java.time.LocalDate
     */
    public static LocalDate toLocalDate(String dateStr){
        return toLocalDate(dateStr, DEFAULT_DATE_FORMAT);
    }

    /**
     * 将日期字符串转化为LocalDate
     * @author zifangsky
     * @date 2018/7/30 13:48
     * @since 1.0.0
     * @param dateStr 日期字符串
     * @param pattern 指定日期格式化表达式
     * @return java.time.LocalDate
     */
    public static LocalDate toLocalDate(String dateStr, String pattern){
        return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(pattern, Locale.SIMPLIFIED_CHINESE));
    }

    /**
     * 将天数转化为秒数
     * @author zifangsky
     * @date 2018/7/30 13:48
     * @since 1.0.0
     * @param days 天数
     * @return java.lang.Integer
     */
    public static Long getSecondOfDays(Long days){
        return days * 86400;
    }

}