package com.qd.peiwen.dcsframework.devices.screen.message.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nick on 2017/12/5.
 */

public class ImageStructure {
    @SerializedName("src")
    private String src;

    public ImageStructure() {
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}
