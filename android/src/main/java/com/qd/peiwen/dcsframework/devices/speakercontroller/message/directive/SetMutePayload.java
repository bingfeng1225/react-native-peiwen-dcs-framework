package com.qd.peiwen.dcsframework.devices.speakercontroller.message.directive;

import com.google.gson.annotations.SerializedName;
import com.qd.peiwen.dcsframework.entity.payload.BasePayload;

/**
 * Created by nick on 2017/12/12.
 */

public class SetMutePayload extends BasePayload {
    @SerializedName("mute")
    private boolean mute;

    public SetMutePayload() {
    }

    public boolean isMute() {
        return mute;
    }

    public void setMute(boolean mute) {
        this.mute = mute;
    }
}
