
package com.qd.peiwen.dcsframework;


import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.google.gson.Gson;
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
import com.qd.peiwen.dcsframework.entity.screen.SendEventCard;
import com.qd.peiwen.dcsframework.framework.DCSFramework;
import com.qd.peiwen.dcsframework.framework.IDCSFrameListener;
import com.qd.peiwen.dcsframework.httppackage.HttpConfig;
import com.qd.peiwen.dcsframework.tools.IDCreator;



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
    public void initFramework(ReadableMap map){
        HttpConfig.token = map.getString("token");
        HttpConfig.eventURL = map.getString("event");
        HttpConfig.deviceID = map.getString("deviceid");
        HttpConfig.speakDownloadURL = map.getString("speak");
        HttpConfig.voiceRecognizeURL = map.getString("voice");
        if(null == this.framework) {
            this.framework = new DCSFramework(this.context);
            this.framework.setListener(this);
            this.framework.init();
        }
    }

    @ReactMethod
    public void enterBackground() {
        if(null != this.framework) {
            this.framework.enterBackground();
        }
    }

    @ReactMethod
    public void becomeForeground() {
        if(null != this.framework) {
            this.framework.becomeForeground();
        }
    }

    @ReactMethod
    public void audioRecordStarted() {
        if(null != this.framework) {
            this.framework.audioRecordStarted();
        }
    }

    @ReactMethod
    public void audioRecordFinished() {
        if(null != this.framework) {
            this.framework.audioRecordFinished();
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
    public void sendTextRequest(ReadableMap map){
        if(null != this.framework) {
            SendEventCard card = new SendEventCard();
            card.setContent(map.getString("content"));
            card.setUuid(IDCreator.createActiveRequestId());
            card.setTimestamp(System.currentTimeMillis());
            this.framework.sendVoiceRecognizeRequest(card,map.getString("location"));
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
        this.context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(
                        RNEventType.ON_RECEIVE_TEXT_CARD.name(),
                        new Gson().toJson(payload).toString()
                );
    }

    @Override
    public void onRecvListCard(ListCardPayload payload) {
        this.context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(
                        RNEventType.ON_RECEIVE_LIST_CARD.name(),
                        new Gson().toJson(payload).toString()
                );
    }

    @Override
    public void onRecvServiceCard(ServiceCardPayload payload) {
        this.context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(
                        RNEventType.ON_RECEIVE_SERVICE_CARD.name(),
                        new Gson().toJson(payload).toString()
                );
    }

    @Override
    public void onRecvStandardCard(StandardCardPayload payload) {
        this.context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(
                        RNEventType.ON_RECEIVE_STANDARD_CARD.name(),
                        new Gson().toJson(payload).toString()
                );
    }

    @Override
    public void onRecvImageListCard(ImageListCardPayload payload) {
        this.context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(
                        RNEventType.ON_RECEIVE_IMAGE_LIST_CARD.name(),
                        new Gson().toJson(payload).toString()
                );
    }

    @Override
    public void onRecvPhoneListCard(PhoneListCardPayload payload) {
        this.context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(
                        RNEventType.ON_RECEIVE_PHONE_LIST_CARD.name(),
                        new Gson().toJson(payload).toString()
                );
    }

    @Override
    public void onRecvVoiceRecognize(VoiceRecognizePayload payload) {
        if(null != this.framework){
            this.framework.sendTextInputEvent(payload);
        }
    }

    @Override
    public void onRecvServiceListCard(ServiceListCardPayload payload) {
        this.context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(
                        RNEventType.ON_RECEIVE_SERVICE_LIST_CARD.name(),
                        new Gson().toJson(payload).toString()
                );
    }

    @Override
    public void onRecvSMSMessageListCard(SMSMessageListCardPayload payload) {
        this.context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(
                        RNEventType.ON_RECEIVE_SMSMESSAGE_LIST_CARD.name(),
                        new Gson().toJson(payload).toString()
                );
    }

    @Override
    public void onRecvNavigation(NavigationPayload payload) {
        this.context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(
                        RNEventType.ON_RECV_NAVIGATION.name(),
                        new Gson().toJson(payload).toString()
                );
    }

    @Override
    public void onRecvTrainTicket(TicketPayload payload) {
        this.context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(
                        RNEventType.ON_RECV_TRAIN_TICKET.name(),
                        new Gson().toJson(payload).toString()
                );
    }

    @Override
    public void onRecvFlightTicket(TicketPayload payload) {
        this.context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(
                        RNEventType.ON_RECV_FLIGHT_TICKET.name(),
                        new Gson().toJson(payload).toString()
                );
    }

}
