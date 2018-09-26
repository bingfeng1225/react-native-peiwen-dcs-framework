package com.qd.peiwen.dcsframework.musicplayer.enmudefine;

/**
 * Created by nick on 2017/12/13.
 */

public enum ChannelType {
    NONE("NONE", 0),
    AUDIO("AUDIO", 1),
    SPEAK("SPEAK", 2);

    private final int priority;
    private final String channelName;

    //数字越大，优先级越高，播放优先级
    ChannelType(String channelName, int priority) {
        this.channelName = channelName;
        this.priority = priority;
    }

    public int priority() {
        return priority;
    }

    public String channelName() {
        return channelName;
    }
}
