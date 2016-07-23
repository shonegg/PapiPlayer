package com.papi.player.play;

/**
 * Author   Shone
 * Date     04/07/16.
 * Github   https://github.com/shonegg
 */

public class PlayParam {
    public boolean isMedia;
    public String mediaId;
    public String mediaName;
    public String subjectId;

    public PlayParam(VideoParam param) {
        super();
        if (param != null) {
            this.subjectId = param.subjectVideoId;
            this.mediaId = param.mediaId;
            if (param.isMedia) {
                this.mediaName = param.mediaName;
                this.isMedia = true;
            } else {
                this.isMedia = false;
                this.mediaName = param.subjectVideoName;
            }
        }
    }

    public PlayParam videoData2Curr(VideoParam param) {
        if (param == null) {
            return null;
        } else {
            this.subjectId = param.subjectVideoId;
            this.mediaId = param.mediaId;
            if (param.isMedia) {
                this.mediaName = param.mediaName;
                this.isMedia = true;
            } else {
                this.isMedia = false;
                this.mediaName = param.subjectVideoName;
            }
        }
        return this;
    }
}

