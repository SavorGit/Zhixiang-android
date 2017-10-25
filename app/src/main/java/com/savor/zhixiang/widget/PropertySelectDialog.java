package com.savor.zhixiang.widget;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.IdRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.common.api.utils.ShowMessage;
import com.savor.zhixiang.R;
import com.savor.zhixiang.bean.PropertyBean;
import com.savor.zhixiang.core.Session;
import com.savor.zhixiang.utils.ActivitiesManager;

/**
 * 资产选择对话框
 * Created by hezd on 2017/9/19.
 */

public class PropertySelectDialog extends Dialog implements View.OnClickListener {

    private OnEnterBtnClickListener onEnterBtnClickListener;
    private final Activity mContext;
    private LayoutInflater mInflater;

    private OnAnimEndListener mOnAnimEndListener;
    private long exitTime;
    private TextView mEnterBtn;
    private RadioGroup mPropertyGroup;

    public PropertySelectDialog(Activity context, OnEnterBtnClickListener listener) {
        super(context, R.style.Dialog_Fullscreen_Share);
        this.mContext = context;
        this.onEnterBtnClickListener = listener;
    }

    public PropertySelectDialog(Activity context, OnAnimEndListener listener, OnEnterBtnClickListener onCloseListener) {
        super(context, R.style.Dialog_Fullscreen_Share);
        this.mContext = context;
        this.mOnAnimEndListener = listener;
        this.onEnterBtnClickListener = onCloseListener;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_property_select);
        setCancelable(false);

        initViews();
        setListeners();

    }

    private void setListeners() {
        mPropertyGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                mEnterBtn.setEnabled(true);
            }
        });

        mEnterBtn.setOnClickListener(this);
    }

    private void initViews() {
        mInflater = LayoutInflater.from(getContext());
        mEnterBtn = (TextView) findViewById(R.id.tv_enter);
        mEnterBtn.setEnabled(false);

        mPropertyGroup = (RadioGroup) findViewById(R.id.rg_property);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            ShowMessage.showToast(mContext,mContext.getString(R.string.confirm_exit_app));
            exitTime = System.currentTimeMillis();
        } else {
            exitApp();
        }
    }

    private void exitApp() {
        ActivitiesManager.getInstance().popAllActivities();
        Process.killProcess(Process.myPid());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_enter:
                int checkedRadioButtonId = mPropertyGroup.getCheckedRadioButtonId();
                PropertyBean propertyBean = new PropertyBean();
                int property = 4;
                if(checkedRadioButtonId == R.id.rb_ten) {
                   property = 1;
                }else if(checkedRadioButtonId == R.id.rb_one) {
                    property = 2;
                }else if(checkedRadioButtonId == R.id.rb_one_mill) {
                    property = 3;
                }else if(checkedRadioButtonId == R.id.rb_none) {
                    property = 4;
                }
                propertyBean.setProperty(property);
                Session session = Session.get(mContext);
                session.setProperty(propertyBean);
                if(onEnterBtnClickListener !=null) {
                    onEnterBtnClickListener.onEnterBtnClick();
                }
                dismiss();
                break;
        }
    }

    public interface OnEnterBtnClickListener {
        void onEnterBtnClick();
    }

    public interface OnAnimEndListener {
        void onAnimEnd();
    }
}
