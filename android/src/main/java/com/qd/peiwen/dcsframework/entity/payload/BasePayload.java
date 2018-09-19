package com.qd.peiwen.dcsframework.entity.payload;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nick on 2017/11/28.
 */

public class BasePayload {
    @SerializedName("uuid")
    protected String uuid;

    public BasePayload() {

    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
