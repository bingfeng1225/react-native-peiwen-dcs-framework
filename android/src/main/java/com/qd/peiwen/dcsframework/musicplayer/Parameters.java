package com.qd.peiwen.dcsframework.musicplayer;



import com.qd.peiwen.dcsframework.musicplayer.enmudefine.ChannelType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nick on 2017/12/26.
 */

public class Parameters {
    public int duration = 0;
    public int position = 0;
    public Object playObject = null;
    public boolean pauseByUser = false;
    public boolean enterBackground = false;
    public boolean audioFocusLossed = false;
    public boolean audioRecordStarted = false;
    public boolean dialogChannelOccupied = false;

    public ChannelType channelType = ChannelType.NONE;
    public Map<ChannelType, Boolean> channelStates = null;

    public void initChannelStates() {
        this.channelStates = new HashMap<>();
        this.channelStates.put(ChannelType.SPEAK, false);
        this.channelStates.put(ChannelType.AUDIO, false);
    }

    public boolean isConditionsMeetRequirements() {
        if (pauseByUser) {
            return false;
        }
        if (enterBackground) {
            return false;
        }
        if (audioFocusLossed) {
            return false;
        }
        if (audioRecordStarted) {
            return false;
        }
        if (dialogChannelOccupied) {
            return false;
        }
        return isConditionOfChannelState();
    }

    private boolean isConditionOfChannelState() {
        ChannelType channelType = findHeightChannnelType();
        if (this.channelType.priority() >= channelType.priority()) {
            return true;
        }
        return false;
    }

    private ChannelType findHeightChannnelType() {
        ChannelType channelType = ChannelType.NONE;
        for (ChannelType type : this.channelStates.keySet()) {
            boolean channelState = this.channelStates.get(type);
            if (channelState && type.priority() > channelType.priority()) {
                channelType = type;
            }
        }
        return channelType;
    }
}
