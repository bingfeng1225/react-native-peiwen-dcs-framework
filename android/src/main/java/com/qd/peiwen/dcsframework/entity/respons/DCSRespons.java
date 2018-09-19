package com.qd.peiwen.dcsframework.entity.respons;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nick on 2017/11/28.
 */

public class DCSRespons {
    @SerializedName("directive")
    private Directive directive;
    @SerializedName("message")
    private String message;

    public DCSRespons() {

    }

    public DCSRespons(Directive directive) {
        this.directive = directive;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Directive getDirective() {
        return directive;
    }

    public void setDirective(Directive directive) {
        this.directive = directive;
    }

}
