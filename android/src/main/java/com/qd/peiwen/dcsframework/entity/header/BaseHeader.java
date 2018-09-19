package com.qd.peiwen.dcsframework.entity.header;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nick on 2017/11/28.
 */
public class BaseHeader {
    @SerializedName("name")
    private String name;
    @SerializedName("namespace")
    private String namespace;

    public BaseHeader() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }
}
