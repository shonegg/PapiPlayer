package com.papi.player.bean;

import android.text.TextUtils;

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
public class ChannelsEntity {

    public String retcode;// "200"
    public String retmsg;// "ok",
    public JsonArray channels;

    public List<ChannelBean> channelBeanList;

    public class ChannelBean {
        public String nav_id;
        public String id;
        public String code;
        public String name;
        public String template;
        public String url;// "",
        public String htemplate;
        public String is_common;
        public String is_drag;
        public String is_audit;
        public String streamplay;
        public String icon1;
        public String icon2;
        public String icon3;
        public String icon4;
        public String icon5;
    }

    private static boolean isVaild(ChannelBean bean) {
        if (bean == null)
            return false;

        if (TextUtils.isEmpty(bean.name))
            return false;


        if (bean.name.equals("电视剧")) return true;
        if (bean.name.equals("电影")) return true;
        if (bean.name.equals("动漫")) return true;
        if (bean.name.equals("综艺")) return true;
        if (bean.name.equals("搞笑")) return true;
        if (bean.name.equals("娱乐")) return true;
        if (bean.name.equals("微电影")) return true;
        if (bean.name.equals("新闻")) return true;
        if (bean.name.equals("生活")) return true;
        if (bean.name.equals("科技")) return true;
        if (bean.name.equals("游戏")) return true;

        return false;
    }

    public static ChannelsEntity createFromJson(String jsonStr) {
        ChannelsEntity result = null;
        try {
            result = new Gson().fromJson(jsonStr, ChannelsEntity.class);
        } catch (Exception e) {
            ILog.e(ILog.TAG_DEFAULT, "" + e.toString());
        }
        if (result == null) {
            return null;
        }

        Iterator<JsonElement> iterator = result.channels.iterator();
        if (result.channelBeanList == null) {
            result.channelBeanList = new ArrayList<>();
        }

        while (iterator.hasNext()) {
            JsonElement element = iterator.next();
            try {
                ChannelBean channelBean = new Gson().fromJson(element, ChannelBean.class);
                if (isVaild(channelBean)) {
                    result.channelBeanList.add(channelBean);
                }
            } catch (Exception e) {
                // Just ignore it.
                e.printStackTrace();
            }
        }
        result.channels = null;
        return result;
    }
}

