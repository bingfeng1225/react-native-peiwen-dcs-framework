package com.qd.peiwen.dcsframework.devices.voiceoutput.message.enevt;

import com.google.gson.annotations.SerializedName;
import com.qd.peiwen.dcsframework.entity.payload.BasePayload;

/**
 * Created by nick on 2017/12/11.
 */

public class SpeechStartedPayload extends BasePayload {
    @SerializedName("token")
    private String token;

    public SpeechStartedPayload() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}