package com.papi.player.api;

import com.papi.player.bean.ChannelAlbumEntity;
import com.papi.player.bean.ChannelMediaEntity;
import com.papi.player.init.Udid;
import com.papi.player.model.BasicMessage;
import com.papi.player.util.log.ILog;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Author   Shone
 * Date     25/06/16.
 * Github   https://github.com/shonegg
 */
public class ChannelMediaApi {


    public static BasicMessage<ChannelMediaEntity> getRecommendEntity(String channel, int pg) {
        String url = Configs.URL_MEDIA_RECOMMEND;//channel=2&pg=1
        url = url + "&channel=" + channel + "&pg=" + pg;
        url = BuildOpt.build(url);
        url = url + "&fudid=" + Udid.getInstance().get();

        ILog.e(ILog.TAG_DEFAULT, "" + url);

        Request request = new Request.Builder()
                .url(url)
                .build();
        ILog.e("加载第" + pg + "页");
        BasicMessage<ChannelMediaEntity> msg = new BasicMessage<>();
        try {
            Response response = new OkHttpClient().newCall(request)
                    .execute();

            String result = response.body().string();
            msg.setObject(ChannelMediaEntity.createFromJson(result));
            msg.setCode(BasicMessage.CODE_SUCCEED);
        } catch (IOException e) {
            e.printStackTrace();
            msg.setCode(BasicMessage.CODE_ERROR);
        }
        return msg;
    }

    //channel=2
    public static BasicMessage<ChannelMediaEntity> getRankEntity(String channel) {
        String url = Configs.URL_MEDIA_RANK;//channel=2
        url = url + "&channel=" + channel;
        Request request = new Request.Builder()
                .url(url)
                .build();

        BasicMessage<ChannelMediaEntity> msg = new BasicMessage<>();
        try {
            Response response = new OkHttpClient().newCall(request)
                    .execute();

            String result = response.body().string();
            msg.setObject(ChannelMediaEntity.createFromJson(result));
            msg.setCode(BasicMessage.CODE_SUCCEED);
        } catch (IOException e) {
            e.printStackTrace();
            msg.setCode(BasicMessage.CODE_ERROR);
        }
        return msg;
    }

    public static BasicMessage<ChannelAlbumEntity> getAlbumEntity(String channel, String pg) {
        String url = Configs.URL_MEDIA_ALBUM;//channel=2&pg=1
        url = url + "&channel=" + channel + "&pg=" + pg;
        url = url + "&fudid=" + Udid.getInstance().get();

        Request request = new Request.Builder()
                .url(url)
                .build();

        BasicMessage<ChannelAlbumEntity> msg = new BasicMessage<>();
        try {
            Response response = new OkHttpClient().newCall(request)
                    .execute();

            String result = response.body().string();
            msg.setObject(ChannelAlbumEntity.createFromJson(result));
            msg.setCode(BasicMessage.CODE_SUCCEED);
        } catch (IOException e) {
            e.printStackTrace();
            msg.setCode(BasicMessage.CODE_ERROR);
        }
        return msg;
    }

}
