package com.papi.player.api;

import com.papi.player.bean.HomePageBean;
import com.papi.player.model.BasicMessage;
import com.papi.player.util.log.ILog;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * author   Shone
 * date     12/06/16.
 * github   https://github.com/shonegg
 */
public class HomePageAPi {

    public static BasicMessage<HomePageBean> getHomePageInfo() {
        String url = Configs.URL_HOMEPAGE;
        url = BuildOpt.build(url);

        Request request = new Request.Builder()
                .url(url)
                .build();

        BasicMessage<HomePageBean> msg = new BasicMessage<>();
        try {
            Response response = new OkHttpClient().newCall(request)
                    .execute();

            String result = response.body().string();

            msg.setObject(HomePageBean.createFromJson(result));
            msg.setCode(BasicMessage.CODE_SUCCEED);
        } catch (IOException e) {
            e.printStackTrace();
            msg.setCode(BasicMessage.CODE_ERROR);
        }
        return msg;
    }


}
