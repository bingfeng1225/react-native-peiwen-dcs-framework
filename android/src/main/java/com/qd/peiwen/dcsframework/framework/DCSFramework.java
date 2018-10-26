package com.qd.peiwen.dcsframework.framework;

import android.content.Context;

import com.qd.peiwen.dcsframework.devices.DeviceManager;
import com.qd.peiwen.dcsframework.devices.audioplayer.listener.IAudioPlayerModuleListener;
import com.qd.peiwen.dcsframework.devices.navigation.listener.INavigationModuleListener;
import com.qd.peiwen.dcsframework.devices.navigation.message.directive.NavigationPayload;
import com.qd.peiwen.dcsframework.devices.phone.listener.IPhoneModuleListener;
import com.qd.peiwen.dcsframework.devices.phone.message.directive.PhoneCallPayload;
import com.qd.peiwen.dcsframework.devices.phone.message.directive.PhoneSearchPayload;
import com.qd.peiwen.dcsframework.devices.phonebill.message.directive.PhoneBillPayload;
import com.qd.peiwen.dcsframework.devices.screen.listener.IScreenModuleListener;
import com.qd.peiwen.dcsframework.devices.screen.message.directive.ImageListCardPayload;
import com.qd.peiwen.dcsframework.devices.screen.message.directive.ListCardPayload;
import com.qd.peiwen.dcsframework.devices.screen.message.directive.PhoneListCardPayload;
import com.qd.peiwen.dcsframework.devices.screen.message.directive.SMSMessageListCardPayload;
import com.qd.peiwen.dcsframework.devices.screen.message.directive.ServiceCardPayload;
import com.qd.peiwen.dcsframework.devices.screen.message.directive.ServiceListCardPayload;
import com.qd.peiwen.dcsframework.devices.screen.message.directive.StandardCardPayload;
import com.qd.peiwen.dcsframework.devices.screen.message.directive.TextCardPayload;
import com.qd.peiwen.dcsframework.devices.sms.listener.ISMSModuleListener;
import com.qd.peiwen.dcsframework.devices.sms.message.directive.SMSSearchPayload;
import com.qd.peiwen.dcsframework.devices.sms.message.directive.SMSSendPayload;
import com.qd.peiwen.dcsframework.devices.speakercontroller.listener.ISpeakerControllerModuleListener;
import com.qd.peiwen.dcsframework.devices.speakercontroller.message.directive.AdjustVolumePayload;
import com.qd.peiwen.dcsframework.devices.speakercontroller.message.directive.SetMutePayload;
import com.qd.peiwen.dcsframework.devices.speakercontroller.message.directive.SetVolumePayload;
import com.qd.peiwen.dcsframework.devices.system.listener.ISystemModuleListener;
import com.qd.peiwen.dcsframework.devices.system.message.directive.SetEndPointPayload;
import com.qd.peiwen.dcsframework.devices.system.message.directive.SetSessionidPayload;
import com.qd.peiwen.dcsframework.devices.system.message.directive.ThrowExceptionPayload;
import com.qd.peiwen.dcsframework.devices.ticket.listener.ITicketModuleListener;
import com.qd.peiwen.dcsframework.devices.ticket.message.directive.TicketPayload;
import com.qd.peiwen.dcsframework.devices.voiceoutput.listener.IVoiceOutputModuleListener;
import com.qd.peiwen.dcsframework.devices.voiceoutput.message.directive.SpeakPayload;
import com.qd.peiwen.dcsframework.devices.voicerecognize.listener.IVoiceRecognizeModuleListener;
import com.qd.peiwen.dcsframework.devices.voicerecognize.message.directive.VoiceRecognizePayload;
import com.qd.peiwen.dcsframework.directivequeue.DirectiveQueueManager;
import com.qd.peiwen.dcsframework.directivequeue.listener.IDirectiveQueueListener;
import com.qd.peiwen.dcsframework.enmudefine.ExceptionType;
import com.qd.peiwen.dcsframework.entity.header.BaseHeader;
import com.qd.peiwen.dcsframework.entity.payload.BasePayload;
import com.qd.peiwen.dcsframework.entity.request.DCSRequest;
import com.qd.peiwen.dcsframework.entity.respons.DCSRespons;
import com.qd.peiwen.dcsframework.entity.respons.Directive;
import com.qd.peiwen.dcsframework.entity.screen.SendEventCard;
import com.qd.peiwen.dcsframework.httppackage.HttpManager;
import com.qd.peiwen.dcsframework.httppackage.listener.IRequestListener;
import com.qd.peiwen.dcsframework.httppackage.request.HttpRequest;
import com.qd.peiwen.dcsframework.musicplayer.ChannelManager;
import com.qd.peiwen.dcsframework.tools.IDCreator;
import com.qd.peiwen.dcsframework.tools.LogUtils;
import com.qd.peiwen.dcsframework.volume.IVolumeListener;
import com.qd.peiwen.dcsframework.volume.VolumeManager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by nick on 2017/12/1.
 */

public class DCSFramework implements
        IVolumeListener,
        ISMSModuleListener,
        IPhoneModuleListener,
        IDirectiveQueueListener,
        ISystemModuleListener,
        IScreenModuleListener,
        IVoiceOutputModuleListener,
        IAudioPlayerModuleListener,
        IVoiceRecognizeModuleListener,
        ISpeakerControllerModuleListener,
        INavigationModuleListener,
        ITicketModuleListener {

    private Context context;
    private String lastSessionid = "";
    private HttpManager httpManager;
    private DeviceManager deviceManager;
    private VolumeManager volumeManager;
    private ChannelManager channelManager;
    private DirectiveQueueManager directiveQueueManager;
    private WeakReference<IDCSFrameListener> listener;

    public DCSFramework(Context context) {
        this.context = context.getApplicationContext();
    }

    public void init() {
        this.initHttpManager();
        this.initMessageQueue();
        this.initDeviceManager();
        this.initChannelManager();
        this.initVolumeManager();
    }

    private void initHttpManager() {
        this.httpManager = new HttpManager(context);
        this.httpManager.init();
    }

    private void initMessageQueue() {
        this.directiveQueueManager = new DirectiveQueueManager(context);
        this.directiveQueueManager.setListener(this);
        this.directiveQueueManager.init();
    }

    private void initVolumeManager() {
        this.volumeManager = new VolumeManager(context);
        this.volumeManager.setListener(this);
        this.volumeManager.init();
    }

    private void initChannelManager() {
        this.channelManager = new ChannelManager(context);
        this.channelManager.addPlayer(this.deviceManager.audioPlayerModule().channelPlayer());
        this.channelManager.addPlayer(this.deviceManager.voiceOutputModule().channelPlayer());
    }

    private void initDeviceManager() {
        this.deviceManager = new DeviceManager(context);
        this.deviceManager.smsModule().setListener(this);
        this.deviceManager.phoneModule().setListener(this);
        this.deviceManager.systemModule().setListener(this);
        this.deviceManager.screenModule().setListener(this);
        this.deviceManager.voiceOutputModule().setListener(this);
        this.deviceManager.audioPlayerModule().setListener(this);
        this.deviceManager.voiceRecognizeModule().setListener(this);
        this.deviceManager.navigationModule().setListener(this);
        this.deviceManager.ticketModule().setListener(this);
        this.deviceManager.speakerControllerModule().setListener(this);
        this.deviceManager.voiceOutputModule().setHttpClient(this.httpManager.httpClient());
        this.deviceManager.init();
    }

    public void setListener(IDCSFrameListener listener) {
        this.listener = new WeakReference<>(listener);
    }

    public void release() {
        this.directiveQueueManager.release();
        this.deviceManager.release();
        this.volumeManager.release();
        this.channelManager.release();
        this.httpManager.release();
    }

    /************************ 功能接口 **********************************/
    public void callPhone(String phone) {
        deviceManager.phoneModule().callPhone(phone);
    }

    public void navigate(String destination) {
        deviceManager.navigationModule().startNavigation(destination);
    }

    public void orderTicket(String url) {

    }

    public void sendMessage(String phone, String content) {
        deviceManager.smsModule().sendMessage(phone, content);
    }

    public void sendVoiceRecognizeRequest(SendEventCard card,String localtion) {
        this.dialogChannelOccupied();
        httpManager.voiceRecognizeRequest(card,this.lastSessionid,localtion, new VoiceRecognizeListener(card));
    }

    public void sendTextInputEvent(VoiceRecognizePayload payload) {
        httpManager.eventRequest(payload.getUuid(), deviceManager.textinputRequest(payload.getContent()), new TextEventListener());
    }

    /************************ 私有方法 **********************************/
    private boolean checkRespons(DCSRespons respons) {
        Directive directive = respons.getDirective();
        if (null == directive) {
            return false;
        }
        BaseHeader header = directive.getHeader();
        BasePayload payload = directive.getPayload();
        if (null == header || null == payload) {
            return false;
        }
        return true;
    }


    private void sendExceptionEncounteredEvent(String unparse, String type, String message) {
        DCSRequest request = deviceManager.exceptionEncounteredRequest(unparse, type, message);
        httpManager.eventRequest(request, new EventListener());
    }

    /************************ 指令队列监听 **********************************/
    @Override
    public void onHandleDirective(DCSRespons respons) {
        try {
            if (!this.deviceManager.handleDirective(respons.getDirective())) {
                String message = "Device cannot handle the directive";
                String type = ExceptionType.EVENT_UNSUPPORTED_OPERATION.message();
                sendExceptionEncounteredEvent(respons.getMessage(), type, message);
            }
        } catch (Exception e) {
            String type = ExceptionType.EVENT_INTERNAL_ERROR.message();
            sendExceptionEncounteredEvent(respons.getMessage(), type, e.getMessage());
        }
    }

    /************************ 声音通道变化 **********************************/
    public void enterBackground() {
        this.channelManager.enterBackground();
    }

    public void becomeForeground() {
        this.channelManager.becomeForeground();
    }

    public void audioRecordStarted() {
        this.channelManager.audioRecordStarted();
        this.deviceManager.voiceOutputModule().channelPlayer().stop();
    }

    public void audioRecordFinished() {
        this.channelManager.audioRecordFinished();
    }

    private void dialogChannelReleased(String uuid) {
        if (IDCreator.isActiveRequestId(uuid)) {
            this.channelManager.dialogChannelReleased();
        }
    }

    public void dialogChannelOccupied() {
        this.channelManager.dialogChannelOccupied();
        this.deviceManager.voiceOutputModule().channelPlayer().stop();
    }

    @Override
    public void onSpeakChannelOccupied() {
        channelManager.speakChannelOccupied();
    }

    @Override
    public void onSpeakChannelReleased() {
        channelManager.speakChannelReleased();
    }

    @Override
    public void onAudioChannelOccupied() {
        this.channelManager.audioChannelOccupied();
    }

    @Override
    public void onAudioChannelReleased() {
        this.channelManager.audioChannelReleased();
    }

    /********************** 端能力（通讯录）监听 ******************************/
    @Override
    public void onPhoneDialogFinished(String uuid) {
        this.dialogChannelReleased(uuid);
    }

    @Override
    public void onPhoneModuleDirective(DCSRespons respons) {
        if (respons.getDirective().getPayload() instanceof SpeakPayload) {
            this.onHandleDirective(respons);
        } else {
            this.directiveQueueManager.handleResponseBody(respons);
        }
    }

    /********************** 端能力（短信）监听 ******************************/
    @Override
    public void onSMSDialogFinished(String uuid) {
        this.dialogChannelReleased(uuid);
    }

    @Override
    public void onSMSModuleDirective(DCSRespons respons) {
        if (respons.getDirective().getPayload() instanceof SpeakPayload) {
            this.onHandleDirective(respons);
        } else {
            this.directiveQueueManager.handleResponseBody(respons);
        }
    }

    /********************** 端能力（系统）监听 ******************************/
    @Override
    public void onResetUserInactivity() {
        deviceManager.systemModule().userActivity();
    }

    @Override
    public void onUserInactivityReport(long userInactivity) {
        DCSRequest request = deviceManager.userInactivityReportRequest(userInactivity);
        httpManager.eventRequest(request, new EventListener());
    }

    @Override
    public void onSetEndpoint(SetEndPointPayload endPointPayload) {
        if (null != endPointPayload) {
            String endpoint = endPointPayload.getEndpoint();
            if (null != endpoint && endpoint.length() > 0) {
                httpManager.setEndpoint(endPointPayload.getEndpoint());
            }
        }
    }

    @Override
    public void onSetSessionid(SetSessionidPayload sessionidPayload) {
        LogUtils.d("onSetSessionid---------frame");
        this.lastSessionid = (sessionidPayload == null ? null : sessionidPayload.getSessionid());
    }

    @Override
    public void onThrowException(ThrowExceptionPayload exceptionPayload) {
        LogUtils.d("ThrowException " + exceptionPayload.getCode());
    }

    /********************** 端能力（屏幕显示）监听 ******************************/
    @Override
    public void onRecvTextCard(TextCardPayload payload) {
        fireRecvTextCard(payload);
    }

    @Override
    public void onRecvListCard(ListCardPayload payload) {
        fireRecvListCard(payload);
    }

    @Override
    public void onRecvServiceCard(ServiceCardPayload payload) {
        fireRecvServiceCard(payload);
    }

    @Override
    public void onRecvStandardCard(StandardCardPayload payload) {
        fireRecvStandardCard(payload);
    }

    @Override
    public void onRecvImageListCard(ImageListCardPayload payload) {
        fireRecvImageListCard(payload);
    }

    @Override
    public void onRecvPhoneListCard(PhoneListCardPayload payload) {
        fireRecvPhoneListCard(payload);
    }

    @Override
    public void onRecvServiceListCard(ServiceListCardPayload payload) {
        fireRecvServiceListCard(payload);
    }

    @Override
    public void onRecvSMSMessageListCard(SMSMessageListCardPayload payload) {
        fireRecvSMSMessageListCard(payload);
    }


    /********************** 端能力（语音识别）监听 ******************************/
    @Override
    public void onRecvVoiceRecognize(VoiceRecognizePayload payload) {
        fireRecvVoiceRecognize(payload);
    }

    /********************** 端能力（扬声器控制）监听 ******************************/
    @Override
    public void onSetMute(SetMutePayload mutepayload) {
        volumeManager.setMute(mutepayload.isMute());
    }

    @Override
    public void onSetVolume(SetVolumePayload volumepayload) {
        volumeManager.setVolume(volumepayload.getVolume());
    }

    @Override
    public void onAdjustVolume(AdjustVolumePayload volumepayload) {
        volumeManager.adjustVolume(volumepayload.getVolume());
    }

    /********************** 端能力（语音播报）监听 ******************************/

    @Override
    public void onSpeechStarted(String token) {
        DCSRequest request = deviceManager.speechStartedRequest(token);
        httpManager.eventRequest(request, new EventListener());
    }

    @Override
    public void onSpeechFinished(String token) {
        DCSRequest request = deviceManager.speechFinishedRequest(token);
        httpManager.eventRequest(request, new EventListener());
    }

    /********************** 端能力（音乐播放）监听 ******************************/
    @Override
    public void onPlaybackQueueCleared() {
        DCSRequest request = deviceManager.playbackQueueClearedRequest();
        httpManager.eventRequest(request, new EventListener());
    }

    @Override
    public void onPlaybackPaused(String token, long offsetInMilliseconds) {
        DCSRequest request = deviceManager.playbackPausedRequest(token, offsetInMilliseconds);
        httpManager.eventRequest(request, new EventListener());
    }

    @Override
    public void onPlaybackResumed(String token, long offsetInMilliseconds) {
        DCSRequest request = deviceManager.playbackResumedRequest(token, offsetInMilliseconds);
        httpManager.eventRequest(request, new EventListener());
    }

    @Override
    public void onPlaybackStopped(String token, long offsetInMilliseconds) {
        DCSRequest request = deviceManager.playbackStoppedRequest(token, offsetInMilliseconds);
        httpManager.eventRequest(request, new EventListener());
    }

    @Override
    public void onPlaybackStarted(String token, long offsetInMilliseconds) {
        DCSRequest request = deviceManager.playbackStartedRequest(token, offsetInMilliseconds);
        httpManager.eventRequest(request, new EventListener());
    }

    @Override
    public void onPlaybackFinished(String token, long offsetInMilliseconds) {
        DCSRequest request = deviceManager.playbackFinishedRequest(token, offsetInMilliseconds);
        httpManager.eventRequest(request, new EventListener());
    }

    @Override
    public void onPlaybackFailed(String token, String type, String message) {
        DCSRequest request = deviceManager.playbackFailedRequest(token, type, message);
        httpManager.eventRequest(request, new EventListener());
    }

    @Override
    public void onPlaybackNearlyFinished(String token, long offsetInMilliseconds) {
        DCSRequest request = deviceManager.playbackNearlyFinishedRequest(token, offsetInMilliseconds);
        httpManager.eventRequest(request, new EventListener());
    }

    @Override
    public void onPlaybackStutterStarted(String token, long offsetInMilliseconds) {
        DCSRequest request = deviceManager.playbackStutterStartedRequest(token, offsetInMilliseconds);
        httpManager.eventRequest(request, new EventListener());
    }

    @Override
    public void onStreamMetadataExtracted(String token, Map<String, Object> metadata) {
        DCSRequest request = deviceManager.streamMetadataExtractedRequest(token, metadata);
        httpManager.eventRequest(request, new EventListener());
    }

    @Override
    public void onProgressReportDelayElapsed(String token, long offsetInMilliseconds) {
        DCSRequest request = deviceManager.progressReportDelayElapsedRequest(token, offsetInMilliseconds);
        httpManager.eventRequest(request, new EventListener());
    }

    @Override
    public void onProgressReportIntervalElapsed(String token, long offsetInMilliseconds) {
        DCSRequest request = deviceManager.progressReportIntervalElapsedRequest(token, offsetInMilliseconds);
        httpManager.eventRequest(request, new EventListener());
    }

    @Override
    public void onPlaybackStutterFinished(String token, long offsetInMilliseconds, long stutterDurationInMilliseconds) {
        DCSRequest request = deviceManager.playbackStutterFinishedRequest(token, offsetInMilliseconds, stutterDurationInMilliseconds);
        httpManager.eventRequest(request, new EventListener());
    }

    /************************ 音量变化监听 **********************************/
    @Override
    public void onMuteChanged(int volume) {
        DCSRequest request = deviceManager.muteChangedRequest(volume, (volume == 0));
        httpManager.eventRequest(request, new EventListener());
    }

    @Override
    public void onVolumeChanged(int volume) {
        DCSRequest request = deviceManager.volumeChangedRequest(volume, (volume == 0));
        httpManager.eventRequest(request, new EventListener());
    }

    @Override
    public void onAudioFocusLossed() {
        this.channelManager.audioFocusLossed();
    }

    @Override
    public void onAudioFocusGranted() {
        this.channelManager.audioFocusGranted();
    }

    /************************ Listener分发方法 **********************************/
    private void fireTextInputFailured(final String uuid) {
        if (null != listener && null != listener.get()) {
            listener.get().onTextInputFailured(uuid);
        }
    }

    private void fireInputEventSuccessed(final String uuid) {
        if (null != listener && null != listener.get()) {
            listener.get().onInputEventSuccessed(uuid);
        }
    }

    private void fireTextInputStarted(final String uuid) {
        if (null != listener && null != listener.get()) {
            listener.get().onTextInputStarted(uuid);
        }
    }


    private void fireVoiceRecognizeStarted(final SendEventCard card) {
        if (null != listener && null != listener.get()) {
            listener.get().onVoiceRecognizeStarted(card);
        }
    }

    private void fireVoiceRecognizeFailured(final String uuid) {
        if (null != listener && null != listener.get()) {
            listener.get().onVoiceRecognizeFailured(uuid);
        }
    }

    private void fireVoiceRecognizeSuccessed(final String uuid) {
        if (null != listener && null != listener.get()) {
            listener.get().onVoiceRecognizeSuccessed(uuid);
        }
    }


    private void fireRecvTextCard(final TextCardPayload payload) {
        if (null != listener && null != listener.get()) {
            listener.get().onRecvTextCard(payload);
        }
    }

    private void fireRecvListCard(final ListCardPayload payload) {
        if (null != listener && null != listener.get()) {
            listener.get().onRecvListCard(payload);
        }
    }

    private void fireRecvServiceCard(final ServiceCardPayload payload) {
        if (null != listener && null != listener.get()) {
            listener.get().onRecvServiceCard(payload);
        }
    }

    private void fireRecvStandardCard(final StandardCardPayload payload) {
        if (null != listener && null != listener.get()) {
            listener.get().onRecvStandardCard(payload);
        }
    }

    private void fireRecvImageListCard(final ImageListCardPayload payload) {
        if (null != listener && null != listener.get()) {
            listener.get().onRecvImageListCard(payload);
        }
    }

    private void fireRecvPhoneListCard(final PhoneListCardPayload payload) {
        if (null != listener && null != listener.get()) {
            listener.get().onRecvPhoneListCard(payload);
        }
    }

    private void fireRecvServiceListCard(final ServiceListCardPayload payload) {
        if (null != listener && null != listener.get()) {
            listener.get().onRecvServiceListCard(payload);
        }
    }

    private void fireRecvSMSMessageListCard(final SMSMessageListCardPayload payload) {
        if (null != listener && null != listener.get()) {
            listener.get().onRecvSMSMessageListCard(payload);
        }
    }

    private void fireRecvVoiceRecognize(final VoiceRecognizePayload payload) {
        if (null != listener && null != listener.get()) {
            listener.get().onRecvVoiceRecognize(payload);
        }
    }


    @Override
    public void onNavigationPayload(final NavigationPayload payload) {
        if (null != listener && null != listener.get()) {
            listener.get().onRecvNavigation(payload);
        }
    }

    @Override
    public void onRecvTrainTicketPayload(final TicketPayload payload) {
        if (null != listener && null != listener.get()) {
            listener.get().onRecvTrainTicket(payload);
        }
    }

    @Override
    public void onRecvFlightTicketPayload(final TicketPayload payload) {
        if (null != listener && null != listener.get()) {
            listener.get().onRecvFlightTicket(payload);
        }
    }


    /************************ 内部私有监听类 **********************************/
    private class EventListener implements IRequestListener {
        private boolean success = false;
        private boolean canceled = false;
        private List<DCSRespons> responses = new ArrayList<>();

        @Override
        public void onStarted(String uuid, HttpRequest request) {
        }

        @Override
        public void onSuccessed(String uuid, HttpRequest request) {
            this.success = true;
        }

        @Override
        public void onCanceled(String uuid, HttpRequest request) {
            this.canceled = true;
        }

        @Override
        public void onFailured(String uuid, HttpRequest request) {
            this.success = false;
        }

        @Override
        public void onDirectiveRecevied(String uuid, HttpRequest request, Object respons) {
            LogUtils.d("onDirectiveRecevied-----EventListener");
            DCSRespons dcsRespons = (DCSRespons) respons;
            if (!checkRespons(dcsRespons)) {
                String message = "parse failed";
                String type = ExceptionType.EVENT_UNEXPECTED_INFORMATION_RECEIVED.message();
                sendExceptionEncounteredEvent(dcsRespons.getMessage(), type, message);
            } else {
                BasePayload payload = dcsRespons.getDirective().getPayload();
                payload.setUuid(request.uuid());
                responses.add(dcsRespons);
            }
        }

        @Override
        public void onFinished(String uuid, HttpRequest request) {
            if (canceled || !success) {
                return;
            }
            for (DCSRespons respons : responses) {
                directiveQueueManager.handleResponseBody(respons);
            }
        }
    }

    private class TextEventListener implements IRequestListener {
        private boolean resume = false;
        private boolean success = false;
        private boolean canceled = false;
        private DCSRespons exceptionRespons = null;
        private List<DCSRespons> responses = new ArrayList<>();

        public TextEventListener() {

        }

        @Override
        public void onStarted(String uuid, HttpRequest request) {
            fireTextInputStarted(uuid);
        }

        @Override
        public void onCanceled(String uuid, HttpRequest request) {
            this.canceled = true;
        }

        @Override
        public void onSuccessed(String uuid, HttpRequest request) {
            this.success = true;
        }

        @Override
        public void onFailured(String uuid, HttpRequest request) {
            this.success = false;
        }

        @Override
        public void onDirectiveRecevied(String uuid, HttpRequest request, Object respons) {
            LogUtils.d("onDirectiveReceived-------------textEventListener");
            DCSRespons dcsRespons = (DCSRespons) respons;
            if (!checkRespons(dcsRespons)) {
                String message = "parse failed";
                String type = ExceptionType.EVENT_UNEXPECTED_INFORMATION_RECEIVED.message();
                sendExceptionEncounteredEvent(dcsRespons.getMessage(), type, message);
            } else {
                BasePayload payload = dcsRespons.getDirective().getPayload();
                payload.setUuid(request.uuid());
                if (payload instanceof SpeakPayload) {
                    onHandleDirective(dcsRespons);
                } else if (payload instanceof ThrowExceptionPayload) {
                    this.exceptionRespons = dcsRespons;
                } else {
                    this.responses.add(dcsRespons);
                }
            }
        }

        @Override
        public void onFinished(String uuid, HttpRequest request) {
            if (!success || canceled) {
                dialogChannelReleased(uuid);
                fireTextInputFailured(uuid);
            } else if (null != exceptionRespons) {
                dialogChannelReleased(uuid);
                fireTextInputFailured(uuid);
                directiveQueueManager.handleResponseBody(exceptionRespons);
            } else {
                for (DCSRespons respons : responses) {
                    directiveQueueManager.handleResponseBody(respons);
                }
                if (!resume) {
                    dialogChannelReleased(uuid);
                }
                fireInputEventSuccessed(uuid);
            }
        }
    }

    private class VoiceRecognizeListener implements IRequestListener {
        private boolean resume = false;
        private boolean success = false;
        private boolean canceled = false;
        private SendEventCard eventCard;
        private DCSRespons exceptionRespons = null;
        private DCSRespons voiceRecongnizeResons = null;
        private DCSRespons phoneCallRespons = null;
        private List<DCSRespons> responses = new ArrayList<>();

        public VoiceRecognizeListener(SendEventCard eventCard) {
            this.eventCard = eventCard;
        }

        @Override
        public void onStarted(String uuid, HttpRequest request) {
            fireVoiceRecognizeStarted(this.eventCard);
        }

        @Override
        public void onSuccessed(String uuid, HttpRequest request) {
            this.success = true;
        }

        @Override
        public void onCanceled(String uuid, HttpRequest request) {
            this.canceled = true;
        }

        @Override
        public void onFailured(String uuid, HttpRequest request) {
            this.success = false;
        }

        @Override
        public void onDirectiveRecevied(String uuid, HttpRequest request, Object respons) {
            LogUtils.d("onDirectiveRecevied-----RecognizeListener");
            DCSRespons dcsRespons = (DCSRespons) respons;
            if (checkRespons(dcsRespons)) {
                BasePayload payload = dcsRespons.getDirective().getPayload();
                payload.setUuid(request.uuid());
                if (payload instanceof SpeakPayload) {
                    onHandleDirective(dcsRespons);
                } else if (payload instanceof ThrowExceptionPayload) {
                    this.exceptionRespons = dcsRespons;
                } else if (payload instanceof VoiceRecognizePayload) {
                    this.voiceRecongnizeResons = dcsRespons;
                } else if (payload instanceof PhoneCallPayload) {
                    this.resume = true;
                    this.phoneCallRespons = dcsRespons;
                } else {
                    if (payload instanceof PhoneSearchPayload) {
                        this.resume = true;
                    } else if (payload instanceof SMSSearchPayload) {
                        this.resume = true;
                    } else if (payload instanceof SMSSendPayload) {
                        this.resume = true;
                    }
                    this.responses.add(dcsRespons);
                }
            }
        }

        @Override
        public void onFinished(String uuid, HttpRequest request) {
            if (!success || canceled) {
                dialogChannelReleased(uuid);
                fireVoiceRecognizeFailured(uuid);
            } else if (null != exceptionRespons) {
                dialogChannelReleased(uuid);
                fireVoiceRecognizeFailured(uuid);
                directiveQueueManager.handleResponseBody(exceptionRespons);
            } else if (null != voiceRecongnizeResons) {
                fireVoiceRecognizeSuccessed(uuid);
                directiveQueueManager.handleResponseBody(voiceRecongnizeResons);
            } else if (null != phoneCallRespons) {
                fireInputEventSuccessed(uuid);
                directiveQueueManager.handleResponseBody(phoneCallRespons);
                onSetSessionid(new SetSessionidPayload(""));
            } else {
                for (DCSRespons respons : responses) {
                    directiveQueueManager.handleResponseBody(respons);
                }
                if (!resume) {
                    dialogChannelReleased(uuid);
                }
                fireInputEventSuccessed(uuid);
            }
        }
    }
}
