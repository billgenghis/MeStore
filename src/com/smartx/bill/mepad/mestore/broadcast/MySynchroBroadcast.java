package com.smartx.bill.mepad.mestore.broadcast;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import cn.trinea.android.common.util.DownloadManagerPro;
import cn.trinea.android.common.util.PreferencesUtils;

import com.smartx.bill.mepad.mestore.Observer.DownloadChangeObserver;
import com.smartx.bill.mepad.mestore.application.MyApplication;
import com.smartx.bill.mepad.mestore.thread.RefreshDownloadUIHandler;
import com.smartx.bill.mepad.mestore.util.CommonTools.CommonViewHolder;

public class MySynchroBroadcast extends BroadcastReceiver {
	private Activity mActivity;
	private DownloadManager downloadManager;
	private DownloadManagerPro downloadManagerPro;
	private long downloadId;
	private String appPackageName;
	private RefreshDownloadUIHandler handler;
	private CommonViewHolder mView;

	public MySynchroBroadcast(Activity activity, CommonViewHolder view,
			String appPackageName) {
		mActivity = activity;
		handler = new RefreshDownloadUIHandler(view, mActivity);
		MyApplication installApplication = (MyApplication) mActivity
				.getApplication();
		downloadManager = installApplication.getDownloadManager();
		downloadManagerPro = new DownloadManagerPro(downloadManager);
		this.mView = view;
		this.appPackageName = appPackageName;
	}

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		downloadId = PreferencesUtils.getLong(mActivity, appPackageName, 0);
		DownloadChangeObserver downloadObserver = new DownloadChangeObserver(
				handler, downloadManagerPro, downloadId);
		
		mActivity.getContentResolver().registerContentObserver(
				DownloadManagerPro.CONTENT_URI, true, downloadObserver);
		DownloadCompleteReceiver completeReceiver = new DownloadCompleteReceiver(
				downloadId, mActivity, downloadObserver, mView);
		mActivity.registerReceiver(completeReceiver, new IntentFilter(
				DownloadManager.ACTION_DOWNLOAD_COMPLETE));
	}
}
