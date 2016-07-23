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
 * Author   Shone
 * Date     25/06/16.
 * Github   https://github.com/shonegg
 */
public class ChannelAlbumEntity {

    public String retcode;// "200",
    public String retmsg;// "ok",
    public JsonArray blocks;

    public List<BlockBean> blockBeanList;

    public class BlockBean {
        public String id;
        public String code;
        public String name;
        public String template;
        public JsonObject channel;
        public ChannelBean channelBean;

        public class ChannelBean {
            public String id;
            public String code;
            public String name;
            public String template;
            public String url;
        }


        public JsonArray contents;
        public List<ContentBean> contentBeanList;

        public class ContentBean {
            public String id;
            public String name;
            public String poster;//海报
            public String still;
            public String director;
            public String actor;
            public String score;
            public String aword;
            public String release;
            public String update;
            public String vv;
            public String isvip;
            public String category;
            public String channel;
            public String channel_id;
            public String area;
            public String template;
            public String mid;
        }

    }

    public static ChannelAlbumEntity createFromJson(String jsonStr) {
        ChannelAlbumEntity result = null;
        try {
            result = new Gson().fromJson(jsonStr, ChannelAlbumEntity.class);
        } catch (Exception e) {
            ILog.e(ILog.TAG_DEFAULT, "" + e.toString());
        }
        if (result == null) {
            return null;
        }

        Iterator<JsonElement> iterator = result.blocks.iterator();
        if (result.blockBeanList == null) {
            result.blockBeanList = new ArrayList<>();
        }

        while (iterator.hasNext()) {
            JsonElement element = iterator.next();
            try {
                BlockBean blockBean = new Gson().fromJson(element, BlockBean.class);
                if (blockBean != null) {
                    blockBean.channelBean = new Gson().fromJson(blockBean.channel, BlockBean.ChannelBean.class);
                    blockBean.channel = null;

                    Iterator<JsonElement> iterator1 = blockBean.contents.iterator();
                    if (blockBean.contentBeanList == null) {
                        blockBean.contentBeanList = new ArrayList<>();
                    }

                    while (iterator1.hasNext()) {
                        JsonElement element1 = iterator1.next();
                        try {
                            BlockBean.ContentBean contentBean = new Gson().fromJson(element1,
                                    BlockBean.ContentBean.class);
                            blockBean.contentBeanList.add(contentBean);
                        } catch (Exception e) {
                            // Just ignore it.
                            e.printStackTrace();
                        }
                    }
                    blockBean.contents = null;
                }
                result.blockBeanList.add(blockBean);
            } catch (Exception e) {
                // Just ignore it.
                e.printStackTrace();
            }
        }
        result.blocks = null;
        return result;
    }


}
