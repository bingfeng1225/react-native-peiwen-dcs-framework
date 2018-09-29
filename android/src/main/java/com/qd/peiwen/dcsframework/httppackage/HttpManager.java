package com.qd.peiwen.dcsframework.httppackage;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.qd.peiwen.dcsframework.entity.request.DCSRequest;
import com.qd.peiwen.dcsframework.entity.respons.Directive;
import com.qd.peiwen.dcsframework.entity.respons.DirectiveAdapter;
import com.qd.peiwen.dcsframework.entity.screen.SendEventCard;
import com.qd.peiwen.dcsframework.entity.voice.SendEntity;
import com.qd.peiwen.dcsframework.httppackage.listener.IRequestListener;
import com.qd.peiwen.dcsframework.httppackage.request.EventRequest;
import com.qd.peiwen.dcsframework.httppackage.request.VoiceRecognizeRequest;
import com.qd.peiwen.dcsframework.tools.LogUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;

/**
 * Created by nick on 2017/11/23.
 */

public class HttpManager {
    private Gson gson;
    private Context context;
    private String endpoint;
    private OkHttpClient httpClient;

    public HttpManager(Context context) {
        this.context = context.getApplicationContext();
    }

    public OkHttpClient httpClient() {
        return httpClient;
    }

    public void init() {
        this.gson = new GsonBuilder().
                registerTypeAdapter(Directive.class, new DirectiveAdapter()).
                create();

        this.httpClient = new OkHttpClient.Builder()
                .readTimeout(HttpConfig.TimeOut.HTTP_DEFAULT, TimeUnit.MILLISECONDS)
                .writeTimeout(HttpConfig.TimeOut.HTTP_DEFAULT, TimeUnit.MILLISECONDS)
                .connectTimeout(HttpConfig.TimeOut.HTTP_DEFAULT, TimeUnit.MILLISECONDS)
                .build();
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public void eventRequest(DCSRequest request, IRequestListener listener) {
        this.eventRequest(UUID.randomUUID().toString(), request, listener);
    }

    public void eventRequest(final String uuid, final DCSRequest request, final IRequestListener listener) {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Object> e) throws Exception {
                new EventRequest()
                        .request(request)
                        .gson(gson)
                        .uuid(uuid)
                        .url(HttpConfig.eventURL)
                        .listener(listener)
                        .httpClient(httpClient)
                        .headers(HttpConfig.HttpHeaders.SAIYA_LOGID, UUID.randomUUID().toString())
                        .headers(HttpConfig.HttpHeaders.DEBUG, HttpConfig.HttpHeaders.DEBUG_PARAM)
                        .headers(HttpConfig.HttpHeaders.DUEROS_DEVICE_ID, HttpConfig.deviceID)
                        .headers(HttpConfig.HttpHeaders.CONTENT_TYPE, HttpConfig.ContentTypes.FORM_MULTIPART)
                        .headers(HttpConfig.HttpHeaders.AUTHORIZATION, HttpConfig.HttpHeaders.BEARER + HttpConfig.token)
                        .execute();
            }
        }).subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).subscribe();
    }


    public void voiceRecognizeRequest(final SendEventCard card,final String sessionid,final String location, final IRequestListener listener) {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Object> e) throws Exception {
                SendEntity sendEntity = new SendEntity();
                sendEntity.setSessionid(sessionid);
                sendEntity.setText(card.getContent());
                sendEntity.setLocation(location);
                sendEntity.setDeviceid(HttpConfig.deviceID);
                new VoiceRecognizeRequest()
                        .entity(sendEntity)
                        .gson(gson)
                        .listener(listener)
                        .uuid(card.getUuid())
                        .httpClient(httpClient)
                        .url(HttpConfig.voiceRecognizeURL)
                        .headers(HttpConfig.HttpHeaders.CONTENT_TYPE, HttpConfig.ContentTypes.APPLICATION_JSON)
                        .execute();
            }
        }).subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).subscribe();
    }

//    private String getEndpoint() {
//        if (null == endpoint || "".equals(endpoint)) {
//            endpoint = HttpConfig.HttpUrls.SHEME + HttpConfig.HttpUrls.HOST;
//        }
//        return endpoint;
//    }
//
//    private String eventsUrl() {
//        return getEndpoint() + HttpConfig.HttpUrls.EVENTS;
//    }

    public void release() {
        this.httpClient.dispatcher().cancelAll();
        this.httpClient = null;
    }

}
