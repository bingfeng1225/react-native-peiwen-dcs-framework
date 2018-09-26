package com.qd.peiwen.dcsframework.devices.speakercontroller;

import android.content.Context;


import com.qd.peiwen.dcsframework.devices.BaseModule;
import com.qd.peiwen.dcsframework.devices.PayloadManager;
import com.qd.peiwen.dcsframework.devices.speakercontroller.listener.ISpeakerControllerModuleListener;
import com.qd.peiwen.dcsframework.devices.speakercontroller.message.directive.AdjustVolumePayload;
import com.qd.peiwen.dcsframework.devices.speakercontroller.message.directive.SetMutePayload;
import com.qd.peiwen.dcsframework.devices.speakercontroller.message.directive.SetVolumePayload;
import com.qd.peiwen.dcsframework.devices.speakercontroller.message.event.MuteChangedPayload;
import com.qd.peiwen.dcsframework.devices.speakercontroller.message.event.VolumeChangedPayload;
import com.qd.peiwen.dcsframework.entity.header.BaseHeader;
import com.qd.peiwen.dcsframework.entity.header.MessageIdHeader;
import com.qd.peiwen.dcsframework.entity.payload.BasePayload;
import com.qd.peiwen.dcsframework.entity.request.EventMessage;
import com.qd.peiwen.dcsframework.entity.respons.Directive;
import com.qd.peiwen.dcsframework.tools.IDCreator;
import com.qd.peiwen.dcsframework.volume.VolumeManager;

import java.lang.ref.WeakReference;

/**
 * Created by nick on 2017/12/12.
 */

public class SpeakerControllerModule extends BaseModule {
    private WeakReference<ISpeakerControllerModuleListener> listener;

    public SpeakerControllerModule(Context context) {
        super(context, ApiConstants.NAME, ApiConstants.NAMESPACE);
    }

    public void setListener(ISpeakerControllerModuleListener listener) {
        this.listener = new WeakReference<>(listener);
    }


    /************************ Base重写方法 **********************************/
    @Override
    public boolean handleDirective(Directive directive) {
        BaseHeader header = directive.getHeader();
        BasePayload payload = directive.getPayload();
        if (ApiConstants.Directives.SetMute.NAME.equals(header.getName())) {
            return processSetMutePayload(payload);
        } else if (ApiConstants.Directives.SetVolume.NAME.equals(header.getName())) {
            return processSetVolumePayload(payload);

        } else if (ApiConstants.Directives.AdjustVolume.NAME.equals(header.getName())) {
            return processAdjustVolumePayload(payload);
        }
        return super.handleDirective(directive);
    }

    @Override
    public void release() {
        super.release();
        this.listener = null;
    }

    /************************ 事件封装方法 **********************************/
    public EventMessage muteChangedRequest(int volume, boolean muted) {
        MessageIdHeader header = IDCreator.createMessageIdHeader(
                ApiConstants.NAMESPACE,
                ApiConstants.Events.MuteChanged.NAME
        );
        MuteChangedPayload payload = new MuteChangedPayload();
        payload.setMuted(muted);
        payload.setVolume(volume);
        return new EventMessage(header, payload);
    }

    public EventMessage volumeChangedRequest(int volume, boolean muted) {
        MessageIdHeader header = IDCreator.createMessageIdHeader(
                ApiConstants.NAMESPACE,
                ApiConstants.Events.VolumeChanged.NAME
        );
        VolumeChangedPayload payload = new VolumeChangedPayload();
        payload.setMuted(muted);
        payload.setVolume(volume);
        return new EventMessage(header, payload);
    }

    /************************ 指令处理方法 **********************************/
    private boolean processSetMutePayload(BasePayload payload) {
        if (payload instanceof SetMutePayload) {
            fireSetMute((SetMutePayload) payload);
            return true;
        }
        return false;
    }

    private boolean processSetVolumePayload(BasePayload payload) {
        if (payload instanceof SetVolumePayload) {
            fireSetVolume((SetVolumePayload) payload);
            return true;
        }
        return false;
    }

    private boolean processAdjustVolumePayload(BasePayload payload) {
        if (payload instanceof AdjustVolumePayload) {
            fireAdjustVolume((AdjustVolumePayload) payload);
            return true;
        }
        return false;
    }

    /************************ Listener分发方法 **********************************/
    private void fireSetMute(SetMutePayload mutepayload) {
        if (null != this.listener && null != listener.get()) {
            this.listener.get().onSetMute(mutepayload);
        }
    }

    private void fireSetVolume(SetVolumePayload volumepayload) {
        if (null != this.listener && null != listener.get()) {
            this.listener.get().onSetVolume(volumepayload);
        }
    }

    private void fireAdjustVolume(AdjustVolumePayload volumepayload) {
        if (null != this.listener && null != listener.get()) {
            this.listener.get().onAdjustVolume(volumepayload);
        }
    }

    static {
        PayloadManager.getInstance().insertPayload(
                ApiConstants.NAMESPACE,
                ApiConstants.Directives.SetMute.NAME,
                SetMutePayload.class
        );
        PayloadManager.getInstance().insertPayload(
                ApiConstants.NAMESPACE,
                ApiConstants.Directives.SetVolume.NAME,
                SetVolumePayload.class
        );
        PayloadManager.getInstance().insertPayload(
                ApiConstants.NAMESPACE,
                ApiConstants.Directives.AdjustVolume.NAME,
                AdjustVolumePayload.class
        );
    }
}
