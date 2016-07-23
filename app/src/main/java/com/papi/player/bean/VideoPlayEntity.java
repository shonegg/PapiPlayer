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
public class VideoPlayEntity {

    public String retcode;// "200",
    public String retmsg;// "ok",
    public String video;
    public String name;
    public String selected;
    public JsonArray mp4;

    public List<Mp4Bean> mp4BeanList;
    public class Mp4Bean {
        public String infohash;
        public String filename;
        public String filesize;
        public String http;
        public String code;// "tv",
        public String name;// "流畅"
    }

    public static VideoPlayEntity createFromJson(String jsonStr) {
        VideoPlayEntity result = null;
        try {
            result = new Gson().fromJson(jsonStr, VideoPlayEntity.class);
        } catch (Exception e) {
            ILog.e(ILog.TAG_DEFAULT, "" + e.toString());
        }
        if (result == null) {
            return null;
        }

        Iterator<JsonElement> iterator = result.mp4.iterator();
        if (result.mp4BeanList == null) {
            result.mp4BeanList = new ArrayList<>();
        }
        while (iterator.hasNext()) {
            JsonElement element = iterator.next();
            try {
                result.mp4BeanList.add(new Gson().fromJson(element, Mp4Bean.class));
            } catch (Exception e) {
                // Just ignore it.
                e.printStackTrace();
            }
        }
        result.mp4 = null;
        return result;
    }

}
