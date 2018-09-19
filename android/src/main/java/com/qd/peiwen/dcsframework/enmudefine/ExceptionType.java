package com.qd.peiwen.dcsframework.enmudefine;

/**
 * Created by nick on 2017/12/26.
 */

public enum ExceptionType {
    EVENT_INTERNAL_ERROR("INTERNAL_ERROR"),
    EVENT_UNSUPPORTED_OPERATION("UNSUPPORTED_OPERATION"),
    EVENT_UNEXPECTED_INFORMATION_RECEIVED("UNEXPECTED_INFORMATION_RECEIVED"),

    DIRECTIVES_SERVER_INVALID("N/A"),
    DIRECTIVES_THROTTLING_EXCEPTION("THROTTLING_EXCEPTION"),
    DIRECTIVES_INVALID_REQUEST_EXCEPTION("INVALID_REQUEST_EXCEPTION"),
    DIRECTIVES_INTERNAL_SERVICE_EXCEPTION("INTERNAL_SERVICE_EXCEPTION"),
    DIRECTIVES_UNAUTHORIZED_REQUEST_EXCEPTION("UNAUTHORIZED_REQUEST_EXCEPTION");

    private final String message;

    ExceptionType(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}