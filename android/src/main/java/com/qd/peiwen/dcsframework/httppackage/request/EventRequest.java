package com.qd.peiwen.dcsframework.httppackage.request;


import com.qd.peiwen.dcsframework.entity.request.DCSRequest;
import com.qd.peiwen.dcsframework.httppackage.HttpConfig;
import com.qd.peiwen.dcsframework.tools.LogUtils;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by nick on 2017/11/23.
 */

public class EventRequest extends HttpRequest {
    // 请求内容
    protected DCSRequest request;

    public EventRequest() {
        this.tag = HttpConfig.HttpTags.EVENT;
    }

    public EventRequest request(DCSRequest request) {
        this.request = request;
        return this;
    }

    public DCSRequest request(){
        return this.request;
    }

    protected Request generateRequest() {
        return new Request.Builder()
                .url(generateParams())
                .headers(generateHeaders())
                .post(generateBody())
                .tag(this.tag)
                .build();
    }

    protected RequestBody generateBody() {
        return new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addPart(generateEventHeaders(), generateEventBody())
                .build();
    }

    protected RequestBody generateEventBody() {
        String body = this.gson.toJson(request);
        LogUtils.d("" + this.gson.toJson(request.getEvent()));
        MediaType mediaType = MediaType.parse(HttpConfig.ContentTypes.APPLICATION_JSON);
        return RequestBody.create(mediaType, body);
    }

    protected Headers generateEventHeaders() {
        return Headers.of("Content-Disposition", "form-data; name=\"" + HttpConfig.Parameters.DATA_METADATA + "\"");
    }
}
