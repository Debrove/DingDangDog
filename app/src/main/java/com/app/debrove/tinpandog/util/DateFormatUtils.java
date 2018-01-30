package com.app.debrove.tinpandog.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by debrove on 2017/11/1.
 * Package Name : com.app.debrove.tinpandog.data
 */

public class DateFormatUtils {

    public static String formatNewsDateLongToStringWithoutZero(long date) {
        String sDate;
        Date d = new Date(date);
        SimpleDateFormat format = new SimpleDateFormat("y-M-d");
        sDate = format.format(d);

        return sDate;
    }

    public static long formatNewsTimeStringToLong(String date) {
        Date d = null;
        try {
            d = new SimpleDateFormat("HH:mm").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d == null ? 0 : d.getTime();
    }

    public static String formatNewsTimeLongToString(long date) {
        String sDate;
        Date d = new Date(date);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        sDate = format.format(d);

        return sDate;
    }

    public static String formatNewsDateLongToString(long date) {
        String sDate;
        Date d = new Date(date);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        sDate = format.format(d);

        return sDate;
    }

    public static long formatNewsDateStringToLong(String date) {
        Date d = null;
        try {
            d = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d == null ? 0 : d.getTime();
    }

    public static long formatSystemDateStringToLong(String date) {
        Date d = null;
        try {
            d = new SimpleDateFormat("EEE MMM dd hh:mm:ss Z yyyy",Locale.ENGLISH).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d == null ? 0 : d.getTime();
    }


    public static String getYear(long date) {
        String sDate;
        Date d = new Date(date);
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        sDate = format.format(d);

        return sDate;
    }

    public static String getMonth(long date) {
        String sDate;
        Date d = new Date(date);
        SimpleDateFormat format = new SimpleDateFormat("MM");
        sDate = format.format(d);

        return sDate;
    }

    public static String getDay(long date) {
        String sDate;
        Date d = new Date(date);
        SimpleDateFormat format = new SimpleDateFormat("dd");
        sDate = format.format(d);

        return sDate;
    }

    public static String getTime(long date) {
        String sDate;
        Date d = new Date(date);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        sDate = format.format(d);

        return sDate;
    }
}
