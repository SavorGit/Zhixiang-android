package com.savor.zhixiang.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.savor.zhixiang.R;
import com.savor.zhixiang.bean.ListItem;
import com.savor.zhixiang.bean.PropertyBean;
import com.savor.zhixiang.bean.UserBean;
import com.savor.zhixiang.core.ApiRequestListener;
import com.savor.zhixiang.core.AppApi;
import com.savor.zhixiang.core.ResponseErrorMessage;
import com.savor.zhixiang.utils.RecordUtils;

import java.util.List;

import static com.savor.zhixiang.R.color.color_b7b6b2;


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
    private String tel = "";
    private String code = "";
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
        back.setOnClickListener(this);
        ev_num.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                //s:变化后的所有字符

            }
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
                //s:变化前的所有字符； start:字符开始的位置； count:变化前的总字节数；after:变化后的字节数
                //Toast.makeText(getApplicationContext(), "变化前:"+s+";"+start+";"+count+";"+after, Toast.LENGTH_SHORT).show();
            }
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub
                //S：变化后的所有字符；start：字符起始的位置；before: 变化之前的总字节数；count:变化后的字节数
                //Toast.makeText(getApplicationContext(), "变化后:"+s+";"+start+";"+before+";"+count, Toast.LENGTH_SHORT).show();
                setCodeView();
                setLoginView();
            }

        });

        ev_code.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                //s:变化后的所有字符

            }
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
                //s:变化前的所有字符； start:字符开始的位置； count:变化前的总字节数；after:变化后的字节数
                //Toast.makeText(getApplicationContext(), "变化前:"+s+";"+start+";"+count+";"+after, Toast.LENGTH_SHORT).show();
            }
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub
                //S：变化后的所有字符；start：字符起始的位置；before: 变化之前的总字节数；count:变化后的字节数
                //Toast.makeText(getApplicationContext(), "变化后:"+s+";"+start+";"+before+";"+count, Toast.LENGTH_SHORT).show();
                setCodeView();
                setLoginView();
            }

        });
    }

    private void setCodeView(){
        tel = ev_num.getText().toString();
        if (!TextUtils.isEmpty(tel)) {
            tv_code.setClickable(true);
            tv_code.setBackgroundResource(R.drawable.corner_remote_view_click);
            tv_code.setTextColor(getColor(R.color.color_333333));
        }else {
            tv_code.setClickable(false);
            tv_code.setBackgroundResource(R.drawable.corner_remote_view_g);
            tv_code.setTextColor(getColor(R.color.color_b7b6b2));
        }
    }

    private void setLoginView(){
        tel = ev_num.getText().toString();
        code = ev_code.getText().toString();
        if (!TextUtils.isEmpty(tel)&&!TextUtils.isEmpty(code)) {
            login_btn.setClickable(true);
            login_btn.setBackgroundResource(R.drawable.corner_remote_view);
            login_btn.setTextColor(getColor(R.color.color_fefefe));
        }else {
            login_btn.setClickable(false);
            login_btn.setBackgroundResource(R.drawable.corner_remote_view_btn);
            login_btn.setTextColor(getColor(R.color.color_fefefe));
        }
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
                login();
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
        tel = ev_num.getText().toString();
        code = ev_code.getText().toString();
        String ptype = mSession.getProperty().getProperty()+"";
        if (!TextUtils.isEmpty(tel)&&!TextUtils.isEmpty(code) ) {
            AppApi.mobileLogin(this,"",tel,code,ptype,this);
        }
    }

    @Override
    public void onSuccess(AppApi.Action method, Object obj) {
        switch (method) {
            case POST_GET_TVERIFY_CODE_JSON:
                //String tel = ev_num.getText().toString();
                break;
            case POST_MOBILE_LOGIN_JSON:
                UserBean userBean = new UserBean();
                userBean.setUserNum(ev_num.getText().toString());
                mSession.setUserBean(userBean);

                PropertyBean property = mSession.getProperty();
                if(property!=null) {
                    property.setUploadPro(true);
                    mSession.setProperty(property);
                }

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

