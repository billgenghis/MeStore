package com.smartx.bill.mepad.mestore.listener;

import java.io.File;
import java.text.DecimalFormat;

import android.app.Activity;
import android.app.Application;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import cn.trinea.android.common.util.DownloadManagerPro;
import cn.trinea.android.common.util.PreferencesUtils;

import com.smartx.bill.mepad.mestore.Observer.DownloadChangeObserver;
import com.smartx.bill.mepad.mestore.application.MyApplication;
import com.smartx.bill.mepad.mestore.broadcast.DownloadCompleteReceiver;
import com.smartx.bill.mepad.mestore.broadcast.MySynchroBroadcast;
import com.smartx.bill.mepad.mestore.matadata.IOStreamDatas;
import com.smartx.bill.mepad.mestore.matadata.MyBroadcast;
import com.smartx.bill.mepad.mestore.thread.RefreshDownloadUIHandler;
import com.smartx.bill.mepad.mestore.util.CommonTools.CommonViewHolder;

/**
 * @ClassName: InstallClickListener
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author coney Geng
 * @date 2014年8月28日 下午6:17:39
 * 
 */
public class InstallClickListener implements OnClickListener {
	private Activity mActivity;
	private DownloadManager downloadManager;
	private DownloadManagerPro downloadManagerPro;
	private String downloadUrl;
	private long downloadId;
	private String appName;
	private RefreshDownloadUIHandler handler;
	private DownloadManager.Request request;
	private File folder;
	private CommonViewHolder mView;

	public InstallClickListener(Activity activity, CommonViewHolder view,
			String downloadUrl, String appName) {
		mActivity = activity;
		handler = new RefreshDownloadUIHandler(view, mActivity);
		downloadManager = (DownloadManager) mActivity
				.getSystemService(mActivity.DOWNLOAD_SERVICE);
		MyApplication installApplication = (MyApplication) mActivity
				.getApplication();
		installApplication.setDownloadManager(downloadManager);
		downloadManagerPro = new DownloadManagerPro(downloadManager);
		this.downloadUrl = downloadUrl;
		this.appName = appName;
		this.mView = view;
	}

	@Override
	public void onClick(View arg0) {
		initDatas();
		String broadcastFilter = MyBroadcast.MESTORE_BROADCAST_TITLE + appName;
		Intent intent = new Intent(broadcastFilter);
		mActivity.sendBroadcast(intent);
	}

	private void initDatas() {
		setDownloadManagerRequest();
		downloadId = downloadManager.enqueue(request);
		PreferencesUtils.putLong(mActivity, appName, downloadId);
		initRegister();
		folder = Environment
				.getExternalStoragePublicDirectory(IOStreamDatas.DOWNLOAD_FOLDER_NAME);
		if (!folder.exists() || !folder.isDirectory()) {
			folder.mkdirs();
		}
	}

	public void initRegister() {
		DownloadChangeObserver downloadObserver = new DownloadChangeObserver(
				handler, downloadManagerPro, downloadId);
		mActivity.getContentResolver().registerContentObserver(
				DownloadManagerPro.CONTENT_URI, true, downloadObserver);
		DownloadCompleteReceiver completeReceiver = new DownloadCompleteReceiver(
				downloadId, mActivity, downloadObserver, mView);
		mActivity.registerReceiver(completeReceiver, new IntentFilter(
				DownloadManager.ACTION_DOWNLOAD_COMPLETE));
	}

	private void setDownloadManagerRequest() {
		request = new DownloadManager.Request(Uri.parse(downloadUrl));
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
	}

	// class DownloadChangeObserver extends ContentObserver {
	//
	// DownloadManagerPro downloadManagerPro;
	// RefreshDownloadUIHandler handler;
	//
	// public DownloadChangeObserver(RefreshDownloadUIHandler handler,
	// DownloadManagerPro downloadManagerPro) {
	// super(handler);
	// this.handler = handler;
	// this.downloadManagerPro = downloadManagerPro;
	// }
	//
	// @Override
	// public void onChange(boolean selfChange) {
	// int[] bytesAndStatus = downloadManagerPro
	// .getBytesAndStatus(downloadId);
	// handler.sendMessage(handler.obtainMessage(0, bytesAndStatus[0],
	// bytesAndStatus[1], bytesAndStatus[2]));
	// }
	//
	// }

	// /**
	// * @ClassName: CompleteReceiver
	// * @Description: TODO(这里用一句话描述这个类的作用)
	// * @author coney Geng
	// * @date 2014年8月28日 下午7:40:46
	// *
	// */
	// class CompleteReceiver extends BroadcastReceiver {
	//
	// @Override
	// public void onReceive(Context context, Intent intent) {
	// /**
	// * get the id of download which have download success, if the id is
	// * my id and it's status is successful, then install it
	// **/
	// long completeDownloadId = intent.getLongExtra(
	// DownloadManager.EXTRA_DOWNLOAD_ID, -1);
	// if (completeDownloadId == downloadId) {
	// mView.appInstall.setVisibility(View.INVISIBLE);
	// mView.appDownload.setVisibility(View.INVISIBLE);
	// mView.appOpen.setVisibility(View.VISIBLE);
	// }
	// // mActivity.unregisterReceiver(completeReceiver);
	// // mActivity.getContentResolver().unregisterContentObserver(
	// // downloadObserver);
	// }
	// };

	public void updateView() {
		int[] bytesAndStatus = downloadManagerPro.getBytesAndStatus(downloadId);
		handler.sendMessage(handler.obtainMessage(0, bytesAndStatus[0],
				bytesAndStatus[1], bytesAndStatus[2]));
	}

	final DecimalFormat DOUBLE_DECIMAL_FORMAT = new DecimalFormat("0.##");

	public static final int MB_2_BYTE = 1024 * 1024;
	public static final int KB_2_BYTE = 1024;

	/**
	 * @Title: getAppSize
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param size
	 * @param @return 设定文件
	 * @return CharSequence 返回类型
	 * @throws
	 */
	public CharSequence getAppSize(long size) {
		if (size <= 0) {
			return "0M";
		}

		if (size >= MB_2_BYTE) {
			return new StringBuilder(16).append(
					DOUBLE_DECIMAL_FORMAT.format((double) size / MB_2_BYTE))
					.append("M");
		} else if (size >= KB_2_BYTE) {
			return new StringBuilder(16).append(
					DOUBLE_DECIMAL_FORMAT.format((double) size / KB_2_BYTE))
					.append("K");
		} else {
			return size + "B";
		}
	}
}
