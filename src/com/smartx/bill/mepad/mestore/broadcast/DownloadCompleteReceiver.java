package com.smartx.bill.mepad.mestore.broadcast;

import java.io.File;

import cn.trinea.android.common.util.DownloadManagerPro;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;

class DownloadCompleteReceiver extends BroadcastReceiver {
	private long downloadId;
	private DownloadManagerPro downloadManagerPro;
	private Activity mActivity;
	private DownloadCompleteReceiver completeReceiver;
	public DownloadCompleteReceiver(long downloadId, Activity mActivity,DownloadCompleteReceiver completeReceiver){
		this.downloadId = downloadId;
		downloadManagerPro = new DownloadManagerPro((DownloadManager) mActivity.getSystemService(mActivity.DOWNLOAD_SERVICE));
		this.mActivity = mActivity;
		this.completeReceiver = completeReceiver;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		/**
		 * get the id of download which have download success, if the id is
		 * my id and it's status is successful, then install it
		 **/
		long completeDownloadId = intent.getLongExtra(
				DownloadManager.EXTRA_DOWNLOAD_ID, -1);
		if (completeDownloadId == downloadId) {
			// if download successful, install apk
			if (downloadManagerPro.getStatusById(downloadId) == DownloadManager.STATUS_SUCCESSFUL) {
				
			}
		}
		mActivity.unregisterReceiver(completeReceiver);
	}
};