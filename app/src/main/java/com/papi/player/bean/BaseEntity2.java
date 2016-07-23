package com.papi.player.bean;

import com.google.gson.JsonArray;

import java.io.Serializable;
import java.util.List;

public class BaseEntity2 implements Serializable {

    public class Block {
        public Channel channel;
        public String code;
        public JsonArray contents;

        public List<Content> contentList;
        public String id;
        public String name;
        public String template;
    }

    public class Channel {
        private String code;
        private String id;
        private String name;
        private String template;
        private String url;

        public Channel() {
            super();
        }

        public String getCode() {
            return this.code;
        }

        public String getId() {
            return this.id;
        }

        public String getName() {
            return this.name;
        }

        public String getTemplate() {
            return this.template;
        }

        public String getUrl() {
            return this.url;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setTemplate(String template) {
            this.template = template;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public class Comment {
        private String content;
        private String time;
        private String uicon;
        private String uname;

        public Comment() {
            super();
        }

        public String getContent() {
            return this.content;
        }

        public String getTime() {
            return this.time;
        }

        public String getUicon() {
            return this.uicon;
        }

        public String getUname() {
            return this.uname;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public void setUicon(String uicon) {
            this.uicon = uicon;
        }

        public void setUname(String uname) {
            this.uname = uname;
        }
    }

    public class Content {
        public String aword;
        public String banner;
        public String channel;
        public String duration;//"5:26",
        public String focus;
        public String id;
        public String live_name;
        public String mid;
        public String name;
        public String poster;
        public String still;
        public Object tag;
        public String template;
        public String update;
        public String url;
    }

    public class Download {
        private String code;
        private String filename;
        private long filesize;
        private String fsp;
        private String http;
        private String infohash;
        private String name;

        public Download() {
            super();
        }

        public String getCode() {
            return this.code;
        }

        public String getFilename() {
            return this.filename;
        }

        public long getFilesize() {
            return this.filesize;
        }

        public String getFsp() {
            return this.fsp;
        }

        public String getHttp() {
            return this.http;
        }

        public String getInfohash() {
            return this.infohash;
        }

        public String getName() {
            return this.name;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public void setFilesize(long filesize) {
            this.filesize = filesize;
        }

        public void setFsp(String fsp) {
            this.fsp = fsp;
        }

        public void setHttp(String http) {
            this.http = http;
        }

        public void setInfohash(String infohash) {
            this.infohash = infohash;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public class Media {
        private String actor;
        private String area;
        private String aword;
        private String category;
        private String channel;
        private String director;
        private String duration;
        private String focus;
        private String id;
        private String isend;
        private String name;
        private String poster;
        private String release;
        private String score;
        private String share;
        private String still;
        private int trend;
        private String update;
        private long vv;

        public Media() {
            super();
        }

        public String getActor() {
            return this.actor;
        }

        public String getArea() {
            return this.area;
        }

        public String getAword() {
            return this.aword;
        }

        public String getCategory() {
            return this.category;
        }

        public String getChannel() {
            return this.channel;
        }

        public String getDirector() {
            return this.director;
        }

        public String getDuration() {
            return this.duration;
        }

        public String getFocus() {
            return this.focus;
        }

        public String getId() {
            return this.id;
        }

        public String getIsend() {
            return this.isend;
        }

        public String getName() {
            return this.name;
        }

        public String getPoster() {
            return this.poster;
        }

        public String getRelease() {
            return this.release;
        }

        public String getScore() {
            return this.score;
        }

        public String getShare() {
            return this.share;
        }

        public String getStill() {
            return this.still;
        }

        public int getTrend() {
            return this.trend;
        }

        public String getUpdate() {
            return this.update;
        }

        public long getVv() {
            return this.vv;
        }

        public void setActor(String actor) {
            this.actor = actor;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public void setAword(String aword) {
            this.aword = aword;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public void setDirector(String director) {
            this.director = director;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public void setFocus(String focus) {
            this.focus = focus;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setIsend(String isend) {
            this.isend = isend;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPoster(String poster) {
            this.poster = poster;
        }

        public void setRelease(String release) {
            this.release = release;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public void setShare(String share) {
            this.share = share;
        }

        public void setStill(String still) {
            this.still = still;
        }

        public void setTrend(int trend) {
            this.trend = trend;
        }

        public void setUpdate(String update) {
            this.update = update;
        }

        public void setVv(long vv) {
            this.vv = vv;
        }
    }

    public class Play {
        private String code;
        private String filename;
        private long filesize;
        private String fsp;
        private String http;
        private String infohash;
        private String name;

        public Play() {
            super();
        }

        public String getCode() {
            return this.code;
        }

        public String getFilename() {
            return this.filename;
        }

        public long getFilesize() {
            return this.filesize;
        }

        public String getFsp() {
            return this.fsp;
        }

        public String getHttp() {
            return this.http;
        }

        public String getInfohash() {
            return this.infohash;
        }

        public String getName() {
            return this.name;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public void setFilesize(long filesize) {
            this.filesize = filesize;
        }

        public void setFsp(String fsp) {
            this.fsp = fsp;
        }

        public void setHttp(String http) {
            this.http = http;
        }

        public void setInfohash(String infohash) {
            this.infohash = infohash;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public class Topic {
        private String aword;
        private String brief;
        private String focus;
        private String id;
        private String name;
        private String poster;
        private String still;

        public Topic() {
            super();
        }

        public String getAword() {
            return this.aword;
        }

        public String getBrief() {
            return this.brief;
        }

        public String getFocus() {
            return this.focus;
        }

        public String getId() {
            return this.id;
        }

        public String getName() {
            return this.name;
        }

        public String getPoster() {
            return this.poster;
        }

        public String getStill() {
            return this.still;
        }

        public void setAword(String aword) {
            this.aword = aword;
        }

        public void setBrief(String brief) {
            this.brief = brief;
        }

        public void setFocus(String focus) {
            this.focus = focus;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPoster(String poster) {
            this.poster = poster;
        }

        public void setStill(String still) {
            this.still = still;
        }
    }

    private String retcode;
    private String retmsg;

    public String getRetcode() {
        return this.retcode;
    }

    public String getRetmsg() {
        return this.retmsg;
    }

    public boolean isOK() {
        boolean isOk = true;
        if (this.retcode != null && !this.retcode.equals("200")) {
            isOk = false;
        }

        return isOk;
    }

    public void setRetcode(String retcode) {
        this.retcode = retcode;
    }

    public void setRetmsg(String retmsg) {
        this.retmsg = retmsg;
    }
}

