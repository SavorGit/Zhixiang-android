package com.savor.zhixiang.bean;

import java.io.Serializable;

/**
 * Created by hezd on 2017/9/22.
 */

public class CardDetailListItem implements Serializable {

    /**
     * dailytype : 1
     * stext : 002
     */

    private String dailytype;
    private String stext;
    private String spicture;

    @Override
    public String toString() {
        return "CardDetailListItem{" +
                "dailytype='" + dailytype + '\'' +
                ", stext='" + stext + '\'' +
                ", spicture='" + spicture + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CardDetailListItem that = (CardDetailListItem) o;

        if (dailytype != null ? !dailytype.equals(that.dailytype) : that.dailytype != null)
            return false;
        if (stext != null ? !stext.equals(that.stext) : that.stext != null) return false;
        return spicture != null ? spicture.equals(that.spicture) : that.spicture == null;

    }

    @Override
    public int hashCode() {
        int result = dailytype != null ? dailytype.hashCode() : 0;
        result = 31 * result + (stext != null ? stext.hashCode() : 0);
        result = 31 * result + (spicture != null ? spicture.hashCode() : 0);
        return result;
    }

    public String getDailytype() {
        return dailytype;
    }

    public void setDailytype(String dailytype) {
        this.dailytype = dailytype;
    }

    public String getStext() {
        return stext;
    }

    public void setStext(String stext) {
        this.stext = stext;
    }

    public String getSpicture() {
        return spicture;
    }

    public void setSpicture(String spicture) {
        this.spicture = spicture;
    }
}
