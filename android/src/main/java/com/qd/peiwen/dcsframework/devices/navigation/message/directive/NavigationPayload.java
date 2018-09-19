package com.qd.peiwen.dcsframework.devices.navigation.message.directive;

import com.qd.peiwen.dcsframework.entity.payload.BasePayload;

/**
 * Created by liudunjian on 2018/6/27.
 */

public class NavigationPayload extends BasePayload {

    private String destination;
    private String token;

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
