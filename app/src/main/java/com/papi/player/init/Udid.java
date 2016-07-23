package com.papi.player.init;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class Udid {
    public static class Args {
        private int channelID;
        private String strAndroidID;
        private String strDeviceID;
        private String strMac;

        public Args(int channelID, String strMac, String strDeviceID, String strAndroidID) {
            super();
            this.channelID = channelID;
            this.strMac = strMac;
            this.strDeviceID = strDeviceID;
            this.strAndroidID = strAndroidID;
        }

        public int getChannelID() {
            return this.channelID;
        }

        public String getStrAndroidID() {
            return this.strAndroidID;
        }

        public String getStrDeviceID() {
            return this.strDeviceID;
        }

        public String getStrMac() {
            return this.strMac;
        }

    }

    private final String PERSIST_UDID_FILE = ".fudid";
    private final String PERSIST_UDID_KEY = "fudid";
    private static final String PREF_FUDID = "fudid";
    private static Udid instance;
    private SharedPreferences sp;
    private String strUDID;


//    private String generate(short appType, int channelID, String strMac, String strDeviceID, String
//            strAndroidID) {
//        return fudid.create(appType, channelID, strMac, strDeviceID, strAndroidID);
//    }

    public String get() {
        return this.strUDID != null ? this.strUDID : "";
    }

    public static Udid getInstance() {
        if (instance == null) {
            instance = new Udid();
        }
        return instance;
    }

    @SuppressLint(value = {"InlinedApi"})
    public void init(Context context, Args args) {
//        this.strUDID = this.generate(args.getType().getType(), args.getChannelID(), args.getStrMac(),
//                args.getStrDeviceID(), args.getStrAndroidID());
    }

}

