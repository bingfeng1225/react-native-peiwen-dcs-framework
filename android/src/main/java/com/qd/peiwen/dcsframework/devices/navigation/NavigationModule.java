package com.qd.peiwen.dcsframework.devices.navigation;

import android.content.Context;
import android.content.Intent;


import com.qd.peiwen.dcsframework.devices.BaseModule;
import com.qd.peiwen.dcsframework.devices.PayloadManager;
import com.qd.peiwen.dcsframework.devices.navigation.listener.INavigationModuleListener;
import com.qd.peiwen.dcsframework.devices.navigation.message.directive.NavigationPayload;
import com.qd.peiwen.dcsframework.entity.header.BaseHeader;
import com.qd.peiwen.dcsframework.entity.payload.BasePayload;
import com.qd.peiwen.dcsframework.entity.respons.Directive;
import com.qd.peiwen.dcsframework.tools.AppUtils;
import com.qd.peiwen.dcsframework.tools.LogUtils;

import java.lang.ref.WeakReference;
import java.net.URISyntaxException;

/**
 * Created by liudunjian on 2018/6/27.
 */

public class NavigationModule extends BaseModule {
    private WeakReference<INavigationModuleListener> listener;

    public NavigationModule(Context context) {
        super(context, ApiConstants.NAME, ApiConstants.NAMESPACE);
    }

    public void setListener(INavigationModuleListener listener) {
        this.listener = new WeakReference<>(listener);
    }

    @Override
    public boolean handleDirective(Directive directive) {
        BaseHeader header = directive.getHeader();
        BasePayload payload = directive.getPayload();
        if (ApiConstants.Directives.Navigation.NAME.equals(header.getName())) {
            LogUtils.d("handleDirective-----------");
            return processLifeVoicePayload(payload);
        }
        return super.handleDirective(directive);
    }

    /*********************导航相关方法*******************************/
    public void startNavigation(String destination) {
        if (AppUtils.isInstallByread("com.autonavi.minimap"))
            startGaoDeMapApp(destination);
        else if (AppUtils.isInstallByread("com.baidu.BaiduMap"))
            startBaiduMapApp(destination);
        else {
            //进行打开网页百度
        }
    }

    private void startGaoDeMapApp(String destination) {
        try {
            Intent intent = Intent.getIntent("androidamap://route?sourceApplication=softname&sname=我的位置&dname=" + destination + "&dev=0&m=0&t=0");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void startBaiduMapApp(String destination) {
        LogUtils.d("startBaiduMapApp------------");
        try {
            Intent intent = Intent.getIntent("intent://map/direction?destination=" + destination + "&mode=driving&src=yourCompanyName|yourAppName#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /************************ 指令处理方法 **********************************/
    private boolean processLifeVoicePayload(BasePayload payload) {
        if (payload instanceof NavigationPayload) {
            NavigationPayload lifeVoice = (NavigationPayload) payload;
            this.fireRecvNavigation(lifeVoice);
        }
        return true;
    }

    /************************ Listener分发方法 **********************************/
    private void fireRecvNavigation(NavigationPayload payload) {
        if (null != this.listener && null != listener.get()) {
            this.listener.get().onNavigationPayload(payload);
        }
    }

    static {
        PayloadManager.getInstance().insertPayload(ApiConstants.NAMESPACE,
                ApiConstants.Directives.Navigation.NAME,
                NavigationPayload.class);
    }
}
