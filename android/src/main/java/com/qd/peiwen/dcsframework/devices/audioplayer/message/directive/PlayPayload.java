package com.qd.peiwen.dcsframework.devices.audioplayer.message.directive;

import com.google.gson.annotations.SerializedName;
import com.qd.peiwen.dcsframework.devices.audioplayer.message.entity.AudioItem;
import com.qd.peiwen.dcsframework.entity.payload.BasePayload;

/**
 * Created by nick on 2017/12/11.
 */

public class PlayPayload extends BasePayload {
    @SerializedName("audioItem")
    private AudioItem audioItem;
    @SerializedName("playBehavior")
    private String playBehavior;

    public PlayPayload() {
    }

    public AudioItem getAudioItem() {
        return audioItem;
    }

    public void setAudioItem(AudioItem audioItem) {
        this.audioItem = audioItem;
    }

    public String getPlayBehavior() {
        return playBehavior;
    }

    public void setPlayBehavior(String playBehavior) {
        this.playBehavior = playBehavior;
    }
}
