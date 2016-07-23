package com.papi.player.api;

/**
 * author   Shone
 * date     13/06/16.
 * github   https://github.com/shonegg
 */
public enum TemplateType {

    MPLAY("mplay"),
    STILL("still"),
    VTOPIC("vtopic"),
    POSTER("poster"),
    VPLAY("vplay"),
    CVIDEO("cvideo"),
    CMEDIA("cmedia"),
    CVIP("cvip"),
    GRID("grid");


    public String type;

    TemplateType(String val) {
        type = val;
    }

    public String nameof() {
        return type;
    }

}
