package com.papi.player.api;

import com.papi.player.bean.SubTitlesEntity;
import com.papi.player.bean.VideoRankEntity;
import com.papi.player.model.BasicMessage;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Author   Shone
 * Date     03/07/16.
 * Github   https://github.com/shonegg
 */
public class VideoTopApi {

    public static BasicMessage<VideoRankEntity> get(String channel) {
        String baseurl = Configs.PV_VIDEO_TOP;
        String url = baseurl + "&channel=" + channel;
        url = BuildOpt.build(url);

        Request request = new Request.Builder()
                .url(url)
                .build();

        BasicMessage<VideoRankEntity> msg = new BasicMessage<>();
        try {
            Response response = new OkHttpClient().newCall(request)
                    .execute();

            String result = response.body().string();
            msg.setObject(VideoRankEntity.createFromJson(result));
            msg.setCode(BasicMessage.CODE_SUCCEED);
        } catch (IOException e) {
            e.printStackTrace();
            msg.setCode(BasicMessage.CODE_ERROR);
        }
        return msg;
    }

}
