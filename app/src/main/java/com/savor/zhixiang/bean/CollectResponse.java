package com.savor.zhixiang.bean;

import java.io.Serializable;

/**
 * 检查文章是否被收藏
 * Created by hezd on 2017/9/25.
 */

public class CollectResponse implements Serializable {
    private String state;

    @Override
    public String toString() {
        return "CollectResponse{" +
                "state='" + state + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CollectResponse that = (CollectResponse) o;

        return state != null ? state.equals(that.state) : that.state == null;

    }

    @Override
    public int hashCode() {
        return state != null ? state.hashCode() : 0;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
