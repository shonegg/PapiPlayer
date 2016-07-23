package com.papi.player.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.papi.player.greendao.DaoMaster;
import com.papi.player.greendao.DaoSession;

/**
 * Author   Shone
 * Date     05/07/16.
 * Github   https://github.com/shonegg
 */
public class DatabaseLoader {
    private static final String DATABASE_NAME = "papi-db";
    private static DaoSession daoSession;

    // 虚方法，可以无实体内容
    public static void init(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, DATABASE_NAME, null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }
}
