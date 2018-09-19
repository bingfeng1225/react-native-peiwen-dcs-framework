package com.qd.peiwen.dcsframework.devices.speakercontroller.listener;


import com.qd.peiwen.dcsframework.devices.speakercontroller.message.directive.AdjustVolumePayload;
import com.qd.peiwen.dcsframework.devices.speakercontroller.message.directive.SetMutePayload;
import com.qd.peiwen.dcsframework.devices.speakercontroller.message.directive.SetVolumePayload;

/**
 * Created by nick on 2017/12/12.
 */

public interface ISpeakerControllerModuleListener {
    void onSetMute(SetMutePayload mutepayload);
    void onSetVolume(SetVolumePayload volumepayload);
    void onAdjustVolume(AdjustVolumePayload volumepayload);
}
