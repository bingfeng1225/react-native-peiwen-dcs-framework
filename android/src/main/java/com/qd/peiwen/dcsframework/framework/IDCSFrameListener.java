package com.qd.peiwen.dcsframework.framework;


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
import com.qd.peiwen.dcsframework.entity.screen.SendEventCard;

/**
 * Created by nick on 2017/12/22.
 */

public interface IDCSFrameListener {

    void onTextInputStarted(String uuid);

    void onTextInputFailured(String uuid);

    void onInputEventSuccessed(String uuid);

    void onVoiceRecognizeStarted(SendEventCard card);

    void onVoiceRecognizeFailured(String uuid);

    void onVoiceRecognizeSuccessed(String uuid);

    void onRecvTextCard(TextCardPayload payload);

    void onRecvListCard(ListCardPayload payload);

    void onRecvServiceCard(ServiceCardPayload payload);

    void onRecvStandardCard(StandardCardPayload payload);

    void onRecvImageListCard(ImageListCardPayload payload);

    void onRecvPhoneListCard(PhoneListCardPayload payload);

    void onRecvVoiceRecognize(VoiceRecognizePayload payload);

    void onRecvServiceListCard(ServiceListCardPayload payload);

    void onRecvSMSMessageListCard(SMSMessageListCardPayload payload);

    void onRecvNavigation(NavigationPayload payload);

    void onRecvTrainTicket(TicketPayload payload);

    void onRecvFlightTicket(TicketPayload payload);
}
