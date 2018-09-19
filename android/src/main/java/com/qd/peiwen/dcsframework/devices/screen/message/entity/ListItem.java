package com.qd.peiwen.dcsframework.devices.screen.message.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nick on 2017/12/5.
 */

public class ListItem {
    @SerializedName("url")
    private String url;
    @SerializedName("title")
    private String title;
    @SerializedName("content")
    private String content;
    @SerializedName("image")
    ImageStructure image;

    public ListItem() {

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ImageStructure getImage() {
        return image;
    }

    public void setImage(ImageStructure image) {
        this.image = image;
    }
}
