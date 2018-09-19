package com.qd.peiwen.dcsframework.devices.sms.message.directive;

import com.google.gson.annotations.SerializedName;
import com.qd.peiwen.dcsframework.entity.payload.BasePayload;
import com.qd.peiwen.dcsframework.pinyinsearch.pinyin.CN;

/**
 * Created by nick on 2018/3/6.
 */

public class SMSSearchPayload extends BasePayload implements CN {
    @SerializedName("msgbody")
    private String msgbody;

    public SMSSearchPayload() {
    }
    public String getMSGBody() {
        return msgbody;
    }

    public void setMSGBody(String msgbody) {
        this.msgbody = msgbody;
    }
    @Override
    public String chinese() {
        return msgbody;
    }
}
