package com.qd.peiwen.dcsframework.devices.ticket.message.directive;


import com.qd.peiwen.dcsframework.entity.payload.BasePayload;

/**
 * Created by liudunjian on 2018/7/5.
 */

public class TicketPayload extends BasePayload {
    String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
