package com.savor.zhixiang.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.savor.zhixiang.R;
import com.savor.zhixiang.bean.ListItem;
import com.savor.zhixiang.core.ApiRequestListener;
import com.savor.zhixiang.core.AppApi;
import com.savor.zhixiang.core.ResponseErrorMessage;
import com.savor.zhixiang.utils.RecordUtils;

import java.util.List;


/**
 * 登录页面
 */
public class LoginForCodeActivity extends BaseActivity implements View.OnClickListener,
        ApiRequestListener {

    private Context context;
    private TextView tv_center;
    private TextView tv_code;
    private ImageView icon;
    private EditText ev_num;
    private EditText ev_code;
    private TextView login_btn;
    private RelativeLayout back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        getViews();
        setViews();
        setListeners();
    }

    @Override
    public void getViews() {
        tv_code = (TextView) findViewById(R.id.tv_code);
        ev_num = (EditText) findViewById(R.id.ev_num);
        ev_code = (EditText) findViewById(R.id.ev_code);
        login_btn = (TextView) findViewById(R.id.login_btn);
        back = (RelativeLayout) findViewById(R.id.back);
    }

    @Override
    public void setViews() {

    }

    @Override
    public void setListeners() {
        tv_code.setOnClickListener(this);
        login_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.tv_code:
                getverifyCode();
                break;
            case R.id.login_btn:

                break;
            default:
                break;
        }
    }

    private void getverifyCode(){
        String tel = ev_num.getText().toString();
        if (!TextUtils.isEmpty(tel)) {
            AppApi.getverifyCode(this,tel,this);
        }
    }

    private void login(){
        String tel = ev_num.getText().toString();
        String code = ev_code.getText().toString();
        if (!TextUtils.isEmpty(tel)&&!TextUtils.isEmpty(code) ) {
            AppApi.mobileLogin(this,"",tel,code,"",this);
        }
    }
    @Override
    public void onSuccess(AppApi.Action method, Object obj) {
        switch (method) {
            case POST_GET_TVERIFY_CODE_JSON:

                break;
            case POST_MOBILE_LOGIN_JSON:
                finish();
                break;
        }
    }

    @Override
    public void onError(AppApi.Action method, Object obj) {

        if(obj instanceof ResponseErrorMessage) {
            ResponseErrorMessage message = (ResponseErrorMessage) obj;
            int code = message.getCode();

        }
    }
}

