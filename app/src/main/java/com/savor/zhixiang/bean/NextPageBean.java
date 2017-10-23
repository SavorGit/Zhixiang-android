package com.savor.zhixiang.bean;

import java.io.Serializable;

/**
 * Created by hezd on 2017/10/20.
 */

public class NextPageBean implements Serializable {

    /**
     * next : 1
     * week : 星期二
     * month : 10月
     * day : 10
     */

    private int next;
    private String week;
    private String month;
    private String day;

    public int getNext() {
        return next;
    }

    public void setNext(int next) {
        this.next = next;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NextPageBean that = (NextPageBean) o;

        if (next != that.next) return false;
        if (week != null ? !week.equals(that.week) : that.week != null) return false;
        if (month != null ? !month.equals(that.month) : that.month != null) return false;
        return day != null ? day.equals(that.day) : that.day == null;

    }

    @Override
    public int hashCode() {
        int result = next;
        result = 31 * result + (week != null ? week.hashCode() : 0);
        result = 31 * result + (month != null ? month.hashCode() : 0);
        result = 31 * result + (day != null ? day.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "NextPageBean{" +
                "next=" + next +
                ", week='" + week + '\'' +
                ", month='" + month + '\'' +
                ", day='" + day + '\'' +
                '}';
    }
}
