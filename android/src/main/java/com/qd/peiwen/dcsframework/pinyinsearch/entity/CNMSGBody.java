package com.qd.peiwen.dcsframework.pinyinsearch.entity;

import com.google.gson.annotations.SerializedName;
import com.qd.peiwen.dcsframework.pinyinsearch.pinyin.CN;
import com.qd.peiwen.dcsframework.pinyinsearch.pinyin.CNPinyinIndex;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nick on 2018/3/5.
 */

public class CNMSGBody implements CN {
    @SerializedName("body")
    private String body;
    @SerializedName("indices")
    private List<CNPinyinIndex> indices = new ArrayList<>();

    public CNMSGBody() {
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<CNPinyinIndex> getIndices() {
        return indices;
    }

    public void addIndices(List<CNPinyinIndex> indices) {
        this.indices.addAll(indices);
    }

    public int hightLightLength() {
        return indices.isEmpty() ? 0 : indices.get(0).getLength();
    }

    public List<Integer> hightLightStarts() {
        List<Integer> starts = new ArrayList<>();
        for (CNPinyinIndex index : indices) {
            starts.add(index.getStart());
        }
        return starts;
    }

    @Override
    public String chinese() {
        return body;
    }
}
