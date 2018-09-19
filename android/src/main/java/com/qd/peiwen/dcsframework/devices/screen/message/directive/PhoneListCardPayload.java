package com.qd.peiwen.dcsframework.devices.screen.message.directive;

import com.google.gson.annotations.SerializedName;
import com.qd.peiwen.dcsframework.devices.screen.message.entity.PhoneItem;
import com.qd.peiwen.dcsframework.entity.payload.BasePayload;
import com.qd.peiwen.dcsframework.pinyinsearch.entity.CNContact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nick on 2018/3/16.
 */

public class PhoneListCardPayload extends BasePayload {
    @SerializedName("type")
    private String type;
    @SerializedName("body")
    private String body;
    @SerializedName("token")
    private String token;
    @SerializedName("contacts")
    private List<CNContact> contacts;

    public PhoneListCardPayload() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<CNContact> getContacts() {
        return contacts;
    }

    public void setContacts(List<CNContact> contacts) {
        this.contacts = contacts;
    }

    public List<PhoneItem> phoneItems(){
        List<PhoneItem> items = new ArrayList<>();
        for (CNContact contact:contacts) {
            for (String phone: contact.getPhones()) {
                PhoneItem item = new PhoneItem();
                item.setPhone(phone);
                item.setContent(body);
                item.setPayload(this);
                item.setName(contact.getName());
                item.setIndices(contact.getIndices());
                items.add(item);
            }
        }
        return items;
    }
}
