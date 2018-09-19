package com.qd.peiwen.dcsframework.devices.system;

import android.content.Context;


import com.qd.peiwen.dcsframework.devices.BaseModule;
import com.qd.peiwen.dcsframework.devices.PayloadManager;
import com.qd.peiwen.dcsframework.devices.system.listener.ISystemModuleListener;
import com.qd.peiwen.dcsframework.devices.system.message.directive.ResetUserInactivityPayload;
import com.qd.peiwen.dcsframework.devices.system.message.directive.SetEndPointPayload;
import com.qd.peiwen.dcsframework.devices.system.message.directive.SetSessionidPayload;
import com.qd.peiwen.dcsframework.devices.system.message.directive.ThrowExceptionPayload;
import com.qd.peiwen.dcsframework.devices.system.message.entity.ErrorMessager;
import com.qd.peiwen.dcsframework.devices.system.message.event.ExceptionEncounteredPayload;
import com.qd.peiwen.dcsframework.devices.system.message.event.SynchronizeStatePayload;
import com.qd.peiwen.dcsframework.devices.system.message.event.UserInactivityReportPayload;
import com.qd.peiwen.dcsframework.entity.header.BaseHeader;
import com.qd.peiwen.dcsframework.entity.header.MessageIdHeader;
import com.qd.peiwen.dcsframework.entity.payload.BasePayload;
import com.qd.peiwen.dcsframework.entity.request.EventMessage;
import com.qd.peiwen.dcsframework.entity.respons.Directive;
import com.qd.peiwen.dcsframework.tools.IDCreator;
import com.qd.peiwen.dcsframework.tools.LogUtils;

import java.lang.ref.WeakReference;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by nick on 2017/12/6.
 */

public class SystemModule extends BaseModule {
    private ScheduledExecutorService executor;
    private AtomicLong lastUserInteractionInSeconds;
    private WeakReference<ISystemModuleListener> listener;

    public SystemModule(Context context) {
        super(context, ApiConstants.NAME, ApiConstants.NAMESPACE);
    }

    public void setListener(ISystemModuleListener listener) {
        this.listener = new WeakReference<>(listener);
    }

    public void userActivity() {
        lastUserInteractionInSeconds.set(currentTimeSeconds());
    }

    private long currentTimeSeconds() {
        return System.currentTimeMillis() / 1000;
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            long inactiveTimeInSeconds = currentTimeSeconds() - lastUserInteractionInSeconds.get();
            fireUserInactivityReport(inactiveTimeInSeconds);
        }
    };

    /************************ Base重写方法 **********************************/
    @Override
    public void init() {
        super.init();
        this.lastUserInteractionInSeconds = new AtomicLong(currentTimeSeconds());
        this.executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(runnable, 1, 1, TimeUnit.HOURS);
    }

    @Override
    public boolean handleDirective(Directive directive) {
        BaseHeader header = directive.getHeader();
        BasePayload payload = directive.getPayload();
        if (ApiConstants.Directives.ResetUserInactivity.NAME.equals(header.getName())) {
            return processResetUserInactivityPayload(payload);
        } else if (ApiConstants.Directives.SetEndpoint.NAME.equals(header.getName())) {
            return processSetEndpointPayload(payload);
        } else if (ApiConstants.Directives.SetSessionid.NAME.equals(header.getName())) {
            return processSetSessionidPayload(payload);
        } else if (ApiConstants.Directives.ThrowException.NAME.equals(header.getName())) {
            return processThrowExceptionPayload(payload);
        }
        return super.handleDirective(directive);
    }

    @Override
    public void release() {
        super.release();
        this.listener = null;
        this.executor.shutdown();
        this.executor = null;
        this.lastUserInteractionInSeconds = null;
    }

    /************************ 事件封装方法 **********************************/
    public EventMessage synchronizeStateRequest() {
        MessageIdHeader header = IDCreator.createMessageIdHeader(
                ApiConstants.NAMESPACE,
                ApiConstants.Events.SynchronizeState.NAME
        );
        return new EventMessage(header, new SynchronizeStatePayload());
    }

    public EventMessage userInactivityReportRequest(long inactiveTimeInSeconds) {
        MessageIdHeader header = IDCreator.createMessageIdHeader(
                ApiConstants.NAMESPACE,
                ApiConstants.Events.UserInactivityReport.NAME
        );
        UserInactivityReportPayload payload = new UserInactivityReportPayload();
        payload.setInactiveTimeInSeconds(inactiveTimeInSeconds);
        return new EventMessage(header, payload);
    }

    public EventMessage exceptionEncounteredRequest(String unparse, String type, String message) {
        MessageIdHeader header = IDCreator.createMessageIdHeader(
                ApiConstants.NAMESPACE,
                ApiConstants.Events.ExceptionEncountered.NAME
        );
        ExceptionEncounteredPayload payload = new ExceptionEncounteredPayload();
        payload.setUnparsedDirective(unparse);
        ErrorMessager error = new ErrorMessager();
        error.setType(type);
        error.setMessage(message);
        payload.setError(error);
        return new EventMessage(header, payload);
    }

    /************************ 指令处理方法 **********************************/
    private boolean processSetEndpointPayload(BasePayload payload) {
        if (payload instanceof SetEndPointPayload) {
            SetEndPointPayload endPointPayload = (SetEndPointPayload) payload;
            this.fireSetEndpoint(endPointPayload);
            return true;
        }
        return false;
    }

    private boolean processSetSessionidPayload(BasePayload payload) {
        LogUtils.d("processSetSessionidPayload-----------------------------");
        if (payload instanceof SetSessionidPayload) {
            SetSessionidPayload sessionidPayload = (SetSessionidPayload) payload;
            this.fireSetSessionid(sessionidPayload);
            return true;
        }
        return false;
    }

    private boolean processThrowExceptionPayload(BasePayload payload) {
        if (payload instanceof ThrowExceptionPayload) {
            ThrowExceptionPayload exceptionPayload = (ThrowExceptionPayload) payload;
            this.fireThrowException(exceptionPayload);
            return true;
        }
        return false;
    }

    private boolean processResetUserInactivityPayload(BasePayload payload) {
        if (payload instanceof ResetUserInactivityPayload) {
            this.fireResetUserInactivity();
            return true;
        }
        return false;
    }

    /************************ Listener分发方法 **********************************/
    private void fireResetUserInactivity() {
        if (null != this.listener && null != listener.get()) {
            this.listener.get().onResetUserInactivity();
        }
    }

    private void fireUserInactivityReport(long userInactivity) {
        if (null != this.listener && null != listener.get()) {
            this.listener.get().onUserInactivityReport(userInactivity);
        }
    }

    private void fireSetEndpoint(SetEndPointPayload endPointPayload) {
        if (null != this.listener && null != listener.get()) {
            this.listener.get().onSetEndpoint(endPointPayload);
        }
    }

    private void fireSetSessionid(SetSessionidPayload sessionidPayload) {
        if (null != this.listener && null != listener.get()) {
            this.listener.get().onSetSessionid(sessionidPayload);
        }
    }

    private void fireThrowException(ThrowExceptionPayload exceptionPayload) {
        if (null != this.listener && null != listener.get()) {
            this.listener.get().onThrowException(exceptionPayload);
        }
    }

    static {
        PayloadManager.getInstance().insertPayload(
                ApiConstants.NAMESPACE,
                ApiConstants.Directives.ResetUserInactivity.NAME,
                ResetUserInactivityPayload.class
        );
        PayloadManager.getInstance().insertPayload(
                ApiConstants.NAMESPACE,
                ApiConstants.Directives.SetEndpoint.NAME,
                SetEndPointPayload.class
        );
        PayloadManager.getInstance().insertPayload(
                ApiConstants.NAMESPACE,
                ApiConstants.Directives.SetSessionid.NAME,
                SetSessionidPayload.class
        );
        PayloadManager.getInstance().insertPayload(
                ApiConstants.NAMESPACE,
                ApiConstants.Directives.ThrowException.NAME,
                ThrowExceptionPayload.class
        );
    }

}
