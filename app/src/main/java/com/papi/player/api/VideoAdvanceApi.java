package com.papi.player.api;

import com.papi.player.bean.VideoAdvanceBean;
import com.papi.player.model.BasicMessage;
import com.papi.player.util.log.ILog;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * author   Shone
 * date     13/06/16.
 * github   https://github.com/shonegg
 */
public class VideoAdvanceApi {

    public static BasicMessage<VideoAdvanceBean> getVideoAdvanceInfo(String id) {
        String baseurl = Configs.URL_VIDEO_ADVANCE;
        String url = baseurl + "&id=" + id;
        url = BuildOpt.build(url);

        Request request = new Request.Builder()
                .url(url)
                .build();

        BasicMessage<VideoAdvanceBean> msg = new BasicMessage<>();
        try {
            Response response = new OkHttpClient().newCall(request)
                    .execute();

            String result = response.body().string();
            ILog.w(ILog.TAG_DEFAULT, "VideoAdvance请求:" + url + "\n" + result);
            msg.setObject(VideoAdvanceBean.createFromJson(result));
            msg.setCode(BasicMessage.CODE_SUCCEED);
        } catch (IOException e) {
            e.printStackTrace();
            msg.setCode(BasicMessage.CODE_ERROR);
        }
        return msg;
    }


}
