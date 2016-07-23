package com.papi.player.dao;

import android.text.TextUtils;

import com.papi.player.greendao.VideoBeanDao;
import com.papi.player.javabean.VideoBean;
import java.util.List;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * Author   Shone
 * Date     05/07/16.
 * Github   https://github.com/shonegg
 */
public class VideoBeanDaoHelper implements DaoHelperInterface<VideoBean> {
    private static VideoBeanDaoHelper instance;
    private VideoBeanDao videoBeanDao;

    private VideoBeanDaoHelper() {
        try {
            videoBeanDao = DatabaseLoader.getDaoSession().getVideoBeanDao();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static VideoBeanDaoHelper getInstance() {
        if (instance == null) {
            instance = new VideoBeanDaoHelper();
        }

        return instance;
    }


    @Override
    public void addData(VideoBean bean) {
        if (videoBeanDao != null && bean != null) {
            videoBeanDao.insertOrReplace(bean);
        }
    }

    @Override
    public void deleteData(String key) {
        if (videoBeanDao != null && !TextUtils.isEmpty(key)) {
            videoBeanDao.deleteByKey(key);
        }
    }

    @Override
    public VideoBean getDataByKey(String key) {
        if (videoBeanDao != null && !TextUtils.isEmpty(key)) {
            return videoBeanDao.load(key);
        }
        return null;
    }

    @Override
    public List<VideoBean> getAllData() {
        if (videoBeanDao != null) {
            return videoBeanDao.loadAll();
        }
        return null;
    }

    @Override
    public long getTotalCount() {
        if (videoBeanDao == null) {
            return 0;
        }

        QueryBuilder<VideoBean> qb = videoBeanDao.queryBuilder();
        return qb.buildCount().count();
    }

    @Override
    public void deleteAll() {
        if (videoBeanDao != null) {
            videoBeanDao.deleteAll();
        }
    }

}
