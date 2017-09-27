package com.savor.zhixiang.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
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
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f,0f);
        alphaAnimation.setDuration(1500);
        alphaAnimation.setFillAfter(true);
        alphaAnimation.setInterpolator(new DecelerateInterpolator());
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(WelcomActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mSplashIv.startAnimation(alphaAnimation);
        //tv_1.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/ACaslonPro-Italic.otf"));
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
