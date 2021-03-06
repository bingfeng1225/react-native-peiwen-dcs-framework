package com.qd.peiwen.dcsframework.entity.request;

import com.google.gson.annotations.SerializedName;
import com.qd.peiwen.dcsframework.entity.header.BaseHeader;
import com.qd.peiwen.dcsframework.entity.payload.BasePayload;

/**
 * Created by nick on 2017/11/28.
 */

public class BaseMessage {
    @SerializedName("header")
    private BaseHeader header;
    @SerializedName("payload")
    private BasePayload payload;

    public BaseMessage(BaseHeader header, BasePayload payload) {
        this.header = header;
        this.payload = payload;
    }

    public void setHeader(BaseHeader header) {
        this.header = header;
    }

    public BaseHeader getHeader() {
        return header;
    }

    public void setPayload(BasePayload payload) {
        this.payload = payload;
    }

    public BasePayload getPayload() {
        return payload;
    }
}
