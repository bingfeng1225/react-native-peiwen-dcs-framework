package com.qd.peiwen.dcsframework.devices.screen.message.entity;


import com.qd.peiwen.dcsframework.devices.screen.message.directive.ListCardPayload;

/**
 * Created by nick on 2018/3/19.
 */

public class ListCardLink {
    private LinkStructure link;
    private ListCardPayload payload;

    public ListCardLink() {
    }

    public LinkStructure getLink() {
        return link;
    }

    public void setLink(LinkStructure link) {
        this.link = link;
    }

    public ListCardPayload getPayload() {
        return payload;
    }

    public void setPayload(ListCardPayload payload) {
        this.payload = payload;
    }
}
