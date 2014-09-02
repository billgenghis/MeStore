package com.smartx.bill.mepad.mestore.adapter;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.smartx.bill.mepad.mestore.R;
import com.smartx.bill.mepad.mestore.listener.InstallClickListener;
import com.smartx.bill.mepad.mestore.listener.OpenClickListener;
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
	protected DisplayImageOptions meSpecialOptions = new DisplayImageOptions.Builder()
			.showImageOnLoading(R.drawable.default_me_special) // resource or
																// drawable
			.showImageForEmptyUri(R.drawable.default_me_special) // resource or
																	// drawable
			.showImageOnFail(R.drawable.default_me_special) // resource or
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
	protected DisplayImageOptions appDialogOptions = new DisplayImageOptions.Builder()
			.showImageOnLoading(R.drawable.default_app_review) // resource or
																// drawable
			.showImageForEmptyUri(R.drawable.default_app_review) // resource or
																	// drawable
			.showImageOnFail(R.drawable.default_app_review) // resource or
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

	private PackageManager mPm;
	private Activity mActivity;

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
			String downloadUrl, String appTitle, String packageName) {
		view.appInstall.setOnClickListener(new InstallClickListener(activity,
				view, downloadUrl, appTitle, packageName));
		view.appOpen.setOnClickListener(new OpenClickListener(packageName,
				activity));
		getAppForPackage(activity, packageName, appTitle, view);
	}

	private void getAppForPackage(Activity activity, String packageName,
			String appTitle, CommonViewHolder mView) {
		if (mActivity == null) {
			mActivity = activity;
			mPm = activity.getPackageManager();
		}
		PackageInfo pkgInfo;
		try {
			pkgInfo = mPm.getPackageInfo(packageName, 0);
		} catch (NameNotFoundException e) {
			pkgInfo = null;
			return;
		}
		if (pkgInfo != null) {
			mView.appInstall.setVisibility(View.INVISIBLE);
			mView.appOpen.setVisibility(View.VISIBLE);
		}
	}

	protected String getImageUrl(String url) {
		return IOStreamDatas.SERVER_IP + url;
	}

}
