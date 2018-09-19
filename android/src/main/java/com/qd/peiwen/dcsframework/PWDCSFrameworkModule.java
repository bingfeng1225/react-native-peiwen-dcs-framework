
package com.qd.peiwen.dcsframework;


import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.google.gson.Gson;
import com.qd.peiwen.dcsframework.devices.lifevoice.message.directive.LifeVoicePayload;
import com.qd.peiwen.dcsframework.devices.navigation.message.directive.NavigationPayload;
import com.qd.peiwen.dcsframework.devices.phonebill.message.directive.PhoneBillPayload;
import com.qd.peiwen.dcsframework.devices.screen.message.directive.ImageListCardPayload;
import com.qd.peiwen.dcsframework.devices.screen.message.directive.ListCardPayload;
import com.qd.peiwen.dcsframework.devices.screen.message.directive.PhoneListCardPayload;
import com.qd.peiwen.dcsframework.devices.screen.message.directive.SMSMessageListCardPayload;
import com.qd.peiwen.dcsframework.devices.screen.message.directive.ServiceCardPayload;
import com.qd.peiwen.dcsframework.devices.screen.message.directive.ServiceListCardPayload;
import com.qd.peiwen.dcsframework.devices.screen.message.directive.StandardCardPayload;
import com.qd.peiwen.dcsframework.devices.screen.message.directive.TextCardPayload;
import com.qd.peiwen.dcsframework.devices.ticket.message.directive.TicketPayload;
import com.qd.peiwen.dcsframework.devices.voicerecognize.message.directive.VoiceRecognizePayload;
import com.qd.peiwen.dcsframework.enmudefine.RNEventType;
import com.qd.peiwen.dcsframework.enmudefine.SendEventState;
import com.qd.peiwen.dcsframework.entity.screen.SendEventCard;
import com.qd.peiwen.dcsframework.framework.DCSFramework;
import com.qd.peiwen.dcsframework.framework.IDCSFrameListener;
import com.qd.peiwen.dcsframework.httppackage.HttpConfig;
import com.qd.peiwen.dcsframework.tools.IDCreator;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class PWDCSFrameworkModule extends ReactContextBaseJavaModule implements IDCSFrameListener {

    private DCSFramework framework;
    private final ReactApplicationContext context;

    public PWDCSFrameworkModule(ReactApplicationContext context) {
        super(context);
        this.context = context;
    }

    @Override
    public String getName() {
        return "PWDCSFramework";
    }

    @ReactMethod
    public void initFramework(String deviceid,String token){
        HttpConfig.token = token;
        HttpConfig.deviceID = deviceid;
        if(null == this.framework) {
            this.framework = new DCSFramework(this.context);
            this.framework.setListener(this);
            this.framework.init();
        }
    }

    @ReactMethod
    public void releaseFramework(){
        if(null != this.framework) {
            this.framework.release();
            this.framework = null;
        }
    }

    @ReactMethod
    public void sendTextRequest(String content){
        if(null != this.framework) {
            SendEventCard card = new SendEventCard();
            card.setContent(content);
            card.setUuid(IDCreator.createActiveRequestId());
            card.setTimestamp(System.currentTimeMillis());
            this.framework.sendVoiceRecognizeRequest(card);
        }
    }

    @Override
    public void onTextInputStarted(String uuid) {
        this.context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(
                        RNEventType.TEXT_INPUT_STARTED.name(),
                        uuid
                );
    }

    @Override
    public void onTextInputFailured(String uuid) {
        this.context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(
                        RNEventType.TEXT_INPUT_FAILURED.name(),
                        uuid
                );
    }

    @Override
    public void onInputEventSuccessed(String uuid) {
        this.context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(
                        RNEventType.INPUT_EVENT_SUCCESSED.name(),
                        uuid
                );
    }

    @Override
    public void onVoiceRecognizeStarted(SendEventCard card) {
        this.context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(
                        RNEventType.VOICE_RECOGNIZE_STARTED.name(),
                        new Gson().toJson(card).toString()
                );
    }

    @Override
    public void onVoiceRecognizeFailured(String uuid) {
        this.context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(
                        RNEventType.VOICE_RECOGNIZE_FAILURED.name(),
                        uuid
                );
    }

    @Override
    public void onVoiceRecognizeSuccessed(String uuid) {
        this.context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(
                        RNEventType.VOICE_RECOGNIZE_SUCCESSED.name(),
                        uuid
                );
    }

    @Override
    public void onRecvTextCard(TextCardPayload payload) {

    }

    @Override
    public void onRecvListCard(ListCardPayload payload) {

    }

    @Override
    public void onRecvServiceCard(ServiceCardPayload payload) {

    }

    @Override
    public void onRecvStandardCard(StandardCardPayload payload) {

    }

    @Override
    public void onRecvImageListCard(ImageListCardPayload payload) {

    }

    @Override
    public void onRecvPhoneListCard(PhoneListCardPayload payload) {

    }

    @Override
    public void onRecvVoiceRecognize(VoiceRecognizePayload payload) {
        if(null != this.framework){
            this.framework.sendTextInputEvent(payload);
        }
    }

    @Override
    public void onRecvServiceListCard(ServiceListCardPayload payload) {

    }

    @Override
    public void onRecvSMSMessageListCard(SMSMessageListCardPayload payload) {

    }

    @Override
    public void onRecvLifeVoice(LifeVoicePayload payload) {

    }

    @Override
    public void onRecvNavigation(NavigationPayload payload) {

    }

    @Override
    public void onRecvPhoneBill(PhoneBillPayload payload) {

    }

    @Override
    public void onRecvTrainTicket(TicketPayload payload) {

    }

    @Override
    public void onRecvFlightTicket(TicketPayload payload) {

    }
}
