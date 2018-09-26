package com.qd.peiwen.dcsframework.devices;

import android.content.Context;

import com.qd.peiwen.dcsframework.entity.respons.Directive;


/**
 * Created by nick on 2017/11/28.
 */

public abstract class BaseModule {
    private String name;
    private String namespace;
    protected Context context;
    protected boolean released = true;

    public BaseModule(Context context, String name, String namespace) {
        this.name = name;
        this.namespace = namespace;
        this.context = context.getApplicationContext();
    }

    public String getName() {
        return name;
    }

    public String getNamespace() {
        return namespace;
    }

    public void init() {
        this.released = false;
    }

    public boolean handleDirective(Directive directive) {
        return false;
    }

    public void authorizeSuccessed() {
    }

    public void release() {
        this.released = true;
    }

}
