/**
 * @(#)EnumUtil.java, 2015年8月28日.
 *
 * Copyright 2015 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yinan.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 工具类：[日期]、[字符串]、[时间]三者的转换工具类
 * <p>
 * 三者的转换是非常常用的，所以定义此工具类。三者的简要说明如下：
 * <li>[日期]：日期对象
 * <li>[字符串]：指的是日期的字符串表示
 * <li>[时间]：long型的值
 * 
 * @author hzyurui
 */
public class DateUtils {

    public static final String DATEFORMAT_YEAR_MONTH_DAY = "yyyy年MM月dd日";
    
    public static final String DATEFORMAT_MONTH_DAY = "MM月dd日";

    public static final String DATETIMEFORMAT = "yyyyMMddHHmmss";

    public static final String DATEFORMAT = "yyyyMMdd";

    public static final String MONTHFORMAT = "yyyyMM";
    
    public static final String YEARFORMAT = "yyyy";

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_TIME_FORMAT_MILLIS = "yyyy-MM-dd HH:mm:ss.SSS";

    public static final String DATE_TIME_FORMAT_COMMA = "yyyy.MM.dd HH:mm:ss";

    public static final String DATE_MIN_FORMAT_COMMA = "yyyy.MM.dd HH:mm";

    public static final String DATE_C_FORMAT = "M月d日 HH:mm";

    public static final String DATE_FORMAT = "yyyy-MM-dd";

    public static final String DATE_HOUR_FORMAT_MILLIS = "yyyy-MM-dd HH";

    public static final String DATE_FORMAT_POINT = "yyyy.MM.dd";

    public static final String TIME_FORMAT = "HH:mm:ss";

    public static final String TIME_FORMAT_NO_SECOND = "HH:mm";

    public static final long TIME_OF_SECOND = 1000L;

    public static final long TIME_OF_MINUTE = 60000L;

    public static final long TIME_OF_HOUR = 3600000L;

    public static final long TIME_OF_DAY = 86400000L;

    private static Logger logger = LoggerFactory.getLogger(DateUtils.class);

    private static long CURRENT_TIME = 0;

    /**
     * 将[日期]按照给定的[日期格式]转成[字符串]
     * 
     * @param date
     *            日期
     * @param format
     *            日期格式
     * @return
     */
    public static String parseDateToString(Date date, String format) {
        if (date == null) {
            return null;
        }
        if (StringUtils.isBlank(format)) {
            format = DATE_TIME_FORMAT;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 将[时间]按照指定的[日期格式]转成[字符串]
     * 
     * @param time
     *            时间
     * @param format
     *            日期格式
     * @return
     */
    public static String parseLongToString(long time, String format) {
        SimpleDateFormat mydate = new SimpleDateFormat(format);
        return mydate.format(new Date(time));
    }

    /**
     * 将[字符串]按照指定的[日期格式]转成[时间]
     * 
     * @param time
     *            字符串
     * @param format
     *            日期格式
     * @return
     */
    public static long parseStringToLong(String time, String format) {
        SimpleDateFormat mydate = new SimpleDateFormat(format);
        try {
            Date date = mydate.parse(time);
            if (date != null) {
                return date.getTime();
            }
        } catch (ParseException e) {
            logger.error("Date parse error:", e);
        }
        return 0;
    }

    /**
     * 将[字符串]按照指定的[日期格式]转成[日期]
     * 
     * @param time
     *            字符串
     * @param format
     *            日期格式
     * @return
     */
    public static Date parseStringToDate(String time, String format) {
        SimpleDateFormat mydate = new SimpleDateFormat(format);
        try {
            Date date = mydate.parse(time);
            if (date != null) {
                return date;
            }
        } catch (ParseException e) {
            logger.error("Date parse error:", e);
        }
        return null;
    }

    /**
     * 添加天数
     * 
     * @param date
     *            日期
     * @param amount
     *            增加天数
     * @return
     */
    public static Date addDay(Date date, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.add(Calendar.DAY_OF_MONTH, amount);

        return calendar.getTime();
    }

    /**
     * 计算两个时间相差的自然天数
     * 
     * @param time1
     * @param time2
     * @return
     */
    public static long getDayDiffOfNatural(long time1, long time2) {
        long time1Start = getStartTimeInMillis(time1);
        long time2Start = getStartTimeInMillis(time2);

        //得到两个日期相差的自然天数
        return (time2Start - time1Start) / TIME_OF_DAY;
    }

    /**
     * 获取某天的开始时间
     * 
     * @param date
     *            日期
     */
    public static Date getStartTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static int getDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek == 0) {
            dayOfWeek = 7;
        }
        return dayOfWeek;
    }

    public static int getDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取某天零点的时间戳 xxxx-xx-xx 00:00:00:000
     * 
     * @param date
     *            日期
     */
    public static long getStartTimeInMillis(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取某时间对应的天的零点的时间戳 xxxx-xx-xx 00:00:00:000
     *
     * @param time
     *            日期
     */
    public static long getStartTimeInMillis(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取某时间对应的小时的零点的时间戳 xxxx-xx-xx 10:00:00:000
     *
     * @param time
     *            日期
     */
    public static long getStartHourInMillis(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取某时间对应的天的零点的时间戳 xxxx-xx-xx 23:59:59:999
     *
     * @param time
     *            日期
     */
    public static long getEndTimeInMillis(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));
        return calendar.getTimeInMillis();
    }

    /**
     * 获取某天结束的时间戳 xxxx-xx-xx 23:59:59:999
     *
     * @param date
     *            日期
     */
    public static long getEndTimeInMillis(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取某天的结束时间
     * 
     * @param date
     *            日期
     */
    public static Date getEndTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    /**
     * 获取某天的结束时间前一秒
     * <p>
     * 预留到最后一秒
     * 
     * @param date
     *            日期
     */
    public static Date getEndTimeWithLastSecond(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取某天的结束时间前N秒
     * <p>
     * 预留到最后N秒
     * 
     * @param date
     *            日期
     */
    public static Date getEndTimeWithLastSecond(Date date, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 60 - second);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取当前时间在一天中的分钟数
     * 
     * @param c
     * @return
     */
    public static int getCurrentMinutesOfDay(Calendar c) {
        //获取当前小时数
        int hour = c.get(Calendar.HOUR_OF_DAY);
        //获取当前小时的分钟数
        int minute = c.get(Calendar.MINUTE);
        return hour * 60 + minute;

    }

    /**
     * 获取当前时间在一天中的小时
     * 
     * @param c
     * @return
     */
    public static int getCurrentHourOfDay(Calendar c) {
        //获取当前小时数
        int hour = c.get(Calendar.HOUR_OF_DAY);
        return hour;
    }

    /**
     * 获取当前的小时数
     *
     * @param currentTime
     *            时间戳
     */
    public static int getHour(long currentTime) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(currentTime);
        return c.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取某天开始的时间戳
     * 
     * @param month
     * @param month
     *            0-11
     * @return
     */
    public static long getStartTimeOfDayInMillis(int year, int month, int date) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取某天结束的时间戳
     * 
     * @param month
     * @param month
     *            0-11
     * @return
     */
    public static long getEndTimeOfDayInMillis(int year, int month, int date) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取某月开始的时间戳
     * 
     * @param month
     * @param month
     *            0-11
     * @return
     */
    public static long getStartTimeOfMonthInMillis(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        return getMonthStartTime(calendar);
    }

    /**
     * 获取某月结束的时间戳
     * 
     * @param year
     * @param month
     *            0-11
     * @return
     */
    public static long getEndTimeOfMonthInMillis(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取指定个月后的时间戳
     * 
     * @param currentTime
     * @param months
     * @return
     */
    public static long getMonthsDelayTime(long currentTime, int months) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(currentTime);
        c.add(Calendar.MONTH, months);
        return c.getTimeInMillis();
    }

    /**
     * 获取指定个天后的时间戳
     * 
     * @param currentTime
     * @param days
     * @return
     */
    public static long getDaysDelayTime(long currentTime, int days) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(currentTime);
        c.add(Calendar.DAY_OF_YEAR, days);
        return c.getTimeInMillis();
    }

    /**
     * 获取指定小时后的时间戳
     * 
     * @param currentTime
     * @param hour
     * @return
     */
    public static long getHoursDelayTime(long currentTime, int hour) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(currentTime);
        c.add(Calendar.HOUR_OF_DAY, hour);
        return c.getTimeInMillis();
    }

    /**
     * 获取指定分钟后的时间戳
     * 
     * @param currentTime
     * @param mins
     * @return
     */
    public static long getMinutesDelayTime(long currentTime, int mins) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(currentTime);
        c.add(Calendar.MINUTE, mins);
        return c.getTimeInMillis();
    }

    /**
     * 获取指定时间的星期一
     * 
     * @param date
     * @return
     */
    public static long getStartTimeOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取指定时间的当月第一天
     * 
     * @param date
     * @return
     */
    public static long getStartTimeOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return getMonthStartTime(calendar);
    }

    /**
     * 获取指定时间的当月第一天
     * 
     * @param date
     * @return
     */
    public static long getStartTimeOfMonth(long currentTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(currentTime);
        return getMonthStartTime(calendar);
    }

    /**
     * 获取当前月的第一天
     * 
     * @param calendar
     * @return
     */
    public static long getMonthStartTime(Calendar calendar) {
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取某年的第一天
     *
     * @param year
     * @return
     */
    public static long getYearStartTime(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        return calendar.getTimeInMillis();
    }


    /**
     * 获取某年的最后一天
     *
     * @param year
     * @return
     */
    public static long getYearEndTime(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取一个月中指定天的开始时间戳
     * 
     * @param date
     * @param day
     * @return
     */
    public static long getDayStartTimeOfMonth(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取一个月中指定天的结束时间戳
     *
     * @param date
     * @param day
     * @return
     */
    public static long getDayEndTimeOfMonth(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));
        return calendar.getTimeInMillis();
    }

    /**
     * 获取指定时间剩余毫秒数
     * 
     * @param date
     * @return
     */
    public static long getLeftTimeMilesForDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis() - date.getTime();
    }

    /**
     * 获取指定时间+mins分钟后的整分时间
     * 
     * @param currentTime
     * @param mins
     * @return
     */
    public static long getStartTimeOfMinitue(long currentTime, int mins) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(currentTime);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        c.add(Calendar.MINUTE, mins);
        return c.getTimeInMillis();
    }

    /**
     * 获得今天的起始时间
     * 
     * @return
     */
    public static long getTodayStartTime() {
        Calendar calendar = Calendar.getInstance();
        return getDayStartTIme(calendar);
    }

    /**
     * 获取当天的起始时间
     * 
     * @param calendar
     * @return
     */
    public static long getDayStartTIme(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime().getTime();
    }

    public static long getTodayEndTime() {
        Calendar currentDate = Calendar.getInstance();
        currentDate.set(Calendar.HOUR_OF_DAY, 23);
        currentDate.set(Calendar.MINUTE, 59);
        currentDate.set(Calendar.SECOND, 59);
        currentDate.set(Calendar.MILLISECOND, 59);
        return currentDate.getTime().getTime();
    }

    /**
     * 获取今天周几
     * 
     * @param time
     * @return
     */
    public static String getWeekOfDate(long time) {
        String[] weekDays = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
        Calendar cal = Calendar.getInstance();
        Date dt = new Date(time);
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     * 获取时间戳属性
     * 
     * @param timeInMillis
     * @see {@code Calendar.Field}
     * @param field
     * @return
     */
    public static int getFieldFromMillis(long timeInMillis, int field) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);

        if (field == Calendar.MONTH) {
            return calendar.get(field) + 1;
        }
        return calendar.get(field);
    }

    /**
     * 获取当前的时间
     */
    public static long getCurrentTime() {
        if (CURRENT_TIME > 0) {
            return CURRENT_TIME;
        } else {
            return System.currentTimeMillis();
        }
    }

    /**
     * 设置当前的时间
     *
     * @param currentTime
     *            当前时间
     */
    public static void setCurrentTime(long currentTime) {
        CURRENT_TIME = currentTime;
    }

    /**
     * 获取指定时间的周一
     *
     * @param currentTime
     *            时间戳
     */
    public static String getWeekFirstDay(long currentTime) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(currentTime);
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
        return parseDateToString(c.getTime(), "yyyyMMdd");
    }

    /**
     * 获取当前周在第几天
     *
     * @param currentTime
     *            时间戳
     */
    public static int dayForWeek(long currentTime) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(currentTime);
        int dayForWeek;
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            dayForWeek = 7;
        } else {
            dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        }
        return dayForWeek;
    }

    /**
     * 查询两个时间相隔的自然天数
     * 
     * @param time1
     * @param time2
     * @return
     */
    public static long getDays(long time1, long time2) {
        long startTime = getStartTimeInMillis(time1);
        long endTime = getStartTimeInMillis(time2);
        return Math.abs(endTime - startTime) / TIME_OF_DAY;
    }

    /**
     * 代入敏感词的管控
     * 
     * @param time
     * @param regex
     * @param suffix
     * @param sensitiveStartTime
     * @param sensitiveEndTime
     * @param sensitiveDate
     * @return
     */
    public static String parseLongToStringWithSensitive(long time, String regex, String suffix,
        long sensitiveStartTime, long sensitiveEndTime, String sensitiveDate) {
        if (sensitiveEndTime == 0 && sensitiveStartTime == 0) {
            // 敏感词不管控
            return parseLongToString(time, regex + (suffix == null ? "" : suffix));
        }

        long curTime = System.currentTimeMillis();
        if (curTime < sensitiveStartTime || curTime > sensitiveEndTime) {
            // 不在敏感词管控时间范围之内
            return parseLongToString(time, regex + (suffix == null ? "" : suffix));
        }

        // 需要敏感词管控，首先判断是改为"今日"还是"明日"
        int curYear = Calendar.getInstance().get(Calendar.YEAR);
        String sensitiveDateComplete = curYear + sensitiveDate;
        long sensitiveDateLong = parseStringToLong(sensitiveDateComplete, DATEFORMAT);
        long sensitiveDateStartTime = getStartTimeInMillis(sensitiveDateLong);
        long promStartDateTime = getStartTimeInMillis(time);
        if (promStartDateTime != sensitiveDateStartTime) {
            // 活动的开始日期不敏感
            return parseLongToString(time, regex + (suffix == null ? "" : suffix));
        }

        long curStartDateTime = getTodayStartTime();

        String replaceWord = "今日H点";
        // 非敏感时间
        if (curStartDateTime < promStartDateTime) {
            replaceWord = "明日H点";
        }

        return parseLongToString(time, replaceWord + (suffix == null ? "" : suffix));
    }

    /**
     * 日期在大促期间特殊处理(大促开始时间-整点)
     *
     * @param time
     *            截止时间
     * @param dateFormat
     *            日期模板(精确到天，如yyyy.MM.dd)
     * @param bigPromTime
     *            大促开始时间
     * @return
     */
    public static String getTimeWithBigProm(long time, String dateFormat, long bigPromTime) {
        if (StringUtils.isEmpty(dateFormat)) {
            return null;
        }
        // 整点模板
        String hourFormat = dateFormat + " H点";

        String format = dateFormat;
        if (bigPromTime > 0) {
            // 以大促时间开始或结束(1s)
            if (time <= bigPromTime && (bigPromTime - time) <= TIME_OF_SECOND) {
                time = bigPromTime;
                format = hourFormat;
            }
        }
        return parseLongToString(time, format);
    }

    /**
     * 日期范围在大促期间特殊处理(大促开始时间-整点)
     *
     * @param startTime
     *            起始时间
     * @param endTime
     *            截止时间
     * @param dateFormat
     *            日期模板(精确到天，如yyyy.MM.dd)
     * @param hyphen
     *            连字符(-或至)
     * @param bigPromTime
     *            大促开始时间
     * @return
     */
    public static String getTimeRangeWithBigProm(long startTime, long endTime, String dateFormat,
        String hyphen, long bigPromTime) {
        if (StringUtils.isEmpty(dateFormat) || StringUtils.isEmpty(hyphen)) {
            return null;
        }
        // 整点模板
        String startFormat = dateFormat;
        String endFormat = dateFormat;

        if (bigPromTime > 0) {
            if (startTime == bigPromTime) {
                // 以大促时间开始
                startFormat = dateFormat + " H点";
                endFormat = dateFormat + " H点";
                // 00:00:00和23:59:59统一处理成24点
                long dayStartTime = getStartTimeInMillis(endTime);
                long dayEndTime = dayStartTime + TIME_OF_DAY;
                if (endTime == dayStartTime) {
                    endTime = endTime - TIME_OF_SECOND;
                    endFormat = dateFormat + " 24点";
                } else {
                    if (endTime < dayEndTime && (dayEndTime - endTime) <= TIME_OF_SECOND) {
                        endFormat = dateFormat + " 24点";
                    }
                }
            } else if (endTime <= bigPromTime && (bigPromTime - endTime) <= TIME_OF_SECOND) {
                // 以大促时间结束(1s)
                startFormat = dateFormat + " H点";
                endTime = bigPromTime;
                endFormat = dateFormat + " H点";
            }
        }
        StringBuilder builder = new StringBuilder();
        builder.append(parseLongToString(startTime, startFormat));
        builder.append(hyphen);
        builder.append(parseLongToString(endTime, endFormat));
        return builder.toString();
    }

    /**
     * 判断是否属于前一天创建的订单 是：返回 true 否：返回 false
     * 
     * @param createTime
     * @return
     */
    public static boolean isYestdayOrder(long createTime) {

        boolean ret = false;

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(createTime);

        Calendar current = Calendar.getInstance();

        int createDay = c.get(Calendar.DAY_OF_YEAR);
        int currentDay = current.get(Calendar.DAY_OF_YEAR);

        int createYear = c.get(Calendar.YEAR);
        int currentYear = current.get(Calendar.YEAR);

        if (createYear == currentYear && (currentDay - createDay) == 1) {
            ret = true;
        }
        return ret;
    }

    /**
     * 判断是否属于前一个月创建的订单 是：返回 true 否：返回 false
     * 
     * @param createTime
     * @return
     */
    public static boolean isLastMonth(long createTime) {

        boolean ret = false;

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(createTime);

        Calendar current = Calendar.getInstance();

        int createMonth = c.get(Calendar.MONTH);
        int currentMonth = current.get(Calendar.MONTH);

        int createYear = c.get(Calendar.YEAR);
        int currentYear = current.get(Calendar.YEAR);

        if ((createYear == currentYear && (currentMonth - createMonth) == 1)
            || ((currentYear - createYear) == 1 && currentMonth == 0 && createMonth == 11)) {
            ret = true;
        }
        return ret;
    }

    public static String formatDuring(long mss, boolean showHours, boolean showMinutes, boolean showSeconds) {
        long days = mss / (1000 * 60 * 60 * 24);
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;
        if (showSeconds) {
            return days + "天" + hours + "小时 " + minutes + "分" + seconds + "秒";
        } else if (showMinutes) {
            return days + "天" + hours + "小时 " + minutes + "分";
        } else if (showHours) {
            return days + "天" + hours + "小时 ";
        }
        return days + "天";
    }
}
