package com.qd.peiwen.dcsframework.devices.system.message.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nick on 2017/12/6.
 */

public class ErrorMessager {
    @SerializedName("type")
    private String type;
    @SerializedName("message")
    private String message;

    public ErrorMessager() {
    }

    public ErrorMessager(String type, String message) {
        this.type = type;
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
