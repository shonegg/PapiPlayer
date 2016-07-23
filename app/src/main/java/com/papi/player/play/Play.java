package com.papi.player.play;

/**
 * Author   Shone
 * Date     04/07/16.
 * Github   https://github.com/shonegg
 */
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


