package com.qd.peiwen.dcsframework.devices.audioplayer.message.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nick on 2017/12/11.
 */

public class AudioStream {
    @SerializedName("url")
    private String url;
    @SerializedName("token")
    private String token;
    @SerializedName("expiryTime")
    private String expiryTime;
    @SerializedName("streamFormat")
    private String streamFormat;
    @SerializedName("offsetInMilliseconds")
    private int offsetInMilliseconds;
    @SerializedName("expectedPreviousToken")
    private String expectedPreviousToken;
    @SerializedName("progressReport")
    private ProgressReport progressReport;

    public AudioStream() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(String expiryTime) {
        this.expiryTime = expiryTime;
    }

    public String getStreamFormat() {
        return streamFormat;
    }

    public void setStreamFormat(String streamFormat) {
        this.streamFormat = streamFormat;
    }

    public int getOffsetInMilliseconds() {
        return offsetInMilliseconds;
    }

    public void setOffsetInMilliseconds(int offsetInMilliseconds) {
        this.offsetInMilliseconds = offsetInMilliseconds;
    }

    public String getExpectedPreviousToken() {
        return expectedPreviousToken;
    }

    public void setExpectedPreviousToken(String expectedPreviousToken) {
        this.expectedPreviousToken = expectedPreviousToken;
    }

    public ProgressReport getProgressReport() {
        return progressReport;
    }

    public void setProgressReport(ProgressReport progressReport) {
        this.progressReport = progressReport;
    }
}
