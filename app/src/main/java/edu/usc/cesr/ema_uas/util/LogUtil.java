package edu.usc.cesr.ema_uas.util;

import android.util.Log;

public class LogUtil {

    public static void e(String tag, String content) {
        if (content.length() > 4000) {
            Log.e(tag, content.substring(0, 4000));
            e(tag, content.substring(4000));
        } else {
            Log.e(tag, content);
        }
    }
}
