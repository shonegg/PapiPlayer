package com.papi.player.api;

import com.papi.player.bean.VideoPlayEntity;
import com.papi.player.model.BasicMessage;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.io.IOException;

/**
 * Author   Shone
 * Date     23/06/16.
 * Github   https://github.com/shonegg
 * desc     播放专题视频...
 */
public class VideoPlayAPi {

    public static BasicMessage<VideoPlayEntity> getPlayInfo(String id) {
        String baseurl = Configs.URL_PV_VIDEO_PLAY;
        String url = baseurl + "&id=" + id;
        url = BuildOpt.build(url);

        Request request = new Request.Builder()
                .url(url)
                .build();

        BasicMessage<VideoPlayEntity> msg = new BasicMessage<>();
        try {
            Response response = new OkHttpClient().newCall(request)
                    .execute();

            String result = response.body().string();
            msg.setObject(VideoPlayEntity.createFromJson(result));
            msg.setCode(BasicMessage.CODE_SUCCEED);
        } catch (IOException e) {
            e.printStackTrace();
            msg.setCode(BasicMessage.CODE_ERROR);
        }
        return msg;
    }
}
