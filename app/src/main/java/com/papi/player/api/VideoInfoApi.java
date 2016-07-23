package com.papi.player.api;

import com.papi.player.bean.VideoAdvanceBean;
import com.papi.player.bean.VideoEntity;
import com.papi.player.model.BasicMessage;
import com.papi.player.util.log.ILog;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Author   Shone
 * Date     01/07/16.
 * Github   https://github.com/shonegg
 */
public class VideoInfoApi {

    public static BasicMessage<VideoEntity> get(String id) {
        String baseurl = Configs.URL_PV_VIDEO;
        String url = baseurl + "&id=" + id;
        url = BuildOpt.build(url);

        Request request = new Request.Builder()
                .url(url)
                .build();

        BasicMessage<VideoEntity> msg = new BasicMessage<>();
        try {
            Response response = new OkHttpClient().newCall(request)
                    .execute();

            String result = response.body().string();
            msg.setObject(VideoEntity.createFromJson(result));
            msg.setCode(BasicMessage.CODE_SUCCEED);
        } catch (IOException e) {
            e.printStackTrace();
            msg.setCode(BasicMessage.CODE_ERROR);
        }
        return msg;


    }
}
