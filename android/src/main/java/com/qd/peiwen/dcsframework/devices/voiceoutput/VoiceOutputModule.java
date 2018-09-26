package com.qd.peiwen.dcsframework.devices.voiceoutput;

import android.content.Context;


import com.qd.peiwen.dcsframework.devices.BaseModule;
import com.qd.peiwen.dcsframework.devices.PayloadManager;
import com.qd.peiwen.dcsframework.devices.voiceoutput.listener.IVoiceOutputModuleListener;
import com.qd.peiwen.dcsframework.devices.voiceoutput.message.directive.SpeakPayload;
import com.qd.peiwen.dcsframework.devices.voiceoutput.message.enevt.SpeechFinishedPayload;
import com.qd.peiwen.dcsframework.devices.voiceoutput.message.enevt.SpeechStartedPayload;
import com.qd.peiwen.dcsframework.entity.header.BaseHeader;
import com.qd.peiwen.dcsframework.entity.header.MessageIdHeader;
import com.qd.peiwen.dcsframework.entity.payload.BasePayload;
import com.qd.peiwen.dcsframework.entity.request.EventMessage;
import com.qd.peiwen.dcsframework.entity.respons.Directive;
import com.qd.peiwen.dcsframework.httppackage.HttpConfig;
import com.qd.peiwen.dcsframework.musicplayer.ChannelPlayer;
import com.qd.peiwen.dcsframework.musicplayer.SynthesisPlayer;
import com.qd.peiwen.dcsframework.musicplayer.enmudefine.ChannelType;
import com.qd.peiwen.dcsframework.musicplayer.enmudefine.ErrorType;
import com.qd.peiwen.dcsframework.musicplayer.enmudefine.PlayerState;
import com.qd.peiwen.dcsframework.musicplayer.listener.IChannelPlayerListener;
import com.qd.peiwen.dcsframework.tools.FileUtils;
import com.qd.peiwen.dcsframework.tools.IDCreator;
import com.qd.peiwen.dcsframework.tools.LogUtils;

import java.lang.ref.WeakReference;
import java.util.LinkedList;

import okhttp3.OkHttpClient;

/**
 * Created by nick on 2017/12/4.
 */

public class VoiceOutputModule extends BaseModule implements IChannelPlayerListener {
    private OkHttpClient httpClient;
    private SynthesisPlayer channelPlayer;
    private LinkedList<SpeakPayload> speakPayloads;
    private WeakReference<IVoiceOutputModuleListener> listener;

    public VoiceOutputModule(Context context) {
        super(context, ApiConstants.NAME, ApiConstants.NAMESPACE);
    }

    public ChannelPlayer channelPlayer() {
        return this.channelPlayer;
    }

    public void setHttpClient(OkHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public void setListener(IVoiceOutputModuleListener listener) {
        this.listener = new WeakReference<>(listener);
    }

    private void initChannelPlayer() {
        this.speakPayloads = new LinkedList<>();
        this.channelPlayer = new SynthesisPlayer(context);
        this.channelPlayer.setChannelType(ChannelType.SPEAK);
        this.channelPlayer.setHttpClient(this.httpClient);
        this.channelPlayer.setListener(this);
        this.channelPlayer.init();
    }

    /************************ Base重写方法 **********************************/
    @Override
    public void init() {
        super.init();
        this.initChannelPlayer();
    }

    @Override
    public boolean handleDirective(Directive directive) {
        BaseHeader header = directive.getHeader();
        BasePayload payload = directive.getPayload();
        if (ApiConstants.Directives.Speak.NAME.equals(header.getName())) {
            return this.processSpeakPayload(payload);
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

    /************************ 状态同步参数准备方法 **********************************/
    private long offsetInMilliseconds() {
        Object object = this.channelPlayer.lastObject();
        if (object != null) {
            return this.channelPlayer.lastPosition();
        }
        return 0;
    }

    /************************ 事件封装方法 **********************************/
    public EventMessage speechStartedRequest(String token) {
        MessageIdHeader header = IDCreator.createMessageIdHeader(
                ApiConstants.NAMESPACE,
                ApiConstants.Events.SpeechStarted.NAME
        );
        SpeechStartedPayload payload = new SpeechStartedPayload();
        payload.setToken(token);
        return new EventMessage(header, payload);
    }

    public EventMessage speechFinishedRequest(String token) {
        MessageIdHeader header = IDCreator.createMessageIdHeader(
                ApiConstants.NAMESPACE,
                ApiConstants.Events.SpeechFinished.NAME
        );
        SpeechFinishedPayload payload = new SpeechFinishedPayload();
        payload.setToken(token);
        return new EventMessage(header, payload);
    }

    /************************ 指令处理方法 **********************************/
    private boolean processSpeakPayload(BasePayload payload) {
        if (payload instanceof SpeakPayload) {
            LogUtils.e("VoiceOutputModule processSpeakPayload");
            if(IDCreator.isActiveRequestId(payload.getUuid())) {
                this.addSpeakPayload((SpeakPayload) payload);
            }
            return true;
        }
        return false;
    }

    private synchronized void addSpeakPayload(SpeakPayload payload) {
        LogUtils.e("VoiceOutputModule addSpeakPayload");
        this.speakPayloads.add(payload);
        this.findRecordToPlay();
    }

    private synchronized void findRecordToPlay() {
        LogUtils.e("VoiceOutputModule findRecordToPlay");
        if (this.speakPayloads.isEmpty()) {
            this.fireSpeakChannelReleased();
        } else {
            if (!this.channelPlayer.hasPlayContent()) {
                this.fireSpeakChannelOccupied();
                SpeakPayload payload = this.speakPayloads.peek();
                this.fireSpeechStarted(payload.getToken());
                if(IDCreator.isActiveRequestId(payload.getUuid())) {
                    this.channelPlayer.unload();
                    this.channelPlayer.setPlayObject(payload);
                    this.channelPlayer.setFilepaht(payload.getPlayerURL(this.context));
                    this.channelPlayer.load(this.synthesisURL(payload));
                }else{
                    this.playerFinished(payload);
                }
            } else {
                this.channelPlayer.stop();
            }
        }
    }

    private String synthesisURL(SpeakPayload payload) {
        StringBuilder builder = new StringBuilder();
        builder.append(HttpConfig.speakDownloadURL);
        builder.append("text=");
        builder.append(payload.getContent());
        builder.append("&cuid=");
        builder.append(HttpConfig.deviceID);
        builder.append("&deviceid=");
        builder.append(HttpConfig.deviceID);
        builder.append("&per=4");
        LogUtils.e("" + builder.toString());
        return builder.toString();
    }

    private synchronized void playerFinished(SpeakPayload payload) {
        LogUtils.e("VoiceOutputModule playerFinished");
        this.speakPayloads.poll();
        FileUtils.deleteFile(payload.getPlayerURL(this.context));
        this.fireSpeechFinished(payload.getToken());
        this.findRecordToPlay();
    }

    /************************ 播放器监听 **********************************/
    @Override
    public void onPlayerStoped(ChannelPlayer player, Object object) {
        LogUtils.e("VoiceOutputModule onPlayerStoped");
        this.playerFinished((SpeakPayload) object);
    }

    @Override
    public void onPlayerCompleted(ChannelPlayer player, Object object) {
        LogUtils.e("VoiceOutputModule onPlayerCompleted");
        this.playerFinished((SpeakPayload) object);
    }

    @Override
    public void onPlayerErrorOccurred(ChannelPlayer player, Object object, ErrorType error) {
        LogUtils.e("VoiceOutputModule onPlayerErrorOccurred");
        this.playerFinished((SpeakPayload) object);
    }

    @Override
    public void onPlayerBufferingStart(ChannelPlayer player, Object object) {
        LogUtils.e("VoiceOutputModule onPlayerBufferingStart");
    }

    @Override
    public void onPlayerBufferingEnded(ChannelPlayer player, Object object) {
        LogUtils.e("VoiceOutputModule onPlayerBufferingEnded");
    }

    @Override
    public void onBufferingUpdated(ChannelPlayer player, Object object, int bufferPercentage) {
        LogUtils.e("VoiceOutputModule onBufferingUpdated");
    }

    @Override
    public void onPlayerStateChanged(ChannelPlayer player, Object object, PlayerState state) {
        LogUtils.e("VoiceOutputModule player state changed " + state.name());
    }

    @Override
    public void onPlayerProgressChanged(ChannelPlayer player, Object object, int position, int duration) {
        LogUtils.e("VoiceOutputModule onPlayerProgressChanged");
    }

    /************************ Listener分发方法 **********************************/
    private void fireSpeakChannelOccupied() {
        if (null != this.listener && null != listener.get()) {
            this.listener.get().onSpeakChannelOccupied();
        }
    }

    private void fireSpeakChannelReleased() {
        if (null != this.listener && null != listener.get()) {
            this.listener.get().onSpeakChannelReleased();
        }
    }

    private void fireSpeechStarted(String token) {
        if (null != this.listener && null != listener.get()) {
            this.listener.get().onSpeechStarted(token);
        }
    }

    private void fireSpeechFinished(String token) {
        if (null != this.listener && null != listener.get()) {
            this.listener.get().onSpeechFinished(token);
        }
    }

    static {
        PayloadManager.getInstance().insertPayload(
                ApiConstants.NAMESPACE,
                ApiConstants.Directives.Speak.NAME,
                SpeakPayload.class
        );
    }
}
