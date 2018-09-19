package com.qd.peiwen.dcsframework.devices.speakercontroller.message.state;

import com.google.gson.annotations.SerializedName;
import com.qd.peiwen.dcsframework.entity.payload.BasePayload;

/**
 * Created by nick on 2017/12/12.
 */

public class VolumeStatePayload extends BasePayload {
    @SerializedName("volume")
    private long volume;
    @SerializedName("muted")
    private boolean muted;

    public VolumeStatePayload() {
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    public boolean isMuted() {
        return muted;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }
}
