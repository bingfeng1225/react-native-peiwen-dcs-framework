package com.qd.peiwen.dcsframework.devices.screen.message.directive;

import com.google.gson.annotations.SerializedName;
import com.qd.peiwen.dcsframework.devices.screen.message.entity.MessageItem;
import com.qd.peiwen.dcsframework.entity.payload.BasePayload;
import com.qd.peiwen.dcsframework.pinyinsearch.entity.SMSMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nick on 2018/3/16.
 */

public class SMSMessageListCardPayload extends BasePayload {
    @SerializedName("type")
    private String type;
    @SerializedName("token")
    private String token;
    @SerializedName("message")
    private List<SMSMessage> messages;

    public SMSMessageListCardPayload() {
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

    public List<SMSMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<SMSMessage> messages) {
        this.messages = messages;
    }


    public List<MessageItem> messageItems() {
        List<MessageItem> items = new ArrayList<>();
        for (SMSMessage message : messages) {
            MessageItem item = new MessageItem();
            item.setPayload(this);
            item.setMessage(message);
            items.add(item);
        }
        return items;
    }
}
