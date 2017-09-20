package com.savor.zhixiang.widget;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.savor.zhixiang.R;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

/**
 * 关键词对话框
 * Created by hezd on 2017/9/19.
 */

public class KeywordDialog extends Dialog  {


    private final Activity mContext;
    private final List<String> mKeywords;
    private TextView mPercentTv;
    private TagFlowLayout mFlowLayout;
    private LayoutInflater mInflater;

    public KeywordDialog(Activity context, List<String> keywords) {
        super(context, R.style.loading_progress_bar);
        this.mContext = context;
        this.mKeywords = keywords;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_keyword);
        setCancelable(false);
        mInflater = LayoutInflater.from(getContext());

        mFlowLayout = (TagFlowLayout) findViewById(R.id.fl_keyword);

        initKeywords();
    }

    private void initKeywords() {
        mFlowLayout.setAdapter(new TagAdapter<String>(mKeywords)
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

    public void updatePercent(double percent) {
        int per = (int) (percent*100);
        mPercentTv.setText(per+"%");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dismiss();
        mContext.finish();
    }
}
