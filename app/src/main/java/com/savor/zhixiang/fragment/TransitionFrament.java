package com.savor.zhixiang.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.savor.zhixiang.R;

/**
 * 过渡页
 * @author hezd
 */
public class TransitionFrament extends Fragment {

    public TransitionFrament() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transition_frament, container, false);
    }

}
