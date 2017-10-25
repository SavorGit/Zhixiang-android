package com.savor.zhixiang.bean;

import java.io.Serializable;

/**
 * Created by hezd on 2017/10/25.
 */

public class ShareUrlBean implements Serializable {
    private String url;

    @Override
    public String toString() {
        return "ShareUrlBean{" +
                "url='" + url + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShareUrlBean that = (ShareUrlBean) o;

        return url != null ? url.equals(that.url) : that.url == null;

    }

    @Override
    public int hashCode() {
        return url != null ? url.hashCode() : 0;
    }

    public String getUrl() {

        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
