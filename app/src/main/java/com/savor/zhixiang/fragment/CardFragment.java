package com.savor.zhixiang.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.common.api.utils.ShowMessage;
import com.savor.zhixiang.R;

/**
 * Created by hezd on 2017/9/21.
 */

public class CardFragment extends Fragment implements View.OnClickListener {

    private CardView mParentView;
    private int index;

    public static CardFragment newInstance(int index) {
        CardFragment cardFragment = new CardFragment();
        cardFragment.setIndex(index);
        return cardFragment;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.view_card_item,container,false);
        initViews(parent);
        return parent;
    }

    private void initViews(View parent) {
        mParentView = (CardView) parent.findViewById(R.id.parent);
        mParentView.setOnClickListener(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.parent:
                ShowMessage.showToast(getActivity(),"click index="+index);
                break;
        }
    }
}
