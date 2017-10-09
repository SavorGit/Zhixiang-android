package com.savor.zhixiang.receiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.common.api.utils.LogUtils;
import com.savor.zhixiang.activity.MainActivity;
import com.savor.zhixiang.utils.ActivitiesManager;

/**
 * 监听按下home键
 * Created by hezd on 2017/9/29.
 */

public class HomeKeyReceiver extends BroadcastReceiver {
    private String SYSTEM_REASON = "reason";
    private String SYSTEM_HOME_KEY = "homekey";
    private String SYSTEM_HOME_KEY_LONG = "recentapps";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
            String reason = intent.getStringExtra(SYSTEM_REASON);
            if (TextUtils.equals(reason, SYSTEM_HOME_KEY)) {
                //表示按了home键,程序到了后台
                LogUtils.d("savor:home 按下home键");
                Activity specialActivity = ActivitiesManager.getInstance().getSpecialActivity(MainActivity.class);
                if(specialActivity!=null&& specialActivity instanceof MainActivity) {
                    MainActivity mainActivity = (MainActivity) specialActivity;
                    mainActivity.killAppDelyed();
                }
            }
        }
    }
}
