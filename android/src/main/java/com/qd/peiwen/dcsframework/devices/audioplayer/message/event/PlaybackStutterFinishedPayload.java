package com.qd.peiwen.dcsframework.devices.audioplayer.message.event;

import com.google.gson.annotations.SerializedName;
import com.qd.peiwen.dcsframework.entity.payload.BasePayload;

/**
 * Created by nick on 2017/12/11.
 */

public class PlaybackStutterFinishedPayload extends BasePayload {
    @SerializedName("token")
    private String token;
    @SerializedName("offsetInMilliseconds")
    private long offsetInMilliseconds;
    @SerializedName("stutterDurationInMilliseconds")
    private long stutterDurationInMilliseconds;

    public PlaybackStutterFinishedPayload() {
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

    public long getStutterDurationInMilliseconds() {
        return stutterDurationInMilliseconds;
    }

    public void setStutterDurationInMilliseconds(long stutterDurationInMilliseconds) {
        this.stutterDurationInMilliseconds = stutterDurationInMilliseconds;
    }
}
