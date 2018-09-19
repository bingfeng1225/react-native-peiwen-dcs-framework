package com.qd.peiwen.dcsframework.devices.audioplayer.message.event;

import com.google.gson.annotations.SerializedName;
import com.qd.peiwen.dcsframework.entity.payload.BasePayload;

import java.util.Map;

/**
 * Created by nick on 2017/12/11.
 */

public class StreamMetadataExtractedPayload extends BasePayload {
    @SerializedName("token")
    private String token;
    @SerializedName("metadata")
    private Map<String,Object> metadata;

    public StreamMetadataExtractedPayload() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }
}
