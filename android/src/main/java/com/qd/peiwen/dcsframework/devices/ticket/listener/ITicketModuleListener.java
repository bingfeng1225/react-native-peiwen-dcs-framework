package com.qd.peiwen.dcsframework.devices.ticket.listener;


import com.qd.peiwen.dcsframework.devices.ticket.message.directive.TicketPayload;

/**
 * Created by liudunjian on 2018/7/5.
 */

public interface ITicketModuleListener {
    void onRecvTrainTicketPayload(TicketPayload payload);
    void onRecvFlightTicketPayload(TicketPayload payload);
}
