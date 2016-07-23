package com.papi.player.dao;

import java.util.List;

/**
 * Author   Shone
 * Date     05/07/16.
 * Github   https://github.com/shonegg
 */
public interface DaoHelperInterface<T> {
    public void addData(T t);

    public void deleteData(String key);

    public <T> T getDataByKey(String key);

    public List<T> getAllData();

    public long getTotalCount();

    public void deleteAll();
}