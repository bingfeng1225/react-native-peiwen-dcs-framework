package com.qd.peiwen.dcsframework.devices.system.message.directive;

import com.google.gson.annotations.SerializedName;
import com.qd.peiwen.dcsframework.entity.payload.BasePayload;

/**
 * Created by nick on 2018/3/8.
 */

public class SetSessionidPayload extends BasePayload {
    @SerializedName("sessionid")
    private String sessionid;

    public SetSessionidPayload() {
    }

    public SetSessionidPayload(String sessionid) {
        this.sessionid = sessionid;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }
}
