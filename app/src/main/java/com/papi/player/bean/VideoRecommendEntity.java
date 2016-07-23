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
 * Date     02/07/16.
 * Github   https://github.com/shonegg
 */
public class VideoRecommendEntity extends BaseEntity2{

    public JsonArray blocks;
    public ArrayList<Block> blockList;


    public static VideoRecommendEntity createFromJson(String jsonStr) {
        VideoRecommendEntity result = null;
        try {
            result = new Gson().fromJson(jsonStr, VideoRecommendEntity.class);
        } catch (Exception e) {
            ILog.e(ILog.TAG_DEFAULT, "" + e.toString());
        }
        if (result == null) {
            return null;
        }

        Iterator<JsonElement> iterator = result.blocks.iterator();
        if (result.blockList == null) {
            result.blockList = new ArrayList<>();
        }

        while (iterator.hasNext()) {
            JsonElement element = iterator.next();
            try {
                Block blockBean = new Gson().fromJson(element, Block.class);
                if (blockBean != null) {

                    Iterator<JsonElement> iterator1 = blockBean.contents.iterator();
                    if (blockBean.contentList == null) {
                        blockBean.contentList = new ArrayList<>();
                    }

                    while (iterator1.hasNext()) {
                        JsonElement element1 = iterator1.next();
                        try {
                            Content contentBean = new Gson().fromJson(element1, Content.class);
                            blockBean.contentList.add(contentBean);
                        } catch (Exception e) {
                            // Just ignore it.
                            e.printStackTrace();
                        }
                    }
                    blockBean.contents = null;
                }
                result.blockList.add(blockBean);
            } catch (Exception e) {
                // Just ignore it.
                e.printStackTrace();
            }
        }
        result.blocks = null;
        return result;
    }

}
