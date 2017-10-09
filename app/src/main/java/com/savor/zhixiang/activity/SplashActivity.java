package com.savor.zhixiang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.savor.zhixiang.R;
import com.savor.zhixiang.bean.KeywordsBean;
import com.savor.zhixiang.core.AppApi;
import com.savor.zhixiang.widget.KeywordDialog;

import java.util.List;

public class SplashActivity extends BaseActivity {

    private KeywordsBean keywords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0){
            finish();
            return;
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 因为启动页设置主题背景图片以后，渐隐的动画被遮罩看不到，所有放到欢迎页做
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                intent.putExtra("keywords",keywords);
                startActivity(intent);
                finish();
            }
        },3000);
        AppApi.getKeywords(this,this);
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

    @Override
    public void onSuccess(AppApi.Action method, Object obj) {
        switch (method) {
            case POST_GET_KEYWORDS_JSON:
                if(obj instanceof KeywordsBean) {
                    keywords = (KeywordsBean) obj;
                }
                break;
        }
    }

    @Override
    public void onError(AppApi.Action method, Object obj) {

    }
}
