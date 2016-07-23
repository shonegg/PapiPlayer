package com.papi.player.bean;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.papi.player.util.log.ILog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Author   Shone
 * Date     25/06/16.
 * Github   https://github.com/shonegg
 */
public class ChannelMediaEntity {

    public String retcode;// "200",
    public String retmsg;// "ok",
    public String total;
    public JsonArray medias;

    public List<MediaBean> mediaBeanList;

    public class MediaBean {

        public String id;
        public String name;
        public String poster;
        public String still;
        public String director;
        public String actor;//陈学冬,颖儿,张哲瀚,安以轩",
        public String score;//7.1",
        public String aword;//猪队友陈学冬鲁莽再犯错",
        public String release;
        public String update;//更新至9集",
        public String vv;
        public String isvip;
        public String focus;
        public String category;//励志,谍战,青春",
        public String channel;//电视剧",
        public String channel_id;
        public String area;//内地"
    }

    public static ChannelMediaEntity createFromJson(String jsonStr) {
        ChannelMediaEntity result = null;
        try {
            result = new Gson().fromJson(jsonStr, ChannelMediaEntity.class);
        } catch (Exception e) {
            ILog.e(ILog.TAG_DEFAULT, "" + e.toString());
        }
        if (result == null) {
            return null;
        }

        Iterator<JsonElement> iterator = result.medias.iterator();
        if (result.mediaBeanList == null) {
            result.mediaBeanList = new ArrayList<>();
        }

        while (iterator.hasNext()) {
            JsonElement element = iterator.next();
            try {
                MediaBean channelBean = new Gson().fromJson(element, MediaBean.class);
                result.mediaBeanList.add(channelBean);
            } catch (Exception e) {
                // Just ignore it.
                e.printStackTrace();
            }
        }
        result.medias = null;
        return result;
    }

}
