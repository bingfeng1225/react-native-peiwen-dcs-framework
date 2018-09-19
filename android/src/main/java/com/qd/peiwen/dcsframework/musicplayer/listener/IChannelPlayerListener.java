package com.qd.peiwen.dcsframework.musicplayer.listener;


import com.qd.peiwen.dcsframework.musicplayer.ChannelPlayer;
import com.qd.peiwen.dcsframework.musicplayer.enmudefine.ErrorType;
import com.qd.peiwen.dcsframework.musicplayer.enmudefine.PlayerState;

/**
 * Created by nick on 2017/3/29.
 */

public interface IChannelPlayerListener {
    void onPlayerStoped(ChannelPlayer player, Object object);

    void onPlayerCompleted(ChannelPlayer player, Object object);

    void onPlayerBufferingStart(ChannelPlayer player, Object object);

    void onPlayerBufferingEnded(ChannelPlayer player, Object object);

    void onBufferingUpdated(ChannelPlayer player, Object object, int bufferPercentage);

    void onPlayerErrorOccurred(ChannelPlayer player, Object object, ErrorType error);

    void onPlayerStateChanged(ChannelPlayer player, Object object, PlayerState state);

    void onPlayerProgressChanged(ChannelPlayer player, Object object, int position, int duration);
}
