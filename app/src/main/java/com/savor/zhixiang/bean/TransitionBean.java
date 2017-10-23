package com.savor.zhixiang.bean;

import java.io.Serializable;

/**
 * 过渡实体bean
 * Created by hezd on 2017/10/23.
 */

public class TransitionBean implements Serializable {
    /**1.今日读完 2.历史读完 3.已读完全部知享*/
    private int type;
    /**名言作者*/
    private String dailyauthor;
    /**名言名句*/
    private String dailyart;

    private NextPageBean nextPageBean;

    @Override
    public String toString() {
        return "TransitionBean{" +
                "type=" + type +
                ", dailyauthor='" + dailyauthor + '\'' +
                ", dailyart='" + dailyart + '\'' +
                ", nextPageBean=" + nextPageBean +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TransitionBean that = (TransitionBean) o;

        if (type != that.type) return false;
        if (dailyauthor != null ? !dailyauthor.equals(that.dailyauthor) : that.dailyauthor != null)
            return false;
        if (dailyart != null ? !dailyart.equals(that.dailyart) : that.dailyart != null)
            return false;
        return nextPageBean != null ? nextPageBean.equals(that.nextPageBean) : that.nextPageBean == null;

    }

    @Override
    public int hashCode() {
        int result = type;
        result = 31 * result + (dailyauthor != null ? dailyauthor.hashCode() : 0);
        result = 31 * result + (dailyart != null ? dailyart.hashCode() : 0);
        result = 31 * result + (nextPageBean != null ? nextPageBean.hashCode() : 0);
        return result;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDailyauthor() {
        return dailyauthor;
    }

    public void setDailyauthor(String dailyauthor) {
        this.dailyauthor = dailyauthor;
    }

    public String getDailyart() {
        return dailyart;
    }

    public void setDailyart(String dailyart) {
        this.dailyart = dailyart;
    }

    public NextPageBean getNextPageBean() {
        return nextPageBean;
    }

    public void setNextPageBean(NextPageBean nextPageBean) {
        this.nextPageBean = nextPageBean;
    }
}
