package com.qd.peiwen.dcsframework.devices.phone;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;


import com.qd.peiwen.dcsframework.devices.BaseModule;
import com.qd.peiwen.dcsframework.devices.PayloadManager;
import com.qd.peiwen.dcsframework.devices.phone.listener.IPhoneModuleListener;
import com.qd.peiwen.dcsframework.devices.phone.message.directive.PhoneCallPayload;
import com.qd.peiwen.dcsframework.devices.phone.message.directive.PhoneSearchPayload;
import com.qd.peiwen.dcsframework.devices.screen.SCPCreator;
import com.qd.peiwen.dcsframework.devices.voiceoutput.VOPCreator;
import com.qd.peiwen.dcsframework.entity.header.BaseHeader;
import com.qd.peiwen.dcsframework.entity.payload.BasePayload;
import com.qd.peiwen.dcsframework.entity.respons.DCSRespons;
import com.qd.peiwen.dcsframework.entity.respons.Directive;
import com.qd.peiwen.dcsframework.pinyinsearch.PhoneSearch;
import com.qd.peiwen.dcsframework.pinyinsearch.entity.CNContact;
import com.qd.peiwen.dcsframework.pinyinsearch.listener.IPhoneSearchListener;
import com.qd.peiwen.dcsframework.pinyinsearch.pinyin.CN;

import java.lang.ref.WeakReference;
import java.util.List;


/**
 * Created by nick on 2018/3/6.
 */

public class PhoneModule extends BaseModule implements IPhoneSearchListener {
    private WeakReference<IPhoneModuleListener> listener;

    public PhoneModule(Context context) {
        super(context, ApiConstants.NAME, ApiConstants.NAMESPACE);
    }

    public void setListener(IPhoneModuleListener listener) {
        this.listener = new WeakReference<>(listener);
    }

    public void callPhone(String phone) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phone));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /************************ Base重写方法 **********************************/
    @Override
    public boolean handleDirective(Directive directive) {
        BaseHeader header = directive.getHeader();
        BasePayload payload = directive.getPayload();
        if (ApiConstants.Directives.Call.NAME.equals(header.getName())) {
            return processCallPayload(payload);
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
    private boolean processCallPayload(BasePayload payload) {
        if (payload instanceof PhoneCallPayload) {
            PhoneCallPayload callPayload = (PhoneCallPayload) payload;
            new PhoneSearch(context).object(callPayload).listener(this).search();
            return true;
        }
        return false;
    }

    private boolean processSearchPayload(BasePayload payload) {
        if (payload instanceof PhoneSearchPayload) {
            PhoneSearchPayload searchPayload = (PhoneSearchPayload) payload;
            new PhoneSearch(context).object(searchPayload).listener(this).search();
            return true;
        }
        return false;
    }

    /************************ 通讯录检索监听 **********************************/
    @Override
    public void onContactEmpty(CN object) {
        String uuid = findUUID(object);
        String content = "很抱歉，没有找到相关联系人";
        this.firePhoneModuleDirective(VOPCreator.speakRespons(uuid, content));
        this.firePhoneModuleDirective(SCPCreator.textcardRespons(uuid, content));
        this.firePhoneDialogFinished(uuid);
    }

    @Override
    public void onFindContacts(CN object, List<CNContact> contacts) {
        String uuid = findUUID(object);
        if (object instanceof PhoneCallPayload && contacts.size() == 1 && contacts.get(0).getPhones().size() == 1) {
            this.callPhone(contacts.get(0).getPhones().get(0));
        } else {
            String content = "为您找到以下联系人，请点击联系人拨打打电话";
            this.firePhoneModuleDirective(VOPCreator.speakRespons(uuid, content));
            this.firePhoneModuleDirective(SCPCreator.phonelistcardRespons(uuid, contacts));
        }
        this.firePhoneDialogFinished(uuid);
    }

    private String findUUID(CN object) {
        if (object instanceof PhoneCallPayload) {
            return ((PhoneCallPayload) object).getUuid();
        } else {
            return ((PhoneSearchPayload) object).getUuid();
        }
    }

    /************************ Listener分发方法 **********************************/

    private void firePhoneDialogFinished(String uuid) {
        if (null != this.listener && null != listener.get()) {
            this.listener.get().onPhoneDialogFinished(uuid);
        }
    }

    private void firePhoneModuleDirective(DCSRespons respons) {
        if (null != this.listener && null != listener.get()) {
            this.listener.get().onPhoneModuleDirective(respons);
        }
    }

    static {
        PayloadManager.getInstance().insertPayload(
                ApiConstants.NAMESPACE,
                ApiConstants.Directives.Call.NAME,
                PhoneCallPayload.class
        );
        PayloadManager.getInstance().insertPayload(
                ApiConstants.NAMESPACE,
                ApiConstants.Directives.Search.NAME,
                PhoneSearchPayload.class
        );
    }
}
