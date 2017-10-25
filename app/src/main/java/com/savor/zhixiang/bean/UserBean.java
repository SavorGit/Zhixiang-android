package com.savor.zhixiang.bean;

import java.io.Serializable;

/**
 * Created by admin on 2017/10/25.
 */

public class UserBean implements Serializable {
    private static final long serialVersionUID = -1;
    private String nickname;
    private String headUrl;
    private String userNum;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "nickname='" + nickname + '\'' +
                ", headUrl='" + headUrl + '\'' +
                ", userNum='" + userNum + '\'' +
                '}';
    }
}
