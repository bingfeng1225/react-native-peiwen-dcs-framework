package com.qd.peiwen.dcsframework.devices.audioplayer.listener;

import java.util.Map;

/**
 * Created by nick on 2017/12/12.
 */

public interface IAudioPlayerModuleListener {
    void onAudioChannelOccupied();

    void onAudioChannelReleased();

    void onPlaybackQueueCleared();

    void onPlaybackPaused(String token, long offsetInMilliseconds);

    void onPlaybackResumed(String token, long offsetInMilliseconds);

    void onPlaybackStopped(String token, long offsetInMilliseconds);

    void onPlaybackStarted(String token, long offsetInMilliseconds);

    void onPlaybackFinished(String token, long offsetInMilliseconds);

    void onPlaybackFailed(String token, String type, String message);

    void onPlaybackNearlyFinished(String token, long offsetInMilliseconds);

    void onPlaybackStutterStarted(String token, long offsetInMilliseconds);

    void onStreamMetadataExtracted(String token, Map<String, Object> metadata);

    void onProgressReportDelayElapsed(String token, long offsetInMilliseconds);

    void onProgressReportIntervalElapsed(String token, long offsetInMilliseconds);

    void onPlaybackStutterFinished(String token, long offsetInMilliseconds, long stutterDurationInMilliseconds);
}