package com.qd.peiwen.dcsframework.entity.voice;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nick on 2018/1/27.
 */

public class SendEntity {
    @SerializedName("type")
    private String type = "text";
    @SerializedName("token")
    private String token = "access_token";
    @SerializedName("deviceid")
    private String deviceid;
    @SerializedName("sessionid")
    private String sessionid = "";
    @SerializedName("location")
    private String location;
    @SerializedName("text")
    private String text;

    public SendEntity() {

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        if(TextUtils.isEmpty(sessionid)){
            this.sessionid = "";
        }else {
            this.sessionid = sessionid;
        }
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
