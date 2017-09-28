package com.savor.zhixiang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.savor.zhixiang.R;
import com.savor.zhixiang.core.AppApi;

import java.util.List;

public class WelcomActivity extends BaseActivity {

    private ImageView mSplashIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom);

        getViews();
        setViews();
        setListeners();

        AppApi.getKeywords(this,this);
    }

    @Override
    public void getViews() {
        mSplashIv = (ImageView) findViewById(R.id.iv_splash);
    }

    @Override
    public void setViews() {
        mSplashIv.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomActivity.this,MainActivity.class);
                startActivity(intent);
                WelcomActivity.this.finish();
            }
        },3000);

    }

    @Override
    public void setListeners() {

    }

    @Override
    public void onSuccess(AppApi.Action method, Object obj) {
        switch (method) {
            case POST_GET_KEYWORDS_JSON:
                if(obj instanceof List) {
                    List<String> keywords = (List<String>) obj;
                    mSession.setKeywords(keywords);
                }
                break;
        }
    }

    @Override
    public void onError(AppApi.Action method, Object obj) {
    }
}
