package com.savor.zhixiang.bean;

import java.io.Serializable;

/**
 * Created by bushlee on 2017/9/21.
 */

public class ListItem implements Serializable {
    private static final long serialVersionUID = -1;
    private String dailyid;
    private String imgUrl;
    private String title;
    private String sourceName;
    private String bespeak_time;
    private String collecTime;

    public String getDailyid() {
        return dailyid;
    }

    public void setDailyid(String dailyid) {
        this.dailyid = dailyid;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getBespeak_time() {
        return bespeak_time;
    }

    public void setBespeak_time(String bespeak_time) {
        this.bespeak_time = bespeak_time;
    }


    public String getCollecTime() {
        return collecTime;
    }

    public void setCollecTime(String collecTime) {
        this.collecTime = collecTime;
    }

    @Override
    public String toString() {
        return "ListItem{" +
                "dailyid='" + dailyid + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", title='" + title + '\'' +
                ", sourceName='" + sourceName + '\'' +
                ", bespeak_time='" + bespeak_time + '\'' +
                ", collecTime='" + collecTime + '\'' +
                '}';
    }
}
