package com.qd.peiwen.dcsframework.devices.voiceoutput.message.state;

import com.google.gson.annotations.SerializedName;
import com.qd.peiwen.dcsframework.entity.payload.BasePayload;

/**
 * Created by nick on 2017/12/11.
 */

public class SpeechStatePayload extends BasePayload {
    @SerializedName("token")
    private String token;
    @SerializedName("playerActivity")
    private String playerActivity;
    @SerializedName("offsetInMilliseconds")
    private long offsetInMilliseconds;

    public SpeechStatePayload() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPlayerActivity() {
        return playerActivity;
    }

    public void setPlayerActivity(String playerActivity) {
        this.playerActivity = playerActivity;
    }

    public long getOffsetInMilliseconds() {
        return offsetInMilliseconds;
    }

    public void setOffsetInMilliseconds(long offsetInMilliseconds) {
        this.offsetInMilliseconds = offsetInMilliseconds;
    }
}
