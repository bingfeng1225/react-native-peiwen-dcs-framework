package com.qd.peiwen.dcsframework.enmudefine;

/**
 * Created by nick on 2018/1/26.
 */

public enum SendEventState {
    SEND_EVENT_STATE_STARTED(0),
    SEND_EVENT_STATE_FAILURED(1),
    SEND_EVENT_STATE_SUCCESSED(2);
    private final int value;

    SendEventState(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SendEventState fromValue(int value){
        switch (value) {
            case 1:
                return SEND_EVENT_STATE_FAILURED;
            case 2:
                return SEND_EVENT_STATE_SUCCESSED;
            default:
                return SEND_EVENT_STATE_FAILURED;
        }
    }
}
