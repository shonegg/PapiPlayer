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
 * Date     03/07/16.
 * Github   https://github.com/shonegg
 */
public class VideoRankEntity extends BaseEntity2 {

    private JsonArray videos;

    public JsonArray getVideos() {
        return videos;
    }

    public void setVideos(JsonArray videos) {
        this.videos = videos;
    }

    public List<Content> getContentList() {
        return contentList;
    }

    public void setContentList(List<Content> contentList) {
        this.contentList = contentList;
    }

    private List<Content> contentList;

    public static VideoRankEntity createFromJson(String jsonStr) {
        VideoRankEntity result = null;
        try {
            result = new Gson().fromJson(jsonStr, VideoRankEntity.class);
        } catch (Exception e) {
            ILog.e(ILog.TAG_DEFAULT, "" + e.toString());
        }
        if (result == null) {
            return null;
        }
        Iterator<JsonElement> iterator = result.videos.iterator();
        if (result.contentList == null) {
            result.contentList = new ArrayList<>();
        }
        while (iterator.hasNext()) {
            JsonElement element = iterator.next();
            try {
                result.contentList.add(new Gson().fromJson(element, Content.class));
            } catch (Exception e) {
                // Just ignore it.
                e.printStackTrace();
            }
        }
        result.videos = null;
        return result;
    }
}
