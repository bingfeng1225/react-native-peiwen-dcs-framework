package com.qd.peiwen.dcsframework.volume;

/**
 * Created by nick on 2017/12/15.
 */

public interface IVolumeListener {
    void onMuteChanged(int volume);

    void onVolumeChanged(int volume);
}
