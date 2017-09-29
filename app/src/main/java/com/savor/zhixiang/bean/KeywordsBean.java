package com.savor.zhixiang.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 关键词
 * Created by hezd on 2017/9/29.
 */

public class KeywordsBean implements Serializable {

    /**
     * list : ["新能源汽车","互联网外卖","郭涛","毕福剑","这是第10个","092909","092908","092907","092906","092905"]
     * put_time : 2017-09-29
     */

    private String put_time;
    private List<String> list;

    public String getPut_time() {
        return put_time;
    }

    public void setPut_time(String put_time) {
        this.put_time = put_time;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "KeywordsBean{" +
                "put_time='" + put_time + '\'' +
                ", list=" + list +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KeywordsBean that = (KeywordsBean) o;

        if (put_time != null ? !put_time.equals(that.put_time) : that.put_time != null)
            return false;
        return list != null ? list.equals(that.list) : that.list == null;

    }

    @Override
    public int hashCode() {
        int result = put_time != null ? put_time.hashCode() : 0;
        result = 31 * result + (list != null ? list.hashCode() : 0);
        return result;
    }
}
