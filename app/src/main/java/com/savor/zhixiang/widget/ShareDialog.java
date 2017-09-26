package com.savor.zhixiang.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.savor.zhixiang.R;
import com.savor.zhixiang.activity.CardDetailActivity;
import com.savor.zhixiang.bean.ShareBean;
import com.savor.zhixiang.utils.ShareManager;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;


/**
 * Created by bushlee on 2016/12/26.
 */

public class ShareDialog extends Dialog implements View.OnClickListener {

    private ShareBean shareBean;
    private RelativeLayout weixin_la;
    private RelativeLayout pyq_la;
    private ShareManager mShareManager;
    private ShareManager.CustomShareListener mShareListener;
    private Context context;
    private CardDetailActivity Activity;

    public ShareDialog(Context context,CardDetailActivity Activity) {
        super(context, R.style.Dialog_Fullscreen);
        this.context = context;
        this.Activity = Activity;
    }

    public ShareDialog(Context context, ShareBean shareBean) {
        super(context, R.style.Dialog_Fullscreen);
        this.shareBean = shareBean;
        this.context = context;
        this.Activity = Activity;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_layout);
        getViews();
        setViews();
        setListeners();
    }

    private void getViews() {
        weixin_la = (RelativeLayout) findViewById(R.id.weixin_la);
        pyq_la = (RelativeLayout) findViewById(R.id.pyq_la);
    }



    private void setViews() {
        mShareManager = ShareManager.getInstance();
        mShareListener = new ShareManager.CustomShareListener(Activity);
//        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
//        layoutParams.gravity= Gravity.CENTER;
//        layoutParams.width= WindowManager.LayoutParams.MATCH_PARENT;
//
//        getWindow().getDecorView().setPadding(0, 0, 0, 0);
//
//        getWindow().setAttributes(layoutParams);
    }

    private void setListeners() {
        weixin_la.setOnClickListener(this);
        pyq_la.setOnClickListener(this);
//        mConfirmBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.weixin_la:
                share(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.pyq_la:
                share(SHARE_MEDIA.WEIXIN_CIRCLE);
                break;

        }
    }

    private void share(SHARE_MEDIA platform){
        mShareManager.setShortcutShare();
        UMWeb umWeb = new UMWeb(shareBean.getUrl());
        umWeb.setThumb(new UMImage(context,R.mipmap.ico_drawer));
        umWeb.setTitle(shareBean.getTitle());
        umWeb.setDescription("投屏神器，进入饭局的才是热点");
        new ShareAction(Activity)
                .withText("投屏神器，进入饭局的才是热点"+shareBean.getTitle())
                .withMedia(umWeb)
                .setPlatform(platform)
                .setCallback(mShareListener)
                .share();

    }

}