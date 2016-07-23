package com.papi.player.localvideo;

/**
 * Author   Shone
 * Date     04/07/16.
 * Github   https://github.com/shonegg
 */

import com.papi.player.BuildConfig;

import java.util.MissingFormatArgumentException;


public class Log {
    public static final String TAG = "[Player]";

    public static void i(String msg, Object... args) {
        try {
            if (BuildConfig.DEBUG)
                android.util.Log.i(TAG, String.format(msg, args));
        } catch (MissingFormatArgumentException e) {
            android.util.Log.i(TAG, msg);
        }
    }

    public static void d(String msg, Object... args) {
        try {
            if (BuildConfig.DEBUG)
                android.util.Log.d(TAG, String.format(msg, args));
        } catch (MissingFormatArgumentException e) {
            android.util.Log.d(TAG, msg);
        }
    }

    public static void e(String msg, Object... args) {
        try {
            android.util.Log.e(TAG, String.format(msg, args));
        } catch (MissingFormatArgumentException e) {
            android.util.Log.e(TAG, msg);
        }
    }

    public static void e(String msg, Throwable t) {
        android.util.Log.e(TAG, msg, t);
    }
}