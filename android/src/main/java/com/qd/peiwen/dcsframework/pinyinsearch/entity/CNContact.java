package com.qd.peiwen.dcsframework.pinyinsearch.entity;

import com.google.gson.annotations.SerializedName;
import com.qd.peiwen.dcsframework.pinyinsearch.pinyin.CN;
import com.qd.peiwen.dcsframework.pinyinsearch.pinyin.CNPinyinIndex;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nick on 2018/3/5.
 */

public class CNContact implements CN {
    @SerializedName("name")
    private String name;
    @SerializedName("contactId")
    private String contactId;
    @SerializedName("phones")
    private List<String> phones = new ArrayList<>();
    @SerializedName("indices")
    private List<CNPinyinIndex> indices = new ArrayList<>();

    public CNContact() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public List<String> getPhones() {
        return phones;
    }

    public void addPhones(List<String> phones) {
        this.phones.addAll(phones);
    }

    public List<CNPinyinIndex> getIndices() {
        return indices;
    }

    public void addIndices(List<CNPinyinIndex> indices) {
        this.indices.addAll(indices);
    }

    @Override
    public String chinese() {
        return this.name;
    }
}
