package com.papi.player.bean;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.papi.player.util.log.ILog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * author   Shone
 * date     12/06/16.
 * github   https://github.com/shonegg
 */
public class HomePageBean {

    public String retcode;
    public String retmsg;
    public JsonArray focus; //banner
    public JsonArray blocks;

    public List<FocusBean> focusBeanList;
    public List<BlockBean> blockBeanList;

    public class FocusBean {
        public String template;
        public String mid;
        public String name;
        public String aword;
        public String update;
        public String url;
        public String duration;
        public String still;
        public String poster;
        public String focus;
        public String vipfocus;
        public String banner;
        public String icon;
        public String picture01;
        public String corner;
        public String still_gif;
        public String picture02;


    }

    public class BlockBean {
        public String id;
        public String name;// "今日热点",
        public String template;
        public JsonObject channel;
        public JsonArray contents;

        public ChannelBean channelBean;
        public ArrayList<ContentBean> contentBeanList;

        public class ChannelBean {
            public String id;
            public String code;
            public String name;
            public String template;
            public String url;
        }

        public class ContentBean {
            public String template;
            public String mid;
            public String name;
            public String aword;
            public String update;
            public String url;// ""
            public String duration;// ""
            public String still;
            public String poster;// ""
            public String focus;// ""
            public String vipfocus;// ""
            public String banner; //""
            public String icon;// ""
            public String picture01;// ""
            public String corner;// ""
            public String still_gif; //""
            public String picture02;// ""
        }

    }


    public static HomePageBean createFromJson(String jsonStr) {
        HomePageBean result = null;
        try {
            result = new Gson().fromJson(jsonStr, HomePageBean.class);
        } catch (Exception e) {
            ILog.e(ILog.TAG_DEFAULT, "" + e.toString());
        }
        if (result == null) {
            return null;
        }

        Iterator<JsonElement> iterator = result.focus.iterator();
        if (result.focusBeanList == null) {
            result.focusBeanList = new ArrayList<>();
        }
        while (iterator.hasNext()) {
            JsonElement element = iterator.next();
            try {
                result.focusBeanList.add(new Gson().fromJson(element, FocusBean.class));
            } catch (Exception e) {
                // Just ignore it.
                e.printStackTrace();
            }
        }
        result.focus = null;

        Iterator<JsonElement> iterator1 = result.blocks.iterator();
        if (result.blockBeanList == null) {
            result.blockBeanList = new ArrayList<>();
        }
        while (iterator1.hasNext()) {
            JsonElement element = iterator1.next();
            try {
                BlockBean blockresult = new Gson().fromJson(element, BlockBean.class);
                if (blockresult != null) {
                    blockresult.channelBean = new Gson().fromJson(blockresult.channel, BlockBean.ChannelBean.class);
                    blockresult.channel = null;

                    Iterator<JsonElement> iterator2 = blockresult.contents.iterator();
                    if (blockresult.contentBeanList == null) {
                        blockresult.contentBeanList = new ArrayList<>();
                    }
                    while (iterator2.hasNext()) {
                        JsonElement element3 = iterator2.next();
                        try {
                            blockresult.contentBeanList.add(new Gson()
                                    .fromJson(element3, BlockBean.ContentBean.class));
                        } catch (Exception e) {
                            // Just ignore it.
                            e.printStackTrace();
                        }
                    }
                    blockresult.contents = null;

                    if (blockresult.contentBeanList.size() > 0) {
                        if (blockresult.name.contains("会员") || blockresult.name.equalsIgnoreCase("vip")) {
                            // do nothing
                        } else {
                            result.blockBeanList.add(blockresult);
                        }
                    }
                }

            } catch (Exception e) {
                // Just ignore it.
                e.printStackTrace();
            }
        }

        return result;
    }


}
