package com.savor.zhixiang.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.common.api.utils.AppUtils;
import com.common.api.utils.DensityUtil;
import com.common.api.utils.LogUtils;
import com.common.api.utils.ShowMessage;
import com.savor.zhixiang.R;
import com.savor.zhixiang.adapter.CardDetailListAdapter;
import com.savor.zhixiang.bean.CardDetail;
import com.savor.zhixiang.bean.CardDetailListItem;
import com.savor.zhixiang.bean.CollectResponse;
import com.savor.zhixiang.bean.ShareBean;
import com.savor.zhixiang.core.ApiRequestListener;
import com.savor.zhixiang.core.AppApi;
import com.savor.zhixiang.utils.ActivitiesManager;
import com.savor.zhixiang.utils.RecordUtils;
import com.savor.zhixiang.widget.ShareDialog;
import com.umeng.socialize.UMShareAPI;

import java.util.List;

public class CardDetailActivity extends AppCompatActivity implements View.OnClickListener, ApiRequestListener {
    public static final float IMAGE_SCALE = 488 / 750f;
    private ListView mRefreshListView;
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
    private RelativeLayout mParentLayout;
    private ShareDialog shareDialog;
    private ShareBean shareBean;
    private CardDetail.ContentDetailBean cardDetailBean;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_card_detail);
        context = this;
        ActivitiesManager.getInstance().pushActivity(this);
        handleIntent();
        getViews();
        setViews();
        setListeners();
        checkisColleted();
    }

    private void checkisColleted() {
        if (detail != null) {
            dailyid = detail.getDailyid();
        }
    }

    private void handleIntent() {
        Intent intent = getIntent();
        detail = (CardDetail) intent.getSerializableExtra("detail");
        dailyid = intent.getStringExtra("dailyid");
        if(detail!=null) {
            dailyid = detail.getDailyid();
        }
        AppApi.isCollected(this, dailyid, this);
    }

    public void getViews() {
        mRefreshListView = (ListView) findViewById(R.id.pts_card_detail);
        mBackBtn = (LinearLayout) findViewById(R.id.ll_back);
        mCollectBtn = (LinearLayout) findViewById(R.id.ll_collect);
        mShareLayout = (LinearLayout) findViewById(R.id.ll_share);
        mCollectIv = (ImageView) findViewById(R.id.iv_collect);

        mTitleLayout = (RelativeLayout) findViewById(R.id.rl_title);

        mParentLayout = (RelativeLayout) findViewById(R.id.rl_parent);

        initHeaderView();
        initFooterView();
    }

    private void initFooterView() {
        View footerView = View.inflate(this,R.layout.footer_view_card_detail,null);
        mRefreshListView.addFooterView(footerView);
    }

    private void initHeaderView() {
        mHeaderView = View.inflate(this, R.layout.header_view_card_detail, null);
        mCardBannerImg = (ImageView) mHeaderView.findViewById(R.id.iv_card_image);
        mTitleTv = (TextView) mHeaderView.findViewById(R.id.tv_title);
        mSourceTv = (TextView) mHeaderView.findViewById(R.id.tv_source);
        mDateTv = (TextView) mHeaderView.findViewById(R.id.tv_date);
    }

    public void setViews() {
        mRefreshListView.addHeaderView(mHeaderView);
        int screenWidth = DensityUtil.getScreenWidth(this);
        int height = (int) (screenWidth * IMAGE_SCALE);
        ViewGroup.LayoutParams layoutParams = mCardBannerImg.getLayoutParams();
        layoutParams.width = screenWidth;
        layoutParams.height = height;

        mAdapter = new CardDetailListAdapter(this);
        mRefreshListView.setAdapter(mAdapter);

        int statusBarHeight = getStatusBarHeight();
        if (statusBarHeight != -1) {
            int h = (int) (statusBarHeight*1.5f);
            mTitleLayout.setPadding(0, h , 0, 0);
            mBackBtn.setPadding(DensityUtil.dip2px(this,15),0,DensityUtil.dip2px(this,15),DensityUtil.dip2px(this,15));
        }

        if (detail != null) {
            cardDetailBean = detail.getContentDetail();
            initCardDetail(cardDetailBean);
        } else {
            AppApi.getCardDetail(this, dailyid, this);
        }

    }

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
                if(!AppUtils.isNetworkAvailable(this)) {
                    ShowMessage.showToast(this,"暂无网络，请稍后重试");
                }else {
                    if (!AppUtils.isFastDoubleClick(1)) {
                        AppApi.addMyCollection(this, dailyid, this);
                    }
                }

                break;
            case R.id.ll_share:
                RecordUtils.onEvent(this,R.string.news_share_detail_share);
                toShare();
                break;
        }
    }

    private void toShare(){
        shareBean = new ShareBean();
        shareBean.setTitle(cardDetailBean.getTitle());
        if(detail!= null && !TextUtils.isEmpty(detail.getShare_url())){
            shareBean.setUrl(detail.getShare_url());
            shareBean.setDesc(detail.getDesc());
        }else {
            shareBean.setUrl(cardDetailBean.getShare_url());
            shareBean.setDesc(cardDetailBean.getDesc());
        }

        shareDialog = new ShareDialog(context,shareBean,CardDetailActivity.this);
        shareDialog.show();

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

    @Override
    public void onBackPressed() {
        finishAfterTransition();
        mTitleLayout.setVisibility(View.GONE);
        overridePendingTransition(0,R.anim.anim_scale);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSuccess(AppApi.Action method, Object obj) {
        switch (method) {
            case POST_ADD_MY_COLLECTION_JSON:
                if (isCollected) {
                    mCollectIv.setImageResource(R.mipmap.ico_uncolect);
                    ShowMessage.showToast(this,"取消收藏");
                } else {
                    mCollectIv.setImageResource(R.mipmap.ico_collected);
                    ShowMessage.showToast(this,"收藏成功");
                }
                isCollected = !isCollected;
                break;
            case POST_IS_COLLECTED_JSON:
                if (obj instanceof CollectResponse) {
                    CollectResponse collectResponse = (CollectResponse) obj;
                    String state = collectResponse.getState();
                    if ("0".equals(state)) {
                        isCollected = false;
                        mCollectIv.setImageResource(R.mipmap.ico_uncolect);
                    } else if ("1".equals(state)) {
                        isCollected = true;
                        mCollectIv.setImageResource(R.mipmap.ico_collected);
                    }
                }
                break;
            case POST_CARD_DETAIL_JSON:
                if (obj instanceof CardDetail.ContentDetailBean) {
                    cardDetailBean = (CardDetail.ContentDetailBean) obj;
                    if(!isFinishing()) {
                        initCardDetail(cardDetailBean);
                    }
                }
                break;
        }
    }

    @Override
    public void onError(AppApi.Action method, Object obj) {

    }

    @Override
    public void onNetworkFailed(AppApi.Action method) {

    }

    private void initCardDetail(CardDetail.ContentDetailBean cardDetailBean) {
        if (cardDetailBean != null) {
            String imgUrl = cardDetailBean.getImgUrl();

            int screenWidth = DensityUtil.getScreenWidth(this);
            float overriedScale = 400/630f;
            int width = screenWidth-DensityUtil.dip2px(this,28)*2;
            int height = (int) (width*overriedScale);
            // 解决首页卡片点击进入详情时显示默认图，因为如果图片尺寸不同会生成两份不同的缓存图片
            Glide.with(getApplicationContext()).
                    load(imgUrl).centerCrop().
                    placeholder(R.mipmap.ico_default).
                    override(width,height)
                    .into(mCardBannerImg);

            LogUtils.d("savor:image carddetail imageurl--"+imgUrl);
            String title = cardDetailBean.getTitle();
            String sourceName = cardDetailBean.getSourceName();
            String bespeak_time = cardDetailBean.getBespeak_time();
            mTitleTv.setText(title);
            if(!TextUtils.isEmpty(sourceName))
            mSourceTv.setText("选自："+sourceName);
            mDateTv.setText(bespeak_time);


            List<CardDetailListItem> details = cardDetailBean.getDetails();
            if (details != null && details.size() > 0) {
                mAdapter.setData(details);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
        ActivitiesManager.getInstance().popActivity(this);
        if(shareDialog!=null) {
            shareDialog.dismiss();
            shareDialog = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        RecordUtils.onEvent(this,R.string.news_share_detail_open);
        RecordUtils.onEvent(this,R.string.news_share_detail_start);
        RecordUtils.onPageStart(this,getString(R.string.news_share_home_card_show));
        RecordUtils.onPageStartAndResume(this,this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        RecordUtils.onEvent(this,R.string.news_share_detail_end);
        RecordUtils.onPageEnd(this,getString(R.string.news_share_home_card_show));
        RecordUtils.onPageEndAndPause(this,this);
    }
}
