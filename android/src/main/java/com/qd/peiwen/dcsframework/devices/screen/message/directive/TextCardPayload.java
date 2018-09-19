package com.qd.peiwen.dcsframework.devices.screen.message.directive;

import com.google.gson.annotations.SerializedName;
import com.qd.peiwen.dcsframework.devices.screen.message.entity.LinkStructure;
import com.qd.peiwen.dcsframework.entity.payload.BasePayload;

/**
 * Created by nick on 2017/12/5.
 */


public class TextCardPayload extends BasePayload {
    @SerializedName("type")
    private String type;
    @SerializedName("token")
    private String token;
    @SerializedName("content")
    private String content;
    @SerializedName("link")
    private LinkStructure link;


    public TextCardPayload() {
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LinkStructure getLink() {
        return link;
    }

    public void setLink(LinkStructure link) {
        this.link = link;
    }
}
