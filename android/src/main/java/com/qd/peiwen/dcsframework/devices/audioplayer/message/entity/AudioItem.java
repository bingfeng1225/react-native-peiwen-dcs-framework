package com.qd.peiwen.dcsframework.devices.audioplayer.message.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nick on 2017/12/11.
 */

public class AudioItem {
    @SerializedName("audioItemId")
    private String audioItemId;

    @SerializedName("stream")
    private AudioStream stream;


    public AudioItem() {
    }

    public String getAudioItemId() {
        return audioItemId;
    }

    public void setAudioItemId(String audioItemId) {
        this.audioItemId = audioItemId;
    }

    public AudioStream getStream() {
        return stream;
    }

    public void setStream(AudioStream stream) {
        this.stream = stream;
    }
}
