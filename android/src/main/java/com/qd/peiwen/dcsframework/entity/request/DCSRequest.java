package com.qd.peiwen.dcsframework.entity.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nick on 2017/11/28.
 */

//{
//  "clientContext": [{
//        "header": {"name": "AlertsState","namespace": "ai.dueros.device_interface.alerts"},
//        "payload": {"activeAlerts": [],"allAlerts": []}
//  }],
//  "event": {
//      "header": {"name": "TextInput","namespace": "ai.dueros.device_interface.text_input","messageId": "e9c91dcf-c33e-4fb5-bddd-4ea4a3057e0b","dialogRequestId": "876c1561-f4af-491e-acd9-a6057fdc91ff"},
//      "payload": {"query": "你好"}
//  }
//}
public class DCSRequest {
    @SerializedName("event")
    private EventMessage event;
    @SerializedName("clientContext")
    private List<ClientContext> clientContexts;

    public DCSRequest() {
        
    }

    public EventMessage getEvent() {
        return event;
    }

    public void setEvent(EventMessage event) {
        this.event = event;
    }

    public List<ClientContext> getClientContexts() {
        return clientContexts;
    }

    public void setClientContexts(List<ClientContext> clientContexts) {
        this.clientContexts = clientContexts;
    }
}
