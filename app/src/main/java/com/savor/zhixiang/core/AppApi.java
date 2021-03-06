package com.savor.zhixiang.core;

import android.content.Context;

import com.common.api.utils.AppUtils;

import java.io.File;
import java.util.HashMap;

public class AppApi {
    public static final String APK_DOWNLOAD_FILENAME = "NewApp.apk";

    /**云平台php接口*/
//    public static final String CLOUND_PLATFORM_PHP_URL = "http://devp.mobile.littlehotspot.com/";
  public static final String CLOUND_PLATFORM_PHP_URL = "http://mobile.littlehotspot.com/";

    /**
     * 常用的一些key值 ,签名、时间戳、token、params
     */
    public static final String SIGN = "sign";
    public static final String TIME = "time";
    public static final String TOKEN = "token";
    public static final String PARAMS = "params";


    /**这是一个临时值，以请求时传入的值为准*/
    public static String tvBoxUrl;
    public static int hotelid;
    public static int roomid;

    /**
     * Action-自定义行为 注意：自定义后缀必须为以下结束 _FORM:该请求是Form表单请求方式 _JSON:该请求是Json字符串
     * _XML:该请求是XML请求描述文件 _GOODS_DESCRIPTION:图文详情 __NOSIGN:参数不需要进行加密
     */
    public static enum Action {
        TEST_DOWNLOAD_JSON,
        TEST_POST_JSON,
        TEST_GET_JSON,
        POST_UPGRADE_JSON,
        /**获取关键词列表*/
        POST_GET_KEYWORDS_JSON,
        /**获取首页列表*/
        POST_GET_CARDLIST_JSON,
        /**全部知享文章列表*/
        POST_GET_ALL_LIST_JSON,
        /**我的收藏列表*/
        POST_GET_MY_COLLECTION_JSON,
        /**收藏、取消收藏*/
        POST_ADD_MY_COLLECTION_JSON,
        /**升级*/
        POST_VERSION_JSON,
        /**详情页*/
        POST_CARD_DETAIL_JSON,
        /**是否收藏*/
        POST_IS_COLLECTED_JSON,
        /**获取验证码*/
        POST_GET_TVERIFY_CODE_JSON,
        /**手机登录*/
        POST_MOBILE_LOGIN_JSON,
        /**缓存配置*/
        POST_GET_DAILY_CONFIG_JSON,
        /**获取分享url*/
        POST_GET_SHARE_URL_JSON,
        /**微信登录*/
        POST_WX_LOGIN_JSON,
    }

    /**
     * API_URLS:(URL集合)
     */
    public static final HashMap<Action, String> API_URLS = new HashMap<Action, String>() {
        private static final long serialVersionUID = -8469661978245513712L;

        {
            put(Action.TEST_GET_JSON, "https://www.baidu.com/");
            put(Action.TEST_GET_JSON, "https://www.baidu.com/");
            put(Action.POST_GET_KEYWORDS_JSON, formatPhpUrl("Dailyknowledge/Keywords/getAllKeywords"));
            put(Action.POST_GET_CARDLIST_JSON, formatPhpUrl("Dailyknowledge/Index/getList"));
            put(Action.POST_GET_ALL_LIST_JSON, formatPhpUrl("Dailyknowledge/Content/getAllList"));
            put(Action.POST_GET_MY_COLLECTION_JSON, formatPhpUrl("Dailyknowledge/Collection/getMyCollection"));
            put(Action.POST_ADD_MY_COLLECTION_JSON, formatPhpUrl("Dailyknowledge/Collection/addMyCollection"));
            put(Action.POST_VERSION_JSON, formatPhpUrl("Dailyknowledge/Version/index"));
            put(Action.POST_CARD_DETAIL_JSON, formatPhpUrl("Dailyknowledge/Content/getDetail"));
            put(Action.POST_IS_COLLECTED_JSON, formatPhpUrl("Dailyknowledge/Collection/isCollected"));
            put(Action.POST_GET_TVERIFY_CODE_JSON, formatPhpUrl("Dailyknowledge/Login/getverifyCode"));
            put(Action.POST_MOBILE_LOGIN_JSON, formatPhpUrl("Dailyknowledge/Login/mobileLogin"));
            put(Action.POST_GET_DAILY_CONFIG_JSON, formatPhpUrl("Dailyknowledge/Config/getdailyconfig"));
            put(Action.POST_GET_SHARE_URL_JSON, formatPhpUrl("Dailyknowledge/Config/getshareApp"));
            put(Action.POST_WX_LOGIN_JSON, formatPhpUrl("Dailyknowledge/Login/weixinLogin"));
        }
    };


    /**
     * php后台接口
     * @param url
     * @return
     */
    private static String formatPhpUrl(String url) {
        return CLOUND_PLATFORM_PHP_URL +url;
    }

    public static void testPost(Context context, String orderNo, ApiRequestListener handler) {
        final HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("loginfield", "15901559579");
        params.put("password", "123456");
        params.put("dr_rg_cd", "86");
        params.put("version_code", 19 + "");
        new AppServiceOk(context, Action.TEST_POST_JSON, handler, params).post(false, false, true, true);

    }

    /**升级*/
    public static void Upgrade(Context context,ApiRequestListener handler) {
        final HashMap<String, Object> params = new HashMap<String, Object>();
        new AppServiceOk(context, Action.POST_VERSION_JSON,handler,params).post();
    }
    public static void testGet(Context context, ApiRequestListener handler) {
//        SmallPlatInfoBySSDP smallPlatInfoBySSDP = Session.get(context).getSmallPlatInfoBySSDP();
//        API_URLS.put(Action.TEST_GET_JSON,"http://"+ smallPlatInfoBySSDP.getServerIp()+":"+ smallPlatInfoBySSDP.getCommandPort()+"/small-platform-1.0.0.0.1-SNAPSHOT/com/execute/call-tdc");
//        final HashMap<String, Object> params = new HashMap<String, Object>();
//        new AppServiceOk(context, Action.TEST_GET_JSON, handler, params).get();
    }

    public static void downApp(Context context, String url, ApiRequestListener handler) {
        final HashMap<String, Object> params = new HashMap<String, Object>();
        String target = AppUtils.getPath(context, AppUtils.StorageFile.file);

        String targetApk = target + APK_DOWNLOAD_FILENAME;
        File tarFile = new File(targetApk);
        if (tarFile.exists()) {
            tarFile.delete();
        }
        new AppServiceOk(context, Action.TEST_DOWNLOAD_JSON, handler, params).downLoad(url, targetApk);

    }

    /**
     * 获取关键词列表
     * @param context
     * @param handler
     */
    public static void getKeywords(Context context, ApiRequestListener handler) {
        final HashMap<String, Object> params = new HashMap<>();
        new AppServiceOk(context, Action.POST_GET_KEYWORDS_JSON, handler, params).post();
    }

    /**
     * 获取关键词列表
     * @param context
     * @param handler
     */
    public static void getCardList(Context context,String bespeak_time, ApiRequestListener handler) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("bespeak_time",bespeak_time);
        new AppServiceOk(context, Action.POST_GET_CARDLIST_JSON, handler, params).post();
    }

    /**
     * 全部知享文章列表
     * @param context
     * @param handler
     */
    public static void getAllList(Context context,String bespeak_time ,ApiRequestListener handler) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("bespeak_time", bespeak_time);
        new AppServiceOk(context, Action.POST_GET_ALL_LIST_JSON, handler, params).post();
    }

    /**
     * 我的收藏列表
     * @param context
     * @param handler
     */
    public static void getMyCollection(Context context,String collecTime ,ApiRequestListener handler) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("collecTime", collecTime);
        new AppServiceOk(context, Action.POST_GET_MY_COLLECTION_JSON, handler, params).post();
    }
    /**
     * 收藏、取消收藏
     * @param context
     * @param handler
     */
    public static void addMyCollection(Context context,String dailyid ,ApiRequestListener handler) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("dailyid", dailyid);
        new AppServiceOk(context, Action.POST_ADD_MY_COLLECTION_JSON, handler, params).post();
    }

    /**
     * 文章详情页
     * @param context
     * @param handler
     */
    public static void getCardDetail(Context context,String dailyid ,ApiRequestListener handler) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("dailyid", dailyid);
        new AppServiceOk(context, Action.POST_CARD_DETAIL_JSON, handler, params).post();
    }

    /**
     * 文章详情页
     * @param context
     * @param handler
     */
    public static void isCollected(Context context,String dailyid ,ApiRequestListener handler) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("dailyid", dailyid);
        new AppServiceOk(context, Action.POST_IS_COLLECTED_JSON, handler, params).post();
    }

    /**
     * 获取验证码
     * @param context
     * @param handler
     */
    public static void getverifyCode(Context context,String tel ,ApiRequestListener handler) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("tel", tel);
        new AppServiceOk(context, Action.POST_GET_TVERIFY_CODE_JSON, handler, params).post();
    }

    /**
     * 获取验证码
     * @param context
     * @param handler
     */
    public static void mobileLogin(Context context,String openid,String tel,String verifycode,String ptype ,ApiRequestListener handler) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("tel", tel);
        params.put("openid", openid);
        params.put("ptype", ptype);
        params.put("verifycode", verifycode);
        new AppServiceOk(context, Action.POST_MOBILE_LOGIN_JSON, handler, params).post();
    }

    /**
     * 缓存配置
     * @param context
     * @param handler
     */
    public static void getdailyconfig(Context context, ApiRequestListener handler) {
        final HashMap<String, Object> params = new HashMap<>();
        new AppServiceOk(context, Action.POST_GET_DAILY_CONFIG_JSON, handler, params).post();
    }

    /**
     * 获取分享url
     * @param context
     * @param handler
     */
    public static void getShareUrl(Context context, ApiRequestListener handler) {
        final HashMap<String, Object> params = new HashMap<>();
        new AppServiceOk(context, Action.POST_GET_SHARE_URL_JSON, handler, params).post();
    }

    /**
     * 上传微信登录
     * @param context
     * @param handler
     */
    public static void sendWxLoginInfo(Context context, String openid,String ptype,String tel,ApiRequestListener handler) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("openid",openid);
        params.put("ptype",ptype);
        params.put("tel",tel);
        new AppServiceOk(context, Action.POST_WX_LOGIN_JSON, handler, params).post();
    }

    // 超时（网络）异常
    public static final String ERROR_TIMEOUT = "3001";
    // 业务异常
    public static final String ERROR_BUSSINESS = "3002";
    // 网络断开
    public static final String ERROR_NETWORK_FAILED = "3003";

    public static final String RESPONSE_CACHE = "3004";

    /**
     * 从这里定义业务的错误码
     */
    public static final int CMS_RESPONSE_STATE_SUCCESS = 1001;
    public static final int CLOUND_RESPONSE_STATE_SUCCESS = 10000;

    /**机顶盒返回响应码*/
    public static final int TVBOX_RESPONSE_STATE_SUCCESS = 0;
    public static final int TVBOX_RESPONSE_STATE_ERROR = -1;
    public static final int TVBOX_RESPONSE_STATE_FORCE = 4;
    /**大小图不匹配失败*/
    public static final int TVBOX_RESPONSE_NOT_MATCH = 10002;
    public static final int TVBOX_RESPONSE_VIDEO_COMPLETE = 10003;

    /**
     * 数据返回错误
     */
    public static final int HTTP_RESPONSE_STATE_ERROR = 101;
    /**没有更多数据响应码*/
    public static final int HTTP_RESPONSE_CODE_NO_DATA = 10060;
}
