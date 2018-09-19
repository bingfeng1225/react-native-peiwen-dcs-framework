package com.qd.peiwen.dcsframework.entity.screen;

import com.qd.peiwen.dcsframework.devices.screen.message.directive.ListCardPayload;
import com.qd.peiwen.dcsframework.devices.screen.message.directive.PhoneListCardPayload;
import com.qd.peiwen.dcsframework.devices.screen.message.directive.SMSMessageListCardPayload;
import com.qd.peiwen.dcsframework.devices.screen.message.entity.ListCardItem;
import com.qd.peiwen.dcsframework.devices.screen.message.entity.ListCardLink;
import com.qd.peiwen.dcsframework.enmudefine.SendEventState;
import com.qd.peiwen.dcsframework.entity.payload.BasePayload;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by nick on 2018/1/26.
 */

public class SendEventCard {
    private String uuid;
    private String content;
    private long timestamp;

    public SendEventCard() {

    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

}
