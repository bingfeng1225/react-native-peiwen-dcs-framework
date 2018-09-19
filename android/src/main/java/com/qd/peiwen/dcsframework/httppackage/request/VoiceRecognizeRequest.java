package com.qd.peiwen.dcsframework.httppackage.request;


import com.qd.peiwen.dcsframework.entity.voice.SendEntity;
import com.qd.peiwen.dcsframework.httppackage.HttpConfig;
import com.qd.peiwen.dcsframework.tools.LogUtils;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by nick on 2018/1/25.
 */

public class VoiceRecognizeRequest extends HttpRequest {

    private SendEntity entity;

    public VoiceRecognizeRequest() {
        this.tag = HttpConfig.HttpTags.VOICE_RECOGNIZE;
    }

    public VoiceRecognizeRequest entity(SendEntity entity){
        this.entity = entity;
        return this;
    }
    @Override
    protected Request generateRequest() {
        return new Request.Builder()
                .url(generateParams())
                .headers(generateHeaders())
                .post(generateBody())
                .tag(this.tag)
                .build();
    }

    protected RequestBody generateBody() {
        String body = this.gson.toJson(entity);
        LogUtils.e("" + this.gson.toJson(entity));
        MediaType mediaType = MediaType.parse(HttpConfig.ContentTypes.APPLICATION_JSON);
        return RequestBody.create(mediaType, body);
    }
}
