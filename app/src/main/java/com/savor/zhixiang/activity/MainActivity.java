package com.savor.zhixiang.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.common.api.http.callback.FileDownProgress;
import com.common.api.utils.AppUtils;
import com.common.api.utils.DensityUtil;
import com.common.api.utils.FileUtils;
import com.common.api.utils.ShowMessage;
import com.savor.zhixiang.R;
import com.savor.zhixiang.adapter.CardListAdapter;
import com.savor.zhixiang.bean.CardBean;
import com.savor.zhixiang.bean.CardDetail;
import com.savor.zhixiang.bean.UpgradeInfo;
import com.savor.zhixiang.core.ApiRequestListener;
import com.savor.zhixiang.core.AppApi;
import com.savor.zhixiang.core.Session;
import com.savor.zhixiang.fragment.CardFragment;
import com.savor.zhixiang.fragment.FooterPagerFragment;
import com.savor.zhixiang.utils.ActivitiesManager;
import com.savor.zhixiang.utils.STIDUtil;
import com.savor.zhixiang.widget.KeywordDialog;
import com.savor.zhixiang.widget.PagingScrollHelper;
import com.savor.zhixiang.widget.UpgradeDialog;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PagingScrollHelper.onPageChangeListener,
        ViewPager.OnPageChangeListener,
        ApiRequestListener,
        FooterPagerFragment.OnclickReloadListener,
        View.OnClickListener{

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
    private RelativeLayout rl_my_collection;
    private RelativeLayout rl_all_list;
    private RelativeLayout mPageNumLayout;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivitiesManager.getInstance().pushActivity(this);
        context = this;
        mSession = Session.get(this);
        getViews();
        setViews();
        setListeners();
        checkKeywords();
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
    }

    private void checkKeywords() {
        List<String> keywords = Session.get(this).getKeywords();
        if(keywords!=null&&keywords.size()>0) {
            showKeywordDialog(keywords);
        }else {
            getData();
        }
    }

    private void getData() {
        AppApi.getCardList(this,"",this);

    }


    private void getViews() {
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
        mLoadingLayout = (CardView) findViewById(R.id.rl_loading_layout);
        mLoadingView = (AVLoadingIndicatorView) findViewById(R.id.av_loading_view);
        mHintTv = (TextView) findViewById(R.id.tv_hint);
    }

    private void setViews() {
        mFooterPagerFragment = FooterPagerFragment.newInstance(this);

        mBottomPageNumTv.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/ACaslonPro-Italic.otf"));
        mTotalPageNumTv.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/ACaslonPro-Regular.otf"));

        mAdapter = new CardListAdapter(getSupportFragmentManager(),mList);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(5);
        mViewPager.setPageMargin(DensityUtil.dpToPx(this,16));
//        fragmentList.add(mFooterPagerFragment);
//        mAdapter.setData(fragmentList);
//        mFooterPagerFragment.startLoading();
    }

    private void setListeners() {
        mViewPager.addOnPageChangeListener(this);
        rl_my_collection.setOnClickListener(this);
        rl_all_list.setOnClickListener(this);
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
        new KeywordDialog(this, keywords, new KeywordDialog.OnCloseBtnClickListener() {
            @Override
            public void onCloseBtnClick() {
                getData();
            }
        }).show();
    }

    private void initDrawerLayout() {

        LinearLayout menuBtn = (LinearLayout) findViewById(R.id.ll_back);

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
                WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
                Display display = manager.getDefaultDisplay();
                //设置右面的布局位置  根据左面菜单的right作为右面布局的left   左面的right+屏幕的宽度（或者right的宽度这里是相等的）为右面布局的right
                right.layout(left.getRight(), 0, left.getRight() + display.getWidth(), display.getHeight());
                //控制是否隐藏两边相邻内容
                mViewPager.setClipChildren(slideOffset>0.4);
            }
            @Override
            public void onDrawerOpened(View drawerView) {
            }
            @Override
            public void onDrawerClosed(View drawerView) {
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
        Toast.makeText(this, "index="+index, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        final Fragment frag = fragmentList.get(position);
        if(mAdapter.getCount()>=11) {
            int index = position;
            if(!(frag instanceof CardFragment)) {
                index = position-1;
                mPageNumLayout.setVisibility(View.INVISIBLE);
            }else {
                mPageNumLayout.setVisibility(View.VISIBLE);
            }

            CardFragment fragment = (CardFragment) fragmentList.get(index);
            final CardDetail cardDetail = fragment.getCardDetail();
            initDate(cardDetail);
            mBottomPageNumTv.setText(String.valueOf(index%10+1));

            if(position==mAdapter.getCount()-4) {
                String bespeak_time = cardDetail.getContentDetail().getBespeak_time();
                AppApi.getCardList(this,bespeak_time,this);
            }

            if(position==mAdapter.getCount()-1) {
                if(mNextPageFragments!=null&&mNextPageFragments.size()>0&&mNextPageBeanList!=null&&mNextPageBeanList.size()>0) {
                    mViewPager.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(mNextPageFragments!=null&&mNextPageFragments.size()==10) {
                                fragmentList.remove(mFooterPagerFragment);
                                fragmentList.addAll(mNextPageFragments);
                                mAdapter.setData(fragmentList);
                                fragmentList.add(mFooterPagerFragment);
                                mAdapter.setData(fragmentList);
                                CardFragment cFrag = (CardFragment) mNextPageFragments.get(0);
                                CardDetail detail = cFrag.getCardDetail();
                                initDate(detail);
                                mNextPageBeanList.clear();
                                mNextPageFragments.clear();
                            }
                        }
                    },500);

                }
            }
        }

    }

    private void initDate(CardDetail cardDetail) {
        String day = cardDetail.getDay();
        String month = cardDetail.getMonth();
        String week = cardDetail.getWeek();
        mPageNumLayout.setVisibility(View.VISIBLE);
        mBottomPageNumTv.setText("1");
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
            case POST_GET_CARDLIST_JSON:
                if(obj instanceof CardBean) {
                    cardBean = (CardBean) obj;
                    String day = cardBean.getDay();
                    String month = cardBean.getMonth();
                    String week = cardBean.getWeek();

                    List<CardDetail> list = cardBean.getList();
                    List<Fragment> fragments = new ArrayList<>();
                    if(list !=null&& list.size()>0) {
                        mLoadingLayout.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mLoadingLayout.setVisibility(View.GONE);
                                mDateLayout.setVisibility(View.VISIBLE);
                                mPageNumLayout.setVisibility(View.VISIBLE);
                            }
                        },200);

                        for(CardDetail detail : list) {
                            detail.setDay(day);
                            detail.setWeek(week);
                            detail.setMonth(month);
                            fragments.add(CardFragment.newInstance(detail));
                        }

                        mNextPageFragments.clear();
                        mNextPageBeanList.clear();
                        mNextPageBeanList.addAll(list);
                        mNextPageFragments.addAll(fragments);
                        if(fragmentList.size()==0) {
                            initDate(list.get(0));
                        }

                        if(fragmentList.size()==0) {
                            fragmentList.addAll(fragments);
                            fragmentList.add(mFooterPagerFragment);
                            mAdapter.setData(fragmentList);
                            mNextPageFragments.clear();
                            mNextPageBeanList.clear();
                        }


                    }else {
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
                break;
            case POST_UPGRADE_JSON:
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
        }
    }

    @Override
    public void onError(AppApi.Action method, Object obj) {
        switch (method) {
            case POST_GET_CARDLIST_JSON:

                mFooterPagerFragment.loadFailed();
                mNextPageBeanList = null;
                mNextPageFragments = null;
                break;
        }
    }

    @Override
    public void onNetworkFailed(AppApi.Action method) {

    }

    @Override
    public void onClickReload() {
        String btime = "";
        if(fragmentList.size()>0) {
            CardFragment fragment  = (CardFragment) fragmentList.get(fragmentList.size() - 2);
            CardDetail cardDetail = fragment.getCardDetail();
            if(cardDetail!=null) {
                CardDetail.ContentDetailBean contentDetail = cardDetail.getContentDetail();
                if(contentDetail!=null) {
                    btime =contentDetail.getBespeak_time();
                }
            }
        }
        AppApi.getCardList(this,btime,this);
        mFooterPagerFragment.startLoading();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_my_collection:
                Intent intent1 = new Intent();
                intent1.setClass(MainActivity.this,MyCollectActivity.class);
                startActivity(intent1);

                break;
            case R.id.rl_all_list:
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,AllListActivity.class);
                startActivity(intent);
                break;


            default:
                break;
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


            }
        }else {

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
                    ActivitiesManager.getInstance().popAllActivities();
                    Process.killProcess(android.os.Process.myPid());
                }
            }
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivitiesManager.getInstance().popActivity(this);
    }
}
