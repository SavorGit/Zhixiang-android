package com.savor.zhixiang.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.savor.zhixiang.R;
import com.savor.zhixiang.bean.NextPageBean;
import com.savor.zhixiang.bean.TransitionBean;

/**
 * 过渡页
 * @author hezd
 */
public class TransitionFrament extends Fragment {
    public static final int TYPE_FINISH_TODAY = 1;
    public static final int TYPE_FINISH_HISTORY = 2;
    public static final int TYPE_FINISH_ALL = 3;
    private TextView mTitleTv;
    private TextView mContentTv;
    private TextView mAuthorTv;
    private TextView mPageHintTv;
    private TransitionBean mTransitionBean;
    private LinearLayout mPageHintLayout;
    private LinearLayout mAuthorLayout;

    public TransitionFrament() {
    }

    public static TransitionFrament newInstance(TransitionBean transitionBean) {
        TransitionFrament transitionFrament = new TransitionFrament();
        Bundle bundle = new Bundle();
        bundle.putSerializable("bean",transitionBean);
        transitionFrament.setArguments(bundle);
        return transitionFrament;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View parent = inflater.inflate(R.layout.fragment_transition_frament, container, false);
        initViews(parent);
        return parent;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setViews();
        setListeners();
    }

    private void setListeners() {

    }

    private void setViews() {
        Bundle arguments = getArguments();
        mTransitionBean = (TransitionBean) arguments.getSerializable("bean");

        if(mTransitionBean!=null) {
            int type = mTransitionBean.getType();
            String dailyart = mTransitionBean.getDailyart();
            String dailyauthor = mTransitionBean.getDailyauthor();
            NextPageBean nextPageBean = mTransitionBean.getNextPageBean();
            switch (type) {
                case TYPE_FINISH_TODAY:
                    mTitleTv.setVisibility(View.VISIBLE);
                    mTitleTv.setText("您已读完今日10条知享");
                    mContentTv.setText(dailyart);
                    mAuthorTv.setText(dailyauthor);
                    mPageHintTv.setText("滑动阅读昨日知享");
                    break;
                case TYPE_FINISH_HISTORY:
                    mTitleTv.setVisibility(View.INVISIBLE);
                    mContentTv.setText(dailyart);
                    mAuthorTv.setText(dailyauthor);
                    if(nextPageBean!=null) {
                        mPageHintTv.setText("滑动阅读"+nextPageBean.getMonth()+"月"+nextPageBean.getDay()+"日知享");
                    }else {
                        mPageHintTv.setText("滑动阅读昨日知享");
                    }
                    break;
                case TYPE_FINISH_ALL:
                    mTitleTv.setText("您已读完全部知享");
                    mContentTv.setText(dailyart);
                    mAuthorTv.setText(dailyauthor);
                    mPageHintLayout.setVisibility(View.INVISIBLE);
                    break;
            }
            if(TextUtils.isEmpty(dailyauthor)) {
                mAuthorLayout.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void initViews(View parent) {
        mAuthorLayout = (LinearLayout) parent.findViewById(R.id.ll_author);
        mTitleTv = (TextView) parent.findViewById(R.id.tv_title);
        mContentTv = (TextView) parent.findViewById(R.id.tv_content);
        mAuthorTv = (TextView) parent.findViewById(R.id.tv_author);
        mPageHintTv = (TextView) parent.findViewById(R.id.tv_page_hint);
        mPageHintLayout = (LinearLayout) parent.findViewById(R.id.ll_page_hint);

        // 将字体文件保存在assets/fonts/目录下，在程序中通过如下方式实例化自定义字体：
        Typeface typeFace = Typeface.createFromAsset(getContext().getAssets(),"fonts/QXyingbikai.ttf");
        // 应用字体
        mContentTv.setTypeface(typeFace);
        mAuthorTv.setTypeface(typeFace);
    }


}
