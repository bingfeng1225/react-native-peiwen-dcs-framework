package com.qd.peiwen.dcsframework.directivequeue;

import android.content.Context;

import com.qd.peiwen.dcsframework.directivequeue.listener.IDirectiveQueueListener;
import com.qd.peiwen.dcsframework.entity.respons.DCSRespons;

import java.lang.ref.WeakReference;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by nick on 2017/12/8.
 */

public class DirectiveQueueManager extends Thread {

    private Context context;
    private boolean stoped = false;
    private BlockingDeque<DCSRespons> queue;
    private WeakReference<IDirectiveQueueListener> listener;

    public DirectiveQueueManager(Context context) {
        this.context = context.getApplicationContext();
    }

    public void init() {
        this.stoped = false;
        this.queue = new LinkedBlockingDeque<>();
        this.start();
    }

    public void handleResponseBody(DCSRespons response) {
        this.queue.add(response);
    }

    public void setListener(IDirectiveQueueListener listener) {
        this.listener = new WeakReference<>(listener);
    }

    private void fireHandleDirective(DCSRespons respons) {
        if (null != this.listener && null != listener.get()) {
            this.listener.get().onHandleDirective(respons);
        }
    }

    @Override
    public void run() {
        try {
            while (!stoped) {
                DCSRespons respons = this.queue.take();
                this.fireHandleDirective(respons);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void release() {
        this.stoped = true;
        this.interrupt();
    }
}
