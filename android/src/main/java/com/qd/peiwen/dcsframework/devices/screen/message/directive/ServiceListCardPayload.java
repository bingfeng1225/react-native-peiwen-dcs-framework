package com.qd.peiwen.dcsframework.devices.screen.message.directive;

import com.google.gson.annotations.SerializedName;
import com.qd.peiwen.dcsframework.entity.Service;
import com.qd.peiwen.dcsframework.entity.payload.BasePayload;

import java.util.List;

/**
 * Created by nick on 2018/1/26.
 */

public class ServiceListCardPayload extends BasePayload {
    @SerializedName("type")
    private String type;
    @SerializedName("token")
    private String token;
    @SerializedName("list")
    private List<Service> services;

    public ServiceListCardPayload() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }
}
