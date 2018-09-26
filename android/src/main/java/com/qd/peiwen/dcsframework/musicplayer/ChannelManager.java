package com.qd.peiwen.dcsframework.musicplayer;

import android.content.Context;


import com.qd.peiwen.dcsframework.musicplayer.enmudefine.ChannelType;
import com.qd.peiwen.dcsframework.tools.LogUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nick on 2017/12/13.
 */

public class ChannelManager {
    private Context context;
    private Map<ChannelType, ChannelPlayer> players = null;

    public ChannelManager(Context context) {
        this.players = new HashMap<>();
        this.context = context.getApplicationContext();
    }

    public void addPlayer(ChannelPlayer player) {
        this.players.put(player.channelType(), player);
    }

    public synchronized void enterBackground() {
        LogUtils.e("enterBackground----------------");
        if (null == this.players) {
            return;
        }
        for (ChannelPlayer player : this.players.values()) {
            player.enterBackground();
        }
    }

    public synchronized void becomeForeground() {
        LogUtils.e("becomeForeground----------------");
        if (null == this.players) {
            return;
        }
        for (ChannelPlayer player : this.players.values()) {
            player.becomeForeground();
        }
    }

    public synchronized void audioRecordStarted() {
        LogUtils.e("audioRecordStarted----------------");
        if (null == this.players) {
            return;
        }
        for (ChannelPlayer player : this.players.values()) {
            player.audioRecordStarted();
        }
    }

    public synchronized void audioRecordFinished() {
        LogUtils.e("audioRecordFinished----------------");
        if (null == this.players) {
            return;
        }
        for (ChannelPlayer player : this.players.values()) {
            player.audioRecordFinished();
        }
    }

    public synchronized void dialogChannelReleased() {
        LogUtils.e("dialogChannelReleased----------------");
        if (null == this.players) {
            return;
        }
        for (ChannelPlayer player : this.players.values()) {
            player.dialogChannelReleased();
        }
    }


    public synchronized void dialogChannelOccupied() {
        LogUtils.e("dialogChannelOccupied----------------");
        if (null == this.players) {
            return;
        }
        for (ChannelPlayer player : this.players.values()) {
            player.dialogChannelOccupied();
        }
    }

    public synchronized void speakChannelReleased() {
        LogUtils.e("speakChannelReleased----------------");
        if (null == this.players) {
            return;
        }
        for (ChannelPlayer player : this.players.values()) {
            player.channelStateChanged(ChannelType.SPEAK, false);
        }
    }

    public synchronized void speakChannelOccupied() {
        LogUtils.e("speakChannelOccupied----------------");
        if (null == this.players) {
            return;
        }
        for (ChannelPlayer player : this.players.values()) {
            player.channelStateChanged(ChannelType.SPEAK, true);
        }
    }

    public synchronized void audioChannelReleased() {
        LogUtils.e("audioChannelReleased----------------");
        if (null == this.players) {
            return;
        }
        for (ChannelPlayer player : this.players.values()) {
            player.channelStateChanged(ChannelType.AUDIO, false);
        }
    }

    public synchronized void audioChannelOccupied() {
        LogUtils.e("audioChannelOccupied----------------");
        if (null == this.players) {
            return;
        }
        for (ChannelPlayer player : this.players.values()) {
            player.channelStateChanged(ChannelType.AUDIO, true);
        }
    }
    

    public void release() {
        this.players = null;
    }
}
