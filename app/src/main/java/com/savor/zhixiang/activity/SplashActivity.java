package com.savor.zhixiang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.savor.zhixiang.R;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash);

        // 因为启动页设置主题背景图片以后，渐隐的动画被遮罩看不到，所有放到欢迎页做
        Intent intent = new Intent(SplashActivity.this,WelcomActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void getViews() {

    }

    @Override
    public void setViews() {

    }

    @Override
    public void setListeners() {

    }
}
