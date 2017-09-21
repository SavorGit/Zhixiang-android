package com.savor.zhixiang.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by bushlee on 2017/9/21.
 */

public class AllListResult implements Serializable {
    private static final long serialVersionUID = -1;
    private String bespeak_time;
    private List<ListItem> list;

    public String getBespeak_time() {
        return bespeak_time;
    }

    public void setBespeak_time(String bespeak_time) {
        this.bespeak_time = bespeak_time;
    }

    public List<ListItem> getList() {
        return list;
    }

    public void setList(List<ListItem> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "AllListResult{" +
                "bespeak_time='" + bespeak_time + '\'' +
                ", list=" + list +
                '}';
    }
}
