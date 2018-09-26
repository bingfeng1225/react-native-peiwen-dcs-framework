package com.qd.peiwen.dcsframework.devices;

import android.content.Context;


import com.qd.peiwen.dcsframework.devices.audioplayer.AudioPlayerModule;
import com.qd.peiwen.dcsframework.devices.lifevoice.LifeVoiceModule;
import com.qd.peiwen.dcsframework.devices.navigation.NavigationModule;
import com.qd.peiwen.dcsframework.devices.phone.PhoneModule;
import com.qd.peiwen.dcsframework.devices.phonebill.PhoneBillModule;
import com.qd.peiwen.dcsframework.devices.screen.ScreenModule;
import com.qd.peiwen.dcsframework.devices.sms.SMSModule;
import com.qd.peiwen.dcsframework.devices.speakercontroller.SpeakerControllerModule;
import com.qd.peiwen.dcsframework.devices.system.SystemModule;
import com.qd.peiwen.dcsframework.devices.textinput.TextInputModule;
import com.qd.peiwen.dcsframework.devices.ticket.TicketModule;
import com.qd.peiwen.dcsframework.devices.voiceoutput.VoiceOutputModule;
import com.qd.peiwen.dcsframework.devices.voicerecognize.VoiceRecognizeModule;
import com.qd.peiwen.dcsframework.entity.header.BaseHeader;
import com.qd.peiwen.dcsframework.entity.request.DCSRequest;
import com.qd.peiwen.dcsframework.entity.respons.Directive;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nick on 2017/11/28.
 */

public class DeviceManager {
    private Context context;
    private ModuleFactory factory;
    private Map<String, BaseModule> modules = new HashMap<>();

    public DeviceManager(Context context) {
        this.context = context;
        this.factory = new ModuleFactory();

        SMSModule sms = smsModule();
        this.modules.put(sms.getNamespace(), sms);

        PhoneModule phone = phoneModule();
        this.modules.put(phone.getNamespace(), phone);

        SystemModule system = systemModule();
        this.modules.put(system.getNamespace(), system);

        ScreenModule screen = screenModule();
        this.modules.put(screen.getNamespace(), screen);

        TextInputModule textInput = textInputModule();
        this.modules.put(textInput.getNamespace(), textInput);

        VoiceOutputModule voiceoutput = voiceOutputModule();
        this.modules.put(voiceoutput.getNamespace(), voiceoutput);

        AudioPlayerModule audioPlayer = audioPlayerModule();
        this.modules.put(audioPlayer.getNamespace(), audioPlayer);

        VoiceRecognizeModule voiceRecognize = voiceRecognizeModule();
        this.modules.put(voiceRecognize.getNamespace(), voiceRecognize);

        SpeakerControllerModule speakerController = speakerControllerModule();
        this.modules.put(speakerController.getNamespace(), speakerController);

        LifeVoiceModule lifeVoice = lifeVoiceModule();
        this.modules.put(lifeVoice.getNamespace(), lifeVoice);

        NavigationModule navigation = navigationModule();
        this.modules.put(navigation.getNamespace(), navigation);

        PhoneBillModule phoneBill = phoneBillModule();
        this.modules.put(phoneBill.getNamespace(), phoneBill);

        TicketModule ticketModule = ticketModule();
        this.modules.put(ticketModule.getNamespace(), ticketModule);
    }

    public void init() {
        for (String key : this.modules.keySet()) {
            BaseModule module = this.modules.get(key);
            module.init();
        }
    }

    public void release() {
        for (String key : this.modules.keySet()) {
            BaseModule module = this.modules.get(key);
            module.release();
        }
    }


    /************************ Directive处理 **********************************/
    public boolean handleDirective(Directive directive) {
        BaseHeader header = directive.getHeader();
        BaseModule module = this.modules.get(header.getNamespace());
        if (null == module) {
            return false;
        }
        return module.handleDirective(directive);
    }

    /************************ SMS Event **********************************/
    public SMSModule smsModule() {
        return factory.smsModule(context);
    }

    /************************ Phone Event **********************************/
    public PhoneModule phoneModule() {
        return factory.phoneModule(context);
    }

    /************************ TextInput Event **********************************/
    public TextInputModule textInputModule() {
        return factory.textInputModule(context);
    }

    public DCSRequest textinputRequest(String text) {
        DCSRequest request = new DCSRequest();
        request.setEvent(textInputModule().textinputMessage(text));
        return request;
    }

    /***************************LifeVoice Event********************************/
    public LifeVoiceModule lifeVoiceModule() {
        return factory.lifeVoiceModule(context);
    }

    /******************************navigation Event****************************/

    public NavigationModule navigationModule() {
        return factory.navigationModule(context);
    }

    /******************************phone bill Event****************************/

    public PhoneBillModule phoneBillModule() {
        return factory.phoneBillModule(context);
    }

    /*******************************ticket events******************************/

    public TicketModule ticketModule() {
        return factory.ticketModule(context);
    }

    /************************ Screen Event **********************************/
    public ScreenModule screenModule() {
        return factory.screenModule(context);
    }

    /************************ System Event **********************************/
    public SystemModule systemModule() {
        return factory.systemModule(context);
    }

    public DCSRequest userInactivityReportRequest(long userInactivity) {
        DCSRequest request = new DCSRequest();
        request.setEvent(systemModule().userInactivityReportRequest(userInactivity));
        return request;
    }

    public DCSRequest exceptionEncounteredRequest(String unparse, String type, String message) {
        DCSRequest request = new DCSRequest();
        request.setEvent(systemModule().exceptionEncounteredRequest(unparse, type, message));
        return request;
    }

    /************************ VoiceOutput Event **********************************/
    public VoiceOutputModule voiceOutputModule() {
        return factory.voiceOutputModule(context);
    }

    public DCSRequest speechStartedRequest(String token) {
        DCSRequest request = new DCSRequest();
        request.setEvent(voiceOutputModule().speechStartedRequest(token));
        return request;
    }

    public DCSRequest speechFinishedRequest(String token) {
        DCSRequest request = new DCSRequest();
        request.setEvent(voiceOutputModule().speechFinishedRequest(token));
        return request;
    }


    /************************ AudioPalyer Event **********************************/
    public AudioPlayerModule audioPlayerModule() {
        return factory.audioPlayerModule(context);
    }

    public DCSRequest playbackStartedRequest(String token, long offsetInMilliseconds) {
        DCSRequest request = new DCSRequest();
        request.setEvent(audioPlayerModule().playbackStartedRequest(token, offsetInMilliseconds));
        return request;
    }

    public DCSRequest playbackFinishedRequest(String token, long offsetInMilliseconds) {
        DCSRequest request = new DCSRequest();
        request.setEvent(audioPlayerModule().playbackFinishedRequest(token, offsetInMilliseconds));
        return request;
    }

    public DCSRequest playbackNearlyFinishedRequest(String token, long offsetInMilliseconds) {
        DCSRequest request = new DCSRequest();
        request.setEvent(audioPlayerModule().playbackNearlyFinishedRequest(token, offsetInMilliseconds));
        return request;
    }

    public DCSRequest playbackFailedRequest(String token, String type, String message) {
        DCSRequest request = new DCSRequest();
        request.setEvent(audioPlayerModule().playbackFailedRequest(token, type, message));
        return request;
    }

    public DCSRequest playbackStutterStartedRequest(String token, long offsetInMilliseconds) {
        DCSRequest request = new DCSRequest();
        request.setEvent(audioPlayerModule().playbackStutterStartedRequest(token, offsetInMilliseconds));
        return request;
    }

    public DCSRequest playbackStutterFinishedRequest(String token, long offsetInMilliseconds, long stutterDurationInMilliseconds) {
        DCSRequest request = new DCSRequest();
        request.setEvent(audioPlayerModule().playbackStutterFinishedRequest(token, offsetInMilliseconds, stutterDurationInMilliseconds));
        return request;
    }

    public DCSRequest playbackPausedRequest(String token, long offsetInMilliseconds) {
        DCSRequest request = new DCSRequest();
        request.setEvent(audioPlayerModule().playbackPausedRequest(token, offsetInMilliseconds));
        return request;
    }

    public DCSRequest playbackResumedRequest(String token, long offsetInMilliseconds) {
        DCSRequest request = new DCSRequest();
        request.setEvent(audioPlayerModule().playbackResumedRequest(token, offsetInMilliseconds));
        return request;
    }

    public DCSRequest playbackStoppedRequest(String token, long offsetInMilliseconds) {
        DCSRequest request = new DCSRequest();
        request.setEvent(audioPlayerModule().playbackStoppedRequest(token, offsetInMilliseconds));
        return request;
    }

    public DCSRequest playbackQueueClearedRequest() {
        DCSRequest request = new DCSRequest();
        request.setEvent(audioPlayerModule().playbackQueueClearedRequest());
        return request;
    }

    public DCSRequest progressReportDelayElapsedRequest(String token, long offsetInMilliseconds) {
        DCSRequest request = new DCSRequest();
        request.setEvent(audioPlayerModule().progressReportDelayElapsedRequest(token, offsetInMilliseconds));
        return request;
    }

    public DCSRequest progressReportIntervalElapsedRequest(String token, long offsetInMilliseconds) {
        DCSRequest request = new DCSRequest();
        request.setEvent(audioPlayerModule().progressReportIntervalElapsedRequest(token, offsetInMilliseconds));
        return request;
    }

    public DCSRequest streamMetadataExtractedRequest(String token, Map<String, Object> metadata) {
        DCSRequest request = new DCSRequest();
        request.setEvent(audioPlayerModule().streamMetadataExtractedRequest(token, metadata));
        return request;
    }

    /************************ SpeakerController Event **********************************/
    public VoiceRecognizeModule voiceRecognizeModule() {
        return factory.voiceRecognizeModule(context);
    }

    /************************ SpeakerController Event **********************************/
    public SpeakerControllerModule speakerControllerModule() {
        return factory.speakerControllerModule(context);
    }

    public DCSRequest muteChangedRequest(int volume, boolean muted) {
        DCSRequest request = new DCSRequest();
        request.setEvent(speakerControllerModule().muteChangedRequest(volume, muted));
        return request;
    }

    public DCSRequest volumeChangedRequest(int volume, boolean muted) {
        DCSRequest request = new DCSRequest();
        request.setEvent(speakerControllerModule().volumeChangedRequest(volume, muted));
        return request;
    }
}
