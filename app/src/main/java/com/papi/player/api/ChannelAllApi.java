package com.papi.player.api;

import com.papi.player.bean.ChannelsEntity;
import com.papi.player.model.BasicMessage;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Author   Shone
 * Date     25/06/16.
 * Github   https://github.com/shonegg
 */
public class ChannelAllApi {

    public static BasicMessage<ChannelsEntity> getChannelsEntity() {
        String url = Configs.URL_CHANNEL_V7;
        url = BuildOpt.build(url);
        Request request = new Request.Builder()
                .url(url)
                .build();

        BasicMessage<ChannelsEntity> msg = new BasicMessage<>();
        try {
            Response response = new OkHttpClient().newCall(request)
                    .execute();

            String result = response.body().string();
            msg.setObject(ChannelsEntity.createFromJson(result));
            msg.setCode(BasicMessage.CODE_SUCCEED);
        } catch (IOException e) {
            e.printStackTrace();
            msg.setCode(BasicMessage.CODE_ERROR);
        }
        return msg;
    }
}
