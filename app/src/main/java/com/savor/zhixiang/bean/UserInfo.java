package com.savor.zhixiang.bean;

import java.io.Serializable;

/**
 * 用户信息
 * Created by hezd on 2017/10/23.
 */

public class UserInfo implements Serializable {
    private String nickname;
    private String headUrl;

    @Override
    public String toString() {
        return "UserInfo{" +
                "nickname='" + nickname + '\'' +
                ", headUrl='" + headUrl + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserInfo userInfo = (UserInfo) o;

        if (nickname != null ? !nickname.equals(userInfo.nickname) : userInfo.nickname != null)
            return false;
        return headUrl != null ? headUrl.equals(userInfo.headUrl) : userInfo.headUrl == null;

    }

    @Override
    public int hashCode() {
        int result = nickname != null ? nickname.hashCode() : 0;
        result = 31 * result + (headUrl != null ? headUrl.hashCode() : 0);
        return result;
    }

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
}
