package com.savor.zhixiang.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.common.api.http.callback.FileDownProgress;
import com.common.api.utils.AppUtils;
import com.common.api.utils.DensityUtil;
import com.common.api.utils.FileUtils;
import com.common.api.utils.LogUtils;
import com.common.api.utils.ShowMessage;
import com.savor.zhixiang.R;
import com.savor.zhixiang.adapter.CardListAdapter;
import com.savor.zhixiang.bean.CardBean;
import com.savor.zhixiang.bean.CardDetail;
import com.savor.zhixiang.bean.ConfigBean;
import com.savor.zhixiang.bean.KeywordsBean;
import com.savor.zhixiang.bean.NextPageBean;
import com.savor.zhixiang.bean.PropertyBean;
import com.savor.zhixiang.bean.ShareUrlBean;
import com.savor.zhixiang.bean.TransitionBean;
import com.savor.zhixiang.bean.UpgradeInfo;
import com.savor.zhixiang.bean.UserBean;
import com.savor.zhixiang.core.ApiRequestListener;
import com.savor.zhixiang.core.AppApi;
import com.savor.zhixiang.core.ResponseErrorMessage;
import com.savor.zhixiang.core.Session;
import com.savor.zhixiang.fragment.CardFragment;
import com.savor.zhixiang.fragment.FooterPagerFragment;
import com.savor.zhixiang.fragment.RecommendFragment;
import com.savor.zhixiang.fragment.TransitionFrament;
import com.savor.zhixiang.receiver.HomeKeyReceiver;
import com.savor.zhixiang.utils.ActivitiesManager;
import com.savor.zhixiang.utils.GlideCircleTransform;
import com.savor.zhixiang.utils.ImageCacheUtils;
import com.savor.zhixiang.utils.RecordUtils;
import com.savor.zhixiang.utils.STIDUtil;
import com.savor.zhixiang.widget.KeywordDialog;
import com.savor.zhixiang.widget.LogoutDialog;
import com.savor.zhixiang.widget.PagingScrollHelper;
import com.savor.zhixiang.widget.PropertySelectDialog;
import com.savor.zhixiang.widget.UpgradeDialog;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.utils.SocializeUtils;
import com.umeng.weixin.handler.UmengWXHandler;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *   1.默认第一次请求直接更新列表，在滑动到还剩3页的时候请求数据，并放入缓存集合
 *   2.当滑动到最后一页加载页显示loading动画延迟0.3秒更新列表
 *   3.另外一种情况是加载失败，在加载页点击重新加载，这时候如果在加载页返回数据直接更新列表并清除下一个分页缓存
 *   4.请求数据返回时，如果当前页不是最后一页时，将下一个分页十条数据放入缓存集合，并执行第2条逻辑
 * @author hezd created on 2017/09/27
 */
public class MainActivity extends AppCompatActivity implements PagingScrollHelper.onPageChangeListener,
        ViewPager.OnPageChangeListener,
        ApiRequestListener,
        FooterPagerFragment.OnclickReloadListener,
        View.OnClickListener{

    private static final int DIALOG_DISMISS = 0x1;
    private static final int KILL_APP = 100;
    private static final long KILL_DELAYED_TIME = 1000 * 60 * 10;
    private static final int REQUEST_CODE_LOGIN = 101;
    private RelativeLayout right;
    private RelativeLayout left;
    private boolean isDrawer;
    private ViewPager mViewPager;
    private List<Fragment> mList = new ArrayList<>();
    private TextView mBottomPageNumTv;
    private TextView mTotalPageNumTv;
    private CardListAdapter mAdapter;
    private List<Fragment> fragmentList = new ArrayList<>();
    private LinearLayout mDateLayout;
    private TextView mDateTv;
    private TextView mMonthTv;
    private TextView mWeekTv;
    private TextView size;
    private RelativeLayout rl_my_collection;
    private RelativeLayout rl_all_list;
    private RelativeLayout mPageNumLayout;
    private RelativeLayout rl_clear_cache;
    private RelativeLayout rl_checkup;
    private FooterPagerFragment mFooterPagerFragment;
    private CardView mLoadingLayout;
    private AVLoadingIndicatorView mLoadingView;
    private TextView mHintTv;
    private List<Fragment> mNextPageFragments = new ArrayList<>();
    private List<CardDetail> mNextPageBeanList = new ArrayList<>();
    private Context context;
    private UpgradeInfo upGradeInfo;
    private Session mSession;
    private UpgradeDialog mUpgradeDialog;
    private NotificationManager manager;
    private Notification notif;
    private final int NOTIFY_DOWNLOAD_FILE=10001;
    private  int msg = 0;
    private CardBean cardBean;
    private DrawerLayout drawer;
    private long exitTime;
    private boolean ismuteUp = false;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case KILL_APP:
                    LogUtils.d("savor:home 后台超过指定时间，killApp");
                    exitApp();
                    break;
                case DIALOG_DISMISS:
                    RecordUtils.onEvent(MainActivity.this,R.string.news_share_key_words_hide);
                    if(mKeywordsDialog!=null&&mKeywordsDialog.isShowing()) {
                        mKeywordsDialog.dismiss();
                    }
                    break;
            }
        }
    };
    private KeywordDialog mKeywordsDialog;
    private int lastValue;
    private HomeKeyReceiver homeKeyReceiver;
    /**最新请求到的keywords*/
    private KeywordsBean currentKeywords;
    /**是否正在请求*/
    private boolean isRequesting;
    private ImageView mHeaderImg;
    private ProgressDialog dialog;
    private PropertySelectDialog mProSelectDialog;
    private TextView mHeaderTv;
    private TextView code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivitiesManager.getInstance().pushActivity(this);
        context = this;
        mSession = Session.get(this);
        handleIntent();
        getViews();
        setViews();
        setListeners();
        checkPropertyStatus();
        checkLoginStatus();
//        checkKeywords();
        getData("");
        registeHomeKeyReceiver();
        upgrade();
    }

    /**
     * 检查登录状态
     */
    private void checkLoginStatus() {
        // 1.如果微信已授权，直接显示登录状态，微信头像和微信名称
        // 2.如果微信未授权，判断是否手机号登录过，如果有显示手机号
        // 3.如果1,2都不满足条件，显示未登录状态(默认就是未登录)
        boolean authorize = UMShareAPI.get(this).isAuthorize(this, SHARE_MEDIA.WEIXIN);
        if(authorize) {
            UMShareAPI.get(this).getPlatformInfo(this,SHARE_MEDIA.WEIXIN,authListener);
        }else {
            UserBean user = mSession.getUserBean();
            if (user != null) {
                String tel = user.getUserNum();
                if (!TextUtils.isEmpty(tel)) {
                 mHeaderTv.setText(tel);
                }
            }
        }
    }

    /**
     * 检查是否需要显示资产
     */
    private void checkPropertyStatus() {
        boolean isPropertyShow = mSession.isPropertyShow();
        if(!isPropertyShow ) {
            if(mProSelectDialog == null) {
                mProSelectDialog = new PropertySelectDialog(this, new PropertySelectDialog.OnEnterBtnClickListener() {
                    @Override
                    public void onEnterBtnClick() {
                        mSession.setPropertyIsShow(true);
                        checkKeywords();
                    }
                });
            }
            mProSelectDialog.show();
        }else {
            checkKeywords();
        }
    }

    private void handleIntent() {
        Intent intent = getIntent();
        currentKeywords = (KeywordsBean) intent.getSerializableExtra("keywords");
    }

    /**
     * 监听按下home键
     */
    private void registeHomeKeyReceiver() {
        homeKeyReceiver = new HomeKeyReceiver();
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        registerReceiver(homeKeyReceiver,intentFilter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("cardBean",cardBean);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        cardBean = (CardBean) savedInstanceState.getSerializable("cardBean");
//        initCardList();
    }

    private void checkKeywords() {
        // 当前请求的关键词和历史保存的关键词做比较
        // 如果没有历史关键词，直接显示关键词并保存到本地
        // 如果有历史关键词，判断如果时间不一致则显示关键词并保存当前请求的关键词
        KeywordsBean lastKeywords = Session.get(this).getKeywords();
        if(currentKeywords!=null) {
            if(lastKeywords == null) {
                List<String> list = currentKeywords.getList();
                if(list!=null&&list.size()>0) {
                    showKeywordDialog(list);
                    mSession.setKeywords(currentKeywords);
                }
            }else {
                String put_time = lastKeywords.getPut_time();
                String currentTime = currentKeywords.getPut_time();
                if(!put_time.equals(currentTime)) {
                    List<String> list = currentKeywords.getList();
                    if(list!=null&&list.size()>0) {
                        showKeywordDialog(list);
                        mSession.setKeywords(currentKeywords);
                    }
                }
            }
        }
    }

    private void getData(String bespeak_time) {
        // 获取分享url
        AppApi.getShareUrl(this,this);

        isRequesting = true;
        LogUtils.d("savor:main bespeak_time="+bespeak_time);
        if(TextUtils.isEmpty(bespeak_time)) {
            mNextPageFragments.clear();
            mNextPageBeanList.clear();
        }

        // 获取卡片列表
        AppApi.getCardList(this,bespeak_time,this);
        AppApi.getdailyconfig(this,this);

    }


    private void getViews() {
        dialog = new ProgressDialog(context);
        initDrawerLayout();

        mViewPager = (ViewPager) findViewById(R.id.rlv_list);
        mBottomPageNumTv = (TextView) findViewById(R.id.bottomPageNumber);
        mTotalPageNumTv = (TextView) findViewById(R.id.pageNumberTotal);

        mDateLayout = (LinearLayout) findViewById(R.id.ll_date);
        mDateTv = (TextView) findViewById(R.id.tv_date);
        mMonthTv = (TextView) findViewById(R.id.tv_month);
        mWeekTv = (TextView) findViewById(R.id.tv_week);

        mPageNumLayout = (RelativeLayout) findViewById(R.id.page_num_layout);
        rl_my_collection = (RelativeLayout) findViewById(R.id.rl_my_collection);
        rl_all_list = (RelativeLayout) findViewById(R.id.rl_all_list);
        rl_clear_cache = (RelativeLayout) findViewById(R.id.rl_clear_cache);
        mLoadingLayout = (CardView) findViewById(R.id.rl_loading_layout);
        mLoadingView = (AVLoadingIndicatorView) findViewById(R.id.av_loading_view);
        mHintTv = (TextView) findViewById(R.id.tv_hint);
        size = (TextView) findViewById(R.id.size);
        mHeaderImg = (ImageView) findViewById(R.id.iv_header);
        mHeaderTv = (TextView) findViewById(R.id.tv_header);
        code = (TextView) findViewById(R.id.code);
        rl_checkup = (RelativeLayout) findViewById(R.id.rl_checkup);
    }

    private void setViews() {
        mFooterPagerFragment = FooterPagerFragment.newInstance(this);

        mBottomPageNumTv.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/ACaslonPro-Italic.otf"));
        mTotalPageNumTv.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/ACaslonPro-Regular.otf"));

        mAdapter = new CardListAdapter(getSupportFragmentManager(),mList);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(5);
        mViewPager.setPageMargin(DensityUtil.dpToPx(this,16));
        size.setText(ImageCacheUtils.getCacheSize());
        code.setText("V"+mSession.getVersionName());
       // mSession.getUserBean().getUserNum();
//        UserBean user = mSession.getUserBean();
//        if (user != null) {
//            String tel = user.getUserNum();
//            if (!TextUtils.isEmpty(tel)) {
//                mHeaderTv.setText(tel);
//            }
//        }



    }

    private void setListeners() {
        mHeaderImg.setOnClickListener(this);
        mViewPager.addOnPageChangeListener(this);
        rl_my_collection.setOnClickListener(this);
        rl_all_list.setOnClickListener(this);
        rl_clear_cache.setOnClickListener(this);
        rl_checkup.setOnClickListener(this);
        drawer.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                    return true;
                }
                return false;
            }
        });
    }

    private void showKeywordDialog(List<String> keywords) {
        RecordUtils.onEvent(this,R.string.news_share_key_words_show);
        mKeywordsDialog = new KeywordDialog(this, keywords, new KeywordDialog.OnAnimEndListener() {
            @Override
            public void onAnimEnd() {
//                mHandler.sendEmptyMessageDelayed(DIALOG_DISMISS,2000);
            }
        }, new KeywordDialog.OnCloseBtnClickListener() {
            @Override
            public void onCloseBtnClick() {
//                mHandler.removeMessages(DIALOG_DISMISS);
//                mHandler.removeCallbacksAndMessages(null);
            }
        });
        mKeywordsDialog.show();

    }

    private void initDrawerLayout() {

        final LinearLayout menuBtn = (LinearLayout) findViewById(R.id.ll_back);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        right = (RelativeLayout) findViewById(R.id.right);
        left = (RelativeLayout) findViewById(R.id.nav_view);

        right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(isDrawer){
                    return left.dispatchTouchEvent(motionEvent);
                }else{
                    return false;
                }
            }
        });
        drawer.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                isDrawer=true;
                //获取屏幕的宽高
                right.scrollTo(left.getRight()*-1 ,0);
                //控制是否隐藏两边相邻内容
                mViewPager.setClipChildren(slideOffset>0.4);
                checkLoginStatus();
            }
            @Override
            public void onDrawerOpened(View drawerView) {
                RecordUtils.onEvent(MainActivity.this,R.string.news_share_menu);
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                RecordUtils.onEvent(MainActivity.this,R.string.news_share_menu_finish);
                isDrawer=false;
                mViewPager.setClipChildren(false);
            }
            @Override
            public void onDrawerStateChanged(int newState) {}
        });

        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecordUtils.onEvent(MainActivity.this,getString(R.string.news_share_home_menu));
                if(!drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });

        drawer.setScrimColor(Color.TRANSPARENT);
        drawer.setDrawerElevation(0);

        drawer.setDrawerShadow(R.color.transparent, GravityCompat.START);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public void onPageChange(int index) {
        RecordUtils.onEvent(this,R.string.news_share_home_card_slide);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        final Fragment frag = fragmentList.get(position);
        // 如果当前不是第一页数据
        if(mAdapter.getCount()>=12) {
            // 获取当前页日期时间并更新右上角日期
            int index = position;
            if(frag instanceof FooterPagerFragment) {
                index = position-3;
                mPageNumLayout.setVisibility(View.INVISIBLE);
                mDateLayout.setVisibility(View.INVISIBLE);
            }else if(frag instanceof TransitionFrament){
                index = position-2;
                mPageNumLayout.setVisibility(View.INVISIBLE);
                mDateLayout.setVisibility(View.VISIBLE);
            }else if(frag instanceof RecommendFragment){
                index = position-1;
                mPageNumLayout.setVisibility(View.INVISIBLE);
                mDateLayout.setVisibility(View.VISIBLE);
            }else {
                mPageNumLayout.setVisibility(View.VISIBLE);
                mDateLayout.setVisibility(View.VISIBLE);
            }

            CardFragment fragment = (CardFragment) fragmentList.get(index);
            if(fragment!=null) {
                final CardDetail cardDetail = fragment.getCardDetail();
                if(cardDetail!=null) {
                    initDate(cardDetail,frag instanceof CardFragment,frag instanceof CardFragment||frag instanceof TransitionFrament||frag instanceof RecommendFragment);
                }
                // 更新底部页码
                int result = 1;
                if(position>11) {
                    result = ((position-12)%11)+1;
                }else {
                    result = position%12+1;
                }
                mBottomPageNumTv.setText(String.valueOf(result)+" ");

                // 如果当前滑动到还剩3页的时候，预加载请求下一页数据
                if(position==mAdapter.getCount()-4) {
                    String bespeak_time = cardDetail.getContentDetail().getBespeak_time();
                    if(!isRequesting) {
                        LogUtils.d("savor:main onPageSelected 非请求状态，发起请求。");
                        getData(bespeak_time);
                    }else {
                        LogUtils.d("savor:main onPageSelected 当前正在请求数据。");
                    }
                }
            }

            // 如果滑动到最后一页加载页，判断如果有预加载的数据则，显示loading动画延迟0.3秒更新列表
            if(position==mAdapter.getCount()-1) {
                if(mNextPageFragments!=null&&mNextPageFragments.size()>0&&mNextPageBeanList!=null&&mNextPageBeanList.size()>0) {
                    mViewPager.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(mNextPageFragments!=null&&mNextPageFragments.size()==11) {
                                mViewPager.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        fragmentList.remove(mFooterPagerFragment);
                                        fragmentList.addAll(mNextPageFragments);
                                        mAdapter.setData(fragmentList);
                                        if(cardBean!=null&&cardBean.getNextpage()!=null&&cardBean.getNextpage().getNext()==1) {
                                            fragmentList.add(mFooterPagerFragment);
                                            mAdapter.setData(fragmentList);
                                        }
                                        CardFragment cFrag = (CardFragment) mNextPageFragments.get(0);
                                        CardDetail detail = cFrag.getCardDetail();


                                        showDateLayoutAlphaAnim(detail);

                                        mNextPageBeanList.clear();
                                        mNextPageFragments.clear();
                                    }
                                });

                            }
                        }
                    },300);

                }
            }
        }

    }

    private void showDateLayoutAlphaAnim(final CardDetail detail) {

        PropertyValuesHolder alphaHolder = PropertyValuesHolder.ofFloat("alpha",0f,1f);
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(mDateLayout, alphaHolder).
                setDuration(500);
        objectAnimator.start();
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                initDate(detail,true,true);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void initDate(CardDetail cardDetail,boolean isShowNumLayout,boolean isShowDateLayout) {
        String day = cardDetail.getDay();
        String month = cardDetail.getMonth();
        String week = cardDetail.getWeek();
        if(isShowNumLayout) {
            mPageNumLayout.setVisibility(View.VISIBLE);
        }else {
            mPageNumLayout.setVisibility(View.INVISIBLE);
        }
        if(isShowDateLayout) {
            mDateLayout.setVisibility(View.VISIBLE);
        }else {
            mDateLayout.setVisibility(View.INVISIBLE);
        }
        mBottomPageNumTv.setText("1 ");
        mDateTv.setText(day);
        mMonthTv.setText(month);
        mWeekTv.setText(week);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onSuccess(AppApi.Action method, Object obj) {
        switch (method) {
            case POST_GET_SHARE_URL_JSON:
                if(obj instanceof ShareUrlBean) {
                    ShareUrlBean shareUrlBean = (ShareUrlBean) obj;
                    mSession.setShareUrl(shareUrlBean);
                }
                break;
            case POST_GET_CARDLIST_JSON:
                if(obj instanceof CardBean) {
                    mLoadingLayout.setOnClickListener(null);
                    cardBean = (CardBean) obj;
                    initCardList();
                }
                isRequesting = false;
                break;
            case POST_VERSION_JSON:
                if (obj instanceof UpgradeInfo) {
                    upGradeInfo = (UpgradeInfo) obj;
                    if (upGradeInfo != null) {
                        setUpGrade();
                    }
                }
                break;
            case TEST_DOWNLOAD_JSON:
                if (obj instanceof FileDownProgress){
                    FileDownProgress fs = (FileDownProgress) obj;
                    long now = fs.getNow();
                    long total = fs.getTotal();
                    if ((int)(((float)now / (float)total)* 100)-msg>=5) {
                        msg = (int) (((float)now / (float)total)* 100);
                        notif.contentView.setTextViewText(R.id.content_view_text1,
                                (Integer) msg + "%");
                        notif.contentView.setProgressBar(R.id.content_view_progress,
                                100, (Integer) msg, false);
                        manager.notify(NOTIFY_DOWNLOAD_FILE, notif);
                    }

                }else if (obj instanceof File) {
                    mSession.setApkDownloading(false);
                    File f = (File) obj;
                    byte[] fRead;
                    String md5Value=null;
                    try {
                        fRead = FileUtils.readFileToByteArray(f);
                        md5Value= AppUtils.getMD5(fRead);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    //比较本地文件版本是否与服务器文件一致，如果一致则启动安装
                    if (md5Value!=null&&md5Value.equals(upGradeInfo.getMd5())){
                        if (manager!=null){
                            manager.cancel(NOTIFY_DOWNLOAD_FILE);
                        }
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.setDataAndType(Uri.parse("file://" + f.getAbsolutePath()),
                                "application/vnd.android.package-archive");
                        startActivity(i);
                    }else {
                        if (manager!=null){
                            manager.cancel(NOTIFY_DOWNLOAD_FILE);
                        }
                        ShowMessage.showToast(context,"下载失败");
                        setUpGrade();
                    }

                }
                break;
            case POST_GET_DAILY_CONFIG_JSON:
                if (obj instanceof ConfigBean) {
                    ConfigBean Info = (ConfigBean) obj;
                    if (Info != null) {
                        String state = Info.getState();
                        if ("1".equals(state)) {
                            rl_clear_cache.setVisibility(View.VISIBLE);
                        }else {
                            rl_clear_cache.setVisibility(View.GONE);
                        }
                    }
                }
                break;

        }
    }

    private void initCardList() {
        if(cardBean==null)
            return;
        String day = cardBean.getDay();
        String month = cardBean.getMonth();
        String week = cardBean.getWeek();
        NextPageBean nextpage = cardBean.getNextpage();

        List<CardDetail> list = cardBean.getList();
        List<Fragment> fragments = new ArrayList<>();
        // 如果列表有数据
        if(list !=null&& list.size()>0) {
            // 隐藏全局加载布局
            mLoadingLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mLoadingLayout.setVisibility(View.GONE);
                    mDateLayout.setVisibility(View.VISIBLE);
                    mPageNumLayout.setVisibility(View.VISIBLE);
                }
            },200);
            // 重新组织卡片数据，将日期时间添加进去，当切换到某个页面时，更新日期时间
            for(CardDetail detail : list) {
                detail.setDay(day);
                detail.setWeek(week);
                detail.setMonth(month);
                fragments.add(CardFragment.newInstance(detail));
            }

            // 添加过渡页
            TransitionBean transitionBean = new TransitionBean();
            if(fragmentList.size() == 0) {
                String is_same_day = cardBean.getIs_same_day();
                if("1".equals(is_same_day)) {
                    transitionBean.setType(TransitionFrament.TYPE_FINISH_TODAY);
                }else {
                    transitionBean.setType(TransitionFrament.TYPE_FINISH_HISTORY);
                }

                fragments.add(RecommendFragment.newInstance());
            }else if(nextpage!=null) {
                if(nextpage.getNext() == 1) {
                    transitionBean.setType(TransitionFrament.TYPE_FINISH_HISTORY);
                }else if(nextpage.getNext() == 0) {
                    transitionBean.setType(TransitionFrament.TYPE_FINISH_ALL);
                }
            }
            transitionBean.setDailyart(cardBean.getDailyart());
            transitionBean.setDailyauthor(cardBean.getDailyauthor());
            transitionBean.setNextPageBean(nextpage);
            fragments.add(TransitionFrament.newInstance(transitionBean));

            //  默认第一次请求直接更新列表，在滑动到还剩3页的时候请求数据，并放入缓存集合，当滑动到最后一页在更新列表请
            // 另外一种情况是加载失败，在加载页点击重新加载，这时候如果在加载页返回数据直接更新列表
            // 请求数据返回时，如果当前页不是最后一页时，更新下一个分页十条数据放入缓存集合
            // 如果是最后加载页，立即更新页面数据并清除下一个分页缓存
            if(mViewPager.getCurrentItem()!=mAdapter.getCount()-1) {
                mNextPageFragments.clear();
                mNextPageBeanList.clear();
                mNextPageBeanList.addAll(list);
                mNextPageFragments.addAll(fragments);
            }else {
                fragmentList.remove(mFooterPagerFragment);
                fragmentList.addAll(fragments);
                mAdapter.setData(fragmentList);
                // 有下一页数据的时候才添加加载页
                if(nextpage!=null && nextpage.getNext() ==1) {
                    fragmentList.add(mFooterPagerFragment);
                    mAdapter.setData(fragmentList);
                }
                mNextPageFragments.clear();
                mNextPageBeanList.clear();
                mPageNumLayout.setVisibility(View.VISIBLE);
                mBottomPageNumTv.setText("1");
                mDateLayout.setVisibility(View.VISIBLE);
                initDate(list.get(0),true,true);
            }

            // 如果是第一次请求数据，这时更新列表数据不会执行onpageselected所以会导致页码不显示
            // 所以判断如果是第一次加载数据更新页面，并直接更新列表
            // 以后第二次更新数据，是在滑动到还剩3页的时候请求数据，并放入缓存集合，当滑动到最后一页在更新列表
            if(fragmentList.size()==0) {
                initDate(list.get(0),true,true);
            }

            if(fragmentList.size()==0) {
                fragmentList.addAll(fragments);
                fragmentList.add(mFooterPagerFragment);
                mAdapter.setData(fragmentList);
                mNextPageFragments.clear();
                mNextPageBeanList.clear();
            }


        }else {
            // 如果是第一次并且没有数据，更新加载布局提示没有数据
            if(fragmentList.size()==0) {
                mLoadingLayout.setVisibility(View.VISIBLE);
                mLoadingView.hide();
                mHintTv.setVisibility(View.VISIBLE);
                mHintTv.setText("没有数据");
            }else {
                mFooterPagerFragment.loadNoData();
            }
        }
    }

    @Override
    public void onError(AppApi.Action method, Object obj) {
        switch (method) {
            case POST_GET_CARDLIST_JSON:
                if(mAdapter.getCount()<11) {
                    ShowMessage.showToast(this,"加载失败");
                    mHintTv.setVisibility(View.VISIBLE);
                    mHintTv.setText("网络连接失败，点击重试");
                    mLoadingView.hide();
                    mLoadingLayout.setOnClickListener(this);
                }else if(fragmentList.contains(mFooterPagerFragment)) {
                    mFooterPagerFragment.loadFailed();
                }

                mNextPageBeanList.clear();
                mNextPageFragments.clear();
                isRequesting = false;
                break;
            case POST_VERSION_JSON:
                if(obj instanceof ResponseErrorMessage) {
                    ResponseErrorMessage message = (ResponseErrorMessage) obj;
                    int code = message.getCode();
                    String msg = message.getMessage();
                    if (ismuteUp){
                        if (!TextUtils.isEmpty(msg)){
                            if (!TextUtils.isEmpty(msg)){
                                ShowMessage.showToast(MainActivity.this,msg);
                            }

                        }
                    }
                }
                break;
            case POST_GET_DAILY_CONFIG_JSON:
                rl_clear_cache.setVisibility(View.GONE);
                break;


        }
    }

    @Override
    public void onNetworkFailed(AppApi.Action method) {

    }

    @Override
    public void onClickReload() {
        if(AppUtils.isFastDoubleClick(1)) {
            LogUtils.d("savor:main 点击太快");
            return;
        }

        String btime = "";
        if(fragmentList.size()>0) {
            CardFragment fragment;
            if(mAdapter.getCount()>13) {
                fragment  = (CardFragment) fragmentList.get(fragmentList.size() - 3);
            }else {
                fragment = (CardFragment) fragmentList.get(fragmentList.size()-4);
            }
            CardDetail cardDetail = fragment.getCardDetail();
            if(cardDetail!=null) {
                CardDetail.ContentDetailBean contentDetail = cardDetail.getContentDetail();
                if(contentDetail!=null) {
                    btime =contentDetail.getBespeak_time();
                }
            }
        }
        mFooterPagerFragment.startLoading();
        final String finalBtime = btime;
        mViewPager.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!isRequesting) {
                    LogUtils.d("savor:main 非请求状态，发起请求。");
                    getData(finalBtime);
                }else {
                    LogUtils.d("savor:main 正在请求数据不做任何处理");
                }
            }
        },1000);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_header:
                // 1.判断是否是登录状态，微信授权或本地已存储手机号。
                // 2.如果未登录,跳转到登录页面
                boolean authorize = UMShareAPI.get(this).isAuthorize(this, SHARE_MEDIA.WEIXIN);
                UserBean userBean = mSession.getUserBean();
                if(authorize||userBean!=null) {
                    new LogoutDialog(this, "退出登录？", new LogoutDialog.OnConfirmListener() {
                        @Override
                        public void onConfirm() {
                            mHeaderImg.setImageResource(R.mipmap.ico_head);
                            mHeaderTv.setText("未登录");
                            mSession.setUserBean(null);
                            UMShareAPI.get(MainActivity.this).deleteOauth(MainActivity.this, SHARE_MEDIA.WEIXIN, null);
                        }
                    }, new LogoutDialog.OnCancelListener() {
                        @Override
                        public void onCancel() {

                        }
                    }).show();
                }else {
                    Intent intent = new Intent(this,LoginActivity.class);
                    startActivityForResult(intent,REQUEST_CODE_LOGIN);
                }
                break;
            case R.id.rl_loading_layout:
                mLoadingView.show();
                mHintTv.setVisibility(View.GONE);
                if(!isRequesting) {
                    getData("");
                }
                break;
            case R.id.rl_my_collection:
                RecordUtils.onEvent(this,R.string.news_share_menu_collect);
                Intent intent1 = new Intent();
                intent1.setClass(MainActivity.this,MyCollectActivity.class);
                startActivity(intent1);

                break;
            case R.id.rl_checkup:
                ismuteUp = true;
                upgrade();
                break;
            case R.id.rl_all_list:
                RecordUtils.onEvent(this,R.string.news_share_menu_all);
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,AllListActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_clear_cache:
                ImageCacheUtils.clearImageAllCache();
                size.setText("0.0MB");
                ShowMessage.showToast(context,"清除缓存成功");
                break;


            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        if(resultCode == LoginActivity.RESULT_CODE_LOGIN) {
            checkLoginStatus();
        }
    }

    private void upgrade(){
        AppApi.Upgrade(context,this);
    }

    private void setUpGrade(){
        String upgradeUrl = upGradeInfo.getOss_addr();
        //String upgradeUrl = "http://a5.pc6.com/pc6_soure/2016-2/com.huiche360.huiche_8.apk";

        if (!TextUtils.isEmpty(upgradeUrl)) {
            if (STIDUtil.needUpdate(mSession, upGradeInfo)) {
//                HashMap<String,String> hashMap = new HashMap<>();
//                hashMap.put(getString(R.string.home_update),"ensure");
//                RecordUtils.onEvent(this,getString(R.string.home_update),hashMap);
                String[] content = upGradeInfo.getRemark();
                if (upGradeInfo.getUpdate_type() == 1) {
                    mUpgradeDialog = new UpgradeDialog(
                            context,
                            TextUtils.isEmpty(upGradeInfo.getVersion_code()+"")?"":"新版本：V"+upGradeInfo.getVersion_code(),
                            content,
                            "确定",
                            forUpdateListener
                    );
                    mUpgradeDialog.show();
                }else {
                    mUpgradeDialog = new UpgradeDialog(
                            context,
                            TextUtils.isEmpty(upGradeInfo.getVersion_code()+"")?"":"新版本：V"+upGradeInfo.getVersion_code(),
                            content,
                            "取消",
                            "确定",
                            cancelListener,
                            forUpdateListener
                    );
                    mUpgradeDialog.show();
                }


            }else{
                if (ismuteUp){
                     ShowMessage.showToast(MainActivity.this,"当前为最新版本");

                }

            }
        }else {
            if (ismuteUp){
                ShowMessage.showToast(MainActivity.this,"当前为最新版本");

            }
        }


    }

    private View.OnClickListener forUpdateListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mUpgradeDialog.dismiss();
            downLoadApk(upGradeInfo.getOss_addr());
            // downLoadApk("http://download.savorx.cn/app-xiaomi-debug.apk");
        }
    };

    private View.OnClickListener cancelListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mUpgradeDialog.dismiss();
        }
    };
    protected void downLoadApk(String apkUrl) {
        if (!mSession.isApkDownloading()){
            mSession.setApkDownloading(true);
            // 下载apk包
            initNotification();
            AppApi.downApp(context,apkUrl,MainActivity.this);
        }else{
            ShowMessage.showToast(context,"下载中,请稍候");
        }
    }
    /**
     * 初始化Notification
     */
    private void initNotification() {
        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notif = new Notification();
        notif.icon = R.mipmap.ic_launcher;
        notif.tickerText = "下载通知";
        // 通知栏显示所用到的布局文件
        notif.contentView = new RemoteViews(context.getPackageName(),
                R.layout.download_content_view);
        notif.contentIntent = PendingIntent.getBroadcast(context, 0, new Intent(
                context.getPackageName()+".debug"), PendingIntent.FLAG_CANCEL_CURRENT);
        // notif.defaults = Notification.DEFAULT_ALL;
        manager.notify(NOTIFY_DOWNLOAD_FILE, notif);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            }else {
                if ((System.currentTimeMillis() - exitTime) > 2000) {
                    ShowMessage.showToast(this,getString(R.string.confirm_exit_app));
                    exitTime = System.currentTimeMillis();
                } else {
                    exitApp();
                }
            }
        }
        return true;
    }

    private void exitApp() {
        ActivitiesManager.getInstance().popAllActivities();
        Process.killProcess(Process.myPid());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivitiesManager.getInstance().popActivity(this);
        if(homeKeyReceiver!=null) {
            unregisterReceiver(homeKeyReceiver);
        }
        UMShareAPI.get(this).release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        RecordUtils.onPageStartAndResume(this,this);
        RecordUtils.onPageStart(this,getString(R.string.news_share_home_start));
        ismuteUp = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        RecordUtils.onPageEndAndPause(this,this);
        RecordUtils.onPageEnd(this,getString(R.string.news_share_home_start));
        ismuteUp = false;
    }

    public void killAppDelyed() {
        LogUtils.d("savor:home 延迟指定时间killApp");
        mHandler.sendEmptyMessageDelayed(KILL_APP,KILL_DELAYED_TIME);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtils.d("savor:home 回到前台取消杀死app任务");
        mHandler.removeMessages(KILL_APP);
        mHandler.removeCallbacksAndMessages(null);
        ismuteUp = false;
        size.setText(ImageCacheUtils.getCacheSize());
        checkLoginStatus();
    }

    UMAuthListener authListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
//            SocializeUtils.safeShowDialog(dialog);
        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
//            SocializeUtils.safeCloseDialog(dialog);
            LogUtils.d("savor:wx openid="+data.get("openid"));
            LogUtils.d("savor:wx iconurl="+data.get("iconurl"));
            String iconurl = data.get("iconurl");
            String name = data.get("name");
            if(!TextUtils.isEmpty(iconurl)) {
                Glide.with(MainActivity.this).load(iconurl).centerCrop().transform(new GlideCircleTransform(MainActivity.this)).into(mHeaderImg);
            }
            if(!TextUtils.isEmpty(name)) {
                mHeaderTv.setText(name);
            }
        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
//            SocializeUtils.safeCloseDialog(dialog);
//            Toast.makeText(mContext, "失败：" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
//            SocializeUtils.safeCloseDialog(dialog);
//            Toast.makeText(mContext, "取消了", Toast.LENGTH_LONG).show();
        }
    };
}
