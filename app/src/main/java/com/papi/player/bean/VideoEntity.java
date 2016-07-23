package com.papi.player.bean;

import com.google.gson.Gson;
import com.papi.player.util.log.ILog;

/**
 * Author   Shone
 * Date     01/07/16.
 * Github   https://github.com/shonegg
 */
//PV_VIDEO -> Entity
public class VideoEntity extends BaseEntity {
    private String aword;
    private String brief;
    private String category;
    private String channel;
    private String duration;
    private String id;
    private String name;
    private String poster;
    private String release;
    private static final long serialVersionUID = -2905531463404568422L;
    private String share;
    private String still;
    private String tag;
    private long vv;

    public VideoEntity() {
        super();
    }

    public String getAword() {
        return this.aword;
    }

    public String getBrief() {
        return this.brief;
    }

    public String getCategory() {
        return this.category;
    }

    public String getChannel() {
        return this.channel;
    }

    public String getDuration() {
        return this.duration;
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

    public String getRelease() {
        return this.release;
    }

    public String getShare() {
        return this.share;
    }

    public String getStill() {
        return this.still;
    }

    public String getTag() {
        return this.tag;
    }

    public long getVv() {
        return this.vv;
    }

    public void setAword(String aword) {
        this.aword = aword;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public void setDuration(String duration) {
        this.duration = duration;
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

    public void setRelease(String release) {
        this.release = release;
    }

    public void setShare(String share) {
        this.share = share;
    }

    public void setStill(String still) {
        this.still = still;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setVv(long vv) {
        this.vv = vv;
    }


    public static VideoEntity createFromJson(String jsonStr) {
        VideoEntity result = null;
        try {
            result = new Gson().fromJson(jsonStr, VideoEntity.class);
        } catch (Exception e) {
            ILog.e(ILog.TAG_DEFAULT, "" + e.toString());
        }
        return result;
    }

}