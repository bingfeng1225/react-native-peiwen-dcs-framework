package com.qd.peiwen.dcsframework.devices.audioplayer.message.event;

import com.google.gson.annotations.SerializedName;
import com.qd.peiwen.dcsframework.entity.payload.BasePayload;

/**
 * Created by nick on 2017/12/11.
 */

public class PlaybackStoppedPayload extends BasePayload {
    @SerializedName("token")
    public String token;
    @SerializedName("offsetInMilliseconds")
    public long offsetInMilliseconds;

    public PlaybackStoppedPayload() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getOffsetInMilliseconds() {
        return offsetInMilliseconds;
    }

    public void setOffsetInMilliseconds(long offsetInMilliseconds) {
        this.offsetInMilliseconds = offsetInMilliseconds;
    }
}
