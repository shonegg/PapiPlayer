package com.papi.player.bean;

import java.io.Serializable;
import java.util.List;

public class BaseEntity implements Serializable {
    public class Channel {
        private String code;
        private String id;
        private String name;
        private String template;
        private String url;

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

    public class Download {
        private String code;
        private String filename;
        private long filesize;
        private String fsp;
        private String http;
        private String infohash;
        private String name;


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

    public class Filter {
        private String code;
        private String name;
        private List options;
        private String selected;

        public String getCode() {
            return this.code;
        }

        public String getName() {
            return this.name;
        }

        public List getOptions() {
            return this.options;
        }

        public String getSelected() {
            return this.selected;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setOptions(List arg1) {
            this.options = arg1;
        }

        public void setSelected(String selected) {
            this.selected = selected;
        }
    }

    public class FilterOpt {
        private String id;
        private String name;


        public String getId() {
            return this.id;
        }

        public String getName() {
            return this.name;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public class Order {
        private String code;
        private String name;
        private List options;
        private String selected;

        public String getCode() {
            return this.code;
        }

        public String getName() {
            return this.name;
        }

        public List getOptions() {
            return this.options;
        }

        public String getSelected() {
            return this.selected;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setOptions(List arg1) {
            this.options = arg1;
        }

        public void setSelected(String selected) {
            this.selected = selected;
        }
    }

    public class OrderOpt {
        private String id;
        private String name;

        public String getId() {
            return this.id;
        }

        public String getName() {
            return this.name;
        }

        public void setId(String id) {
            this.id = id;
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
    private static final long serialVersionUID = -3707497022515767800L;

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

