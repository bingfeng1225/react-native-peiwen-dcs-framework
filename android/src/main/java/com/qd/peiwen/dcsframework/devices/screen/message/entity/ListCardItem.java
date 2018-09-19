package com.qd.peiwen.dcsframework.devices.screen.message.entity;

import com.qd.peiwen.dcsframework.devices.screen.message.directive.ListCardPayload;

/**
 * Created by nick on 2018/3/19.
 */

public class ListCardItem {
    private ListItem item;
    private ListCardPayload payload;

    public ListCardItem() {
    }

    public ListItem getItem() {
        return item;
    }

    public void setItem(ListItem item) {
        this.item = item;
    }

    public ListCardPayload getPayload() {
        return payload;
    }

    public void setPayload(ListCardPayload payload) {
        this.payload = payload;
    }
}
