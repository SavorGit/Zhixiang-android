package com.savor.zhixiang.fragment;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.transition.Fade;
import android.support.transition.Transition;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.common.api.utils.DensityUtil;
import com.savor.zhixiang.R;
import com.savor.zhixiang.activity.CardDetailActivity;
import com.savor.zhixiang.bean.CardDetail;
import com.wang.avi.AVLoadingIndicatorView;

/**
 * 首页列表选项卡
 * Created by hezd on 2017/9/21.
 */

public class CardFragment extends Fragment implements View.OnClickListener {
    public static final float IMAGE_SCALE = 400/630f;
    private CardView mParentView;
    private ImageView mBannerIv;
    private TextView mTitleTv;
    private TextView mDescTv;
    private CardDetail detail;
    private TextView mSourcetv;
    private RelativeLayout mLoadingLayout;
    private AVLoadingIndicatorView mLoadingView;

    public static CardFragment newInstance(CardDetail detail) {
        CardFragment cardFragment = new CardFragment();
        cardFragment.setCardDetail(detail);
        return cardFragment;
    }

    public void setCardDetail(CardDetail detail) {
        this.detail = detail;
    }

    public CardDetail getCardDetail() {
        return detail;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.view_card_item,container,false);
        initViews(parent);

        return parent;
    }

    private void initViews(View parent) {
        mParentView = (CardView) parent.findViewById(R.id.parent);
        mBannerIv = (ImageView) parent.findViewById(R.id.iv_banner);
        mTitleTv = (TextView) parent.findViewById(R.id.tv_title);
        mDescTv = (TextView) parent.findViewById(R.id.tv_desc);
        mSourcetv = (TextView) parent.findViewById(R.id.tv_source);

        mLoadingLayout = (RelativeLayout) parent.findViewById(R.id.rl_loading_layout);
        mLoadingView = (AVLoadingIndicatorView) parent.findViewById(R.id.av_loading_view);

    }

    private void setViews() {
        ViewGroup.LayoutParams layoutParams = mBannerIv.getLayoutParams();
        int screenWidth = DensityUtil.getScreenWidth(getContext());
        int width = screenWidth-DensityUtil.dip2px(getContext(),28)*2;
        int height = (int) (width*IMAGE_SCALE);
        layoutParams.width = width;
        layoutParams.height = height;

        Glide.with(getActivity().getApplicationContext()).
                load(detail.getImgUrl()).asBitmap().dontAnimate().
                placeholder(R.mipmap.ico_default).
                centerCrop().
                into(mBannerIv);

        String title = detail.getTitle();
        String desc = detail.getDesc();
        String sourceName = detail.getSourceName();
        mTitleTv.setText(title);
        mDescTv.setText(desc);
        mSourcetv.setText(sourceName);

        mBannerIv.postDelayed(new Runnable() {
            @Override
            public void run() {
                hideLodingLayout();
            }
        },500);
    }

    private void hideLodingLayout() {
        mLoadingLayout.setVisibility(View.GONE);
        mLoadingView.hide();
    }


    private void setLiteners() {
        mParentView.setOnClickListener(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setViews();
        setLiteners();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.parent:
                Intent intent = new Intent(getContext(), CardDetailActivity.class);
                intent.putExtra("detail",detail);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeScaleUpAnimation(mParentView,mParentView.getWidth()/2,mParentView.getHeight()/2,0,0);
                ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
                break;
        }
    }
}
