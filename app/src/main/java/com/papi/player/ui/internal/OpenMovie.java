package com.papi.player.ui.internal;

import android.content.Context;

import com.papi.player.bean.EnterMediaEntity;
import com.papi.player.ui.MediaInfoActivity;
import com.papi.player.ui.VTopicActivity;
import com.papi.player.ui.VideoInfoActivity;
import com.papi.player.ui.common.BrowserActivity;
import com.papi.player.util.log.ILog;

import java.util.HashMap;

/**
 * Author   Shone
 * Date     01/07/16.
 * Github   https://github.com/shonegg
 */
public class OpenMovie {
    public enum Template {
        MPLAY("mplay", "媒体"),
        VPLAY("vplay", "视频"),
        LPLAY("lplay", "直播"),
        MTOPIC("mtopic", "媒体专题"),
        VTOPIC("vtopic", "视频专题"),
        WEB("web", "跳转网页"),
        NONE("none", "不做操作"),
        UNKNOWN("unknown", "未知的");

        public String desc;
        public String name;


        private Template(String name, String desc) {
            this.name = name;
            this.desc = desc;
            OpenMovie.map.put(name, this);
        }

        public static Template getTemplate(String name) {
            Template template = null;
            Template.initTemplates();
            if (OpenMovie.map.containsKey(name)) {
                template = OpenMovie.map.get(name);
            } else {
                template = Template.UNKNOWN;
            }
            return template;
        }

        private static void initTemplates() {
            if (OpenMovie.map.size() <= 0) {
                Template[] values = Template.values();
                int length = values.length;
                int i = 0;
                for (; i < length; i++) {
                    OpenMovie.map.put(values[i].name, values[i]);
                }
            }
        }


    }

    private static OpenMovie mIntance = null;
    private static HashMap<String, OpenMovie.Template> map = new HashMap();

    public static OpenMovie getIntance() {
        if (mIntance == null) {
            mIntance = new OpenMovie();
        }

        return mIntance;
    }

    public void open(Context ctx, String template, String mId, String cId, String cCode, String url, String coverUrl) {
        this.open(ctx, Template.getTemplate(template), mId, cId, cCode, url, coverUrl);
    }

//    MPLAY("mplay", "媒体"),
//    VPLAY("vplay", "视频"),
//    LPLAY("lplay", "直播"),
//    MTOPIC("mtopic", "媒体专题"),
//    VTOPIC("vtopic", "视频专题"),
//    WEB("web", "跳转网页"),
//    NONE("none", "不做操作"),
//    UNKNOWN("unknown", "未知的");

    public void open(Context ctx, Template template, String mId, String cId, String cCode, String url,String coverUrl) {
        if (ctx == null) {
            return;
        }
        if (template == null) {
            return;
        }
        ILog.e(ILog.TAG_DEFAULT, "打开 template:" + template.name);
        switch (template) {
            case MPLAY: {
                MediaInfoActivity.start(ctx, new EnterMediaEntity(mId, null, null, 0, cId, cCode, null));
                return;
            }
            case VPLAY: {
                VideoInfoActivity.start(ctx, new EnterMediaEntity(mId, null, null, 0, cId, cCode, null), coverUrl);
                return;
            }
            case LPLAY: {
//                LivePlayEpgActivity.start(ctx, new FSEnterMediaEntity(mId, null, null, 0, cId, cCode, null));
                return;
            }
            case MTOPIC: {
//                TopicDetailActivity.start(ctx, mId, cCode, cId);
                return;
            }
            case VTOPIC: {
                VTopicActivity.launch(ctx, mId, cCode, cId);
                return;
            }
            case WEB: {
                BrowserActivity.launch(ctx, url, "");
                return;
            }
        }
    }
}
