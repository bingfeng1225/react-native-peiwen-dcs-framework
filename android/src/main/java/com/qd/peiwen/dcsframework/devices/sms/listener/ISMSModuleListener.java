package com.qd.peiwen.dcsframework.devices.sms.listener;


import com.qd.peiwen.dcsframework.entity.respons.DCSRespons;

/**
 * Created by nick on 2018/3/6.
 */

public interface ISMSModuleListener {
    void onSMSDialogFinished(String uuid);

    void onSMSModuleDirective(DCSRespons respons);
}
