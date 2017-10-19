package com.savor.zhixiang.bean;

import java.io.Serializable;

/**
 * 资产实体类
 * Created by hezd on 2017/10/19.
 */

public class PropertyBean implements Serializable {
    /**资产数额，10（10亿以上），1（1亿以上），1000（一千万以上），0,（暂不透露）*/
    private int property;
    /**是否 已将上报过资产，默认是false*/
    private boolean isUploadPro;

    @Override
    public String toString() {
        return "PropertyBean{" +
                "property=" + property +
                ", isUploadPro=" + isUploadPro +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PropertyBean that = (PropertyBean) o;

        if (property != that.property) return false;
        return isUploadPro == that.isUploadPro;

    }

    @Override
    public int hashCode() {
        int result = property;
        result = 31 * result + (isUploadPro ? 1 : 0);
        return result;
    }

    public int getProperty() {
        return property;
    }

    public boolean isUploadPro() {
        return isUploadPro;
    }

    public void setUploadPro(boolean uploadPro) {
        isUploadPro = uploadPro;
    }

    /**
     * 资产数额，10（10亿以上），1（1亿以上），1000（一千万以上），0,（暂不透露）
     */
    public void setProperty(int property) {
        this.property = property;
    }
}
