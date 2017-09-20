package com.savor.zhixiang.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.savor.zhixiang.R;

public class WelcomActivity extends BaseActivity {

    private ImageView mSplashIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom);

        getViews();
        setViews();
        setListeners();
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
}
