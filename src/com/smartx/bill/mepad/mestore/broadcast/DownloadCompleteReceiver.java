package com.smartx.bill.mepad.mestore.broadcast;

import java.io.File;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import cn.trinea.android.common.util.DownloadManagerPro;

import com.smartx.bill.mepad.mestore.Observer.DownloadChangeObserver;
import com.smartx.bill.mepad.mestore.matadata.IOStreamDatas;
import com.smartx.bill.mepad.mestore.util.CommonTools.CommonViewHolder;

public class DownloadCompleteReceiver extends BroadcastReceiver {
	private long downloadId;
	private DownloadManagerPro downloadManagerPro;
	private Activity mActivity;
	private String appName;
	private String appPackageName;
	private CommonViewHolder mView;
	private DownloadChangeObserver downloadObserver;

	public DownloadCompleteReceiver(long downloadId, Activity mActivity,
			DownloadChangeObserver downloadObserver, CommonViewHolder mView,
			String appName, String appPackageName) {
		this.downloadId = downloadId;
		downloadManagerPro = new DownloadManagerPro(
				(DownloadManager) mActivity
						.getSystemService(mActivity.DOWNLOAD_SERVICE));
		this.mActivity = mActivity;
		this.downloadObserver = downloadObserver;
		this.mView = mView;
		this.appName = appName;
		this.appPackageName = appPackageName;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		/**
		 * get the id of download which have download success, if the id is my
		 * id and it's status is successful, then install it
		 **/
		Log.i("intent.getAction()",intent.getAction() + "" );
		if (intent.getAction() == DownloadManager.ACTION_DOWNLOAD_COMPLETE) {
			long completeDownloadId = intent.getLongExtra(
					DownloadManager.EXTRA_DOWNLOAD_ID, -1);
			if (completeDownloadId == downloadId) {
				// if download successful, install apk
				if (downloadManagerPro.getStatusById(downloadId) == DownloadManager.STATUS_SUCCESSFUL) {
					// OpenHelper.startViewIntent(context, downloadId, 0);
					installApk();
					// mActivity.unregisterReceiver(this);
					mActivity.getContentResolver().unregisterContentObserver(
							downloadObserver);
				}
			}
		} else if (intent.getAction() == Intent.ACTION_PACKAGE_ADDED) {
			String data = intent.getDataString();
			Log.i("InstallCompleteReceiver", "onReceive");
			Log.i("PACKAGE_NAME_START_INDEX", data);
			Log.i("PACKAGE_NAME_START_INDEX", data.substring(8));
			if (data.equals(appPackageName))
				// TODO Auto-generated method stub
				mView.appInstall.setVisibility(View.INVISIBLE);
			mView.appDownload.setVisibility(View.INVISIBLE);
			mView.appOpen.setVisibility(View.VISIBLE);
			mActivity.unregisterReceiver(this);
		}
	}

	private boolean installApk() {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		String apkFilePath = new StringBuilder(Environment
				.getExternalStorageDirectory().getAbsolutePath())
				.append(File.separator)
				.append(IOStreamDatas.DOWNLOAD_FOLDER_NAME)
				.append(File.separator).append(appName).toString();
		File file = new File(apkFilePath);
		if (file != null && file.length() > 0 && file.exists() && file.isFile()) {
			intent.setDataAndType(Uri.parse("file://" + apkFilePath),
					"application/vnd.android.package-archive");
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setAction("android.intent.action.mepad.INSTALL_PACKAGE");
			mActivity.sendBroadcast(intent);
			return true;
		}
		return false;
	}
};