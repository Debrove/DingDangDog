package com.app.debrove.tinpandog.util;

import android.util.Log;

/**
 * Created by debrove on 2017/8/17.
 * Package Name : com.app.debrove.tinpandog.util
 * <p>
 * Log封装类
 */

public class L {
    //开关
    public static final boolean DEBUG = true;
    //TAG
//    public static final String TAG = "TinPanDog";

    //四个等级  DIWE

    public static void d(String TAG, String text) {
        if (DEBUG) {
            Log.d(TAG, text);
        }
    }

    public static void i(String TAG, String text) {
        if (DEBUG) {
            Log.i(TAG, text);
        }
    }

    public static void w(String TAG, String text) {
        if (DEBUG) {
            Log.w(TAG, text);
        }
    }

    public static void e(String TAG, String text) {
        if (DEBUG) {
            Log.e(TAG, text);
        }
    }
}
