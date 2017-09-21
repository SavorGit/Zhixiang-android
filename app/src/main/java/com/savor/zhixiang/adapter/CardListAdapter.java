package com.savor.zhixiang.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.common.api.utils.ShowMessage;

import java.util.List;

/**
 * Created by hezd on 8/30/16.
 */
public class CardListAdapter extends FragmentStatePagerAdapter {
    List<Fragment> fragments;

    public CardListAdapter(FragmentManager fm) {
        super(fm);
    }

    public CardListAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    public void setData(List<Fragment> fragments) {
        this.fragments = fragments;
        notifyDataSetChanged();
    }

    public void addData(List<Fragment> fragments) {
        if(fragments!=null&&fragments.size()>0) {
            this.fragments.addAll(fragments);
            notifyDataSetChanged();
        }
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        int i = fragments.indexOf(object);
        if(i==-1)
            return PagerAdapter.POSITION_NONE;
        return i;
    }

    @Override
    public int getCount() {
        return fragments == null?0:fragments.size();
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowMessage.showToast(container.getContext(),"position="+position);
            }
        });
        return super.instantiateItem(container, position);
    }
}
