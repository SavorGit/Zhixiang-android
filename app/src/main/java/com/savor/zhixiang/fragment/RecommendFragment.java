package com.savor.zhixiang.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.api.utils.DensityUtil;
import com.savor.zhixiang.R;
import com.savor.zhixiang.utils.ConstantValues;
import com.savor.zhixiang.utils.ShareManager;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.utils.SocializeUtils;

import java.util.Map;

/**
 * 推荐App给好友
 * @author hezd
 */
public class RecommendFragment extends BaseFragment implements View.OnClickListener {
    public static final float IMAGE_SCALE = 400/630f;
    private ImageView mBannerIv;
    private TextView mWxTv;
    private TextView mCircleTv;
    private ProgressDialog dialog;
    private ShareManager.CustomShareListener mShareListener;

    public RecommendFragment() {
    }

    public static RecommendFragment newInstance() {
        RecommendFragment fragment = new RecommendFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View parent = inflater.inflate(R.layout.fragment_recommend, container, false);
        initViews(parent);
        setListeners();
        return parent;
    }

    public void setListeners() {
        mWxTv.setOnClickListener(this);
        mCircleTv.setOnClickListener(this);
    }

    private void initViews(View parent) {
        dialog = new ProgressDialog(getContext());

        mBannerIv = (ImageView) parent.findViewById(R.id.iv_banner);
        ViewGroup.LayoutParams layoutParams = mBannerIv.getLayoutParams();
        int screenWidth = DensityUtil.getScreenWidth(getContext());
        int width = screenWidth-DensityUtil.dip2px(getContext(),28)*2;
        int height = (int) (width*IMAGE_SCALE);
        layoutParams.width = width;
        layoutParams.height = height;

        mWxTv = (TextView) parent.findViewById(R.id.tv_share_wx);
        mCircleTv = (TextView) parent.findViewById(R.id.tv_circle);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mShareListener = new ShareManager.CustomShareListener(getActivity());
    }

    @Override
    public void onClick(View v) {
        boolean authorize = UMShareAPI.get(getContext()).isAuthorize(getActivity(), SHARE_MEDIA.WEIXIN);
        switch (v.getId()) {
            case R.id.tv_share_wx:
                SocializeUtils.safeShowDialog(dialog);
                // 是否已授权
                if(authorize) {
                    share(SHARE_MEDIA.WEIXIN);
                }else {
                    UMShareAPI.get(getContext()).doOauthVerify(mActivity, SHARE_MEDIA.WEIXIN, new UMAuthListener() {
                        @Override
                        public void onStart(SHARE_MEDIA share_media) {

                        }

                        @Override
                        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                            share(SHARE_MEDIA.WEIXIN);
                        }

                        @Override
                        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

                        }

                        @Override
                        public void onCancel(SHARE_MEDIA share_media, int i) {

                        }
                    });
                }
                break;
            case R.id.tv_circle:
                SocializeUtils.safeShowDialog(dialog);
                if(authorize) {
                    share(SHARE_MEDIA.WEIXIN_CIRCLE);
                }else {
                    UMShareAPI.get(getContext()).doOauthVerify(mActivity, SHARE_MEDIA.WEIXIN, new UMAuthListener() {
                        @Override
                        public void onStart(SHARE_MEDIA share_media) {

                        }

                        @Override
                        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                            share(SHARE_MEDIA.WEIXIN_CIRCLE);
                        }

                        @Override
                        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

                        }

                        @Override
                        public void onCancel(SHARE_MEDIA share_media, int i) {

                        }
                    });
                }
                break;
        }
    }

    private void share(SHARE_MEDIA platform){
        UMWeb umWeb = new UMWeb(ConstantValues.H5_SHARE_URL);
        umWeb.setThumb(new UMImage(getContext(),R.mipmap.ico_share_img));
        umWeb.setTitle("专为高端人士打造的内容App");
        umWeb.setDescription("每天十条内容，知世界生活");
        new ShareAction(getActivity())
                .withText("专为高端人士打造的内容App")
                .withMedia(umWeb)
                .setPlatform(platform)
                .setCallback(mShareListener)
                .share();
    }

    @Override
    public void onPause() {
        super.onPause();
        SocializeUtils.safeCloseDialog(dialog);
    }
}
