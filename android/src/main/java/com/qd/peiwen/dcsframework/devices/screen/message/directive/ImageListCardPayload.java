package com.qd.peiwen.dcsframework.devices.screen.message.directive;

import com.google.gson.annotations.SerializedName;
import com.qd.peiwen.dcsframework.devices.screen.message.entity.ImageStructure;
import com.qd.peiwen.dcsframework.entity.payload.BasePayload;

import java.util.List;

/**
 * Created by nick on 2017/12/5.
 */

public class ImageListCardPayload extends BasePayload {
    @SerializedName("type")
    private String type;
    @SerializedName("token")
    private String token;
    @SerializedName("imageList")
    private List<ImageStructure> imageList;

    public ImageListCardPayload() {
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

    public List<ImageStructure> getImageList() {
        return imageList;
    }

    public void setImageList(List<ImageStructure> imageList) {
        this.imageList = imageList;
    }
}
