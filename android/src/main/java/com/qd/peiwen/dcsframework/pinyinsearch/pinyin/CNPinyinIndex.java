package com.qd.peiwen.dcsframework.pinyinsearch.pinyin;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by you on 2017/9/8.
 */

public class CNPinyinIndex implements Serializable {
    @SerializedName("start")
    private int start;
    @SerializedName("length")
    private int length;

    public CNPinyinIndex() {
    }

    CNPinyinIndex(int start, int length) {
        this.start = start;
        this.length = length;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
