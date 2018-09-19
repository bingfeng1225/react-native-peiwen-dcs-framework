package com.qd.peiwen.dcsframework.devices.screen.message.entity;

import android.text.TextUtils;


import com.qd.peiwen.dcsframework.devices.screen.message.directive.PhoneListCardPayload;
import com.qd.peiwen.dcsframework.pinyinsearch.pinyin.CNPinyinIndex;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nick on 2018/3/17.
 */

public class PhoneItem {
    private String name;
    private String phone;
    private String content;
    PhoneListCardPayload payload;
    private List<CNPinyinIndex> indices;

    public PhoneItem() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public PhoneListCardPayload getPayload() {
        return payload;
    }

    public void setPayload(PhoneListCardPayload payload) {
        this.payload = payload;
    }

    public List<CNPinyinIndex> getIndices() {
        return indices;
    }

    public void setIndices(List<CNPinyinIndex> indices) {
        this.indices = indices;
    }

    public boolean hasContent(){
        return (!TextUtils.isEmpty(content));
    }

    public int hightLightLength(){
        return indices.isEmpty()?0:indices.get(0).getLength();
    }

    public List<Integer> hightLightStarts(){
        List<Integer> starts = new ArrayList<>();
        for (CNPinyinIndex index:indices) {
            starts.add(index.getStart());
        }
        return starts;
    }
}
