package com.savor.zhixiang.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hezd on 2017/9/21.
 */

public class CardDetail implements Serializable {

    /**
     * dailyid : 5
     * imgUrl : http://devp.oss.littlehotspot.com/media/resource/henmkQ4dRN.png
     * title : 知享第一次测试
     * desc : 圣诞节卡积分阿萨德科技暗黑实际开发阿斯顿空间哈是否阿斯利康大师傅老地方爱空间佛欺负阿萨水电费阿飞开发暗示法拉风龙开始阿斯利康发了康师傅你
     * sourceName : 环球网
     * contentDetail : {"dailyid":"5","imgUrl":"http://devp.oss.littlehotspot.com/media/resource/henmkQ4dRN.png","desc":"圣诞节卡积分阿萨德科技暗黑实际开发阿斯顿空间哈是否阿斯利康大师傅老地方爱空间佛欺负阿萨水电费阿飞开发暗示法拉风龙开始阿斯利康发了康师傅你","bespeak_time":"2017-09-20","sourceName":"环球网","details":[{"dailytype":"1","stext":"奥斯卡电话却无法南京市覅偶却无法卢卡斯的大口径舞动奇迹分离器拉萨开讲啦看谁的"},{"dailytype":"1","stext":"阿萨德请问我恢复期晚饭后欺负回去哦if后期会放弃哦我维护法律是开发商来发挥其我发红包 起飞后起飞后期维护佛IQ晚饭后前往喜欢"},{"dailytype":"1","stext":"阿斯达群考完就发货气氛很穷违法和看电视年轻化佛IQ还哦"},{"dailytype":"3","spicture":"http://devp.oss.littlehotspot.com/media/resource/QnDcQGdW3C.jpg"},{"dailytype":"3","spicture":"http://devp.oss.littlehotspot.com/media/resource/KcWYxQxYRz.jpg"},{"dailytype":"1","stext":"阿斯顿两千晚饭后IQ哈佛和我佛千万富豪榜上打开机器哦哦其文化宫IQ玩过强迫风景强迫我打算离开饭饭vqwvbqowifgbhqwpoifghqwpoif"},{"dailytype":"1","stext":"阿萨德请问佛IQ佛IQ恢复刷卡缴费把快乐时间起违反环球网覅偶返回"}]}
     */

    private String dailyid;
    private String imgUrl;
    private String title;
    private String desc;
    private String sourceName;
    private ContentDetailBean contentDetail;
    private String week;
    private String month;
    private String day;
    private String share_url;

    @Override
    public String toString() {
        return "CardDetail{" +
                "dailyid='" + dailyid + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", sourceName='" + sourceName + '\'' +
                ", contentDetail=" + contentDetail +
                ", week='" + week + '\'' +
                ", month='" + month + '\'' +
                ", day='" + day + '\'' +
                ", share_url='" + share_url + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CardDetail detail = (CardDetail) o;

        if (dailyid != null ? !dailyid.equals(detail.dailyid) : detail.dailyid != null)
            return false;
        if (imgUrl != null ? !imgUrl.equals(detail.imgUrl) : detail.imgUrl != null) return false;
        if (title != null ? !title.equals(detail.title) : detail.title != null) return false;
        if (desc != null ? !desc.equals(detail.desc) : detail.desc != null) return false;
        if (sourceName != null ? !sourceName.equals(detail.sourceName) : detail.sourceName != null)
            return false;
        if (contentDetail != null ? !contentDetail.equals(detail.contentDetail) : detail.contentDetail != null)
            return false;
        if (week != null ? !week.equals(detail.week) : detail.week != null) return false;
        if (month != null ? !month.equals(detail.month) : detail.month != null) return false;
        if (day != null ? !day.equals(detail.day) : detail.day != null) return false;
        return share_url != null ? share_url.equals(detail.share_url) : detail.share_url == null;

    }

    @Override
    public int hashCode() {
        int result = dailyid != null ? dailyid.hashCode() : 0;
        result = 31 * result + (imgUrl != null ? imgUrl.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (desc != null ? desc.hashCode() : 0);
        result = 31 * result + (sourceName != null ? sourceName.hashCode() : 0);
        result = 31 * result + (contentDetail != null ? contentDetail.hashCode() : 0);
        result = 31 * result + (week != null ? week.hashCode() : 0);
        result = 31 * result + (month != null ? month.hashCode() : 0);
        result = 31 * result + (day != null ? day.hashCode() : 0);
        result = 31 * result + (share_url != null ? share_url.hashCode() : 0);
        return result;
    }

    public String getDailyid() {
        return dailyid;
    }

    public void setDailyid(String dailyid) {
        this.dailyid = dailyid;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public ContentDetailBean getContentDetail() {
        return contentDetail;
    }

    public void setContentDetail(ContentDetailBean contentDetail) {
        this.contentDetail = contentDetail;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public class ContentDetailBean implements Serializable{
        /**
         * dailyid : 5
         * imgUrl : http://devp.oss.littlehotspot.com/media/resource/henmkQ4dRN.png
         * desc : 圣诞节卡积分阿萨德科技暗黑实际开发阿斯顿空间哈是否阿斯利康大师傅老地方爱空间佛欺负阿萨水电费阿飞开发暗示法拉风龙开始阿斯利康发了康师傅你
         * bespeak_time : 2017-09-20
         * sourceName : 环球网
         * details : [{"dailytype":"1","stext":"奥斯卡电话却无法南京市覅偶却无法卢卡斯的大口径舞动奇迹分离器拉萨开讲啦看谁的"},{"dailytype":"1","stext":"阿萨德请问我恢复期晚饭后欺负回去哦if后期会放弃哦我维护法律是开发商来发挥其我发红包 起飞后起飞后期维护佛IQ晚饭后前往喜欢"},{"dailytype":"1","stext":"阿斯达群考完就发货气氛很穷违法和看电视年轻化佛IQ还哦"},{"dailytype":"3","spicture":"http://devp.oss.littlehotspot.com/media/resource/QnDcQGdW3C.jpg"},{"dailytype":"3","spicture":"http://devp.oss.littlehotspot.com/media/resource/KcWYxQxYRz.jpg"},{"dailytype":"1","stext":"阿斯顿两千晚饭后IQ哈佛和我佛千万富豪榜上打开机器哦哦其文化宫IQ玩过强迫风景强迫我打算离开饭饭vqwvbqowifgbhqwpoifghqwpoif"},{"dailytype":"1","stext":"阿萨德请问佛IQ佛IQ恢复刷卡缴费把快乐时间起违反环球网覅偶返回"}]
         */

        private String dailyid;
        private String imgUrl;
        private String desc;
        private String bespeak_time;
        private String sourceName;
        private String title;
        private String share_url;
        private List<CardDetailListItem> details;


        @Override
        public String toString() {
            return "ContentDetailBean{" +
                    "dailyid='" + dailyid + '\'' +
                    ", imgUrl='" + imgUrl + '\'' +
                    ", desc='" + desc + '\'' +
                    ", bespeak_time='" + bespeak_time + '\'' +
                    ", sourceName='" + sourceName + '\'' +
                    ", title='" + title + '\'' +
                    ", share_url='" + share_url + '\'' +
                    ", details=" + details +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ContentDetailBean)) return false;

            ContentDetailBean that = (ContentDetailBean) o;

            if (dailyid != null ? !dailyid.equals(that.dailyid) : that.dailyid != null)
                return false;
            if (imgUrl != null ? !imgUrl.equals(that.imgUrl) : that.imgUrl != null) return false;
            if (desc != null ? !desc.equals(that.desc) : that.desc != null) return false;
            if (bespeak_time != null ? !bespeak_time.equals(that.bespeak_time) : that.bespeak_time != null)
                return false;
            if (sourceName != null ? !sourceName.equals(that.sourceName) : that.sourceName != null)
                return false;
            if (title != null ? !title.equals(that.title) : that.title != null) return false;
            if (share_url != null ? !share_url.equals(that.share_url) : that.share_url != null)
                return false;
            return details != null ? details.equals(that.details) : that.details == null;

        }

        @Override
        public int hashCode() {
            int result = dailyid != null ? dailyid.hashCode() : 0;
            result = 31 * result + (imgUrl != null ? imgUrl.hashCode() : 0);
            result = 31 * result + (desc != null ? desc.hashCode() : 0);
            result = 31 * result + (bespeak_time != null ? bespeak_time.hashCode() : 0);
            result = 31 * result + (sourceName != null ? sourceName.hashCode() : 0);
            result = 31 * result + (title != null ? title.hashCode() : 0);
            result = 31 * result + (share_url != null ? share_url.hashCode() : 0);
            result = 31 * result + (details != null ? details.hashCode() : 0);
            return result;
        }

        public String getDailyid() {
            return dailyid;
        }

        public void setDailyid(String dailyid) {
            this.dailyid = dailyid;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getBespeak_time() {
            return bespeak_time;
        }

        public void setBespeak_time(String bespeak_time) {
            this.bespeak_time = bespeak_time;
        }

        public String getSourceName() {
            return sourceName;
        }

        public void setSourceName(String sourceName) {
            this.sourceName = sourceName;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<CardDetailListItem> getDetails() {
            return details;
        }

        public void setDetails(List<CardDetailListItem> details) {
            this.details = details;
        }

        public String getShare_url() {
            return share_url;
        }

        public void setShare_url(String share_url) {
            this.share_url = share_url;
        }
    }
}
