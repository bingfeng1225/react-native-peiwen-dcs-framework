package com.qd.peiwen.dcsframework.pinyinsearch.listener;


import com.qd.peiwen.dcsframework.pinyinsearch.entity.CNContact;
import com.qd.peiwen.dcsframework.pinyinsearch.pinyin.CN;

import java.util.List;

/**
 * Created by nick on 2018/3/9.
 */

public interface IPhoneSearchListener {
    void onContactEmpty(CN object);

    void onFindContacts(CN object, List<CNContact> contacts);
}
