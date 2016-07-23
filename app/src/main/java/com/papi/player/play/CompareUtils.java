package com.papi.player.play;

import android.text.TextUtils;

import com.papi.player.util.log.ILog;

/**
 * Author   Shone
 * Date     07/07/16.
 * Github   https://github.com/shonegg
 */
public class CompareUtils {

    public static boolean compare(String str1, String str2) {
        if (TextUtils.isEmpty(str1) || TextUtils.isEmpty(str2)) {
            return false;
        }
        String name1 = getName(str1);
        String name2 = getName(str2);

        ILog.e(ILog.TAG_DEFAULT, "-------------------------" + "\n");
        ILog.e(ILog.TAG_DEFAULT, "" + name1 + "\n" + name2);
        if (!TextUtils.isEmpty(name1) && !TextUtils.isEmpty(name2)) {
            if (name1.equals(name2)) {
                return true;
            }
        }
        return false;
    }

    public static boolean compare1(String str1, String request) {
        if (TextUtils.isEmpty(str1) || TextUtils.isEmpty(request)) {
            return false;
        }
        String name1 = getName(str1);
        String name2 = getName1(request);

        ILog.e(ILog.TAG_DEFAULT, "-------------------------" + "\n");
        ILog.e(ILog.TAG_DEFAULT, "" + name1 + "\n" + name2);
        if (!TextUtils.isEmpty(name1) && !TextUtils.isEmpty(name2)) {
            if (name1.equals(name2)) {
                return true;
            }
        }
        return false;
    }

    private static String getName(String url) {
        int index = url.lastIndexOf("/");
        String str = url.substring(index + 1, url.length());
        int index1 = str.lastIndexOf(".");
        return str.substring(0, index1);
    }

    private static String getName1(String url) {
        int index = url.lastIndexOf("?");
        String str = url.substring(index + 1, url.length());
        str = str.replaceAll("file=", "");
        int index1 = str.lastIndexOf(".");
        return str.substring(0, index1);
    }

}
