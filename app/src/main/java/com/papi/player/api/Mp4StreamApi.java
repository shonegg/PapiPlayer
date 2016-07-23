package com.papi.player.api;

import com.papi.player.bean.Mp4StreamBean;
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
public class Mp4StreamApi {

    public static BasicMessage<Mp4StreamBean> getMp4Info(String url) {

        Request request = new Request.Builder()
                .url(url)
                .build();

        BasicMessage<Mp4StreamBean> msg = new BasicMessage<>();
        try {
            Response response = new OkHttpClient().newCall(request)
                    .execute();

            String result = response.body().string();
            String content = "----------------Mp4StreamApi:------------" + url + "\n"
                    + result + "\n" + "------------------------------------------";
            FileUtils.write(content);

            ILog.e("" + content);
            msg.setObject(Mp4StreamBean.createFromJson(result));
            msg.setCode(BasicMessage.CODE_SUCCEED);
        } catch (IOException e) {
            e.printStackTrace();
            msg.setCode(BasicMessage.CODE_ERROR);
        }
        return msg;
    }

}
