package com.yusong.yslib.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ruanjian on 2017/3/11.
 */

public class DateTimeUtil {

    public static String getDate() {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//可以方便地修改日期格式
        return dateFormat.format(now);
    }

    public  static String getHidatWeek(String time) {
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sf.parse(time);
            SimpleDateFormat sf1 = new SimpleDateFormat("MM-dd");
            return sf1.format(date);
        } catch (Exception e) {
        }
        return null;
    }
    public static String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }
    public static String getLeaveDate(String time) {
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sf.parse(time);
            SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sf1.format(date);
        } catch (Exception e) {
        }
        return null;
    }

    public static String getHi(String timeNew) {
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sf.parse(timeNew);
            SimpleDateFormat sf1 = new SimpleDateFormat("MM-dd");
            return sf1.format(date);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 获取给定时间所在月的第一天Ｆ的日期和最后一天的日期
     *
     * @param
     * @return Date数组，[0]为第一天的日期，[1]最后一天的日期
     */
    public static String[] getMonthStartAndEndDate() {
        Calendar calendar = Calendar.getInstance();
        Date[] dates = new Date[2];
        String[] strings = new String[2];
        Date firstDateOfMonth, lastDateOfMonth;
        // 得到当天是这月的第几天
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        // 减去dayOfMonth,得到第一天的日期，因为Calendar用０代表每月的第一天，所以要减一
        calendar.add(Calendar.DAY_OF_MONTH, -(dayOfMonth - 1));
        firstDateOfMonth = calendar.getTime();
        // calendar.getActualMaximum(Calendar.DAY_OF_MONTH)得到这个月有几天
        calendar.add(Calendar.DAY_OF_MONTH, calendar
                .getActualMaximum(Calendar.DAY_OF_MONTH) - 1);
        lastDateOfMonth = calendar.getTime();

        dates[0] = firstDateOfMonth;
        dates[1] = lastDateOfMonth;
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        strings[0] = sf.format(firstDateOfMonth);
        strings[1] = sf.format(lastDateOfMonth);
        return strings;
    }

    public static String[] getMonthStartAndEndDateNew(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);

        Date[] dates = new Date[2];
        String[] strings = new String[2];
        Date firstDateOfMonth, lastDateOfMonth;
        // 得到当天是这月的第几天
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        // 减去dayOfMonth,得到第一天的日期，因为Calendar用０代表每月的第一天，所以要减一
        calendar.add(Calendar.DAY_OF_MONTH, -(dayOfMonth - 1));
        firstDateOfMonth = calendar.getTime();
        // calendar.getActualMaximum(Calendar.DAY_OF_MONTH)得到这个月有几天
        calendar.add(Calendar.DAY_OF_MONTH, calendar
                .getActualMaximum(Calendar.DAY_OF_MONTH) - 1);
        lastDateOfMonth = calendar.getTime();

        dates[0] = firstDateOfMonth;
        dates[1] = lastDateOfMonth;
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        strings[0] = sf.format(firstDateOfMonth);
        strings[1] = sf.format(lastDateOfMonth);
        return strings;
    }


    public static String getFormateDate(String time) {
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sf.parse(time);
            SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd");
            return sf1.format(date);
        } catch (Exception e) {
        }
        return null;
    }

    public static String getHidate(String time) {
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sf.parse(time);
            SimpleDateFormat sf1 = new SimpleDateFormat("yyyy年MM月dd日");
            return sf1.format(date);
        } catch (Exception e) {
        }
        return null;
    }

    public static String getHidatenew(String time) {
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sf.parse(time);
            SimpleDateFormat sf1 = new SimpleDateFormat("yyyy年MM月dd日");
            return sf1.format(date);
        } catch (Exception e) {
        }
        return null;
    }

    public static String getLastDate(String tiems) {
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sf.parse(tiems);
            Date as = new Date(date.getTime() - 24 * 60 * 60 * 1000);
            SimpleDateFormat matter1 = new SimpleDateFormat("yyyy-MM-dd");
            return matter1.format(as);
        } catch (Exception e) {
        }
        return null;
    }

    public static String getWorkDay(int a) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 设置时间格式
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        System.out.println("要计算日期为:" + sdf.format(cal.getTime())); // 输出要计算日期
        cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        int day = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, a);
        String imptimeMi = sdf.format(cal.getTime());
        return imptimeMi;
    }


    //
    public static String getDateYearNewToString(String time) {
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sf.parse(time);
            SimpleDateFormat sf1 = new SimpleDateFormat("MM月d日");
            return sf1.format(date);
        } catch (Exception e) {
        }
        return null;
    }

    public static String getMinusToString(String time) {
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date endDate = sf.parse(time);
            Date dates = sf.parse(df.format(new Date()));
            long diff = endDate.getTime() - dates.getTime();//这样得到的差值是微秒级别
            long days = diff / (1000 * 60 * 60 * 24);
            long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
            return "" + days + "天" + hours + "小时" + minutes + "分";
        } catch (Exception e) {
        }

        return null;
    }

    public static String getMinusToStringNew(String timeOne, String timeTwo) {
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date startDate = sf.parse(timeOne);
            Date endDate = sf.parse(timeTwo);
            long diff = endDate.getTime() - startDate.getTime();//这样得到的差值是微秒级别
            long days = diff / (1000 * 60 * 60 * 24);
            long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
            return "" + days + "天";
        } catch (Exception e) {
        }

        return null;
    }

    /**
     * 比较两个日期之间的大小
     *
     * @param time1
     * @param time2
     * @return 前者大于后者返回true 反之false
     */
    public static boolean compareDate(String time1, String time2) {
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Date d1 = sf.parse(time1);
            Date d2 = sf.parse(time2);
            Calendar c1 = Calendar.getInstance();
            Calendar c2 = Calendar.getInstance();
            c1.setTime(d1);
            c2.setTime(d2);
            int result = c1.compareTo(c2);
            return result >= 0;
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 格式化时间戳
     *
     * @param time
     * @param formatType
     * @return
     */
    public static String getFormateDate(long time, String formatType) {
        try {
            SimpleDateFormat sf1 = new SimpleDateFormat(formatType);
            return sf1.format(new Date(time));
        } catch (Exception e) {
        }
        return "";
    }

}
