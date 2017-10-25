package com.savor.zhixiang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.common.api.utils.ShowMessage;
import com.savor.zhixiang.R;
import com.savor.zhixiang.core.ApiRequestListener;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;


/**
 * 登录页面
 */
public class LoginActivity extends BaseActivity  implements View.OnClickListener,
        ApiRequestListener {

    private TextView login_code;
    private TextView mLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        getViews();
        setViews();
        setListeners();
    }

    @Override
    public void getViews() {
        login_code = (TextView) findViewById(R.id.login_code);
        mLoginBtn = (TextView) findViewById(R.id.tv_wx_login);
    }

    @Override
    public void setViews() {

    }

    @Override
    public void setListeners() {
        mLoginBtn.setOnClickListener(this);
        login_code.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_wx_login:
                UMShareAPI.get(this).doOauthVerify(this, SHARE_MEDIA.WEIXIN, new UMAuthListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                        ShowMessage.showToast(LoginActivity.this,"登录成功");
                        finish();
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media, int i) {

                    }
                });
                break;
            case R.id.back:
                finish();
                break;
            case R.id.login_code:
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this,LoginForCodeActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

}

