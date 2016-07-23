package com.papi.player.bean;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.papi.player.util.log.ILog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Author   Shone
 * Date     23/06/16.
 * Github   https://github.com/shonegg
 */
public class TopicBean {
    public String retcode;//: "200";
    public String retmsg;//: "ok",
    public String id;
    public String name;
    public String focus;
    public String poster;
    public String still;
    public String aword;
    public String brief;
    public String total;
    public JsonArray videos;

    public List<VideoInfo> videoInfoList;

    public class VideoInfo {
        public String id;
        public String name;
        public String poster;
        public String still;
        public String release;
        public String aword;//"
        public String duration;//1:09",
        public String vv;
        public String template;
        public String share;
    }

    public static TopicBean createFromJson(String jsonStr) {
        TopicBean result = null;
        try {
            result = new Gson().fromJson(jsonStr, TopicBean.class);
        } catch (Exception e) {
            ILog.e(ILog.TAG_DEFAULT, "" + e.toString());
        }
        if (result == null) {
            return null;
        }
        Iterator<JsonElement> iterator = result.videos.iterator();
        if (result.videoInfoList == null) {
            result.videoInfoList = new ArrayList<>();
        }
        while (iterator.hasNext()) {
            JsonElement element = iterator.next();
            try {
                result.videoInfoList.add(new Gson().fromJson(element, VideoInfo.class));
            } catch (Exception e) {
                // Just ignore it.
                e.printStackTrace();
            }
        }
        result.videos = null;
        return result;
    }

}
