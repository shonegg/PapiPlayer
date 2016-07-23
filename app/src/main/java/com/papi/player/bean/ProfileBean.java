package com.papi.player.bean;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.papi.player.util.log.ILog;
/**
 * author   Shone
 * date     13/06/16.
 * github   https://github.com/shonegg
 */
public class ProfileBean {

    public String retcode;// "200",
    public String retmsg;// "ok",
    public String id;
    public String name;
    public String poster;
    public String still;
    public String director;
    public String actor;
    public String aword;
    public String description;
    public String isend;// "0",
    public String score;// "7.1",
    public String vv;
    public String release;
    public String update;
    public String isvip;// "0",
    public String pay;// "0",
    public JsonObject extend;

    public ExtendInfo extendInfo;
    public String area;// "内地",
    public String category;// "历史,战争",
    public String channel;// "电视剧",
    public String channel_id;
    public String isdisabled;// "0",
    public String share;

    public class ExtendInfo {
        public String totalnum;
    }

    public static ProfileBean createFromJson(String jsonStr) {
        ProfileBean result = null;
        try {
            result = new Gson().fromJson(jsonStr, ProfileBean.class);
        } catch (Exception e) {
            ILog.e(ILog.TAG_DEFAULT, "" + e.toString());
        }
        if (result == null) {
            return null;
        }

        if (result.extend != null) {
            ExtendInfo info = new Gson().fromJson(result.extend, ExtendInfo.class);
            result.extendInfo = info;
        }
        result.extend = null;
        return result;
    }


}
