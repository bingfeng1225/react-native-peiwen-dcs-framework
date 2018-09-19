package com.qd.peiwen.dcsframework.devices.voiceoutput.listener;

/**
 * Created by nick on 2017/12/11.
 */

public interface IVoiceOutputModuleListener {

    void onSpeakChannelOccupied();

    void onSpeakChannelReleased();

    void onSpeechStarted(String token);

    void onSpeechFinished(String token);

}
