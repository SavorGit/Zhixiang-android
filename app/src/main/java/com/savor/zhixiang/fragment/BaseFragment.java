package com.savor.zhixiang.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.api.utils.LogUtils;
import com.common.api.utils.ShowMessage;
import com.common.api.utils.ShowProgressDialog;
import com.savor.zhixiang.R;
import com.savor.zhixiang.core.ApiRequestListener;
import com.savor.zhixiang.core.AppApi;
import com.savor.zhixiang.core.ResponseErrorMessage;
import com.savor.zhixiang.core.Session;
import com.savor.zhixiang.interfaces.IBaseView;


/**
 * Fragment基类
 *
 * 基类实现了bindview和senseview接口
 * 模拟适配器模式，有一些接口方法是空实现
 * @author bc
 * 
 */
public abstract class BaseFragment extends Fragment implements ApiRequestListener,IBaseView{
	Session mSession;
//	protected PictureUtils bitmapUtils;
//	protected BitmapDisplayConfig config;
	
	protected FrameLayout backFL;
	protected ImageView backIV;
	protected TextView backTV;
	protected FrameLayout nextFL;
	protected ImageView nextIV;
	protected TextView nextTV;
	protected TextView titleTV;

	protected Activity mActivity;
	private ProgressDialog mProgressDialog;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		LogUtils.i(getFragmentName() + " onAttach()");
		mActivity = activity;
		mSession = Session.get(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogUtils.i(getFragmentName() + " onCreate()");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		LogUtils.i(getFragmentName() + " onCreateView()");
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		LogUtils.i(getFragmentName() + " onViewCreated()");
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		LogUtils.i(getFragmentName() + " onActivityCreated()");
	}

	@Override
	public void onStart() {
		super.onStart();
		LogUtils.i(getFragmentName() + " onStart()");
	}

	@Override
	public void onResume() {
		super.onResume();
		LogUtils.i(getFragmentName() + " onResume()");
	}

	@Override
	public void onPause() {
		super.onPause();
		LogUtils.i(getFragmentName() + " onPause()");
	}

	@Override
	public void onStop() {
		super.onStop();
		LogUtils.i(getFragmentName() + " onStop()");
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		LogUtils.i(getFragmentName() + " onDestroyView()");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		LogUtils.i(getFragmentName() + " onDestroy()");
	}

	@Override
	public void onDetach() {
		super.onDetach();
		LogUtils.i(getFragmentName() + " onDetach()");
	}

	protected void initBitmapUtils() {
//		bitmapUtils = PictureUtils.getInstance(getActivity());
//		config = new BitmapDisplayConfig();
//		config.setLoadingDrawable(getActivity().getResources().getDrawable(
//				R.drawable.ic_empty));
//		config.setLoadFailedDrawable(getActivity().getResources().getDrawable(
//				R.drawable.ic_empty));
	}
	
	/**
	 * fragment name
	 */
	public String getFragmentName() {
		return getClass().getSimpleName();
	}

	
	@Override
	public void onError(AppApi.Action method, Object statusCode) {

		if(statusCode instanceof ResponseErrorMessage) {
			ResponseErrorMessage msg = (ResponseErrorMessage) statusCode;
			int code = msg.getCode();
			String message = msg.getMessage();
			showToast(message);
		}
	}

	@Override
	public void onSuccess(AppApi.Action method, Object obj) {

	}

	@Override
	public void onNetworkFailed(AppApi.Action method) {

	}

	protected void showErrorToast(Object obj, String defaultMsg) {
		if (obj instanceof ResponseErrorMessage) {
			ResponseErrorMessage errorMessage = (ResponseErrorMessage) obj;
			if (!TextUtils.isEmpty(errorMessage.getMessage())) {
				defaultMsg = errorMessage.getMessage();
			}
		}
		showToast(defaultMsg);
	}

	public void showToast(String message) {
		ShowMessage.showToast(getActivity(),message);
	}

	@Override
	public void getViews() {

	}

	@Override
	public void setViews() {

	}

	@Override
	public void setListeners() {
		
	}

}
