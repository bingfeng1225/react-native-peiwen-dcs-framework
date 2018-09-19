package com.qd.peiwen.dcsframework.devices.voiceoutput;


import com.qd.peiwen.dcsframework.devices.voiceoutput.message.directive.SpeakPayload;
import com.qd.peiwen.dcsframework.entity.header.DialogIdHeader;
import com.qd.peiwen.dcsframework.entity.respons.DCSRespons;
import com.qd.peiwen.dcsframework.entity.respons.Directive;
import com.qd.peiwen.dcsframework.tools.IDCreator;

import java.util.UUID;

/**
 * Created by nick on 2018/3/13.
 */

public class VOPCreator {
    public static DCSRespons speakRespons(String uuid, String content) {
        DialogIdHeader header = IDCreator.createDialogIdHeader(
                ApiConstants.NAMESPACE,
                ApiConstants.Directives.Speak.NAME
        );
        return new DCSRespons(new Directive(header,speakPayload(uuid,content)));
    }

    private static SpeakPayload speakPayload(String uuid, String content){
        SpeakPayload payload = new SpeakPayload();
        payload.setUuid(uuid);
        payload.setContent(content);
        payload.setToken(UUID.randomUUID().toString());
        return payload;
    }
}
