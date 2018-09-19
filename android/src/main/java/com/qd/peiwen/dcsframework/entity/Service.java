package com.qd.peiwen.dcsframework.entity;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by liudunjian on 2017/11/21.
 */

public class Service {

    @SerializedName("sid")
    private String sid;
    @SerializedName("category")
    private long category;
    @SerializedName("type")
    private long type;
    @SerializedName("watchcount")
    private int watchcount;
    @SerializedName("favritecount")
    private int favritecount;
    @SerializedName("score")
    private String score;
    @SerializedName("name")
    private String name;
    @SerializedName("profile")
    private String profile;
    @SerializedName("iconurl")
    private String iconurl;
    @SerializedName("linkurl")
    private String linkurl;
    @SerializedName("imageurl")
    private String imageurl;
    @SerializedName("bannerImg")
    private String bannerImg;
    @SerializedName("belongtype")
    private ArrayList<String> belongtype;
    @SerializedName("favorite")
    private boolean isFavorite;
    @SerializedName("displayType") //1 h5，2 pc，3 apk
    private int displayType;


    public Service() {
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public long getCategory() {
        return category;
    }

    public void setCategory(long category) {
        this.category = category;
    }

    public long getType() {
        return type;
    }

    public void setType(long type) {
        this.type = type;
    }

    public int getWatchcount() {
        return watchcount;
    }

    public void setWatchcount(int watchcount) {
        this.watchcount = watchcount;
    }

    public int getFavritecount() {
        return favritecount;
    }

    public void setFavritecount(int favritecount) {
        this.favritecount = favritecount;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getIconurl() {
        return iconurl;
    }

    public void setIconurl(String iconurl) {
        this.iconurl = iconurl;
    }

    public String getLinkurl() {
        return linkurl;
    }

    public void setLinkurl(String linkurl) {
        this.linkurl = linkurl;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getBannerImg() {
        return bannerImg;
    }

    public void setBannerImg(String bannerImg) {
        this.bannerImg = bannerImg;
    }

    public ArrayList<String> getBelongtype() {
        return belongtype;
    }

    public void setBelongtype(ArrayList<String> belongtype) {
        this.belongtype = belongtype;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }


    public int getDisplayType() {
        return displayType;
    }

    public void setDisplayType(int displayType) {
        this.displayType = displayType;
    }
}
