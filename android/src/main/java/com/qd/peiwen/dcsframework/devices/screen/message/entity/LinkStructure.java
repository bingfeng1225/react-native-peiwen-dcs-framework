package com.qd.peiwen.dcsframework.devices.screen.message.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nick on 2017/12/5.
 */

public class LinkStructure {
    @SerializedName("url")
    private String url;
    @SerializedName("anchorText")
    private String anchorText;

    public LinkStructure() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAnchorText() {
        return anchorText;
    }

    public void setAnchorText(String anchorText) {
        this.anchorText = anchorText;
    }
}
