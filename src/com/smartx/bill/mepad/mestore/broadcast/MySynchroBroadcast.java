package com.smartx.bill.mepad.mestore.broadcast;

import java.io.File;

import cn.trinea.android.common.util.DownloadManagerPro;
import cn.trinea.android.common.util.PreferencesUtils;

import com.smartx.bill.mepad.mestore.Observer.DownloadChangeObserver;
import com.smartx.bill.mepad.mestore.matadata.IOStreamDatas;
import com.smartx.bill.mepad.mestore.thread.RefreshDownloadUIHandler;
import com.smartx.bill.mepad.mestore.util.CommonTools.CommonViewHolder;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

public class MySynchroBroadcast extends BroadcastReceiver {
	private Activity mActivity;
	private DownloadManager downloadManager;
	private DownloadManagerPro downloadManagerPro;
	private long downloadId;
	private String appName;
	private RefreshDownloadUIHandler handler;
	private CommonViewHolder mView;

	public MySynchroBroadcast(Activity activity, CommonViewHolder view,String appName) {
		mActivity = activity;
		this.appName = appName;
		handler = new RefreshDownloadUIHandler(view, mActivity);
		downloadManager = this.downloadManager;
		downloadManagerPro = new DownloadManagerPro(downloadManager);
		downloadId = PreferencesUtils.getLong(mActivity, appName,
				0);
		Log.i("downloadId", downloadId + " ");
		this.mView = view;
	}

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// appInstall.performClick();
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
