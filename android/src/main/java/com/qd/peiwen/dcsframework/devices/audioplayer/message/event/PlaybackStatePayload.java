package com.qd.peiwen.dcsframework.devices.audioplayer.message.event;

import com.google.gson.annotations.SerializedName;
import com.qd.peiwen.dcsframework.entity.payload.BasePayload;

/**
 * Created by nick on 2017/12/11.
 */

public class PlaybackStatePayload extends BasePayload {
    @SerializedName("token")
    public String token;
    @SerializedName("playerActivity")
    public String playerActivity;
    @SerializedName("offsetInMilliseconds")
    public long offsetInMilliseconds;

    public PlaybackStatePayload() {
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
