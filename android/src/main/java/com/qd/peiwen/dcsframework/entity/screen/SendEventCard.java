package com.qd.peiwen.dcsframework.entity.screen;

/**
 * Created by nick on 2018/1/26.
 */

public class SendEventCard {
    private String uuid;
    private String content;
    private long timestamp;
    private String sessionid;

    public SendEventCard() {

    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }
}
