package com.qd.peiwen.dcsframework.devices.lifevoice.message.directive;

import com.google.gson.annotations.SerializedName;
import com.qd.peiwen.dcsframework.entity.payload.BasePayload;

/**
 * Created by liudunjian on 2018/5/17.
 */

public class LifeVoicePayload extends BasePayload {
    @SerializedName("url")
    private String url;
    @SerializedName("token")
    private String token;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
