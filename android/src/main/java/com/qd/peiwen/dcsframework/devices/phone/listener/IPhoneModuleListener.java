package com.qd.peiwen.dcsframework.devices.phone.listener;

import com.qd.peiwen.dcsframework.entity.respons.DCSRespons;

/**
 * Created by nick on 2018/3/6.
 */

public interface IPhoneModuleListener {
    void onPhoneDialogFinished(String uuid);

    void onPhoneModuleDirective(DCSRespons respons);
}
