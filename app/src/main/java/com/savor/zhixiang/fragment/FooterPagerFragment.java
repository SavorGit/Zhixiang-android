package com.savor.zhixiang.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.api.utils.AppUtils;
import com.common.api.utils.ShowMessage;
import com.savor.zhixiang.R;
import com.wang.avi.AVLoadingIndicatorView;

/**
 * 首页卡片列表最后一页加载状态
 *@author hezd create on 2017/09/22
 */
public class FooterPagerFragment extends BaseFragment implements View.OnClickListener {
    private OnclickReloadListener mListener;
    private AVLoadingIndicatorView mLoadingView;
    private TextView mHintTv;
    private RelativeLayout mParentLayout;
    private LoadingType currentType = LoadingType.LOADING;

    public enum LoadingType {
        /**正在加载*/
        LOADING,
        /**没有更多数据*/
        LOAD_NO_DATA,
        /**加载失败*/
        LOAD_FAILED,
    }


    public FooterPagerFragment() {
    }

    public static FooterPagerFragment newInstance(OnclickReloadListener listener) {
        FooterPagerFragment footerPagerFragment = new FooterPagerFragment();
        footerPagerFragment.setOnClickReloadListener(listener);
        return footerPagerFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_footer_pager, container, false);
        initViews(parent);
        setViews();
        setListener();
        return parent;
    }

    private void initViews(View parent) {
        mLoadingView = (AVLoadingIndicatorView) parent.findViewById(R.id.av_loading_view);
        mParentLayout = (RelativeLayout) parent.findViewById(R.id.rl_parent);
        mHintTv = (TextView) parent.findViewById(R.id.tv_hint);
    }

    public void setViews() {

    }

    public void setOnClickReloadListener(OnclickReloadListener listener) {
        this.mListener = listener;
    }

    private void setListener() {
        mParentLayout.setOnClickListener(this);
    }

    public void loadNoData() {
        currentType = LoadingType.LOAD_NO_DATA;
        mLoadingView.hide();
        mHintTv.setVisibility(View.VISIBLE);
        mHintTv.setText("您已看完全部内容");
    }

    public LoadingType getCurrentType () {
        return currentType;
    }

   public void loadFailed() {
       currentType = LoadingType.LOAD_FAILED;
       mLoadingView.hide();
       mHintTv.setVisibility(View.VISIBLE);
       mHintTv.setText("网络连接失败，点击重试");
   }

   public void startLoading() {
       currentType = LoadingType.LOADING;
       mLoadingView.show();
       mHintTv.setVisibility(View.GONE);
   }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_parent:
                if(currentType == LoadingType.LOAD_FAILED) {
                    if(!AppUtils.isNetworkAvailable(getContext())) {
                        ShowMessage.showToast(getActivity(),"加载失败");
                    }else {
                        if(mListener!=null) {
                            mListener.onClickReload();
                        }
                    }

                }
                break;
        }
    }

    public interface  OnclickReloadListener {
        void onClickReload();
    }
}
