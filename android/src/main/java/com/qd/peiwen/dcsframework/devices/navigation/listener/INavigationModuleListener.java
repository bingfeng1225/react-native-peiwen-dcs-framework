package com.qd.peiwen.dcsframework.devices.navigation.listener;


import com.qd.peiwen.dcsframework.devices.navigation.message.directive.NavigationPayload;

/**
 * Created by liudunjian on 2018/6/27.
 */

public interface INavigationModuleListener {
    void onNavigationPayload(NavigationPayload payload);
}
