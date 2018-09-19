package com.qd.peiwen.dcsframework.devices.system.listener;


import com.qd.peiwen.dcsframework.devices.system.message.directive.SetEndPointPayload;
import com.qd.peiwen.dcsframework.devices.system.message.directive.SetSessionidPayload;
import com.qd.peiwen.dcsframework.devices.system.message.directive.ThrowExceptionPayload;

/**
 * Created by nick on 2017/12/6.
 */

public interface ISystemModuleListener {
    void onResetUserInactivity();

    void onUserInactivityReport(long userInactivity);

    void onSetEndpoint(SetEndPointPayload endPointPayload);

    void onSetSessionid(SetSessionidPayload sessionidPayload);

    void onThrowException(ThrowExceptionPayload exceptionPayload);
}
