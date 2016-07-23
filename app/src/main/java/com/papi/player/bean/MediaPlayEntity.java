package com.papi.player.bean;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.papi.player.play.Play;
import com.papi.player.util.log.ILog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Author   Shone
 * Date     04/07/16.
 * Github   https://github.com/shonegg
 */
//电视剧-媒体类
public class MediaPlayEntity extends BaseEntity {
    private static final long serialVersionUID = 7632566046631709672L;

    private String episode;
    private JsonArray mp4;
    private List<Play> mp4List;
    private String selected;

    public JsonArray getMp4() {
        return mp4;
    }

    public void setMp4(JsonArray mp4) {
        this.mp4 = mp4;
    }

    public List<Play> getMp4List() {
        return mp4List;
    }

    public void setMp4List(List<Play> mp4List) {
        this.mp4List = mp4List;
    }

    public String getEpisode() {
        return this.episode;
    }

    public String getSelected() {
        return this.selected;
    }

    public static long getSerialversionuid() {
        return 7632566046631709672L;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public static MediaPlayEntity createFromJson(String jsonStr) {
        MediaPlayEntity result = null;
        try {
            result = new Gson().fromJson(jsonStr, MediaPlayEntity.class);
        } catch (Exception e) {
            ILog.e(ILog.TAG_DEFAULT, "" + e.toString());
        }
        if (result == null) {
            return null;
        }

        Iterator<JsonElement> iterator = result.mp4.iterator();
        if (result.mp4List == null) {
            result.mp4List = new ArrayList<>();
        }
        while (iterator.hasNext()) {
            JsonElement element = iterator.next();
            try {
                result.mp4List.add(new Gson().fromJson(element, Play.class));
            } catch (Exception e) {
                // Just ignore it.
                e.printStackTrace();
            }
        }
        result.mp4 = null;
        return result;
    }

    @Override
    public String toString() {
        String myString = episode + "\n" +
                          selected + "\n" + mp4.toString();
        return myString;
    }
}