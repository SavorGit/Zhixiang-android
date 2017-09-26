package com.savor.zhixiang.bean;

import java.io.Serializable;

/**
 * Created by admin on 2017/9/26.
 */

public class ShareBean implements Serializable {
    private static final long serialVersionUID = -1;
    private String title;
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ShareBean{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
