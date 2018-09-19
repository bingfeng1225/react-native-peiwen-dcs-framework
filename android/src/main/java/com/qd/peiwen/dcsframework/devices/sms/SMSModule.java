package com.qd.peiwen.dcsframework.devices.sms;

import android.content.Context;
import android.telephony.SmsManager;

import com.qd.peiwen.dcsframework.devices.BaseModule;
import com.qd.peiwen.dcsframework.devices.PayloadManager;
import com.qd.peiwen.dcsframework.devices.screen.SCPCreator;
import com.qd.peiwen.dcsframework.devices.sms.listener.ISMSModuleListener;
import com.qd.peiwen.dcsframework.devices.sms.message.directive.SMSSearchPayload;
import com.qd.peiwen.dcsframework.devices.sms.message.directive.SMSSendPayload;
import com.qd.peiwen.dcsframework.devices.voiceoutput.VOPCreator;
import com.qd.peiwen.dcsframework.entity.header.BaseHeader;
import com.qd.peiwen.dcsframework.entity.payload.BasePayload;
import com.qd.peiwen.dcsframework.entity.respons.DCSRespons;
import com.qd.peiwen.dcsframework.entity.respons.Directive;
import com.qd.peiwen.dcsframework.pinyinsearch.PhoneSearch;
import com.qd.peiwen.dcsframework.pinyinsearch.SMSSearch;
import com.qd.peiwen.dcsframework.pinyinsearch.entity.CNContact;
import com.qd.peiwen.dcsframework.pinyinsearch.entity.SMSMessage;
import com.qd.peiwen.dcsframework.pinyinsearch.listener.IPhoneSearchListener;
import com.qd.peiwen.dcsframework.pinyinsearch.listener.ISMSSearchListener;
import com.qd.peiwen.dcsframework.pinyinsearch.pinyin.CN;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by nick on 2018/3/6.
 */

public class SMSModule extends BaseModule implements IPhoneSearchListener, ISMSSearchListener {
    private WeakReference<ISMSModuleListener> listener;

    public SMSModule(Context context) {
        super(context, ApiConstants.NAME, ApiConstants.NAMESPACE);
    }

    public void setListener(ISMSModuleListener listener) {
        this.listener = new WeakReference<>(listener);
    }

    public void sendMessage(String phone, String content) {
        SmsManager manager = SmsManager.getDefault();
        ArrayList<String> list = manager.divideMessage(content);
        for (int i = 0; i < list.size(); i++) {
            manager.sendTextMessage(phone, null, list.get(i), null, null);
        }
    }

    /************************ Base重写方法 **********************************/
    @Override
    public boolean handleDirective(Directive directive) {
        BaseHeader header = directive.getHeader();
        BasePayload payload = directive.getPayload();
        if (ApiConstants.Directives.Send.NAME.equals(header.getName())) {
            return processSendPayload(payload);
        } else if (ApiConstants.Directives.Search.NAME.equals(header.getName())) {
            return processSearchPayload(payload);
        }
        return super.handleDirective(directive);
    }

    @Override
    public void release() {
        super.release();
        this.listener = null;
    }

    /************************ 指令处理方法 **********************************/
    private boolean processSendPayload(BasePayload payload) {
        if (payload instanceof SMSSendPayload) {
            SMSSendPayload sendPayload = (SMSSendPayload) payload;
            new PhoneSearch(context).object(sendPayload).listener(this).search();
            return true;
        }
        return false;
    }

    private boolean processSearchPayload(BasePayload payload) {
        if (payload instanceof SMSSearchPayload) {
            SMSSearchPayload searchPayload = (SMSSearchPayload) payload;
            new SMSSearch(context).object(searchPayload).listener(this).search();
            return true;
        }
        return false;
    }

    /************************ 短信/通讯录检索监听 **********************************/
    @Override
    public void onContactEmpty(CN object) {
        String content = "很抱歉，没有找到相关联系人";
        String uuid = ((SMSSendPayload) object).getUuid();
        this.firePhoneModuleDirective(VOPCreator.speakRespons(uuid, content));
        this.firePhoneModuleDirective(SCPCreator.textcardRespons(uuid, content));
        this.fireSMSDialogFinished(uuid);
    }

    @Override
    public void onFindContacts(CN object, List<CNContact> contacts) {
        String uuid = ((SMSSendPayload) object).getUuid();
        if (contacts.size() == 1 && contacts.get(0).getPhones().size() == 1) {
            String content = "发送成功";
            this.sendMessage(contacts.get(0).getPhones().get(0), ((SMSSendPayload) object).getMSGBody());
            this.firePhoneModuleDirective(VOPCreator.speakRespons(uuid, content));
            this.firePhoneModuleDirective(SCPCreator.textcardRespons(uuid, content));
        } else {
            String content = "为您找到以下联系人，请点击联系人发送短信";
            String body = ((SMSSendPayload) object).getMSGBody();
            this.firePhoneModuleDirective(VOPCreator.speakRespons(uuid, content));
            this.firePhoneModuleDirective(SCPCreator.phonelistcardRespons(uuid, body, contacts));
        }
        this.fireSMSDialogFinished(uuid);
    }

    @Override
    public void onMessageEmpty(CN object) {
        String content = "很抱歉，没有找到相关短信";
        String uuid = ((SMSSearchPayload) object).getUuid();
        this.firePhoneModuleDirective(VOPCreator.speakRespons(uuid, content));
        this.firePhoneModuleDirective(SCPCreator.textcardRespons(uuid, content));
        this.fireSMSDialogFinished(uuid);
    }

    @Override
    public void onFindMessages(CN object, List<SMSMessage> messages) {
        String uuid = ((SMSSearchPayload) object).getUuid();
        this.firePhoneModuleDirective(VOPCreator.speakRespons(uuid, "为您找到以下短信"));
        this.firePhoneModuleDirective(SCPCreator.smsmessagelistcardRespons(uuid, messages));
        this.fireSMSDialogFinished(uuid);
    }


    /************************ Listener分发方法 **********************************/
    private void fireSMSDialogFinished(String uuid) {
        if (null != this.listener && null != listener.get()) {
            this.listener.get().onSMSDialogFinished(uuid);
        }
    }

    private void firePhoneModuleDirective(DCSRespons respons) {
        if (null != this.listener && null != listener.get()) {
            this.listener.get().onSMSModuleDirective(respons);
        }
    }

    static {
        PayloadManager.getInstance().insertPayload(
                ApiConstants.NAMESPACE,
                ApiConstants.Directives.Send.NAME,
                SMSSendPayload.class
        );
        PayloadManager.getInstance().insertPayload(
                ApiConstants.NAMESPACE,
                ApiConstants.Directives.Search.NAME,
                SMSSearchPayload.class
        );
    }
}
