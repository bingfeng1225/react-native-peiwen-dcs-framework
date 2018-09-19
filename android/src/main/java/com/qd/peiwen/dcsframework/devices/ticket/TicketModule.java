package com.qd.peiwen.dcsframework.devices.ticket;

import android.content.Context;


import com.qd.peiwen.dcsframework.devices.BaseModule;
import com.qd.peiwen.dcsframework.devices.PayloadManager;
import com.qd.peiwen.dcsframework.devices.ticket.listener.ITicketModuleListener;
import com.qd.peiwen.dcsframework.devices.ticket.message.directive.TicketPayload;
import com.qd.peiwen.dcsframework.entity.header.BaseHeader;
import com.qd.peiwen.dcsframework.entity.payload.BasePayload;
import com.qd.peiwen.dcsframework.entity.respons.Directive;
import com.qd.peiwen.dcsframework.tools.LogUtils;

import java.lang.ref.WeakReference;

/**
 * Created by liudunjian on 2018/7/5.
 */

public class TicketModule extends BaseModule {

    private WeakReference<ITicketModuleListener> listener;

    public TicketModule(Context context) {
        super(context, ApiConstants.NAME, ApiConstants.NAMESPACE);
    }

    public void setListener(ITicketModuleListener listener) {
        this.listener = new WeakReference<>(listener);
    }

    public void orderTicket(String url) {

    }

    /************************ Base重写方法 **********************************/
    @Override
    public boolean handleDirective(Directive directive) {
        LogUtils.d("handleDirective------inTIcketModeul");
        BaseHeader header = directive.getHeader();
        BasePayload payload = directive.getPayload();
        if (ApiConstants.Directives.Train.NAME.equals(header.getName())) {
            return processTrainTicketPayload(payload);
        }else if(ApiConstants.Directives.Flight.NAME.equals(header.getName())) {
            return processFlightTicketPayload(payload);
        }
        return super.handleDirective(directive);
    }

    @Override
    public void release() {
        super.release();
        this.listener = null;
    }


    /************************ 指令处理方法 **********************************/
    private boolean processTrainTicketPayload(BasePayload payload) {
        LogUtils.d("processTrainTicketPayload");
        if (payload instanceof TicketPayload) {
            TicketPayload ticket = (TicketPayload) payload;
            this.fireBookTrainTicket(ticket);
        }
        return true;
    }

    private boolean processFlightTicketPayload(BasePayload payload) {
        LogUtils.d("processFlightTicketPayload");
        if (payload instanceof TicketPayload) {
            TicketPayload ticket = (TicketPayload) payload;
            this.fireBookFLightTicket(ticket);
        }
        return true;
    }

    /************************ Listener分发方法 **********************************/
    private void fireBookTrainTicket(TicketPayload payload) {
        if (null != this.listener && null != listener.get()) {
            this.listener.get().onRecvTrainTicketPayload(payload);
        }
    }

    private void fireBookFLightTicket(TicketPayload payload) {
        if (null != this.listener && null != listener.get()) {
            this.listener.get().onRecvFlightTicketPayload(payload);
        }
    }

    static {
        PayloadManager.getInstance().insertPayload(
                ApiConstants.NAMESPACE,
                ApiConstants.Directives.Train.NAME,
                TicketPayload.class
        );

        PayloadManager.getInstance().insertPayload(
                ApiConstants.NAMESPACE,
                ApiConstants.Directives.Flight.NAME,
                TicketPayload.class
        );
    }
}
