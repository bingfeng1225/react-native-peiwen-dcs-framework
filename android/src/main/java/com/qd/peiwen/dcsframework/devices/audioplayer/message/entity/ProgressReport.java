package com.qd.peiwen.dcsframework.devices.audioplayer.message.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nick on 2017/12/11.
 */

public class ProgressReport {
    @SerializedName("progressReportDelayInMilliseconds")
    private int progressReportDelayInMilliseconds;
    @SerializedName("progressReportIntervalInMilliseconds")
    private int progressReportIntervalInMilliseconds;

    public ProgressReport() {
    }

    public boolean isReportDelay(){
        return progressReportDelayInMilliseconds > 0;
    }
    public boolean isReportInterval(){
        return progressReportIntervalInMilliseconds > 0;
    }


    public int getProgressReportDelayInMilliseconds() {
        return progressReportDelayInMilliseconds;
    }

    public void setProgressReportDelayInMilliseconds(int progressReportDelayInMilliseconds) {
        this.progressReportDelayInMilliseconds = progressReportDelayInMilliseconds;
    }

    public int getProgressReportIntervalInMilliseconds() {
        return progressReportIntervalInMilliseconds;
    }

    public void setProgressReportIntervalInMilliseconds(int progressReportIntervalInMilliseconds) {
        this.progressReportIntervalInMilliseconds = progressReportIntervalInMilliseconds;
    }
}
