package com.papi.player.bean;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.papi.player.util.log.ILog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Author   Shone
 * Date     02/07/16.
 * Github   https://github.com/shonegg
 */


public class SubTitlesEntity {

    public JsonArray subtitles;

    public class Subtitle implements Serializable {
        public String id;
        public String name;
        public static final long serialVersionUID = 1508784826905146347L;
        public String template;
    }

    public List<Subtitle> subtitleList;

    public static SubTitlesEntity createFromJson(String jsonStr) {
        SubTitlesEntity result = null;
        try {
            result = new Gson().fromJson(jsonStr, SubTitlesEntity.class);
        } catch (Exception e) {
            ILog.e(ILog.TAG_DEFAULT, "" + e.toString());
        }
        if (result == null) {
            return null;
        }
        Iterator<JsonElement> iterator = result.subtitles.iterator();
        if (result.subtitleList == null) {
            result.subtitleList = new ArrayList<>();
        }
        while (iterator.hasNext()) {
            JsonElement element = iterator.next();
            try {
                result.subtitleList.add(new Gson().fromJson(element, Subtitle.class));
            } catch (Exception e) {
                // Just ignore it.
                e.printStackTrace();
            }
        }
        result.subtitles = null;
        return result;
    }
}
