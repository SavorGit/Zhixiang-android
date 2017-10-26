package com.savor.zhixiang.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.api.utils.ShowMessage;
import com.savor.zhixiang.R;
import com.savor.zhixiang.bean.PropertyBean;
import com.savor.zhixiang.bean.UserBean;
import com.savor.zhixiang.core.ApiRequestListener;
import com.savor.zhixiang.core.AppApi;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;


/**
 * 登录页面
 */
public class LoginActivity extends BaseActivity  implements View.OnClickListener,
        ApiRequestListener {

    public static final int RESULT_CODE_LOGIN = 1000;
    private TextView login_code;
    private TextView mLoginBtn;
    private RelativeLayout back;

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
        back = (RelativeLayout) findViewById(R.id.back);
    }

    @Override
    public void setViews() {
        login_code.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );
    }

    @Override
    public void setListeners() {
        mLoginBtn.setOnClickListener(this);
        login_code.setOnClickListener(this);
        back.setOnClickListener(this);
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
                        UserBean userBean = mSession.getUserBean();
                        String userNum = "";
                        if(userBean!=null) {
                            userNum = userBean.getUserNum();
                        }
                        if(map!=null) {
                            String openid = map.get("openid");
                            PropertyBean property = mSession.getProperty();
                            int pty = 4;
                            if(property!=null) {
                                pty = property.getProperty();
                            }
                            AppApi.sendWxLoginInfo(LoginActivity.this,openid,String.valueOf(pty),userNum,LoginActivity.this);
                        }
                        setResult(RESULT_CODE_LOGIN);
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
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onSuccess(AppApi.Action method, Object obj) {
        super.onSuccess(method, obj);
        switch (method) {
            case POST_WX_LOGIN_JSON:
                PropertyBean property = mSession.getProperty();
                if(property!=null) {
                    property.setUploadPro(true);
                    mSession.setProperty(property);
                }
                break;
        }
    }
}

