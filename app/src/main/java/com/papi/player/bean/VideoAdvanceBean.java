package com.papi.player.bean;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.papi.player.util.log.ILog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * author   Shone
 * date     13/06/16.
 * github   https://github.com/shonegg
 */
public class VideoAdvanceBean {

    public String retcode;// "200",
    public String retmsg;// "ok",
    public String media;
    public JsonArray videos;

    public List<VideoAdvInfo> videosList;
    public JsonArray prevues;

    public List<VideoAdvInfo> prevuesList;

    public class VideoAdvInfo {
        public String id;
        public String name;
        public String poster;
        public String still;
        public String release;
        public String aword;
        public String duration;//1:28",
        public String vv;
        public String share;

    }

    public static VideoAdvanceBean createFromJson(String jsonStr) {
        VideoAdvanceBean result = null;
        try {
            result = new Gson().fromJson(jsonStr, VideoAdvanceBean.class);
        } catch (Exception e) {
            ILog.e(ILog.TAG_DEFAULT, "" + e.toString());
        }
        if (result == null) {
            return null;
        }

        Iterator<JsonElement> iterator = result.videos.iterator();
        if (result.videosList == null) {
            result.videosList = new ArrayList<>();
        }
        while (iterator.hasNext()) {
            JsonElement element = iterator.next();
            try {
                result.videosList.add(new Gson().fromJson(element, VideoAdvInfo.class));
            } catch (Exception e) {
                // Just ignore it.
                e.printStackTrace();
            }
        }
        result.videos = null;


        Iterator<JsonElement> iterator1 = result.prevues.iterator();
        if (result.prevuesList == null) {
            result.prevuesList = new ArrayList<>();
        }
        while (iterator1.hasNext()) {
            JsonElement element = iterator1.next();
            try {
                result.prevuesList.add(new Gson().fromJson(element, VideoAdvInfo.class));
            } catch (Exception e) {
                // Just ignore it.
                e.printStackTrace();
            }
        }
        result.prevues = null;

        return result;
    }

}
