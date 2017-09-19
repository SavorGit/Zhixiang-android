package com.savor.zhixiang.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.savor.zhixiang.R;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.lang.reflect.Array;
import java.util.Arrays;

public class KeyWordActivity extends BaseActivity {
    //    private String[] mVals = new String[]
//            {"iPhone X", "孙宏斌", "美联储", "蒂芙尼珠宝", "北海道肉蟹", "贵族学校",
//                    "百年普洱茶", "小米科技", "特朗普", "蒂芙尼"};
    private String[] mVals = new String[]
            {"Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
                    "Android", "Weclome", "Button ImageView", "TextView", "Helloworld",
                    "Android", "Weclome Hello", "Button Text", "TextView","Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
                    "Android", "Weclome", "Button ImageView"};
    private LayoutInflater mInflater;
    private TagFlowLayout mFlowLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key_word);

        getViews();
        setViews();
        setListeners();

    }

    private void initKeywords() {
        mFlowLayout.setMaxSelectCount(0);
        mFlowLayout.setAdapter(new TagAdapter<String>(Arrays.asList(mVals))
        {

            @Override
            public View getView(FlowLayout parent, int position, String s)
            {
                TextView tv = (TextView)mInflater.inflate(R.layout.item_keyword,
                        mFlowLayout, false);
                tv.setText(s);
                return tv;
            }
        });

        mFlowLayout.startAnimation(0);
    }


    @Override
    public void getViews() {
        mInflater = LayoutInflater.from(this);
        mFlowLayout = (TagFlowLayout) findViewById(R.id.fl_keyword);

    }

    @Override
    public void setViews() {
        initKeywords();
    }

    @Override
    public void setListeners() {

    }
}
