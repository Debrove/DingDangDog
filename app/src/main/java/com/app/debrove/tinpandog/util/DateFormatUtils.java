package com.app.debrove.tinpandog.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by debrove on 2017/11/1.
 * Package Name : com.app.debrove.tinpandog.data
 */

public class DateFormatUtils {

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
}
