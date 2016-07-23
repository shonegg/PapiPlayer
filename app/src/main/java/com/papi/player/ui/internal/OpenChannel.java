package com.papi.player.ui.internal;

import android.content.Context;

import com.papi.player.bean.Channel;
import com.papi.player.ui.ChannelMediaActivity;
import com.papi.player.ui.ChannelVideoV1Activity;
import com.papi.player.ui.common.BrowserActivity;
import com.papi.player.util.log.ILog;

import java.util.HashMap;

/**
 * Author   Shone
 * Date     27/06/16.
 * Github   https://github.com/shonegg
 */
public class OpenChannel {
    public enum Template {
        MEDIA("cmedia", "媒体频道海报"),
        CSTILL("cstill", "媒体频道剧照"),
        VEDIO("cvideo", "小视频频道"),
        VARIETY("cvariety", "综艺频道"),
        WEB("web", "web浏览"),
        PERSONAL("cpersonal", "个性化频道"),
        APP("capp", "打开应用频道"),
        CLIVE("clive", "打开直播频道"),
        NONE("null", "不操作的"),
        UNKNOWN("unknown", "未知的");


        public String desc;
        public String name;


        private Template(String name, String desc) {
            this.name = name;
            this.desc = desc;
            OpenChannel.map.put(name, this);
        }

        public static Template getTemplate(String name) {
            Template template;
            Template.initTemplates();
            if (OpenChannel.map.containsKey(name)) {
                template = OpenChannel.map.get(name);
            } else {
                template = Template.UNKNOWN;
            }
            return template;
        }

        private static void initTemplates() {
            if (OpenChannel.map.size() <= 0) {
                Template[] array = Template.values();
                for (int i = 0; i < array.length; i++) {
                    OpenChannel.map.put(array[i].name, array[i]);
                }
            }
        }
    }

    private static OpenChannel mInstance;
    private static HashMap<String, Template> map = new HashMap();

    public static OpenChannel getInstance() {
        if (mInstance == null) {
            mInstance = new OpenChannel();
        }

        return mInstance;
    }

    public void open(Context ctx, Channel channel) {
        if (ctx != null && channel != null) {
            String code = channel.getCode();
            String id = channel.getId();
            String name = channel.getName();
            ILog.e(ILog.TAG_DEFAULT, "openChannel:" + channel.getTemplate());
            switch (Template.getTemplate(channel.getTemplate())) {
                case MEDIA: {
                    ChannelMediaActivity.launch(ctx, code, id, name, Template.MEDIA);
                    return;
                }
                case CSTILL: {
                    ChannelMediaActivity.launch(ctx, code, id, name, Template.CSTILL);
                    return;
                }
                case VEDIO: {
                   ChannelVideoV1Activity.start(ctx, code, id, name);
                    return;
                }
                case VARIETY: {
//                    ChannelVarietyActivity.start(ctx, code, id, name);
                    return;
                }
                case WEB: {
                    BrowserActivity.launch(ctx, channel.getUrl(), channel.getName());
                    return;
                }
                case PERSONAL: {
//                    AppWebviewActivity.start(ctx, channel.getUrl());
                    return;
                }
                case APP: {
//                    ChannelPersonalizeActivity.start(ctx, code, id, name);
                    return;
                }
                case CLIVE: {
//                    ChannelLiveActivity.start(ctx, code, id, name);
                    return;
                }
            }
        }
    }
}


