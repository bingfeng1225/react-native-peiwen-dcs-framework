package com.qd.peiwen.dcsframework.devices.screen;


import com.qd.peiwen.dcsframework.devices.screen.message.directive.PhoneListCardPayload;
import com.qd.peiwen.dcsframework.devices.screen.message.directive.SMSMessageListCardPayload;
import com.qd.peiwen.dcsframework.devices.screen.message.directive.TextCardPayload;
import com.qd.peiwen.dcsframework.enmudefine.RenderCardType;
import com.qd.peiwen.dcsframework.entity.header.DialogIdHeader;
import com.qd.peiwen.dcsframework.entity.respons.DCSRespons;
import com.qd.peiwen.dcsframework.entity.respons.Directive;
import com.qd.peiwen.dcsframework.pinyinsearch.entity.CNContact;
import com.qd.peiwen.dcsframework.pinyinsearch.entity.SMSMessage;
import com.qd.peiwen.dcsframework.tools.IDCreator;

import java.util.List;
import java.util.UUID;

/**
 * Created by nick on 2018/3/13.
 */

public class SCPCreator {

    public static DCSRespons textcardRespons(String uuid, String content) {
        DialogIdHeader header = IDCreator.createDialogIdHeader(
                ApiConstants.NAMESPACE,
                ApiConstants.Directives.RenderCard.NAME
        );
        return new DCSRespons(new Directive(header, textcardPayload(uuid, content)));
    }


    public static DCSRespons phonelistcardRespons(String uuid, List<CNContact> contacts) {
        DialogIdHeader header = IDCreator.createDialogIdHeader(
                ApiConstants.NAMESPACE,
                ApiConstants.Directives.RenderCard.NAME
        );
        return new DCSRespons(new Directive(header, phonelistcardPayload(uuid, null, contacts)));
    }

    public static DCSRespons phonelistcardRespons(String uuid, String body, List<CNContact> contacts) {
        DialogIdHeader header = IDCreator.createDialogIdHeader(
                ApiConstants.NAMESPACE,
                ApiConstants.Directives.RenderCard.NAME
        );
        return new DCSRespons(new Directive(header, phonelistcardPayload(uuid, body, contacts)));
    }


    public static DCSRespons smsmessagelistcardRespons(String uuid, List<SMSMessage> messages) {
        DialogIdHeader header = IDCreator.createDialogIdHeader(
                ApiConstants.NAMESPACE,
                ApiConstants.Directives.RenderCard.NAME
        );
        return new DCSRespons(new Directive(header, smsmessagelistcardPayload(uuid, messages)));
    }

    private static TextCardPayload textcardPayload(String uuid, String content) {
        TextCardPayload payload = new TextCardPayload();
        payload.setUuid(uuid);
        payload.setContent(content);
        payload.setToken(UUID.randomUUID().toString());
        payload.setType(RenderCardType.RENDER_TEXT_CARD.message());
        return payload;
    }

    private static PhoneListCardPayload phonelistcardPayload(String uuid, String body, List<CNContact> contacts) {
        PhoneListCardPayload payload = new PhoneListCardPayload();
        payload.setUuid(uuid);
        payload.setBody(body);
        payload.setContacts(contacts);
        payload.setToken(UUID.randomUUID().toString());
        payload.setType(RenderCardType.RENDER_PHONE_LIST_CARD.message());
        return payload;
    }

    private static SMSMessageListCardPayload smsmessagelistcardPayload(String uuid, List<SMSMessage> messages) {
        SMSMessageListCardPayload payload = new SMSMessageListCardPayload();
        payload.setUuid(uuid);
        payload.setMessages(messages);
        payload.setToken(UUID.randomUUID().toString());
        payload.setType(RenderCardType.RENDER_SMS_MESSAGE_LIST_CARD.message());
        return payload;
    }
}
