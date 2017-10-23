package com.savor.zhixiang.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 首页卡片列表item
 * Created by hezd on 2017/9/21.
 */

public class CardBean implements Serializable {
    private NextPageBean nextpage;
    private String week;
    private String month;
    private String day;
    private String dailyauthor;
    private String dailyart;
    private List<CardDetail> list;

    @Override
    public String toString() {
        return "CardBean{" +
                "nextpage=" + nextpage +
                ", week='" + week + '\'' +
                ", month='" + month + '\'' +
                ", day='" + day + '\'' +
                ", dailyauthor='" + dailyauthor + '\'' +
                ", dailyart='" + dailyart + '\'' +
                ", list=" + list +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CardBean cardBean = (CardBean) o;

        if (nextpage != null ? !nextpage.equals(cardBean.nextpage) : cardBean.nextpage != null)
            return false;
        if (week != null ? !week.equals(cardBean.week) : cardBean.week != null) return false;
        if (month != null ? !month.equals(cardBean.month) : cardBean.month != null) return false;
        if (day != null ? !day.equals(cardBean.day) : cardBean.day != null) return false;
        if (dailyauthor != null ? !dailyauthor.equals(cardBean.dailyauthor) : cardBean.dailyauthor != null)
            return false;
        if (dailyart != null ? !dailyart.equals(cardBean.dailyart) : cardBean.dailyart != null)
            return false;
        return list != null ? list.equals(cardBean.list) : cardBean.list == null;

    }

    @Override
    public int hashCode() {
        int result = nextpage != null ? nextpage.hashCode() : 0;
        result = 31 * result + (week != null ? week.hashCode() : 0);
        result = 31 * result + (month != null ? month.hashCode() : 0);
        result = 31 * result + (day != null ? day.hashCode() : 0);
        result = 31 * result + (dailyauthor != null ? dailyauthor.hashCode() : 0);
        result = 31 * result + (dailyart != null ? dailyart.hashCode() : 0);
        result = 31 * result + (list != null ? list.hashCode() : 0);
        return result;
    }

    public NextPageBean getNextpage() {
        return nextpage;
    }

    public void setNextpage(NextPageBean nextpage) {
        this.nextpage = nextpage;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
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

    public List<CardDetail> getList() {
        return list;
    }

    public void setList(List<CardDetail> list) {
        this.list = list;
    }
}
