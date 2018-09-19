package com.qd.peiwen.dcsframework.devices.speakercontroller.message.event;

import com.google.gson.annotations.SerializedName;
import com.qd.peiwen.dcsframework.entity.payload.BasePayload;

/**
 * Created by nick on 2017/12/12.
 */

public class MuteChangedPayload extends BasePayload {
    @SerializedName("volume")
    private int volume;
    @SerializedName("muted")
    private boolean muted;

    public MuteChangedPayload() {
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public boolean isMuted() {
        return muted;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }
}
