package com.qd.peiwen.dcsframework.devices.phonebill.listener;


import com.qd.peiwen.dcsframework.devices.phonebill.message.directive.PhoneBillPayload;

/**
 * Created by liudunjian on 2018/6/27.
 */

public interface IPhoneBillModuleListener {
    void onRecvPhoneBill(PhoneBillPayload payload);
}
