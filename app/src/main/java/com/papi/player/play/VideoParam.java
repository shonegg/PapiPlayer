package com.papi.player.play;

import android.text.TextUtils;
import android.util.Log;

import com.papi.player.util.log.ILog;

/**
 * Author   Shone
 * Date     04/07/16.
 * Github   https://github.com/shonegg
 */
public class VideoParam {
    public String channelCode;
    public String episodeNum;
    public int historyPosition = 0;
    public boolean isAuto = true;
    public boolean isMedia;
    public String mediaId;
    public String mediaName;
    public String subjectVideoId;
    public String subjectVideoName;

    public boolean isLegal() {
        boolean isLegal = true;
        if (this.isMedia) {
            if (!TextUtils.isEmpty(this.subjectVideoId) && !TextUtils.isEmpty(this.mediaId) && !TextUtils
                    .isEmpty(this.mediaName) && !TextUtils.isEmpty(this.subjectVideoName) && !TextUtils
                    .isEmpty(this.episodeNum)) {
                return isLegal;
            }

            Log.w(ILog.TAG_DEFAULT, "subjectVideoId=" + this.subjectVideoId + ",mediaId="
                    + this.mediaId + ", mediaName=" + this.mediaName + ",subjectVideoName=" + this.
                    subjectVideoName + ", episodeNum=" + this.episodeNum);
            isLegal = false;
        } else {
            if (!TextUtils.isEmpty(this.subjectVideoId) && !TextUtils.isEmpty(this.subjectVideoName)) {
                return isLegal;
            }

            Log.w(ILog.TAG_DEFAULT, "subjectVideoId=" + this.subjectVideoId + ", subjectVideoName="
                    + this.subjectVideoName);
            isLegal = false;
        }

        return isLegal;
    }

    public VideoParam setChannelCode(String channelCode) {
        this.channelCode = channelCode;
        return this;
    }

    public VideoParam setEpisodeNum(String num) {
        this.episodeNum = num;
        return this;
    }

    public VideoParam setHistoryPosition(int position) {
        this.historyPosition = position;
        return this;
    }

    public VideoParam setMediaFlag(boolean flag) {
        this.isMedia = flag;
        return this;
    }

    public VideoParam setMediaId(String mediaId) {
        this.mediaId = mediaId;
        return this;
    }

    public VideoParam setMediaName(String mediaName) {
        this.mediaName = mediaName;
        return this;
    }

    public VideoParam setSubjectVideoId(String videoId) {
        this.subjectVideoId = videoId;
        return this;
    }

    public VideoParam setSubjectVideoName(String videoName) {
        this.subjectVideoName = videoName;
        return this;
    }
}