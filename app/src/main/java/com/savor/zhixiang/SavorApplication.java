package com.savor.zhixiang;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.common.api.utils.AppUtils;
import com.common.api.utils.LogUtils;
import com.google.gson.Gson;
import com.savor.zhixiang.core.Session;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import java.io.File;
import java.util.Map;

/**
 * 全局application
 * Created by hezd on 2016/12/13.
 */

public class SavorApplication extends Application {

    private static SavorApplication mInstance;


    public static SavorApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        Debug.startMethodTracing("operation");

        // 设置异常捕获处理类
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        MobclickAgent.openActivityDurationTrack(false);
        Session.get(this);
        mInstance = this;
        UMShareAPI.get(this);
        initUmengPush();

//        initCachePath();
//        Debug.stopMethodTracing();
    }

    private void initUmengPush() {

    }

    //各个平台的配置，建议放在全局Application或者程序入口
    {
        PlatformConfig.setWeixin("wx59643f058e9b544c", "ad5cf8b259673427421a1181614c33c7");
//        PlatformConfig.setQQZone("1105235421", "wZ1iLVjm6vRUyxbv");
//        PlatformConfig.setSinaWeibo("258257010", "7b2701caad98239314089869bec08982","http://sns.whalecloud.com/sina2/callback");
//        PlatformConfig.setSinaWeibo("258257010", "7b2701caad98239314089869bec08982","https://api.weibo.com/oauth2/default.html");
    }
}
