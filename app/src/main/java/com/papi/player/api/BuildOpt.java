package com.papi.player.api;

import com.papi.player.AppContext;

/**
 * Created by wangxiong on 2016/7/7.
 */
public class BuildOpt {

    public static String build(String url) {
        return url + "&cl=aphone" + "&ve=4.1.1" + "&mac=" + AppContext.getLocalMacAddress(AppContext.getContext());
    }

}
