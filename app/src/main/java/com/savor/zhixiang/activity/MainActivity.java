package com.savor.zhixiang.activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.common.api.utils.DensityUtil;
import com.savor.zhixiang.R;
import com.savor.zhixiang.adapter.CardListAdapter;
import com.savor.zhixiang.bean.CardBean;
import com.savor.zhixiang.bean.CardDetail;
import com.savor.zhixiang.core.ApiRequestListener;
import com.savor.zhixiang.core.AppApi;
import com.savor.zhixiang.fragment.CardFragment;
import com.savor.zhixiang.widget.KeywordDialog;
import com.savor.zhixiang.widget.PagingScrollHelper;
import com.savor.zhixiang.widget.cardrecyclerview.CardScaleHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PagingScrollHelper.onPageChangeListener, ViewPager.OnPageChangeListener, ApiRequestListener {

    private RelativeLayout right;
    private RelativeLayout left;
    private boolean isDrawer;
    private String[] mVals = new String[]
        {"iPhone X", "孙宏斌", "美联储", "蒂芙尼珠宝", "北海道肉蟹", "贵族学校",
                "百年普洱茶", "小米科技", "特朗普", "蒂芙尼"};
    private ViewPager mViewPager;
    private List<Fragment> mList = new ArrayList<>();
    private CardScaleHelper mCardScaleHelper;
    private TextView mBottomPageNumTv;
    private TextView mTotalPageNumTv;
    private CardListAdapter mAdapter;
    private List<Fragment> fragmentList = new ArrayList<>();
    private LinearLayout mDateLayout;
    private TextView mDateTv;
    private TextView mMonthTv;
    private TextView mWeekTv;
    private RelativeLayout mPageNumLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getViews();
        setViews();
        setListeners();
        getData();


    }

    private void getData() {
        AppApi.getKeywords(this,this);
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
    }

    private void setViews() {
//        for(int i =0 ;i<10;i++) {
//            mList.add(CardFragment.newInstance(i));
//        }

        mBottomPageNumTv.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/ACaslonPro-Italic.otf"));
        mTotalPageNumTv.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/ACaslonPro-Regular.otf"));

        mAdapter = new CardListAdapter(getSupportFragmentManager(),mList);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(5);
        mViewPager.setPageMargin(DensityUtil.dpToPx(this,16));

    }

    private void setListeners() {
        mViewPager.addOnPageChangeListener(this);
//        mAdapter.setoni.setonI
    }

    private void showKeywordDialog(List<String> keywords) {
        new KeywordDialog(this, keywords).show();
    }

    private void initDrawerLayout() {

        ImageView menuBtn = (ImageView) findViewById(R.id.iv_menu);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
            }
            @Override
            public void onDrawerOpened(View drawerView) {}
            @Override
            public void onDrawerClosed(View drawerView) {
                isDrawer=false;
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
        CardFragment fragment = (CardFragment) fragmentList.get(position);
        CardDetail cardDetail = fragment.getCardDetail();
        initDate(cardDetail);
        mBottomPageNumTv.setText(String.valueOf(position%10+1));

        if(mAdapter.getCount()>=10&&position==mAdapter.getCount()-1) {
            String bespeak_time = cardDetail.getContentDetail().getBespeak_time();
            AppApi.getCardList(this,bespeak_time,this);
        }
    }

    private void initDate(CardDetail cardDetail) {
        String day = cardDetail.getDay();
        String month = cardDetail.getMonth();
        String week = cardDetail.getWeek();
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
            case POST_GET_KEYWORDS_JSON:
                if(obj instanceof List) {
                    List<String> keywords = (List<String>) obj;
                    showKeywordDialog(keywords);
                }
                break;
            case POST_GET_CARDLIST_JSON:

                if(obj instanceof CardBean) {
                    CardBean cardBean = (CardBean) obj;
                    String day = cardBean.getDay();
                    String month = cardBean.getMonth();
                    String week = cardBean.getWeek();
                    mDateLayout.setVisibility(View.VISIBLE);
                    mPageNumLayout.setVisibility(View.VISIBLE);

                    List<CardDetail> list = cardBean.getList();
                    List<Fragment> fragments = new ArrayList<>();
                    if(list!=null&&list.size()>0) {
                        for(CardDetail detail : list) {
                            detail.setDay(day);
                            detail.setWeek(week);
                            detail.setMonth(month);
                            fragments.add(CardFragment.newInstance(detail));
                        }
                        if(fragments.size()>0) {
                            fragmentList.addAll(fragments);
                            mAdapter.addData(fragments);
                        }
                        if(fragmentList.size()<=10) {
                            initDate(list.get(0));
                        }
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
}
