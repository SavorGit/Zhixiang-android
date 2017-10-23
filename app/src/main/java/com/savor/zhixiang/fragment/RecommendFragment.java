package com.savor.zhixiang.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.api.utils.DensityUtil;
import com.savor.zhixiang.R;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * 推荐App给好友
 * @author hezd
 */
public class RecommendFragment extends BaseFragment implements View.OnClickListener {
    public static final float IMAGE_SCALE = 400/630f;
    private ImageView mBannerIv;
    private TextView mWxTv;
    private TextView mCircleTv;

    public RecommendFragment() {
    }

    public static RecommendFragment newInstance() {
        RecommendFragment fragment = new RecommendFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View parent = inflater.inflate(R.layout.fragment_recommend, container, false);
        initViews(parent);
        setListeners();
        return parent;
    }

    public void setListeners() {
        mWxTv.setOnClickListener(this);
    }

    private void initViews(View parent) {
        mBannerIv = (ImageView) parent.findViewById(R.id.iv_banner);
        ViewGroup.LayoutParams layoutParams = mBannerIv.getLayoutParams();
        int screenWidth = DensityUtil.getScreenWidth(getContext());
        int width = screenWidth-DensityUtil.dip2px(getContext(),28)*2;
        int height = (int) (width*IMAGE_SCALE);
        layoutParams.width = width;
        layoutParams.height = height;

        mWxTv = (TextView) parent.findViewById(R.id.tv_share_wx);
        mCircleTv = (TextView) parent.findViewById(R.id.tv_circle);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_share_wx:
                boolean authorize = UMShareAPI.get(getContext()).isAuthorize(getActivity(), SHARE_MEDIA.WEIXIN);
                if(authorize) {

                }
                break;
        }
    }
}
