package com.qd.peiwen.dcsframework.devices.voicerecognize;

import android.content.Context;


import com.qd.peiwen.dcsframework.devices.BaseModule;
import com.qd.peiwen.dcsframework.devices.PayloadManager;
import com.qd.peiwen.dcsframework.devices.voicerecognize.listener.IVoiceRecognizeModuleListener;
import com.qd.peiwen.dcsframework.devices.voicerecognize.message.directive.VoiceRecognizePayload;
import com.qd.peiwen.dcsframework.enmudefine.SpeechState;
import com.qd.peiwen.dcsframework.entity.header.BaseHeader;
import com.qd.peiwen.dcsframework.entity.payload.BasePayload;
import com.qd.peiwen.dcsframework.entity.request.ClientContext;
import com.qd.peiwen.dcsframework.entity.respons.Directive;

import java.lang.ref.WeakReference;

/**
 * Created by nick on 2018/1/26.
 */

public class VoiceRecognizeModule extends BaseModule {
    private SpeechState speechState = SpeechState.FINISHED;
    private WeakReference<IVoiceRecognizeModuleListener> listener;

    public VoiceRecognizeModule(Context context) {
        super(context, ApiConstants.NAME, ApiConstants.NAMESPACE);
    }

    public void setListener(IVoiceRecognizeModuleListener listener) {
        this.listener = new WeakReference<>(listener);
    }

    /************************ Base重写方法 **********************************/
    @Override
    public void init() {
        super.init();
    }

    @Override
    public ClientContext clientContext() {
        return null;
    }

    @Override
    public boolean handleDirective(Directive directive) {
        BaseHeader header = directive.getHeader();
        BasePayload payload = directive.getPayload();
        if (ApiConstants.Directives.VoiceRecognize.NAME.equals(header.getName())) {
            return this.processVoiceRecognizePayload(payload);
        }
        return super.handleDirective(directive);
    }


    @Override
    public void release() {
        super.release();
        this.listener = null;
    }

    /************************ 指令处理方法 **********************************/
    private boolean processVoiceRecognizePayload(BasePayload payload) {
        if (payload instanceof VoiceRecognizePayload) {
            VoiceRecognizePayload recognize = (VoiceRecognizePayload) payload;
            this.fireRecvVoiceRecognize(recognize);
        }
        return true;
    }

    /************************ Listener分发方法 **********************************/
    private void fireRecvVoiceRecognize(VoiceRecognizePayload payload) {
        if (null != this.listener && null != listener.get()) {
            this.listener.get().onRecvVoiceRecognize(payload);
        }
    }

    static {
        PayloadManager.getInstance().insertPayload(
                ApiConstants.NAMESPACE,
                ApiConstants.Directives.VoiceRecognize.NAME,
                VoiceRecognizePayload.class
        );
    }
}
