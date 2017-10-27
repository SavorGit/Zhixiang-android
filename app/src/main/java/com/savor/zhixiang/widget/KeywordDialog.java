package com.savor.zhixiang.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.savor.zhixiang.R;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

/**
 * 关键词对话框
 * Created by hezd on 2017/9/19.
 */

public class KeywordDialog extends Dialog implements View.OnClickListener {

    private OnCloseBtnClickListener onCloseBtnClickListener;
    private final Activity mContext;
    private final List<String> mKeywords;
    private TextView mPercentTv;
    private TagFlowLayout mFlowLayout;
    private LayoutInflater mInflater;
    private TextView mCloseBtn;

    private OnAnimEndListener mOnAnimEndListener;

    public KeywordDialog(Activity context, List<String> keywords,OnCloseBtnClickListener listener) {
        super(context, R.style.Dialog_Fullscreen);
        this.mContext = context;
        this.mKeywords = keywords;
        this.onCloseBtnClickListener = listener;
    }

    public KeywordDialog(Activity context, List<String> keywords,OnAnimEndListener listener,OnCloseBtnClickListener onCloseListener) {
        super(context, R.style.Dialog_Fullscreen);
        this.mContext = context;
        this.mKeywords = keywords;
        this.mOnAnimEndListener = listener;
        this.onCloseBtnClickListener = onCloseListener;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_keyword);
        setCancelable(false);

        initViews();
        setListeners();

        initKeywords();
    }

    private void setListeners() {
        mCloseBtn.setOnClickListener(this);
    }

    private void initViews() {
        mInflater = LayoutInflater.from(getContext());

        mFlowLayout = (TagFlowLayout) findViewById(R.id.fl_keyword);

        mCloseBtn = (TextView) findViewById(R.id.tv_close);
    }

    private void initKeywords() {
        mFlowLayout.setAdapter(new TagAdapter<String>(mKeywords)
        {

            @Override
            public View getView(FlowLayout parent, int position, String s)
            {
                TextView tv = (TextView)mInflater.inflate(R.layout.item_keyword,
                        mFlowLayout, false);
                tv.setText(s);
                return tv;
            }
        });

        mFlowLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mFlowLayout.startAnimation(0, new FlowLayout.OnAnimEndListener() {
                    @Override
                    public void onAnimEnd() {
                        mCloseBtn.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mCloseBtn.setVisibility(View.VISIBLE);
                                PropertyValuesHolder alphaHolder = PropertyValuesHolder.ofFloat("alpha",0f,1f);
                                ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(mCloseBtn, alphaHolder).
                                        setDuration(500);
                                objectAnimator.start();
                                objectAnimator.addListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        if(mOnAnimEndListener!=null) {
                                            mOnAnimEndListener.onAnimEnd();
                                        }
                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animation) {

                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animation) {

                                    }
                                });
                            }
                        },500);

                    }
                });
            }
        },500);
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
                if(onCloseBtnClickListener!=null) {
                    onCloseBtnClickListener.onCloseBtnClick();
                }
                dismiss();
                break;
        }
    }

    public interface OnCloseBtnClickListener {
        void onCloseBtnClick();
    }

    public interface OnAnimEndListener {
        void onAnimEnd();
    }
}
