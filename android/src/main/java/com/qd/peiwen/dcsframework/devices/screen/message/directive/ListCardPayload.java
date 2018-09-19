package com.qd.peiwen.dcsframework.devices.screen.message.directive;

import com.google.gson.annotations.SerializedName;
import com.qd.peiwen.dcsframework.devices.screen.message.entity.LinkStructure;
import com.qd.peiwen.dcsframework.devices.screen.message.entity.ListItem;
import com.qd.peiwen.dcsframework.entity.payload.BasePayload;

import java.util.List;

/**
 * Created by nick on 2017/12/5.
 */

public class ListCardPayload  extends BasePayload {
    @SerializedName("type")
    private String type;
    @SerializedName("token")
    private String token;
    @SerializedName("link")
    private LinkStructure link;
    @SerializedName("list")
    private List<ListItem> listItems;

    public ListCardPayload() {
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

    public LinkStructure getLink() {
        return link;
    }

    public void setLink(LinkStructure link) {
        this.link = link;
    }

    public List<ListItem> getListItems() {
        return listItems;
    }

    public void setListItems(List<ListItem> listItems) {
        this.listItems = listItems;
    }
}
