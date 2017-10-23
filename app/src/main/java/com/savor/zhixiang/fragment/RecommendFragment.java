package com.savor.zhixiang.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.common.api.utils.DensityUtil;
import com.savor.zhixiang.R;

/**
 * 推荐App给好友
 * @author hezd
 */
public class RecommendFragment extends Fragment {
    public static final float IMAGE_SCALE = 400/630f;
    private ImageView mBannerIv;

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
        return parent;
    }

    private void initViews(View parent) {
        mBannerIv = (ImageView) parent.findViewById(R.id.iv_banner);
        ViewGroup.LayoutParams layoutParams = mBannerIv.getLayoutParams();
        int screenWidth = DensityUtil.getScreenWidth(getContext());
        int width = screenWidth-DensityUtil.dip2px(getContext(),28)*2;
        int height = (int) (width*IMAGE_SCALE);
        layoutParams.width = width;
        layoutParams.height = height;
    }

}
