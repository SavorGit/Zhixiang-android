package com.savor.zhixiang.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.savor.zhixiang.R;
import com.savor.zhixiang.bean.TransitionBean;

/**
 * 过渡页
 * @author hezd
 */
public class TransitionFrament extends Fragment {
    public static final int TYPE_FINISH_TODAY = 1;
    public static final int TYPE_FINISH_HISTORY = 2;
    public static final int TYPE_FINISH_ALL = 3;

    public TransitionFrament() {
    }

    public static TransitionFrament newInstance(TransitionBean transitionBean) {
        TransitionFrament transitionFrament = new TransitionFrament();
        Bundle bundle = new Bundle();
        bundle.putSerializable("bean",transitionBean);
        transitionFrament.setArguments(bundle);
        return transitionFrament;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transition_frament, container, false);
    }

}
