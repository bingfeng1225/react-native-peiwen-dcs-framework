package com.qd.peiwen.dcsframework.entity.respons;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.qd.peiwen.dcsframework.devices.PayloadManager;
import com.qd.peiwen.dcsframework.entity.header.BaseHeader;
import com.qd.peiwen.dcsframework.entity.header.DialogIdHeader;
import com.qd.peiwen.dcsframework.entity.header.MessageIdHeader;
import com.qd.peiwen.dcsframework.entity.payload.BasePayload;

import java.lang.reflect.Type;

/**
 * Created by nick on 2017/11/30.
 */

public class DirectiveAdapter implements JsonDeserializer<Directive> {
    @Override
    public Directive deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject root = json.getAsJsonObject();
        BaseHeader header = null;
        JsonElement jheader = root.get("header");
        JsonElement messageId = jheader.getAsJsonObject().get("messageId");
        JsonElement dialogId = jheader.getAsJsonObject().get("dialogRequestId");
        if (null != dialogId) {
            header = context.deserialize(jheader, DialogIdHeader.class);
        } else if (null != messageId) {
            header = context.deserialize(jheader, MessageIdHeader.class);
        } else {
            header = context.deserialize(jheader, BaseHeader.class);
        }

        Type payloadType = null;
        BasePayload payload = null;
        JsonElement jpayload = root.get("payload");
        JsonElement jtype = jpayload.getAsJsonObject().get("type");
        if (null == jtype) {
            payloadType = PayloadManager.getInstance().findPayloadClass(header.getNamespace(), header.getName());
        } else {
            String type = jtype.getAsString();
            payloadType = PayloadManager.getInstance().findPayloadClass(header.getNamespace(), header.getName(), type);
        }
        if (null == payloadType) {
            payloadType = BasePayload.class;
        }
        payload = context.deserialize(jpayload, payloadType);
        return new Directive(header, payload);
    }
}
