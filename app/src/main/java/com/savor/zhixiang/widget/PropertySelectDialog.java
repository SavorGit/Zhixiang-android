package com.savor.zhixiang.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.savor.zhixiang.R;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

/**
 * 资产选择对话框
 * Created by hezd on 2017/9/19.
 */

public class PropertySelectDialog extends Dialog implements View.OnClickListener {

    private OnEnterBtnClickListener onEnterBtnClickListener;
    private final Activity mContext;
    private LayoutInflater mInflater;

    private OnAnimEndListener mOnAnimEndListener;
    private TextView mTenProTv;
    private TextView mOneProTv;
    private TextView mOneMillProTv;

    public PropertySelectDialog(Activity context, OnEnterBtnClickListener listener) {
        super(context, R.style.Dialog_Fullscreen);
        this.mContext = context;
        this.onEnterBtnClickListener = listener;
    }

    public PropertySelectDialog(Activity context, OnAnimEndListener listener, OnEnterBtnClickListener onCloseListener) {
        super(context, R.style.Dialog_Fullscreen);
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
        mTenProTv.setOnClickListener(this);
        mOneProTv.setOnClickListener(this);
        mOneMillProTv.setOnClickListener(this);
    }

    private void initViews() {
        mInflater = LayoutInflater.from(getContext());
        mTenProTv = (TextView) findViewById(R.id.tv_ten);
        mOneProTv = (TextView) findViewById(R.id.tv_one);
        mOneMillProTv = (TextView) findViewById(R.id.tv_one_mill);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dismiss();
        mContext.finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_close:
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
