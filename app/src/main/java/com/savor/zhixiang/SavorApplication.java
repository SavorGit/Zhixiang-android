package com.savor.zhixiang;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.common.api.utils.LogUtils;
import com.google.gson.Gson;
import com.savor.zhixiang.core.Session;

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
        Session.get(this);
        mInstance = this;

        initUmengPush();

//        Debug.stopMethodTracing();
    }

    private void initUmengPush() {

    }

}
