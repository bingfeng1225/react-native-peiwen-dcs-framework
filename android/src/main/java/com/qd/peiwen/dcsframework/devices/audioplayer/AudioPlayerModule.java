package com.qd.peiwen.dcsframework.devices.audioplayer;

import android.content.Context;


import com.qd.peiwen.dcsframework.devices.BaseModule;
import com.qd.peiwen.dcsframework.devices.PayloadManager;
import com.qd.peiwen.dcsframework.devices.audioplayer.listener.IAudioPlayerModuleListener;
import com.qd.peiwen.dcsframework.devices.audioplayer.message.directive.ClearQueuePayload;
import com.qd.peiwen.dcsframework.devices.audioplayer.message.directive.PlayPayload;
import com.qd.peiwen.dcsframework.devices.audioplayer.message.directive.StopPayload;
import com.qd.peiwen.dcsframework.devices.audioplayer.message.entity.AudioStream;
import com.qd.peiwen.dcsframework.devices.audioplayer.message.entity.ProgressReport;
import com.qd.peiwen.dcsframework.devices.audioplayer.message.event.PlaybackFailedPayload;
import com.qd.peiwen.dcsframework.devices.audioplayer.message.event.PlaybackFinishedPayload;
import com.qd.peiwen.dcsframework.devices.audioplayer.message.event.PlaybackNearlyFinishedPayload;
import com.qd.peiwen.dcsframework.devices.audioplayer.message.event.PlaybackPausedPayload;
import com.qd.peiwen.dcsframework.devices.audioplayer.message.event.PlaybackQueueClearedPayload;
import com.qd.peiwen.dcsframework.devices.audioplayer.message.event.PlaybackResumedPayload;
import com.qd.peiwen.dcsframework.devices.audioplayer.message.event.PlaybackStartedPayload;
import com.qd.peiwen.dcsframework.devices.audioplayer.message.event.PlaybackStatePayload;
import com.qd.peiwen.dcsframework.devices.audioplayer.message.event.PlaybackStoppedPayload;
import com.qd.peiwen.dcsframework.devices.audioplayer.message.event.PlaybackStutterFinishedPayload;
import com.qd.peiwen.dcsframework.devices.audioplayer.message.event.PlaybackStutterStartedPayload;
import com.qd.peiwen.dcsframework.devices.audioplayer.message.event.ProgressReportDelayElapsedPayload;
import com.qd.peiwen.dcsframework.devices.audioplayer.message.event.ProgressReportIntervalElapsedPayload;
import com.qd.peiwen.dcsframework.devices.audioplayer.message.event.StreamMetadataExtractedPayload;
import com.qd.peiwen.dcsframework.devices.system.message.entity.ErrorMessager;
import com.qd.peiwen.dcsframework.enmudefine.ClearBehavior;
import com.qd.peiwen.dcsframework.entity.header.BaseHeader;
import com.qd.peiwen.dcsframework.entity.header.MessageIdHeader;
import com.qd.peiwen.dcsframework.entity.payload.BasePayload;
import com.qd.peiwen.dcsframework.entity.request.ClientContext;
import com.qd.peiwen.dcsframework.entity.request.EventMessage;
import com.qd.peiwen.dcsframework.entity.respons.Directive;
import com.qd.peiwen.dcsframework.musicplayer.ChannelPlayer;
import com.qd.peiwen.dcsframework.musicplayer.enmudefine.ChannelType;
import com.qd.peiwen.dcsframework.musicplayer.enmudefine.ErrorType;
import com.qd.peiwen.dcsframework.musicplayer.enmudefine.PlayerState;
import com.qd.peiwen.dcsframework.musicplayer.listener.IChannelPlayerListener;
import com.qd.peiwen.dcsframework.tools.IDCreator;
import com.qd.peiwen.dcsframework.tools.LogUtils;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by nick on 2017/12/11.
 */

public class AudioPlayerModule extends BaseModule implements IChannelPlayerListener {
    private boolean firstPlay = true;
    private long bufferStartedTime = 0;
    private ChannelPlayer channelPlayer;
    private boolean reportedDelay = false;
    private int reportedIntervalIndex = 0;
    private LinkedList<AudioStream> audioStreams;
    private WeakReference<IAudioPlayerModuleListener> listener;

    public AudioPlayerModule(Context context) {
        super(context, ApiConstants.NAME, ApiConstants.NAMESPACE);
    }

    public ChannelPlayer channelPlayer() {
        return this.channelPlayer;
    }

    public void setListener(IAudioPlayerModuleListener listener) {
        this.listener = new WeakReference<>(listener);
    }

    private void initChannnelPlayer(){
        this.audioStreams = new LinkedList<>();
        this.channelPlayer = new ChannelPlayer(context);
        this.channelPlayer.setChannelType(ChannelType.AUDIO);
        this.channelPlayer.setListener(this);
        this.channelPlayer.init();
    }
    /************************ Base重写方法 **********************************/
    @Override
    public void init() {
        super.init();
        this.initChannnelPlayer();
    }

    @Override
    public ClientContext clientContext() {
        BaseHeader header = new BaseHeader();
        header.setNamespace(ApiConstants.NAMESPACE);
        header.setName(ApiConstants.States.PlaybackState.NAME);
        PlaybackStatePayload payload = new PlaybackStatePayload();
        Object object = channelPlayer.lastObject();
        payload.setOffsetInMilliseconds(channelPlayer.lastPosition());
        payload.setToken((object == null) ? null : ((AudioStream) object).getToken());
        payload.setPlayerActivity(this.playActivity());
        return new ClientContext(header, payload);
    }

    @Override
    public boolean handleDirective(Directive directive) {
        BaseHeader header = directive.getHeader();
        BasePayload payload = directive.getPayload();
        if (ApiConstants.Directives.Play.NAME.equals(header.getName())) {
            return processPlayPayload(payload);
        } else if (ApiConstants.Directives.Stop.NAME.equals(header.getName())) {
            return processStopPayload(payload);
        } else if (ApiConstants.Directives.ClearQueue.NAME.equals(header.getName())) {
            return processClearQueuePayload(payload);
        }
        return super.handleDirective(directive);
    }

    @Override
    public void release() {
        super.release();
        this.listener = null;
        this.channelPlayer.stop();
        this.channelPlayer.release();
        this.channelPlayer = null;
    }

    /************************ 事件封装方法 **********************************/
    public EventMessage playbackStartedRequest(String token, long offsetInMilliseconds) {
        MessageIdHeader header = IDCreator.createMessageIdHeader(
                ApiConstants.NAMESPACE,
                ApiConstants.Events.PlaybackStarted.NAME
        );
        PlaybackStartedPayload payload = new PlaybackStartedPayload();
        payload.setToken(token);
        payload.setOffsetInMilliseconds(offsetInMilliseconds);
        return new EventMessage(header, payload);
    }

    public EventMessage playbackFinishedRequest(String token, long offsetInMilliseconds) {
        MessageIdHeader header = IDCreator.createMessageIdHeader(
                ApiConstants.NAMESPACE,
                ApiConstants.Events.PlaybackFinished.NAME
        );
        PlaybackFinishedPayload payload = new PlaybackFinishedPayload();
        payload.setToken(token);
        payload.setOffsetInMilliseconds(offsetInMilliseconds);
        return new EventMessage(header, payload);
    }

    public EventMessage playbackNearlyFinishedRequest(String token, long offsetInMilliseconds) {
        MessageIdHeader header = IDCreator.createMessageIdHeader(
                ApiConstants.NAMESPACE,
                ApiConstants.Events.PlaybackNearlyFinished.NAME
        );
        PlaybackNearlyFinishedPayload payload = new PlaybackNearlyFinishedPayload();
        payload.setToken(token);
        payload.setOffsetInMilliseconds(offsetInMilliseconds);
        return new EventMessage(header, payload);
    }

    public EventMessage playbackFailedRequest(String token, String type, String message) {
        MessageIdHeader header = IDCreator.createMessageIdHeader(
                ApiConstants.NAMESPACE,
                ApiConstants.Events.PlaybackFailed.NAME
        );
        PlaybackFailedPayload payload = new PlaybackFailedPayload();
        payload.setToken(token);
        ErrorMessager error = new ErrorMessager();
        error.setType(type);
        error.setMessage(message);
        payload.setError(error);
        return new EventMessage(header, payload);
    }

    public EventMessage playbackStutterStartedRequest(String token, long offsetInMilliseconds) {
        MessageIdHeader header = IDCreator.createMessageIdHeader(
                ApiConstants.NAMESPACE,
                ApiConstants.Events.PlaybackStutterStarted.NAME
        );
        PlaybackStutterStartedPayload payload = new PlaybackStutterStartedPayload();
        payload.setToken(token);
        payload.setOffsetInMilliseconds(offsetInMilliseconds);
        return new EventMessage(header, payload);
    }

    public EventMessage playbackStutterFinishedRequest(String token, long offsetInMilliseconds, long stutterDurationInMilliseconds) {
        MessageIdHeader header = IDCreator.createMessageIdHeader(
                ApiConstants.NAMESPACE,
                ApiConstants.Events.PlaybackStutterFinished.NAME
        );
        PlaybackStutterFinishedPayload payload = new PlaybackStutterFinishedPayload();
        payload.setToken(token);
        payload.setOffsetInMilliseconds(offsetInMilliseconds);
        payload.setStutterDurationInMilliseconds(stutterDurationInMilliseconds);
        return new EventMessage(header, payload);
    }

    public EventMessage playbackPausedRequest(String token, long offsetInMilliseconds) {
        MessageIdHeader header = IDCreator.createMessageIdHeader(
                ApiConstants.NAMESPACE,
                ApiConstants.Events.PlaybackPaused.NAME
        );
        PlaybackPausedPayload payload = new PlaybackPausedPayload();
        payload.setToken(token);
        payload.setOffsetInMilliseconds(offsetInMilliseconds);
        return new EventMessage(header, payload);
    }

    public EventMessage playbackResumedRequest(String token, long offsetInMilliseconds) {
        MessageIdHeader header = IDCreator.createMessageIdHeader(
                ApiConstants.NAMESPACE,
                ApiConstants.Events.PlaybackResumed.NAME
        );
        PlaybackResumedPayload payload = new PlaybackResumedPayload();
        payload.setToken(token);
        payload.setOffsetInMilliseconds(offsetInMilliseconds);
        return new EventMessage(header, payload);
    }

    public EventMessage playbackStoppedRequest(String token, long offsetInMilliseconds) {
        MessageIdHeader header = IDCreator.createMessageIdHeader(
                ApiConstants.NAMESPACE,
                ApiConstants.Events.PlaybackStopped.NAME
        );
        PlaybackStoppedPayload payload = new PlaybackStoppedPayload();
        payload.setToken(token);
        payload.setOffsetInMilliseconds(offsetInMilliseconds);
        return new EventMessage(header, payload);
    }

    public EventMessage playbackQueueClearedRequest() {
        MessageIdHeader header = IDCreator.createMessageIdHeader(
                ApiConstants.NAMESPACE,
                ApiConstants.Events.PlaybackQueueCleared.NAME
        );
        PlaybackQueueClearedPayload payload = new PlaybackQueueClearedPayload();
        return new EventMessage(header, payload);
    }

    public EventMessage progressReportDelayElapsedRequest(String token, long offsetInMilliseconds) {
        MessageIdHeader header = IDCreator.createMessageIdHeader(
                ApiConstants.NAMESPACE,
                ApiConstants.Events.ProgressReportDelayElapsed.NAME
        );
        ProgressReportDelayElapsedPayload payload = new ProgressReportDelayElapsedPayload();
        payload.setToken(token);
        payload.setOffsetInMilliseconds(offsetInMilliseconds);
        return new EventMessage(header, payload);
    }

    public EventMessage progressReportIntervalElapsedRequest(String token, long offsetInMilliseconds) {
        MessageIdHeader header = IDCreator.createMessageIdHeader(
                ApiConstants.NAMESPACE,
                ApiConstants.Events.ProgressReportIntervalElapsed.NAME
        );
        ProgressReportIntervalElapsedPayload payload = new ProgressReportIntervalElapsedPayload();
        payload.setToken(token);
        payload.setOffsetInMilliseconds(offsetInMilliseconds);
        return new EventMessage(header, payload);
    }

    public EventMessage streamMetadataExtractedRequest(String token, Map<String, Object> metadata) {
        MessageIdHeader header = IDCreator.createMessageIdHeader(
                ApiConstants.NAMESPACE,
                ApiConstants.Events.StreamMetadataExtracted.NAME
        );
        StreamMetadataExtractedPayload payload = new StreamMetadataExtractedPayload();
        payload.setToken(token);
        payload.setMetadata(metadata);
        return new EventMessage(header, payload);
    }

    /************************ 状态同步参数准备方法 **********************************/
    private String playActivity() {
        if (this.channelPlayer.isStoped()) {
            return "STOPPED";
        }
        if (this.channelPlayer.isError() || this.channelPlayer.isEnded()) {
            return "FINISHED";
        }
        PlayerState state = this.channelPlayer.playerState();
        if (state == PlayerState.PLAYER_PREPAREING || state == PlayerState.PLAYER_PREPARED) {
            return "BUFFER_UNDERRUN";
        } else if (state == PlayerState.PLAYER_PAUSED) {
            return "PAUSED";
        } else if (state == PlayerState.PLAYER_PLAYING) {
            if (this.channelPlayer.isBuffing()) {
                return "BUFFER_UNDERRUN";
            } else {
                return "PLAYING";
            }
        } else {
            return "FINISHED";
        }
    }

    /************************ 指令处理方法 **********************************/
    private boolean processStopPayload(BasePayload payload) {
        if (payload instanceof StopPayload) {
            this.channelPlayer.stop();
            return true;
        }
        return false;
    }

    private boolean processPlayPayload(BasePayload payload) {
        if (payload instanceof PlayPayload) {
            PlayPayload playPayload = (PlayPayload) payload;
            AudioStream stream = playPayload.getAudioItem().getStream();
            this.clearEnqueuedByBehavior(playPayload.getPlayBehavior());
            this.addAudioStream(stream);
            return true;
        }
        return false;
    }

    private boolean processClearQueuePayload(BasePayload payload) {
        if (payload instanceof ClearQueuePayload) {
            ClearQueuePayload clearQueue = (ClearQueuePayload) payload;
            this.clearEnqueuedByBehavior(clearQueue.getClearBehavior());
            this.firePlaybackQueueCleared();
            return true;
        }
        return false;
    }

    private synchronized void clearAll() {
        this.channelPlayer.stop();
        this.audioStreams.clear();
    }

    private synchronized void clearEnqueued() {
        AudioStream stream = this.audioStreams.poll();
        this.audioStreams.clear();
        this.audioStreams.add(stream);
    }

    private synchronized void addAudioStream(AudioStream stream) {
        LogUtils.e("AudioPlayerModule addAudioStream");
        this.audioStreams.add(stream);
        this.findAudioStreamToPlay();
    }

    public void clearEnqueuedByBehavior(String behavior) {
        LogUtils.e("AudioPlayerModule clearEnqueuedByBehavior " + behavior);
        if (ClearBehavior.REPLACE_ALL.name().equals(behavior)) {
            this.clearAll();
        } else if (ClearBehavior.REPLACE_ENQUEUED.name().equals(behavior)) {
            this.clearEnqueued();
        }
    }

    private synchronized void findAudioStreamToPlay() {
        LogUtils.e("AudioPlayerModule findAudioStreamToPlay");
        if (this.audioStreams.isEmpty()) {
            this.fireAudioChannelReleased();
        } else {
            if (!this.channelPlayer.hasPlayContent()) {
                this.fireAudioChannelOccupied();
                AudioStream stream = this.audioStreams.peek();
                this.firstPlay = true;
                this.reportedDelay = false;
                this.channelPlayer.unload();
                this.reportedIntervalIndex = 0;
                this.channelPlayer.setPlayObject(stream);
                this.channelPlayer.setSeekTime(stream.getOffsetInMilliseconds());
                this.channelPlayer.load(stream.getUrl());
            }
        }
    }

    /************************ 播放器监听 **********************************/
    @Override
    public void onPlayerStoped(ChannelPlayer player, Object palyObject) {
        LogUtils.e("AudioPlayerModule player stoped");
        AudioStream stream = (AudioStream) palyObject;
        this.firePlaybackStopped(stream.getToken(), player.lastPosition());
    }

    @Override
    public void onPlayerCompleted(ChannelPlayer player, Object palyObject) {
        LogUtils.e("AudioPlayerModule player completed");
        AudioStream stream = (AudioStream) palyObject;
        this.firePlaybackNearlyFinished(stream.getToken(), player.lastPosition());
    }

    @Override
    public void onPlayerBufferingStart(ChannelPlayer player, Object palyObject) {
        LogUtils.e("AudioPlayerModule player buffering start");
        bufferStartedTime = System.currentTimeMillis();
        AudioStream stream = (AudioStream) palyObject;
        this.firePlaybackStutterStarted(stream.getToken(), player.lastPosition());
    }

    @Override
    public void onPlayerBufferingEnded(ChannelPlayer player, Object palyObject) {
        LogUtils.e("AudioPlayerModule player buffering ended");
        long duration = System.currentTimeMillis() - bufferStartedTime;
        AudioStream stream = (AudioStream) palyObject;
        this.firePlaybackStutterFinished(stream.getToken(), player.lastPosition(), duration);
    }

    @Override
    public void onBufferingUpdated(ChannelPlayer player, Object palyObject, int bufferPercentage) {
        AudioStream stream = (AudioStream) palyObject;
    }

    @Override
    public void onPlayerErrorOccurred(ChannelPlayer player, Object palyObject, ErrorType error) {
        LogUtils.e("AudioPlayerModule player error occurred");
        AudioStream stream = (AudioStream) palyObject;
        this.firePlaybackFailed(stream.getToken(), error.name(), error.message());
    }

    @Override
    public void onPlayerStateChanged(ChannelPlayer player, Object palyObject, PlayerState state) {
        LogUtils.e("AudioPlayerModule player state changed " + state.name());
        AudioStream stream = (AudioStream) palyObject;
        if (state == PlayerState.PLAYER_PREPAREING) {
            bufferStartedTime = System.currentTimeMillis();
            this.firePlaybackStutterStarted(stream.getToken(), player.lastPosition());
        } else if (state == PlayerState.PLAYER_PREPARED) {
            long duration = System.currentTimeMillis() - bufferStartedTime;
            this.firePlaybackStutterFinished(stream.getToken(), player.lastPosition(), duration);
        } else if (state == PlayerState.PLAYER_PLAYING) {
            if (this.firstPlay) {
                this.firstPlay = false;
                this.firePlaybackStarted(stream.getToken(), player.lastPosition());
            } else {
                this.firePlaybackResumed(stream.getToken(), player.lastPosition());
            }
        } else if (state == PlayerState.PLAYER_PAUSED) {
            if (!this.firstPlay) {
                this.firePlaybackPaused(stream.getToken(), player.lastPosition());
            }
        }
    }

    @Override
    public void onPlayerProgressChanged(ChannelPlayer player, Object palyObject, int position, int duration) {
        AudioStream stream = (AudioStream) palyObject;
        ProgressReport report = stream.getProgressReport();
        if (null != report) {
            if (!this.reportedDelay && report.isReportDelay() && position >= (stream.getOffsetInMilliseconds() + report.getProgressReportDelayInMilliseconds())) {
                this.reportedDelay = true;
                this.fireProgressReportDelayElapsed(stream.getToken(), position);
            }
            if (report.isReportInterval()) {
                int time = position - stream.getOffsetInMilliseconds();
                int index = time / report.getProgressReportIntervalInMilliseconds();
                if (index != this.reportedIntervalIndex) {
                    this.reportedIntervalIndex = index;
                    this.fireProgressReportIntervalElapsed(stream.getToken(), position);
                }
            }
        }
    }

    /************************ Listener分发方法 **********************************/
    private void fireAudioChannelOccupied() {
        if (null != this.listener && null != listener.get()) {
            this.listener.get().onAudioChannelOccupied();
        }
    }

    private void fireAudioChannelReleased() {
        if (null != this.listener && null != listener.get()) {
            this.listener.get().onAudioChannelReleased();
        }
    }

    private void firePlaybackQueueCleared() {
        if (null != this.listener && null != listener.get()) {
            this.listener.get().onPlaybackQueueCleared();
        }
    }

    private void firePlaybackPaused(String token, long offsetInMilliseconds) {
        if (null != this.listener && null != listener.get()) {
            this.listener.get().onPlaybackPaused(token, offsetInMilliseconds);
        }
    }

    private void firePlaybackResumed(String token, long offsetInMilliseconds) {
        if (null != this.listener && null != listener.get()) {
            this.listener.get().onPlaybackResumed(token, offsetInMilliseconds);
        }
    }

    private synchronized void firePlaybackStopped(String token, long offsetInMilliseconds) {
        this.audioStreams.poll();
        if (null != this.listener && null != listener.get()) {
            this.listener.get().onPlaybackStopped(token, offsetInMilliseconds);
        }
        this.findAudioStreamToPlay();
    }

    private void firePlaybackStarted(String token, long offsetInMilliseconds) {
        if (null != this.listener && null != listener.get()) {
            this.listener.get().onPlaybackStarted(token, offsetInMilliseconds);
        }
    }

    private synchronized void firePlaybackFinished(String token, long offsetInMilliseconds) {
        this.audioStreams.poll();
        if (null != this.listener && null != listener.get()) {
            this.listener.get().onPlaybackFinished(token, offsetInMilliseconds);
        }
        this.findAudioStreamToPlay();
    }

    private synchronized void firePlaybackFailed(String token, String type, String message) {
        this.audioStreams.poll();
        if (null != this.listener && null != listener.get()) {
            this.listener.get().onPlaybackFailed(token, type, message);
        }
        this.findAudioStreamToPlay();
    }

    private synchronized void firePlaybackNearlyFinished(String token, long offsetInMilliseconds) {
        this.audioStreams.poll();
        if (null != this.listener && null != listener.get()) {
            this.listener.get().onPlaybackNearlyFinished(token, offsetInMilliseconds);
        }
        this.findAudioStreamToPlay();
    }

    private void firePlaybackStutterStarted(String token, long offsetInMilliseconds) {
        if (null != this.listener && null != listener.get()) {
            this.listener.get().onPlaybackStutterStarted(token, offsetInMilliseconds);
        }
    }

    private void fireStreamMetadataExtracted(String token, Map<String, Object> metadata) {
        if (null != this.listener && null != listener.get()) {
            this.listener.get().onStreamMetadataExtracted(token, metadata);
        }
    }

    private void fireProgressReportDelayElapsed(String token, long offsetInMilliseconds) {
        if (null != this.listener && null != listener.get()) {
            this.listener.get().onProgressReportDelayElapsed(token, offsetInMilliseconds);
        }
    }

    private void fireProgressReportIntervalElapsed(String token, long offsetInMilliseconds) {
        if (null != this.listener && null != listener.get()) {
            this.listener.get().onProgressReportIntervalElapsed(token, offsetInMilliseconds);
        }
    }

    private void firePlaybackStutterFinished(String token, long offsetInMilliseconds, long stutterDurationInMilliseconds) {
        if (null != this.listener && null != listener.get()) {
            this.listener.get().onPlaybackStutterFinished(token, offsetInMilliseconds, stutterDurationInMilliseconds);
        }
    }

    static {
        PayloadManager.getInstance().insertPayload(
                ApiConstants.NAMESPACE,
                ApiConstants.Directives.Play.NAME,
                PlayPayload.class
        );
        PayloadManager.getInstance().insertPayload(
                ApiConstants.NAMESPACE,
                ApiConstants.Directives.Stop.NAME,
                StopPayload.class
        );
        PayloadManager.getInstance().insertPayload(
                ApiConstants.NAMESPACE,
                ApiConstants.Directives.ClearQueue.NAME,
                ClearQueuePayload.class
        );
    }
}
