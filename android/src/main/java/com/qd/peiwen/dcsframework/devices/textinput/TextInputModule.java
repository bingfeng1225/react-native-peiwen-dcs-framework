package com.qd.peiwen.dcsframework.devices.textinput;

import android.content.Context;

import com.qd.peiwen.dcsframework.devices.BaseModule;
import com.qd.peiwen.dcsframework.devices.textinput.message.TextInputPayload;
import com.qd.peiwen.dcsframework.entity.header.DialogIdHeader;
import com.qd.peiwen.dcsframework.entity.request.EventMessage;
import com.qd.peiwen.dcsframework.tools.IDCreator;


/**
 * Created by nick on 2017/11/28.
 */

public class TextInputModule extends BaseModule {

    public TextInputModule(Context context) {
        super(context, ApiConstants.NAME, ApiConstants.NAMESPACE);
    }

    /************************ 事件封装方法 **********************************/
    public EventMessage textinputMessage(String text) {
        DialogIdHeader header = IDCreator.createDialogIdHeader(
                ApiConstants.NAMESPACE,
                ApiConstants.Events.TextInput.NAME
        );
        TextInputPayload payload = new TextInputPayload();
        payload.setQuery(text);
        return new EventMessage(header, payload);
    }
}
