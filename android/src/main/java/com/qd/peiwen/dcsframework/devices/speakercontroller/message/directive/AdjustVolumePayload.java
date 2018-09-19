package com.qd.peiwen.dcsframework.devices.speakercontroller.message.directive;

import com.google.gson.annotations.SerializedName;
import com.qd.peiwen.dcsframework.entity.payload.BasePayload;

/**
 * Created by nick on 2017/12/12.
 */

public class AdjustVolumePayload extends BasePayload {
    @SerializedName("volume")
    private int volume;

    public AdjustVolumePayload() {
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }
}
