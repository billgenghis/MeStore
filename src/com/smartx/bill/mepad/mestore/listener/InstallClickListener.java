package com.smartx.bill.mepad.mestore.listener;

import java.io.File;
import java.text.DecimalFormat;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import cn.trinea.android.common.util.DownloadManagerPro;
import cn.trinea.android.common.util.PreferencesUtils;

import com.smartx.bill.mepad.mestore.R;
import com.smartx.bill.mepad.mestore.Observer.DownloadChangeObserver;
import com.smartx.bill.mepad.mestore.application.MyApplication;
import com.smartx.bill.mepad.mestore.broadcast.DownloadCompleteReceiver;
import com.smartx.bill.mepad.mestore.broadcast.InstallCompleteReceiver;
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
	private String appPackageName;
	private RefreshDownloadUIHandler handler;
	private DownloadManager.Request request;
	private File folder;
	private CommonViewHolder mView;
	private boolean pauseFlag = false;

	public InstallClickListener(Activity activity, CommonViewHolder view,
			String downloadUrl, String appName, String appPackageName) {
		mActivity = activity;
		downloadManager = (DownloadManager) mActivity
				.getSystemService(mActivity.DOWNLOAD_SERVICE);
		MyApplication installApplication = (MyApplication) mActivity
				.getApplication();
		installApplication.setDownloadManager(downloadManager);
		downloadManagerPro = new DownloadManagerPro(downloadManager);
		this.downloadUrl = downloadUrl;
		this.appName = appName;
		this.appPackageName = appPackageName;
		this.mView = view;
	}

	@Override
	public void onClick(View arg0) {
		initDatas();
		sendBroadcastToDialog();
		sendBroadcastToLauncher();
		setListener();
	}

	/**
	 * @Title: initDatas
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void initDatas() {
		folder = Environment
				.getExternalStoragePublicDirectory(IOStreamDatas.DOWNLOAD_FOLDER_NAME);
		if (!folder.exists() || !folder.isDirectory()) {
			folder.mkdirs();
		}
		setDownloadManagerRequest();
		downloadId = downloadManager.enqueue(request);
		PreferencesUtils.putLong(mActivity, appPackageName, downloadId);
		initRegister();
	}

	/**
	 * @Title: sendBroadcastToLaunch
	 * @Description: sendBroadcastToLaunch
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void sendBroadcastToLauncher() {
		Intent installLaucherIntent = new Intent(
				"android.intent.action.mepad.DOWNLOAD_APPLICATION");
		installLaucherIntent.putExtra("meDownloadPackage", appPackageName);
		mView.imgViewFlag.setDrawingCacheEnabled(true);
		installLaucherIntent.putExtra("meDownloadBitmap",
				mView.imgViewFlag.getDrawingCache());
		installLaucherIntent.putExtra("meDownloadID", downloadId);
		mActivity.sendBroadcast(installLaucherIntent);
		mView.imgViewFlag.setDrawingCacheEnabled(false);
	}

	/**
	 * @Title: sendBroadcastToDialog
	 * @Description: sendBroadcastToDialog
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void sendBroadcastToDialog() {
		String broadcastFilter = MyBroadcast.MESTORE_BROADCAST_TITLE
				+ appPackageName;
		Intent intent = new Intent(broadcastFilter);
		mActivity.sendBroadcast(intent);
	}

	private void setListener() {
		mView.appDownload.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (!pauseFlag) {
					int i = downloadManagerPro.pauseDownload(downloadId);
					Log.i("pauseDownload", i + "  " + downloadId);
					pauseFlag = true;
				} else {
					int i = downloadManagerPro.resumeDownload(downloadId);
					Log.i("resumeDownload", i + "  " + downloadId);
					pauseFlag = false;
				}
			}
		});
	}

	public void initRegister() {
		handler = new RefreshDownloadUIHandler(mView, appName, mActivity);
		DownloadChangeObserver downloadObserver = new DownloadChangeObserver(
				handler, downloadManagerPro, downloadId);
		mActivity.getContentResolver().registerContentObserver(
				DownloadManagerPro.CONTENT_URI, true, downloadObserver);
		DownloadCompleteReceiver completeReceiver = new DownloadCompleteReceiver(
				downloadId, mActivity, downloadObserver, mView, appName,
				appPackageName);
		MyApplication installApplication = (MyApplication) mActivity
				.getApplication();
		installApplication.setBroadCast(String.valueOf(downloadId),
				completeReceiver);

		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
		mActivity.registerReceiver(completeReceiver, intentFilter);
		// InstallCompleteReceiver installCompleteReceiver = new
		// InstallCompleteReceiver(
		// mActivity, mView, appPackageName);
		// mActivity.registerReceiver(installCompleteReceiver,
		// new IntentFilter(Intent.ACTION_PACKAGE_ADDED));
	}

	private void setDownloadManagerRequest() {
		request = new DownloadManager.Request(Uri.parse(downloadUrl));
		request.setDestinationInExternalPublicDir(
				IOStreamDatas.DOWNLOAD_FOLDER_NAME, appName);
		request.setTitle(appName);
		request.setDescription(appName + "downloading");
		request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
		request.setVisibleInDownloadsUi(true);
		// request.allowScanningByMediaScanner();
		// request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
		// request.setShowRunningNotification(false);
		// request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
		request.setMimeType("application/vnd.android.package-archive");
	}

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
