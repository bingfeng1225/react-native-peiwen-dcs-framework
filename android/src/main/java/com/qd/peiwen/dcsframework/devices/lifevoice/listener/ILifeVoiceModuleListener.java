package com.qd.peiwen.dcsframework.devices.lifevoice.listener;


import com.qd.peiwen.dcsframework.devices.lifevoice.message.directive.LifeVoicePayload;

/**
 * Created by liudunjian on 2018/5/17.
 */

public interface ILifeVoiceModuleListener {
    void onRecvLifeVoice(LifeVoicePayload payload);
}
