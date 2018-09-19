package com.qd.peiwen.dcsframework.devices.sms.message.directive;

import com.google.gson.annotations.SerializedName;
import com.qd.peiwen.dcsframework.entity.payload.BasePayload;
import com.qd.peiwen.dcsframework.pinyinsearch.pinyin.CN;

/**
 * Created by nick on 2018/3/6.
 */

public class SMSSendPayload extends BasePayload implements CN {
    @SerializedName("name")
    private String name;
    @SerializedName("msgbody")
    private String msgbody;

    public SMSSendPayload() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMSGBody() {
        return msgbody;
    }

    public void setMSGBody(String msgbody) {
        this.msgbody = msgbody;
    }

    @Override
    public String chinese() {
        return name;
    }
}
