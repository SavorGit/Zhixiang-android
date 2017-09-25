package com.savor.zhixiang.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.api.utils.ShowMessage;
import com.common.api.widget.pulltorefresh.library.PullToRefreshBase;
import com.common.api.widget.pulltorefresh.library.PullToRefreshListView;
import com.savor.zhixiang.R;
import com.savor.zhixiang.adapter.AllListAdapter;
import com.savor.zhixiang.bean.ListItem;
import com.savor.zhixiang.core.ApiRequestListener;
import com.savor.zhixiang.core.AppApi;
import com.savor.zhixiang.core.ResponseErrorMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bushlee on 2017/9/21.
 */

public class MyCollectActivity extends BaseActivity implements View.OnClickListener,ApiRequestListener {
    private Context context;
    private String collecTime = "";
    private PullToRefreshListView mPullRefreshListView;
    private AllListAdapter mAdapter;
    private TextView mRefreshDataHinttv;
    private TextView tv_center;
    private boolean isUp = true;
    private List<ListItem> list = new ArrayList<ListItem>();
    private RelativeLayout back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        context = this;
        getViews();
        setViews();
        setListeners();
        getData();
    }

    @Override
    public void getViews() {

        mPullRefreshListView = (PullToRefreshListView)findViewById(R.id.listview);
        mRefreshDataHinttv = (TextView) findViewById(R.id.tv_refresh_data_hint);
        tv_center = (TextView) findViewById(R.id.tv_center);
        back = (RelativeLayout) findViewById(R.id.back);
    }

    @Override
    public void setViews() {
        mAdapter = new AllListAdapter(context);
        // mListView = mPullRefreshListView.getRefreshableView();
        mPullRefreshListView.setAdapter(mAdapter);
        tv_center.setText("收藏馆");
    }

    @Override
    public void setListeners() {
        mPullRefreshListView.setOnRefreshListener(onRefreshListener);
        mPullRefreshListView.setOnLastItemVisibleListener(onLastItemVisibleListener);
        mPullRefreshListView.onLoadComplete(true,false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                //RecordUtils.onEvent(this,getString(R.string.menu_collection_back));
                finish();
                break;

            default:
                break;
        }
    }

    private void getData(){
        AppApi.getMyCollection(this,collecTime,this);

    }
    PullToRefreshBase.OnRefreshListener onRefreshListener = new PullToRefreshBase.OnRefreshListener() {
        @Override
        public void onRefresh(PullToRefreshBase refreshView) {
            collecTime = "";
            isUp = true;
            getData();
        }
    };

    PullToRefreshBase.OnLastItemVisibleListener onLastItemVisibleListener = new PullToRefreshBase.OnLastItemVisibleListener() {
        @Override
        public void onLastItemVisible() {
            isUp = false;
            getData();
        }
    };
    @Override
    public void onSuccess(AppApi.Action method, Object obj) {
        switch (method) {
            case POST_GET_MY_COLLECTION_JSON:
               // mProgressLayout.loadSuccess();
                mPullRefreshListView.onRefreshComplete();
                if(obj instanceof List<?>) {
                    List<ListItem> mList = (List<ListItem>) obj;
                    handleVodList(mList);
                }
                break;
        }
    }

    @Override
    public void onError(AppApi.Action method, Object obj) {

        if(obj instanceof ResponseErrorMessage) {
            ResponseErrorMessage message = (ResponseErrorMessage) obj;
            String msg = message.getMessage();
            ShowMessage.showToast(this,msg);
        }
    }

    private void handleVodList(List<ListItem> mList){

        if (mList != null && mList.size() > 0) {
            mPullRefreshListView.setVisibility(View.VISIBLE);
            if (isUp) {
                list.clear();
                mAdapter.clear();
                mPullRefreshListView.onLoadComplete(true,false);

            }else {
                mPullRefreshListView.onLoadComplete(true,false);
            }
            collecTime = mList.get(mList.size()-1).getCollecTime()+"";
            list.addAll(mList);
            mAdapter.setData(list);
//            int haveNext = 0;
//            haveNext =  listResult.getNextpage();
//
//            if (haveNext==0) {
//                mPullRefreshListView.onLoadComplete(false,false);
//            }else {
//                mPullRefreshListView.onLoadComplete(true,false);
//            }

            if (mList!=null && mList.size()<20) {
                mPullRefreshListView.onLoadComplete(false,false);
            }else {
                mPullRefreshListView.onLoadComplete(true,false);
            }
        }else {
//            if (list != null && list.size()>0  ) {
//                mAdapter.clear();
//            }
            //mPullRefreshListView.setVisibility(View.GONE);

            mPullRefreshListView.onLoadComplete(false,true);
        }



    }

}
