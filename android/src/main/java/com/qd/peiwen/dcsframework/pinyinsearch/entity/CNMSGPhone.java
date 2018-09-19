package com.qd.peiwen.dcsframework.pinyinsearch.entity;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.qd.peiwen.dcsframework.pinyinsearch.pinyin.CN;
import com.qd.peiwen.dcsframework.pinyinsearch.pinyin.CNPinyinIndex;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nick on 2018/3/5.
 */

public class CNMSGPhone implements CN {
    @SerializedName("name")
    private String name;
    @SerializedName("phone")
    private String phone;
    @SerializedName("indices")
    private List<CNPinyinIndex> indices = new ArrayList<>();

    public CNMSGPhone() {

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

    public List<CNPinyinIndex> getIndices() {
        return indices;
    }

    public void addIndices(List<CNPinyinIndex> indices) {
        this.indices.addAll(indices);
    }

    public String display(){
        if(TextUtils.isEmpty(name)){
            return phone;
        }
        return name;
    }
    public int hightLightLength(){
        return indices.isEmpty() ? 0 : indices.get(0).getLength();
    }

    public List<Integer> hightLightStarts(){
        List<Integer> starts = new ArrayList<>();
        for (CNPinyinIndex index:indices) {
            starts.add(index.getStart());
        }
        return starts;
    }

    @Override
    public String chinese() {
        return name;
    }
}
