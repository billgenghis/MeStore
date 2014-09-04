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

/**
 * @ClassName: MySynchroBroadcast
 * @Description:负责同步dialog与gridView里下载的进度状态发起的广播
 * @author coney Geng
 * @date 2014年9月3日 下午5:28:42
 * 
 */
public class MySynchroBroadcast extends BroadcastReceiver {
	private Activity mActivity;
	private DownloadManager downloadManager;
	private DownloadManagerPro downloadManagerPro;
	private long downloadId;
	private String appName;
	private String appPackageName;
	private RefreshDownloadUIHandler handler;
	private CommonViewHolder mView;

	public MySynchroBroadcast(Activity activity, CommonViewHolder view,
			String appPackageName, String appName) {
		mActivity = activity;
		handler = new RefreshDownloadUIHandler(view, appName, mActivity);
		MyApplication installApplication = (MyApplication) mActivity
				.getApplication();
		downloadManager = installApplication.getDownloadManager();
		downloadManagerPro = new DownloadManagerPro(downloadManager);
		this.mView = view;
		this.appName = appName;
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
				downloadId, mActivity, downloadObserver, mView, appName,
				appPackageName);
//		MyApplication installApplication = (MyApplication) mActivity
//				.getApplication();
//		installApplication.setBroadCast(String.valueOf(downloadId) + "Synchro", completeReceiver);
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
		intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
		mActivity.registerReceiver(completeReceiver, intentFilter);
		mActivity.unregisterReceiver(this);
	}
}
