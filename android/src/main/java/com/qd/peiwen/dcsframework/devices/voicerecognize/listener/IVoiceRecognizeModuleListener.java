package com.qd.peiwen.dcsframework.devices.voicerecognize.listener;


import com.qd.peiwen.dcsframework.devices.voicerecognize.message.directive.VoiceRecognizePayload;

/**
 * Created by nick on 2017/12/11.
 */

public interface IVoiceRecognizeModuleListener {
    void onRecvVoiceRecognize(VoiceRecognizePayload payload);
}