package com.savor.zhixiang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.savor.zhixiang.R;
import com.savor.zhixiang.core.ApiRequestListener;


/**
 * 登录页面
 */
public class LoginActivity extends BaseActivity  implements View.OnClickListener,
        ApiRequestListener {

    private TextView login_code;
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
    }

    @Override
    public void setViews() {

    }

    @Override
    public void setListeners() {
        login_code.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
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

