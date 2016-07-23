package com.papi.player.api;

import com.papi.player.bean.SubTitlesEntity;
import com.papi.player.bean.VideoPlayEntity;
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
public class VideoSubTitleApi {

    public static BasicMessage<SubTitlesEntity> get(String channel) {
        String baseurl = Configs.URL_PV_VIDEO_SUB_TITLE;
        String url = baseurl + "&channel=" + channel;
        url = BuildOpt.build(url);

        Request request = new Request.Builder()
                .url(url)
                .build();

        BasicMessage<SubTitlesEntity> msg = new BasicMessage<>();
        try {
            Response response = new OkHttpClient().newCall(request)
                    .execute();

            String result = response.body().string();
            msg.setObject(SubTitlesEntity.createFromJson(result));
            msg.setCode(BasicMessage.CODE_SUCCEED);
        } catch (IOException e) {
            e.printStackTrace();
            msg.setCode(BasicMessage.CODE_ERROR);
        }
        return msg;
    }
}
