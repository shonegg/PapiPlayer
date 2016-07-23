package com.papi.player.dao;

import com.papi.player.bean.EnterMediaEntity;
import com.papi.player.bean.ProfileBean;
import com.papi.player.bean.VideoEntity;
import com.papi.player.javabean.MediaBean;
import com.papi.player.javabean.VideoBean;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Author   Shone
 * Date     05/07/16.
 * Github   https://github.com/shonegg
 */
public class HistoryManager {


    public static void saveMedia(EnterMediaEntity mEnterEntity, ProfileBean profile) {
        MediaBean bean = new MediaBean();
        bean.setCId(mEnterEntity.getcId());
        bean.setCCode(mEnterEntity.getcCode());
        bean.setEnter_id(mEnterEntity.getId());
        bean.setCover_url(profile.still);
        bean.setName(profile.name);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String time = df.format(new Date());// new Date()为获取当前系统时间
        bean.setTime(time);
        MediaBeanDaoHelper instance = MediaBeanDaoHelper.getInstance();
        MediaBean dataBean = instance.getDataByKey(bean.getEnter_id());
        if (dataBean != null){
            instance.deleteData(bean.getEnter_id());//先删除
        }
        instance.addData(bean);
    }


    public static void saveVideo(EnterMediaEntity mEnterEntity, VideoEntity entity) {
        VideoBean bean = new VideoBean();
        bean.setCId(mEnterEntity.getcId());
        bean.setCCode(mEnterEntity.getcCode());
        bean.setEnter_id(mEnterEntity.getId());
        bean.setCover_url(entity.getStill());
        bean.setName(entity.getName());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String time = df.format(new Date());// new Date()为获取当前系统时间
        bean.setTime(time);
        VideoBeanDaoHelper instance = VideoBeanDaoHelper.getInstance();
        VideoBean dataBean = instance.getDataByKey(bean.getEnter_id());
        if (dataBean != null){
            instance.deleteData(bean.getEnter_id());////先删除
        }
        VideoBeanDaoHelper.getInstance().addData(bean);
    }


}
