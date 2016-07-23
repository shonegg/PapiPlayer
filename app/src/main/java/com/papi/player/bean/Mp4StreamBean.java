package com.papi.player.bean;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.papi.player.util.log.ILog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * author   Shone
 * date     15/06/16.
 * github   https://github.com/shonegg
 */
public class Mp4StreamBean {

    //    public String return;// "succ",
    public JsonObject client;

    public ClientInfo clientInfo;

    public class ClientInfo {
        public String ip;
        public String sp;// "0",
        public String loc;// "0"
    }

    public JsonArray playlist;

    public List<PlayListInfo> playListInfoList;

    public class PlayListInfo implements Serializable {
        public String bits;// "",
        public String tname;// "",
        public String size;// "4194304",
        public String[] urls;
    }

    public static Mp4StreamBean createFromJson(String jsonStr) {
        Mp4StreamBean result = null;
        try {
            result = new Gson().fromJson(jsonStr, Mp4StreamBean.class);
        } catch (Exception e) {
            ILog.e(ILog.TAG_DEFAULT, "" + e.toString());
        }
        if (result == null) {
            return null;
        }

        if (result.client != null) {
            result.clientInfo = new Gson().fromJson(result.client, ClientInfo.class);
        }

        result.client = null;

        Iterator<JsonElement> iterator = result.playlist.iterator();
        if (result.playListInfoList == null) {
            result.playListInfoList = new ArrayList<>();
        }
        while (iterator.hasNext()) {
            JsonElement element = iterator.next();
            try {
                result.playListInfoList.add(new Gson().fromJson(element, PlayListInfo.class));
            } catch (Exception e) {
                // Just ignore it.
                e.printStackTrace();
            }
        }
        result.playlist = null;
        return result;
    }


}
