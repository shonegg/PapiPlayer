package com.papi.player.api;

import com.papi.player.bean.MediaPlayEntity;
import com.papi.player.model.BasicMessage;
import com.papi.player.util.FileUtils;
import com.papi.player.util.log.ILog;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * author   Shone
 * date     15/06/16.
 * github   https://github.com/shonegg
 */
public class MediaPlayApi {

    public static BasicMessage<MediaPlayEntity> retrofit(String id) {

        String baseurl = Configs.URL_MEDIA_PLAY;
        String url = baseurl + "&id=" + id;
        url = BuildOpt.build(url);

        Request request = new Request.Builder()
                .url(url)
                .build();
        ILog.e("MediaPlayApi:" + url);
        BasicMessage<MediaPlayEntity> msg = new BasicMessage<>();
        try {
            Response response = new OkHttpClient().newCall(request)
                    .execute();

            String result = response.body().string();
            String content = "----------------MediaPlayApi:------------" + url + "\n"
                        + result + "\n" + "---------------------------";
            FileUtils.write(content);

            msg.setObject(MediaPlayEntity.createFromJson(result));
            msg.setCode(BasicMessage.CODE_SUCCEED);
        } catch (IOException e) {
            e.printStackTrace();
            msg.setCode(BasicMessage.CODE_ERROR);
        }
        return msg;
    }

}
