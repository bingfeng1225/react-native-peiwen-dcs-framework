package com.qd.peiwen.dcsframework.devices.textinput.message;

import com.google.gson.annotations.SerializedName;
import com.qd.peiwen.dcsframework.entity.payload.BasePayload;

/**
 * Created by nick on 2017/11/28.
 */

public class TextInputPayload extends BasePayload {
    @SerializedName("query")
    private String query;

    public TextInputPayload() {
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
