package com.qd.peiwen.dcsframework.devices.phonebill;

import android.content.Context;


import com.qd.peiwen.dcsframework.devices.BaseModule;
import com.qd.peiwen.dcsframework.devices.PayloadManager;
import com.qd.peiwen.dcsframework.devices.phonebill.listener.IPhoneBillModuleListener;
import com.qd.peiwen.dcsframework.devices.phonebill.message.directive.PhoneBillPayload;
import com.qd.peiwen.dcsframework.entity.header.BaseHeader;
import com.qd.peiwen.dcsframework.entity.payload.BasePayload;
import com.qd.peiwen.dcsframework.entity.respons.Directive;
import com.qd.peiwen.dcsframework.tools.LogUtils;

import java.lang.ref.WeakReference;

/**
 * Created by liudunjian on 2018/6/27.
 */

public class PhoneBillModule extends BaseModule {

    private WeakReference<IPhoneBillModuleListener> listener;

    public PhoneBillModule(Context context) {
        super(context, ApiConstants.NAME, ApiConstants.NAMESPACE);
    }

    public void setListener(IPhoneBillModuleListener listener) {
        this.listener = new WeakReference<>(listener);
    }

    @Override
    public boolean handleDirective(Directive directive) {
        LogUtils.d("handleDirective----------phobe bill");
        BaseHeader header = directive.getHeader();
        BasePayload payload = directive.getPayload();
        if (ApiConstants.Directives.Phonecharge.NAME.equals(header.getName())) {
            LogUtils.d("handleDirective-----------");
            return processPhoneBillPayload(payload);
        }
        return super.handleDirective(directive);
    }

    /************************ 指令处理方法 **********************************/
    private boolean processPhoneBillPayload(BasePayload payload) {
        if (payload instanceof PhoneBillPayload) {
            PhoneBillPayload phoneBill = (PhoneBillPayload) payload;
            this.fireRecvPhoneBill(phoneBill);
        }
        return true;
    }

    /************************ Listener分发方法 **********************************/
    private void fireRecvPhoneBill(PhoneBillPayload payload) {
        if (null != this.listener && null != listener.get()) {
            this.listener.get().onRecvPhoneBill(payload);
        }
    }

    static {
        PayloadManager.getInstance().insertPayload(ApiConstants.NAMESPACE,
                ApiConstants.Directives.Phonecharge.NAME,
                PhoneBillPayload.class);
    }
}
