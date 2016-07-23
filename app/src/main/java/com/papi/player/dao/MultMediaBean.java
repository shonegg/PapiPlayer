package com.papi.player.dao;

import com.papi.player.javabean.MediaBean;

/**
 * Author   Shone
 * Date     05/07/16.
 * Github   https://github.com/shonegg
 */
public class MultMediaBean extends MediaBean{



    private int type;// 1 - video ; 2 - media

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
