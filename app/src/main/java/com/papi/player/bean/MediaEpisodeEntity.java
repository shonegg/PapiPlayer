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
 * Date     04/07/16.
 * Github   https://github.com/shonegg
 */
public class MediaEpisodeEntity extends BaseEntity {
    public class Definition {
        private String code;//"dvd",
        private String name;//标清"

        public String getCode() {
            return this.code;
        }

        public String getName() {
            return this.name;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public enum EpisodeDownloadState {
        stateless, downloaded, selected;
    }

    public class Episode {
        private String id;
        private String name;
        private String num;
        private boolean preview;
        private EpisodeDownloadState state = EpisodeDownloadState.stateless;

        public String getId() {
            return this.id;
        }

        public String getName() {
            return this.name;
        }

        public String getNum() {
            return this.num;
        }

        public EpisodeDownloadState getState() {
            return this.state;
        }

        public boolean isPreview() {
            return this.preview;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public void setPreview(boolean preview) {
            this.preview = preview;
        }

        public void setState(EpisodeDownloadState state) {
            this.state = state;
        }
    }

    private JsonArray definition;
    private JsonArray episodes;
    private String media;
    private String name;
    private static final long serialVersionUID = -4323734551927157884L;
    private String template;
    private int total;
    private List<Definition> definitionList;
    private List<Episode> episodeList;

    public List<Definition> getDefinitionList() {
        return definitionList;
    }

    public void setDefinitionList(List<Definition> definitionList) {
        this.definitionList = definitionList;
    }

    public List<Episode> getEpisodeList() {
        return episodeList;
    }

    public void setEpisodeList(List<Episode> episodeList) {
        this.episodeList = episodeList;
    }

    public JsonArray getDefinition() {
        return definition;
    }

    public void setDefinition(JsonArray definition) {
        this.definition = definition;
    }

    public JsonArray getEpisodes() {
        return episodes;
    }

    public void setEpisodes(JsonArray episodes) {
        this.episodes = episodes;
    }

    public String getMedia() {
        return this.media;
    }

    public String getName() {
        return this.name;
    }

    public String getTemplate() {
        return this.template;
    }

    public int getTotal() {
        return this.total;
    }


    public void setMedia(String media) {
        this.media = media;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public static MediaEpisodeEntity createFromJson(String jsonStr) {
        MediaEpisodeEntity result = null;
        try {
            result = new Gson().fromJson(jsonStr, MediaEpisodeEntity.class);
        }catch (Exception e){
            ILog.e(ILog.TAG_DEFAULT, "" + e.toString());
        }
        if (result == null){
            return null;
        }

        Iterator<JsonElement> iterator = result.definition.iterator();
        if (result.definitionList == null) {
            result.definitionList = new ArrayList<>();
        }
        while (iterator.hasNext()) {
            JsonElement element = iterator.next();
            try {
                result.definitionList.add(new Gson().fromJson(element, Definition.class));
            } catch (Exception e) {
                // Just ignore it.
                e.printStackTrace();
            }
        }
        result.definition = null;


        Iterator<JsonElement> iterator1 = result.episodes.iterator();
        if (result.episodeList == null){
            result.episodeList = new ArrayList<>();
        }
        while (iterator1.hasNext()) {
            JsonElement element = iterator1.next();
            try {
                result.episodeList.add(new Gson().fromJson(element, Episode.class));
            } catch (Exception e) {
                // Just ignore it.
                e.printStackTrace();
            }
        }
        result.episodes = null;
        return result;
    }
}