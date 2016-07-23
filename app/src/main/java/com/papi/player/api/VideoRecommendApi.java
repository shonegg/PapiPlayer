package com.papi.player.api;

import com.papi.player.bean.SubTitlesEntity;
import com.papi.player.bean.VideoRecommendEntity;
import com.papi.player.init.Udid;
import com.papi.player.model.BasicMessage;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Author   Shone
 * Date     02/07/16.
 * Github   https://github.com/shonegg
 */
public class VideoRecommendApi {

    public static BasicMessage<VideoRecommendEntity> get(String channel) {
        String baseurl = Configs.PV_VIDEO_RECOMMEND;
        String url = baseurl + "&channel=" + channel;
        url = BuildOpt.build(url);
        url = url + "&fudid=" + Udid.getInstance().get();

        Request request = new Request.Builder()
                .url(url)
                .build();

        BasicMessage<VideoRecommendEntity> msg = new BasicMessage<>();
        try {
            Response response = new OkHttpClient().newCall(request)
                    .execute();

            String result = response.body().string();
            msg.setObject(VideoRecommendEntity.createFromJson(result));
            msg.setCode(BasicMessage.CODE_SUCCEED);
        } catch (IOException e) {
            e.printStackTrace();
            msg.setCode(BasicMessage.CODE_ERROR);
        }
        return msg;
    }

}
