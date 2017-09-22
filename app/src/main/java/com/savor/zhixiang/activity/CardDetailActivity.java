package com.savor.zhixiang.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.api.widget.pulltorefresh.library.PullToRefreshListView;
import com.savor.zhixiang.R;
import com.savor.zhixiang.adapter.CardDetailListAdapter;

public class CardDetailActivity extends BaseActivity implements View.OnClickListener {

    private PullToRefreshListView mRefreshListView;
    private LinearLayout mBackBtn;
    private LinearLayout mCollectBtn;
    private LinearLayout mShareLayout;
    private ImageView mCollectIv;
    private View mHeaderView;
    private ImageView mCardBannerImg;
    private TextView mTitleTv;
    private TextView mSourceTv;
    private TextView mDateTv;
    private CardDetailListAdapter mAdapter;
    private RelativeLayout mTitleLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_card_detail);
        getViews();
        setViews();
        setListeners();
    }

    @Override
    public void getViews() {
        mRefreshListView = (PullToRefreshListView) findViewById(R.id.pts_card_detail);
        mBackBtn = (LinearLayout) findViewById(R.id.ll_back);
        mCollectBtn = (LinearLayout) findViewById(R.id.ll_collect);
        mShareLayout = (LinearLayout) findViewById(R.id.ll_share);
        mCollectIv = (ImageView) findViewById(R.id.iv_collect);

        mTitleLayout = (RelativeLayout) findViewById(R.id.rl_title);

        initHeaderView();
    }

    private void initHeaderView() {
        mHeaderView = View.inflate(this, R.layout.header_view_card_detail,null);
        mCardBannerImg = (ImageView) mHeaderView.findViewById(R.id.iv_card_image);
        mTitleTv = (TextView) mHeaderView.findViewById(R.id.tv_title);
        mSourceTv = (TextView) mHeaderView.findViewById(R.id.tv_source);
        mDateTv = (TextView) mHeaderView.findViewById(R.id.tv_date);
    }

    @Override
    public void setViews() {
        mRefreshListView.getRefreshableView().addHeaderView(mHeaderView);

        mAdapter = new CardDetailListAdapter(this);
        mRefreshListView.setAdapter(mAdapter);

        int statusBarHeight = getStatusBarHeight();
        if(statusBarHeight!=-1) {
            mTitleLayout.setPadding(0,statusBarHeight*2,0,0);
//            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mTitleLayout.getLayoutParams();
//            layoutParams.set(0,statusBarHeight,0,0);
        }
    }

    @Override
    public void setListeners() {
        mBackBtn.setOnClickListener(this);
        mShareLayout.setOnClickListener(this);
        mCollectBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                finish();
                break;
        }
    }

    private int getStatusBarHeight() {
        /**
         * 获取状态栏高度——方法1
         * */
        int statusBarHeight1 = -1;
        //获取status_bar_height资源的ID
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight1 = getResources().getDimensionPixelSize(resourceId);
        }

        return statusBarHeight1;
    }
}
