package com.papi.player.play;

import com.papi.player.bean.MediaEpisodeEntity;

/**
 * Author   Shone
 * Date     04/07/16.
 * Github   https://github.com/shonegg
 */
public class PlayUtil {

    public static VideoParam episodeToVideoParam(MediaEpisodeEntity.Episode episode,
                                                 String mediaName, String mediaId,
                                                 int position, String cCode) {
        if (episode == null) {
            return null;
        }
        VideoParam videoParam = new VideoParam();
        videoParam.setMediaId(mediaId);
        videoParam.setMediaName(mediaName);
        videoParam.setSubjectVideoId(episode.getId());
        videoParam.setSubjectVideoName(episode.getName());
        videoParam.setEpisodeNum(episode.getNum());
        videoParam.setChannelCode(cCode);
        videoParam.setMediaFlag(true);
        videoParam.setHistoryPosition(position);
        return videoParam;
    }
}
