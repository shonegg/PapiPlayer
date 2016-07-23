package com.papi.player.dao;

import android.text.TextUtils;

import com.papi.player.greendao.MediaBeanDao;
import com.papi.player.javabean.MediaBean;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Author   Shone
 * Date     05/07/16.
 * Github   https://github.com/shonegg
 */
public class MediaBeanDaoHelper implements DaoHelperInterface<MediaBean> {
    private static MediaBeanDaoHelper instance;
    private MediaBeanDao mediaBeanDao;

    private MediaBeanDaoHelper() {
        try {
            mediaBeanDao = DatabaseLoader.getDaoSession().getMediaBeanDao();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static MediaBeanDaoHelper getInstance() {
        if (instance == null) {
            instance = new MediaBeanDaoHelper();
        }

        return instance;
    }


    @Override
    public void addData(MediaBean bean) {
        if (mediaBeanDao != null && bean != null) {
            mediaBeanDao.insertOrReplace(bean);
        }
    }

    @Override
    public void deleteData(String key) {
        if (mediaBeanDao != null && !TextUtils.isEmpty(key)) {
            mediaBeanDao.deleteByKey(key);
        }
    }

    @Override
    public MediaBean getDataByKey(String key) {
        if (mediaBeanDao != null && !TextUtils.isEmpty(key)) {
            return mediaBeanDao.load(key);
        }
        return null;
    }

    @Override
    public List<MediaBean> getAllData() {
        if (mediaBeanDao != null) {
            return mediaBeanDao.loadAll();
        }
        return null;
    }

    @Override
    public long getTotalCount() {
        if (mediaBeanDao == null) {
            return 0;
        }

        QueryBuilder<MediaBean> qb = mediaBeanDao.queryBuilder();
        return qb.buildCount().count();
    }

    @Override
    public void deleteAll() {
        if (mediaBeanDao != null) {
            mediaBeanDao.deleteAll();
        }
    }

}
