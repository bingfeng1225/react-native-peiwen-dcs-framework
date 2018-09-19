package com.qd.peiwen.dcsframework.tools;

import com.qd.peiwen.dcsframework.entity.header.DialogIdHeader;
import com.qd.peiwen.dcsframework.entity.header.MessageIdHeader;

import java.util.UUID;

/**
 * Created by nick on 2017/12/4.
 */

public class IDCreator {
    private static String activeRequestId;

    public static String createActiveRequestId() {
        activeRequestId = UUID.randomUUID().toString();
        return activeRequestId;
    }

    public static boolean isActiveRequestId(String uuid){
        return uuid.equals(activeRequestId);
    }

    public static MessageIdHeader createMessageIdHeader(String namespace, String name) {
        return createMessageIdHeader(namespace, name, UUID.randomUUID().toString());
    }

    public static MessageIdHeader createMessageIdHeader(String namespace, String name, String msgid) {
        MessageIdHeader header = new MessageIdHeader();
        header.setName(name);
        header.setNamespace(namespace);
        header.setMessageId(msgid);
        return header;
    }

    public static DialogIdHeader createDialogIdHeader(String namespace, String name) {
        return createDialogIdHeader(namespace, name, UUID.randomUUID().toString(), UUID.randomUUID().toString());
    }

    public static DialogIdHeader createDialogIdHeader(String namespace, String name, String msgid, String dialogid) {
        DialogIdHeader header = new DialogIdHeader();
        header.setName(name);
        header.setNamespace(namespace);
        header.setMessageId(msgid);
        header.setDialogId(dialogid);
        return header;
    }
}