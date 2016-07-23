package com.papi.player.bean;

import java.io.Serializable;

/**
 * Author   Shone
 * Date     01/07/16.
 * Github   https://github.com/shonegg
 */
public class EnterMediaEntity implements Serializable {
    private String cCode;
    private String cId;
    private String channelNum;
    private String eId;
    private String eNum;
    private String id;
    private String name;
    private int playTime;
    private static final long serialVersionUID = -6220412393511985213L;

    public EnterMediaEntity(String id, String eId, String eNum, int pTime, String cId, String cCode,
                            String channelNum) {
        super();
        this.playTime = 0;
        this.id = id;
        this.eId = eId;
        this.eNum = eNum;
        this.playTime = pTime;
        this.cId = cId;
        this.cCode = cCode;
        this.channelNum = channelNum;
    }

    public EnterMediaEntity() {
        super();
        this.playTime = 0;
    }


    public EnterMediaEntity(String id, String cId, String cCode, String channelNum, String name) {
        super();
        this.playTime = 0;
        this.id = id;
        this.cId = cId;
        this.cCode = cCode;
        this.channelNum = channelNum;
        this.name = name;
    }

    public String getChannelNum() {
        return this.channelNum;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getPlayTime() {
        return this.playTime;
    }

    public String getcCode() {
        return this.cCode;
    }

    public String getcId() {
        return this.cId;
    }

    public String geteId() {
        return this.eId;
    }

    public String geteNum() {
        return this.eNum;
    }

    public void setChannelNum(String channelNum) {
        this.channelNum = channelNum;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlayTime(int playTime) {
        this.playTime = playTime;
    }

    public void setcCode(String cCode) {
        this.cCode = cCode;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public void seteId(String eId) {
        this.eId = eId;
    }

    public void seteNum(String eNum) {
        this.eNum = eNum;
    }
}
