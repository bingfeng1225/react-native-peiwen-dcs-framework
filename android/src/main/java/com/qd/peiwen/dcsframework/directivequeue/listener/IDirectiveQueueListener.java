package com.qd.peiwen.dcsframework.directivequeue.listener;


import com.qd.peiwen.dcsframework.entity.respons.DCSRespons;

/**
 * Created by nick on 2017/12/8.
 */

public interface IDirectiveQueueListener {
    void onHandleDirective(DCSRespons respons);
}
