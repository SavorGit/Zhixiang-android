package com.savor.zhixiang.bean;

import java.io.Serializable;

/**
 * Created by hezd on 2017/10/16.
 */

public class PushCardIdBean implements Serializable {
    private String dailyid;

    @Override
    public String toString() {
        return "PushCardIdBean{" +
                "dailyid='" + dailyid + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PushCardIdBean that = (PushCardIdBean) o;

        return dailyid != null ? dailyid.equals(that.dailyid) : that.dailyid == null;

    }

    @Override
    public int hashCode() {
        return dailyid != null ? dailyid.hashCode() : 0;
    }

    public String getDailyid() {
        return dailyid;
    }

    public void setDailyid(String dailyid) {
        this.dailyid = dailyid;
    }
}
