package com.qd.peiwen.dcsframework.pinyinsearch.listener;

import com.qd.peiwen.dcsframework.pinyinsearch.entity.SMSMessage;
import com.qd.peiwen.dcsframework.pinyinsearch.pinyin.CN;

import java.util.List;

/**
 * Created by nick on 2018/3/13.
 */

public interface ISMSSearchListener {
    void onMessageEmpty(CN object);

    void onFindMessages(CN object, List<SMSMessage> messages);
}
