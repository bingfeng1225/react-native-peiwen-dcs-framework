package com.qd.peiwen.dcsframework.devices.phonebill;

import android.content.Context;


import com.qd.peiwen.dcsframework.devices.BaseModule;
import com.qd.peiwen.dcsframework.devices.PayloadManager;
import com.qd.peiwen.dcsframework.devices.phonebill.message.directive.PhoneBillPayload;
import com.qd.peiwen.dcsframework.entity.header.BaseHeader;
import com.qd.peiwen.dcsframework.entity.payload.BasePayload;
import com.qd.peiwen.dcsframework.entity.respons.Directive;
import com.qd.peiwen.dcsframework.tools.LogUtils;


/**
 * Created by liudunjian on 2018/6/27.
 */

public class PhoneBillModule extends BaseModule {


    public PhoneBillModule(Context context) {
        super(context, ApiConstants.NAME, ApiConstants.NAMESPACE);
    }



    @Override
    public boolean handleDirective(Directive directive) {
        LogUtils.d("handleDirective----------phobe bill");
        BaseHeader header = directive.getHeader();
        BasePayload payload = directive.getPayload();
        if (ApiConstants.Directives.Phonecharge.NAME.equals(header.getName())) {
            LogUtils.d("handleDirective-----------");
            return true;
        }
        return super.handleDirective(directive);
    }

    static {
        PayloadManager.getInstance().insertPayload(ApiConstants.NAMESPACE,
                ApiConstants.Directives.Phonecharge.NAME,
                PhoneBillPayload.class);
    }
}
