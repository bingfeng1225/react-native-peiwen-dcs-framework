package com.qd.peiwen.dcsframework.devices.lifevoice;

import android.content.Context;


import com.qd.peiwen.dcsframework.devices.BaseModule;
import com.qd.peiwen.dcsframework.devices.PayloadManager;
import com.qd.peiwen.dcsframework.devices.lifevoice.listener.ILifeVoiceModuleListener;
import com.qd.peiwen.dcsframework.devices.lifevoice.message.directive.LifeVoicePayload;
import com.qd.peiwen.dcsframework.entity.header.BaseHeader;
import com.qd.peiwen.dcsframework.entity.payload.BasePayload;
import com.qd.peiwen.dcsframework.entity.respons.Directive;
import com.qd.peiwen.dcsframework.tools.LogUtils;

import java.lang.ref.WeakReference;

/**
 * Created by liudunjian on 2018/5/17.
 */

public class LifeVoiceModule extends BaseModule {

    private WeakReference<ILifeVoiceModuleListener> listener;

    public LifeVoiceModule(Context context) {
        super(context, ApiConstants.NAME, ApiConstants.NAMESPACE);
    }

    public void setListener(ILifeVoiceModuleListener listener) {
        this.listener = new WeakReference<>(listener);
    }

    @Override
    public boolean handleDirective(Directive directive) {
        BaseHeader header = directive.getHeader();
        BasePayload payload = directive.getPayload();
        if (ApiConstants.Directives.LifeVoice.NAME.equals(header.getName())) {
            LogUtils.d("handleDirective-----------");
            return processLifeVoicePayload(payload);
        }
        return super.handleDirective(directive);
    }

    /************************ 指令处理方法 **********************************/
    private boolean processLifeVoicePayload(BasePayload payload) {
        if (payload instanceof LifeVoicePayload) {
            LifeVoicePayload lifeVoice = (LifeVoicePayload) payload;
            this.fireRecvLifeVoice(lifeVoice);
        }
        return true;
    }

    /************************ Listener分发方法 **********************************/
    private void fireRecvLifeVoice(LifeVoicePayload payload) {
        if (null != this.listener && null != listener.get()) {
            this.listener.get().onRecvLifeVoice(payload);
        }
    }

    static {
        PayloadManager.getInstance().insertPayload(ApiConstants.NAMESPACE,
                ApiConstants.Directives.LifeVoice.NAME,
                LifeVoicePayload.class);
    }

}
