package com.smartx.bill.mepad.mestore.listener;

import java.io.File;

import cn.trinea.android.common.util.PreferencesUtils;

import com.smartx.bill.mepad.mestore.matadata.IOStreamDatas;
import com.smartx.bill.mepad.mestore.util.CommonTools.CommonViewHolder;

import android.app.Activity;
import android.app.DownloadManager;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;

public class InstallClickListener implements OnClickListener {
	Activity mActivity;
	DownloadManager downloadManager;
	String downloadUrl;
	String appName;

	public InstallClickListener(Activity activity, CommonViewHolder view,
			String downloadUrl, String appName) {
		mActivity = activity;
		downloadManager = (DownloadManager) mActivity
				.getSystemService(mActivity.DOWNLOAD_SERVICE);
		this.downloadUrl = downloadUrl;
		this.appName = appName;
	}

	@Override
	public void onClick(View arg0) {
		File folder = Environment
				.getExternalStoragePublicDirectory(IOStreamDatas.DOWNLOAD_FOLDER_NAME);
		if (!folder.exists() || !folder.isDirectory()) {
			folder.mkdirs();
		}

		DownloadManager.Request request = new DownloadManager.Request(
				Uri.parse(downloadUrl));
		request.setDestinationInExternalPublicDir(
				IOStreamDatas.DOWNLOAD_FOLDER_NAME, appName);
		request.setTitle(appName);
		request.setDescription(appName + "downloading");
		request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
		request.setVisibleInDownloadsUi(false);
		// request.allowScanningByMediaScanner();
		// request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
		// request.setShowRunningNotification(false);
		// request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
		request.setMimeType("application/vnd.android.package-archive");
		long downloadId = downloadManager.enqueue(request);
		/** save download id to preferences **/
		PreferencesUtils.putLong(mActivity, IOStreamDatas.KEY_NAME_DOWNLOAD_ID,
				downloadId);
	}

}
