package com.qd.peiwen.dcsframework.enmudefine;

/**
 * Created by nick on 2017/12/26.
 */

public enum RenderCardType {
    RENDER_TEXT_CARD("TextCard"),
    RENDER_LIST_CARD("ListCard"),
    RENDER_SERVICE_CARD("ServiceCard"),
    RENDER_STANDAR_DCARD("StandardCard"),
    RENDER_IMAGE_LIST_CARD("ImageListCard"),
    RENDER_PHONE_LIST_CARD("PhoneListCard"),
    RENDER_SERVICE_LIST_CARD("ServiceListCard"),
    RENDER_SMS_MESSAGE_LIST_CARD("SMSMessageListCard");

    private final String message;

    RenderCardType(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
