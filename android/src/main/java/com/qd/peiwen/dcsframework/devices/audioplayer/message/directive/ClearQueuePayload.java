package com.qd.peiwen.dcsframework.devices.audioplayer.message.directive;

import com.google.gson.annotations.SerializedName;
import com.qd.peiwen.dcsframework.entity.payload.BasePayload;

/**
 * Created by nick on 2017/12/11.
 */

public class ClearQueuePayload extends BasePayload {
    @SerializedName("clearBehavior")
    private String clearBehavior;

    public ClearQueuePayload() {
    }

    public String getClearBehavior() {
        return clearBehavior;
    }

    public void setClearBehavior(String clearBehavior) {
        this.clearBehavior = clearBehavior;
    }
}
