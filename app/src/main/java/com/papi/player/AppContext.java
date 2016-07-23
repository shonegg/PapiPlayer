package com.papi.player;

import android.app.Application;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.multidex.MultiDex;
import android.telephony.TelephonyManager;

import com.papi.player.dao.DatabaseLoader;
import com.papi.player.init.Udid;
import com.papi.player.util.SimpleImageLoader;

import java.util.Locale;


public class AppContext extends Application {
    public static final boolean IS_DEBUG = true;
    private static Context mContext;
    private static Thread mMainThread;
    private static Handler mMainHandler;

    public static Thread getMainThread() {
        return mMainThread;
    }

    public static Handler getMainHandler() {
        return mMainHandler;
    }

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        mMainThread = Thread.currentThread();
        mMainHandler = new Handler();
        initConfig();

        DatabaseLoader.init(mContext);
        initUid(this);
    }

    public static void initUid(Context context) {
        Udid.getInstance().init(context, new Udid.Args(2168,
                getLocalMacAddress(context), getDeviceID(context), getAndroidID(
                context)));
    }

    private void initConfig() {
        /* 注册crashHandler,保存日志但不上传到服务器 */
//        CrashHandler.getInstance().init(this);
        SimpleImageLoader.checkImageLoaderConfiguration(this);
        if (IS_DEBUG && Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static String getLocalMacAddress(Context context) {
        try {
            String macAddress1 = getMacAddress1(context);
            if (macAddress1 == null) {
                return "000000000000";
            }
            return macAddress1.replace(":", "").toLowerCase(Locale.getDefault());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "000000000000";
    }

    public static String getMacAddress1(Context context) {
        try {
            WifiInfo wifiInfo = getWifiInfo(context);
            if (wifiInfo == null) {
                return "00:00:00:00:00:00";
            }

            String macAddress = wifiInfo.getMacAddress();
            if (macAddress == null) {
                return "00:00:00:00:00:00";
            }
            return macAddress.toLowerCase(Locale.getDefault());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "00:00:00:00:00:00";
    }

    public static WifiInfo getWifiInfo(Context context) {
        WifiInfo wifiInfo = null;
        WifiManager manager = (WifiManager) context.getSystemService("wifi");
        if (manager != null) {
            wifiInfo = manager.getConnectionInfo();
        }
        return wifiInfo;
    }

    public static String getDeviceID(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        return telephonyManager != null ? telephonyManager.getDeviceId() : null;
    }

    public static String getAndroidID(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), "android_id");
    }
}

