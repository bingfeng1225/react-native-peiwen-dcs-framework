/*
 * Copyright (c) 2017 Baidu, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.qd.peiwen.dcsframework.devices.screen;

import android.content.Context;


import com.qd.peiwen.dcsframework.devices.BaseModule;
import com.qd.peiwen.dcsframework.devices.PayloadManager;
import com.qd.peiwen.dcsframework.devices.screen.listener.IScreenModuleListener;
import com.qd.peiwen.dcsframework.devices.screen.message.directive.ImageListCardPayload;
import com.qd.peiwen.dcsframework.devices.screen.message.directive.ListCardPayload;
import com.qd.peiwen.dcsframework.devices.screen.message.directive.PhoneListCardPayload;
import com.qd.peiwen.dcsframework.devices.screen.message.directive.SMSMessageListCardPayload;
import com.qd.peiwen.dcsframework.devices.screen.message.directive.ServiceCardPayload;
import com.qd.peiwen.dcsframework.devices.screen.message.directive.ServiceListCardPayload;
import com.qd.peiwen.dcsframework.devices.screen.message.directive.StandardCardPayload;
import com.qd.peiwen.dcsframework.devices.screen.message.directive.TextCardPayload;
import com.qd.peiwen.dcsframework.devices.screen.message.event.LinkClickedPayload;
import com.qd.peiwen.dcsframework.devices.screen.message.state.ViewStatePayload;
import com.qd.peiwen.dcsframework.enmudefine.RenderCardType;
import com.qd.peiwen.dcsframework.entity.header.BaseHeader;
import com.qd.peiwen.dcsframework.entity.header.MessageIdHeader;
import com.qd.peiwen.dcsframework.entity.payload.BasePayload;
import com.qd.peiwen.dcsframework.entity.request.ClientContext;
import com.qd.peiwen.dcsframework.entity.request.EventMessage;
import com.qd.peiwen.dcsframework.entity.respons.Directive;
import com.qd.peiwen.dcsframework.tools.IDCreator;

import java.lang.ref.WeakReference;

/**
 * Screen模块处理并执行服务下发的指令，如HtmlView指令，以及发送事件，如LinkClicked事件
 * <p>
 * Created by wuruisheng on 2017/5/31.
 */
public class ScreenModule extends BaseModule {
    private String lastViewToken = null;
    private WeakReference<IScreenModuleListener> listener;

    public ScreenModule(Context context) {
        super(context, ApiConstants.NAME, ApiConstants.NAMESPACE);
    }

    public void setListener(IScreenModuleListener listener) {
        this.listener = new WeakReference<>(listener);
    }

    /************************ Base重写方法 **********************************/
    @Override
    public ClientContext clientContext() {
        BaseHeader header = new BaseHeader();
        header.setName(ApiConstants.States.ViewState.NAME);
        header.setNamespace(ApiConstants.NAMESPACE);
        ViewStatePayload payload = new ViewStatePayload();
        payload.setToken(lastViewToken);
        return new ClientContext(header, payload);
    }

    @Override
    public boolean handleDirective(Directive directive) {
        BaseHeader header = directive.getHeader();
        BasePayload payload = directive.getPayload();
        if (ApiConstants.Directives.RenderCard.NAME.equals(header.getName())) {
            return processRenderCardPayload(payload);
        }
        return super.handleDirective(directive);
    }

    @Override
    public void release() {
        super.release();
        this.listener = null;
        this.lastViewToken = null;
    }

    /************************ 事件封装方法 **********************************/
    public EventMessage linkclickedRequest(String url) {
        MessageIdHeader header = IDCreator.createMessageIdHeader(
                ApiConstants.NAMESPACE,
                ApiConstants.Events.LinkClicked.NAME
        );
        LinkClickedPayload payload = new LinkClickedPayload();
        payload.setUrl(url);
        return new EventMessage(header, payload);
    }

    /************************ 指令处理方法 **********************************/
    private boolean processRenderCardPayload(BasePayload payload) {
        if (payload instanceof TextCardPayload) {
            TextCardPayload card = (TextCardPayload) payload;
            this.fireRecvTextCard(card);
            return true;
        } else if (payload instanceof StandardCardPayload) {
            StandardCardPayload card = (StandardCardPayload) payload;
            this.fireRecvStandardCard(card);
            return true;
        } else if (payload instanceof ListCardPayload) {
            ListCardPayload card = (ListCardPayload) payload;
            this.fireRecvListCard(card);
            return true;
        } else if (payload instanceof ServiceCardPayload) {
            ServiceCardPayload card = (ServiceCardPayload) payload;
            this.fireRecvServiceCard(card);
            return true;
        } else if (payload instanceof ImageListCardPayload) {
            ImageListCardPayload card = (ImageListCardPayload) payload;
            this.fireRecvImageListCard(card);
            return true;
        } else if (payload instanceof PhoneListCardPayload) {
            PhoneListCardPayload card = (PhoneListCardPayload) payload;
            this.fireRecvPhoneListCard(card);
            return true;
        } else if (payload instanceof ServiceListCardPayload) {
            ServiceListCardPayload card = (ServiceListCardPayload) payload;
            this.fireRecvServiceListCard(card);
            return true;
        } else if (payload instanceof SMSMessageListCardPayload) {
            SMSMessageListCardPayload card = (SMSMessageListCardPayload) payload;
            this.fireRecvSMSMessageListCard(card);
            return true;
        }
        return false;
    }

    /************************ Listener分发方法 **********************************/
    private void fireRecvTextCard(TextCardPayload payload) {
        this.lastViewToken = payload.getToken();
        if (null != this.listener && null != listener.get()) {
            this.listener.get().onRecvTextCard(payload);
        }
    }

    private void fireRecvListCard(ListCardPayload payload) {
        this.lastViewToken = payload.getToken();
        if (null != this.listener && null != listener.get()) {
            this.listener.get().onRecvListCard(payload);
        }
    }

    private void fireRecvServiceCard(ServiceCardPayload payload) {
        if (null != this.listener && null != listener.get()) {
            this.listener.get().onRecvServiceCard(payload);
        }
    }

    private void fireRecvStandardCard(StandardCardPayload payload) {
        this.lastViewToken = payload.getToken();
        if (null != this.listener && null != listener.get()) {
            this.listener.get().onRecvStandardCard(payload);
        }
    }

    private void fireRecvImageListCard(ImageListCardPayload payload) {
        this.lastViewToken = payload.getToken();
        if (null != this.listener && null != listener.get()) {
            this.listener.get().onRecvImageListCard(payload);
        }
    }

    private void fireRecvPhoneListCard(PhoneListCardPayload payload) {
        if (null != this.listener && null != listener.get()) {
            this.listener.get().onRecvPhoneListCard(payload);
        }
    }

    private void fireRecvServiceListCard(ServiceListCardPayload payload) {
        if (null != this.listener && null != listener.get()) {
            this.listener.get().onRecvServiceListCard(payload);
        }
    }

    private void fireRecvSMSMessageListCard(SMSMessageListCardPayload payload) {
        if (null != this.listener && null != listener.get()) {
            this.listener.get().onRecvSMSMessageListCard(payload);
        }
    }

    static {
        PayloadManager.getInstance().insertPayload(
                ApiConstants.NAMESPACE,
                ApiConstants.Directives.RenderCard.NAME,
                RenderCardType.RENDER_TEXT_CARD.message(),
                TextCardPayload.class
        );
        PayloadManager.getInstance().insertPayload(
                ApiConstants.NAMESPACE,
                ApiConstants.Directives.RenderCard.NAME,
                RenderCardType.RENDER_LIST_CARD.message(),
                ListCardPayload.class
        );
        PayloadManager.getInstance().insertPayload(
                ApiConstants.NAMESPACE,
                ApiConstants.Directives.RenderCard.NAME,
                RenderCardType.RENDER_SERVICE_CARD.message(),
                ServiceCardPayload.class
        );
        PayloadManager.getInstance().insertPayload(
                ApiConstants.NAMESPACE,
                ApiConstants.Directives.RenderCard.NAME,
                RenderCardType.RENDER_STANDAR_DCARD.message(),
                StandardCardPayload.class
        );
        PayloadManager.getInstance().insertPayload(
                ApiConstants.NAMESPACE,
                ApiConstants.Directives.RenderCard.NAME,
                RenderCardType.RENDER_IMAGE_LIST_CARD.message(),
                ImageListCardPayload.class
        );
        PayloadManager.getInstance().insertPayload(
                ApiConstants.NAMESPACE,
                ApiConstants.Directives.RenderCard.NAME,
                RenderCardType.RENDER_PHONE_LIST_CARD.message(),
                PhoneListCardPayload.class
        );
        PayloadManager.getInstance().insertPayload(
                ApiConstants.NAMESPACE,
                ApiConstants.Directives.RenderCard.NAME,
                RenderCardType.RENDER_SERVICE_LIST_CARD.message(),
                ServiceListCardPayload.class
        );

        PayloadManager.getInstance().insertPayload(
                ApiConstants.NAMESPACE,
                ApiConstants.Directives.RenderCard.NAME,
                RenderCardType.RENDER_SMS_MESSAGE_LIST_CARD.message(),
                SMSMessageListCardPayload.class
        );
    }
}
