package com.savor.zhixiang.bean;

import java.io.Serializable;

/**
 * Created by admin on 2017/10/24.
 */

public class ConfigBean implements Serializable {
    private static final long serialVersionUID = -1;
    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "ConfigBean{" +
                "state='" + state + '\'' +
                '}';
    }
}
