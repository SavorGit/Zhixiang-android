package com.savor.zhixiang.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.common.api.utils.DensityUtil;
import com.savor.zhixiang.CardFragment;
import com.savor.zhixiang.R;
import com.savor.zhixiang.adapter.CardListAdapter;
import com.savor.zhixiang.widget.KeywordDialog;
import com.savor.zhixiang.widget.PagingScrollHelper;
import com.savor.zhixiang.widget.cardrecyclerview.CardAdapter;
import com.savor.zhixiang.widget.cardrecyclerview.CardScaleHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PagingScrollHelper.onPageChangeListener {

    private RelativeLayout right;
    private RelativeLayout left;
    private boolean isDrawer;
    private String[] mVals = new String[]
        {"iPhone X", "孙宏斌", "美联储", "蒂芙尼珠宝", "北海道肉蟹", "贵族学校",
                "百年普洱茶", "小米科技", "特朗普", "蒂芙尼"};
    private ViewPager mViewPager;
    private List<Fragment> mList = new ArrayList<>();
    private CardScaleHelper mCardScaleHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getViews();
        setViews();
        setListeners();
        showKeywordDialog();
    }


    private void getViews() {
        initDrawerLayout();

        mViewPager = (ViewPager) findViewById(R.id.rlv_list);
    }

    private void setViews() {
        for(int i =0 ;i<10;i++) {
            mList.add(CardFragment.newInstance());
        }

        CardListAdapter mAdapter = new CardListAdapter(getSupportFragmentManager(),mList);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(5);
        mViewPager.setPageMargin(DensityUtil.dpToPx(this,16));
    }

    private void setListeners() {
//        PagingScrollHelper helper = new PagingScrollHelper();
//        helper.setUpRecycleView(mViewPager);
//        helper.setOnPageChangeListener(this);
    }

    private void showKeywordDialog() {
        new KeywordDialog(this, Arrays.asList(mVals)).show();
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
}
