package com.smartx.bill.mepad.mestore.adapter;

import java.io.File;

import android.app.Activity;
import android.app.DownloadManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import cn.trinea.android.common.util.PreferencesUtils;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.smartx.bill.mepad.mestore.R;
import com.smartx.bill.mepad.mestore.listener.InstallClickListener;
import com.smartx.bill.mepad.mestore.matadata.IOStreamDatas;
import com.smartx.bill.mepad.mestore.util.CommonTools.CommonViewHolder;

public class MyBaseAdapter extends BaseAdapter {

	protected DisplayImageOptions options = new DisplayImageOptions.Builder()
			.showImageOnLoading(R.drawable.default_app_icon) // resource or
																// drawable
			.showImageForEmptyUri(R.drawable.default_app_icon) // resource or
																// drawable
			.showImageOnFail(R.drawable.default_app_icon) // resource or
															// drawable
			.resetViewBeforeLoading(false) // default
			.delayBeforeLoading(1000).cacheInMemory(true) // default
			.cacheOnDisk(true) // default
			.considerExifParams(false) // default
			.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default
			.bitmapConfig(Bitmap.Config.ARGB_8888) // default
			.displayer(new SimpleBitmapDisplayer()) // default
			.handler(new Handler()) // default
			.build();

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	protected void setInstallClick(Activity activity, CommonViewHolder view,
			String downloadUrl, String appTitle) {
		view.appInstall.setOnClickListener(new InstallClickListener(activity, view, downloadUrl, appTitle));
	}
	protected String getImageUrl(String url) {
		return IOStreamDatas.SERVER_IP + url;
	}

}
