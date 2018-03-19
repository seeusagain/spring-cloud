package com.java.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by lu.xu on 2017/7/22.
 * TODO:日期操作类
 */
public class DateUtils {

    /**
     * 获取今天开始时间
     *
     * @return xxxx-xx-xx 00:00:00
     */
    public static Date getDayStart() {
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取当天结束时间
     *
     * @return xxxx-xx-xx 23:59:59
     */
    public static Date getDayEnd() {
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    /**
     * 获取当月开始时间
     *
     * @return xxxx-xx-01 00:00:00
     */
    public static Date getMonthStart() {
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取当月最后时间
     *
     * @return xxxx-xx-lastDay 23:59:59
     */
    public static Date getMonthEnd() {
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    /**
     * 将timestamp字符串转换为 yyyy-MM-dd HH:mm:ss类型的日期格式
     *
     * @param timestamp 时间戳字符串
     * @return yyyy-MM-dd HH:mm:ss类型的日期格式
     */
    public static String pareTimestamp(String timestamp) {
        if (EmptyUtils.isEmpty(timestamp)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (timestamp.length() == 10) {
            return sdf.format(new Date(Long.valueOf(timestamp + "000")));
        } else if (timestamp.length() == 13) {
            return sdf.format(new Date(Long.valueOf(timestamp)));
        } else {
            return null;
        }
    }

}
