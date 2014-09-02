package com.smartx.bill.mepad.mestore.broadcast;

import com.smartx.bill.mepad.mestore.Observer.DownloadChangeObserver;
import com.smartx.bill.mepad.mestore.util.CommonTools.CommonViewHolder;
import com.smartx.bill.mepad.mestore.util.OpenHelper;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import cn.trinea.android.common.util.DownloadManagerPro;

public class DownloadCompleteReceiver extends BroadcastReceiver {
	private long downloadId;
	private DownloadManagerPro downloadManagerPro;
	private Activity mActivity;
	private CommonViewHolder mView;
	private DownloadChangeObserver downloadObserver;

	public DownloadCompleteReceiver(long downloadId, Activity mActivity,
			DownloadChangeObserver downloadObserver, CommonViewHolder mView) {
		this.downloadId = downloadId;
		downloadManagerPro = new DownloadManagerPro(
				(DownloadManager) mActivity
						.getSystemService(mActivity.DOWNLOAD_SERVICE));
		this.mActivity = mActivity;
		this.downloadObserver = downloadObserver;
		this.mView = mView;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		/**
		 * get the id of download which have download success, if the id is my
		 * id and it's status is successful, then install it
		 **/
		long completeDownloadId = intent.getLongExtra(
				DownloadManager.EXTRA_DOWNLOAD_ID, -1);
		if (completeDownloadId == downloadId) {
			// if download successful, install apk
			if (downloadManagerPro.getStatusById(downloadId) == DownloadManager.STATUS_SUCCESSFUL) {
				mView.appInstall.setVisibility(View.INVISIBLE);
				mView.appDownload.setVisibility(View.INVISIBLE);
				mView.appOpen.setVisibility(View.VISIBLE);
//				OpenHelper.startViewIntent(context, downloadId, Intent.FLAG_ACTIVITY_SINGLE_TOP);
				mActivity.unregisterReceiver(this);
				mActivity.getContentResolver().unregisterContentObserver(
						downloadObserver);
			}
		}
	}
};