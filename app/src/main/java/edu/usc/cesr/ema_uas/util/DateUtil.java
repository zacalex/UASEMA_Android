package edu.usc.cesr.ema_uas.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtil {
    public static String stringifyAll(Calendar calendar){
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return (calendar == null) ? "null" : format.format(calendar.getTime());
    }
    public static String stringifyTime(Calendar calendar){
        SimpleDateFormat format = new SimpleDateFormat("kk:mm");
        return (calendar == null) ? "null" : format.format(calendar.getTime());
    }
    public static String stringifyDate(Calendar calendar){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return (calendar == null) ? "null" : format.format(calendar.getTime());
    }
    public static int intDate(Calendar calendar){
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmm");
        return (calendar == null) ? -1 : Integer.parseInt(format.format(calendar.getTime()));
    }

}
