package com.qd.peiwen.dcsframework.devices.phonebill.message.directive;


import com.qd.peiwen.dcsframework.entity.payload.BasePayload;

/**
 * Created by liudunjian on 2018/6/27.
 */

public class PhoneBillPayload extends BasePayload {

    private String message;
    private String token ;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
