package com.qd.peiwen.dcsframework.entity.request;

import com.qd.peiwen.dcsframework.entity.header.BaseHeader;
import com.qd.peiwen.dcsframework.entity.payload.BasePayload;

/**
 * Created by nick on 2017/11/28.
 */

public class ClientContext extends BaseMessage {
    public ClientContext(BaseHeader header, BasePayload payload) {
        super(header, payload);
    }
}
