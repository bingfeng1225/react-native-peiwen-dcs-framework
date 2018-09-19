package com.qd.peiwen.dcsframework.pinyinsearch.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nick on 2018/3/5.
 */

public class SMSMessage {
    @SerializedName("type")
    private int type;
    @SerializedName("date")
    private long date;
    @SerializedName("body")
    private CNMSGBody body;
    @SerializedName("phone")
    private CNMSGPhone phone;

    public SMSMessage() {

    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public CNMSGBody getBody() {
        return body;
    }

    public void setBody(CNMSGBody body) {
        this.body = body;
    }

    public CNMSGPhone getPhone() {
        return phone;
    }

    public void setPhone(CNMSGPhone phone) {
        this.phone = phone;
    }
}
