package com.qd.peiwen.dcsframework.httppackage.listener;


import com.qd.peiwen.dcsframework.httppackage.request.HttpRequest;

/**
 * Created by nick on 2017/12/1.
 */

public interface IRequestListener {
    void onStarted(String uuid, HttpRequest request);

    void onCanceled(String uuid, HttpRequest request);

    void onFailured(String uuid, HttpRequest request);

    void onFinished(String uuid, HttpRequest request);

    void onSuccessed(String uuid, HttpRequest request);

    void onDirectiveRecevied(String uuid, HttpRequest request, Object respons);
}
