package com.qd.peiwen.dcsframework.devices.screen.listener;


import com.qd.peiwen.dcsframework.devices.screen.message.directive.ImageListCardPayload;
import com.qd.peiwen.dcsframework.devices.screen.message.directive.ListCardPayload;
import com.qd.peiwen.dcsframework.devices.screen.message.directive.PhoneListCardPayload;
import com.qd.peiwen.dcsframework.devices.screen.message.directive.SMSMessageListCardPayload;
import com.qd.peiwen.dcsframework.devices.screen.message.directive.ServiceCardPayload;
import com.qd.peiwen.dcsframework.devices.screen.message.directive.ServiceListCardPayload;
import com.qd.peiwen.dcsframework.devices.screen.message.directive.StandardCardPayload;
import com.qd.peiwen.dcsframework.devices.screen.message.directive.TextCardPayload;

/**
 * Created by nick on 2017/12/12.
 */

public interface IScreenModuleListener {
    void onRecvTextCard(TextCardPayload payload);

    void onRecvListCard(ListCardPayload payload);

    void onRecvServiceCard(ServiceCardPayload payload);

    void onRecvStandardCard(StandardCardPayload payload);

    void onRecvImageListCard(ImageListCardPayload payload);

    void onRecvPhoneListCard(PhoneListCardPayload payload);

    void onRecvServiceListCard(ServiceListCardPayload payload);

    void onRecvSMSMessageListCard(SMSMessageListCardPayload payload);
}
