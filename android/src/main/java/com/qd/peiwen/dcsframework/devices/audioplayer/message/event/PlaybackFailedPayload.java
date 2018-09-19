package com.qd.peiwen.dcsframework.devices.audioplayer.message.event;

import com.google.gson.annotations.SerializedName;
import com.qd.peiwen.dcsframework.devices.system.message.entity.ErrorMessager;
import com.qd.peiwen.dcsframework.entity.payload.BasePayload;

/**
 * Created by nick on 2017/12/11.
 */

public class PlaybackFailedPayload extends BasePayload {
    @SerializedName("token")
    private String token;
    @SerializedName("error")
    private ErrorMessager error;

    public PlaybackFailedPayload() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ErrorMessager getError() {
        return error;
    }

    public void setError(ErrorMessager error) {
        this.error = error;
    }
}
