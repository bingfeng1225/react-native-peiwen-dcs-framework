package com.qd.peiwen.dcsframework.devices.phone.message.directive;

import com.google.gson.annotations.SerializedName;
import com.qd.peiwen.dcsframework.entity.payload.BasePayload;
import com.qd.peiwen.dcsframework.pinyinsearch.pinyin.CN;

/**
 * Created by nick on 2018/3/6.
 */

public class PhoneCallPayload extends BasePayload implements CN {
    @SerializedName("name")
    private String name;

    public PhoneCallPayload() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String chinese() {
        return name;
    }
}
