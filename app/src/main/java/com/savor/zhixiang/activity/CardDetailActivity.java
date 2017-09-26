package com.savor.zhixiang.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.common.api.utils.AppUtils;
import com.common.api.utils.DensityUtil;
import com.common.api.widget.pulltorefresh.library.PullToRefreshListView;
import com.savor.zhixiang.R;
import com.savor.zhixiang.adapter.CardDetailListAdapter;
import com.savor.zhixiang.bean.CardDetail;
import com.savor.zhixiang.bean.CardDetailListItem;
import com.savor.zhixiang.bean.CollectResponse;
import com.savor.zhixiang.bean.ShareBean;
import com.savor.zhixiang.core.AppApi;
import com.savor.zhixiang.widget.ShareDialog;

import java.util.List;

public class CardDetailActivity extends BaseActivity implements View.OnClickListener {
    public static final float IMAGE_SCALE = 488/750f;
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
    private CardDetail detail;
    private String dailyid;
    private boolean isCollected;
    private ShareDialog shareDialog;
    private CardDetail.ContentDetailBean cardDetailBean;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_card_detail);
        context = this;
        handleIntent();
        getViews();
        setViews();
        setListeners();
        checkisColleted();
    }

    private void checkisColleted() {
        if(detail!=null) {
            dailyid = detail.getDailyid();
        }
    }

    private void handleIntent() {
        Intent intent = getIntent();
        detail = (CardDetail) intent.getSerializableExtra("detail");
        dailyid = intent.getStringExtra("dailyid");
        AppApi.isCollected(this,dailyid,this);
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
        int screenWidth = DensityUtil.getScreenWidth(this);
        int height = (int) (screenWidth*IMAGE_SCALE);
        ViewGroup.LayoutParams layoutParams = mCardBannerImg.getLayoutParams();
        layoutParams.width = screenWidth;
        layoutParams.height = height;

        mRefreshListView.getRefreshableView().addHeaderView(mHeaderView);

        mAdapter = new CardDetailListAdapter(this);
        mRefreshListView.setAdapter(mAdapter);

        int statusBarHeight = getStatusBarHeight();
        if(statusBarHeight!=-1) {
            mTitleLayout.setPadding(0,statusBarHeight*2,0,0);
        }

        if(detail!=null) {
            cardDetailBean = detail.getContentDetail();
            initCardDetail(cardDetailBean);
        }else {
            AppApi.getCardDetail(this,dailyid,this);
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
                onBackPressed();
                break;
            case R.id.ll_collect:
                if(!AppUtils.isFastDoubleClick(1)) {
                    AppApi.addMyCollection(this,dailyid,this);
                }
                break;
            case R.id.ll_share:
                toShare();
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

    private void toShare(){
        ShareBean shareBean = new ShareBean();
        shareBean.setTitle(cardDetailBean.getTitle());
        shareBean.setUrl(cardDetailBean.getShare_url());
        shareDialog = new ShareDialog(context,shareBean,CardDetailActivity.this);
        shareDialog.show();
    }
    @Override
    public void onSuccess(AppApi.Action method, Object obj) {
        switch (method) {
            case POST_ADD_MY_COLLECTION_JSON:
                if(isCollected) {
                    mCollectIv.setImageResource(R.mipmap.ico_uncolect);
                }else {
                    mCollectIv.setImageResource(R.mipmap.ico_collected);
                }
                isCollected=!isCollected;
                break;
            case POST_IS_COLLECTED_JSON:
                if(obj instanceof CollectResponse) {
                    CollectResponse collectResponse = (CollectResponse) obj;
                    String state = collectResponse.getState();
                    if("0".equals(state)) {
                        isCollected = false;
                        mCollectIv.setBackgroundResource(R.mipmap.ico_uncolect);
                    }else if("1".equals(state)) {
                        isCollected = true;
                        mCollectIv.setBackgroundResource(R.mipmap.ico_collected);
                    }
                }
                break;
            case POST_CARD_DETAIL_JSON:
                if(obj instanceof CardDetail.ContentDetailBean) {
                    cardDetailBean = (CardDetail.ContentDetailBean) obj;
                    initCardDetail(cardDetailBean);
                }
                break;
        }
    }

    private void initCardDetail(CardDetail.ContentDetailBean cardDetailBean) {
        if(cardDetailBean!=null) {
            String imgUrl = cardDetailBean.getImgUrl();
            Glide.with(this).load(imgUrl).placeholder(R.mipmap.ico_default).centerCrop().into(mCardBannerImg);

            String title = cardDetailBean.getTitle();
            mTitleTv.setText(title);

            List<CardDetailListItem> details = cardDetailBean.getDetails();
            if(details!=null&&details.size()>0) {
                mAdapter.setData(details);
            }
        }
    }
}
