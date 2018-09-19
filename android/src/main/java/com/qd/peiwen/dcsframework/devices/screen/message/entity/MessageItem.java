package com.qd.peiwen.dcsframework.devices.screen.message.entity;


import com.qd.peiwen.dcsframework.devices.screen.message.directive.SMSMessageListCardPayload;
import com.qd.peiwen.dcsframework.pinyinsearch.entity.SMSMessage;

/**
 * Created by nick on 2018/3/19.
 */

public class MessageItem {
    private SMSMessage message;
    private SMSMessageListCardPayload payload;

    public MessageItem() {
    }

    public SMSMessage getMessage() {
        return message;
    }

    public void setMessage(SMSMessage message) {
        this.message = message;
    }

    public SMSMessageListCardPayload getPayload() {
        return payload;
    }

    public void setPayload(SMSMessageListCardPayload payload) {
        this.payload = payload;
    }
}
