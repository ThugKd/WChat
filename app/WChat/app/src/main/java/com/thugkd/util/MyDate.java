package com.thugkd.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by thugkd on 06/04/2017.
 */

public class MyDate {
    public static String getDateCN() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日  HH:mm:ss");
        String date = format.format(new Date(System.currentTimeMillis()));
        return date;// 2012骞?10鏈?03鏃? 23:41:31
    }

    public static String getDateEN() {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1 = format1.format(new Date(System.currentTimeMillis()));
        return date1;// 2012-10-03 23:41:31
    }

    public static String getDate() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        String date = format.format(new Date(System.currentTimeMillis()));
        return date;
    }
}
