package com.smartx.bill.mepad.mestore.listener;

import java.io.File;
import java.text.DecimalFormat;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import cn.trinea.android.common.util.DownloadManagerPro;
import cn.trinea.android.common.util.PreferencesUtils;

import com.smartx.bill.mepad.mestore.R;
import com.smartx.bill.mepad.mestore.matadata.IOStreamDatas;
import com.smartx.bill.mepad.mestore.myview.MyRoundProgressBar;
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
	private MyHandler handler;
	private DownloadChangeObserver downloadObserver;
	private CompleteReceiver completeReceiver;
	private DownloadManager.Request request;
	private File folder;
	private CommonViewHolder mView;
	private Button appInstall;
	private Button appOpen;
	private MyRoundProgressBar appDownload;

//	public InstallClickListener(Activity activity, CommonViewHolder view,
//			String downloadUrl, String appName) {
//		mActivity = activity;
//		handler = new MyHandler();
//		downloadManager = (DownloadManager) mActivity
//				.getSystemService(mActivity.DOWNLOAD_SERVICE);
//		downloadManagerPro = new DownloadManagerPro(downloadManager);
//		this.downloadUrl = downloadUrl;
//		this.appName = appName;
//		this.mView = view;
//	}

	public InstallClickListener(Activity activity, Button appInstall,
			Button appOpen, MyRoundProgressBar appDownload, String downloadUrl,
			String appName) {
		mActivity = activity;
		handler = new MyHandler();
		downloadManager = (DownloadManager) mActivity
				.getSystemService(mActivity.DOWNLOAD_SERVICE);
		downloadManagerPro = new DownloadManagerPro(downloadManager);
		this.downloadUrl = downloadUrl;
		this.appName = appName;
		this.appInstall = appInstall;
		this.appOpen = appOpen;
		this.appDownload = appDownload;
	}

	@Override
	public void onClick(View arg0) {
		initDatas();
		setDownloadManagerRequest();
		downloadId = downloadManager.enqueue(request);
		/** save download id to preferences **/
		PreferencesUtils.putLong(mActivity, IOStreamDatas.KEY_NAME_DOWNLOAD_ID,
				downloadId);
	}

	private void initDatas() {
		downloadObserver = new DownloadChangeObserver();
		mActivity.getContentResolver().registerContentObserver(
				DownloadManagerPro.CONTENT_URI, true, downloadObserver);
		completeReceiver = new CompleteReceiver();
		mActivity.registerReceiver(completeReceiver, new IntentFilter(
				DownloadManager.ACTION_DOWNLOAD_COMPLETE));
		folder = Environment
				.getExternalStoragePublicDirectory(IOStreamDatas.DOWNLOAD_FOLDER_NAME);
		if (!folder.exists() || !folder.isDirectory()) {
			folder.mkdirs();
		}
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

	class DownloadChangeObserver extends ContentObserver {

		public DownloadChangeObserver() {
			super(handler);
		}

		@Override
		public void onChange(boolean selfChange) {
			updateView();
		}

	}

	/**
	 * @ClassName: CompleteReceiver
	 * @Description: TODO(这里用一句话描述这个类的作用)
	 * @author coney Geng
	 * @date 2014年8月28日 下午7:40:46
	 * 
	 */
	class CompleteReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			/**
			 * get the id of download which have download success, if the id is
			 * my id and it's status is successful, then install it
			 **/
			long completeDownloadId = intent.getLongExtra(
					DownloadManager.EXTRA_DOWNLOAD_ID, -1);
			if (completeDownloadId == downloadId) {
				appInstall.setVisibility(View.INVISIBLE);
				appDownload.setVisibility(View.INVISIBLE);
				appOpen.setVisibility(View.VISIBLE);
			}

			mActivity.unregisterReceiver(completeReceiver);
			mActivity.getContentResolver().unregisterContentObserver(
					downloadObserver);
		}
	};

	public void updateView() {
		int[] bytesAndStatus = downloadManagerPro.getBytesAndStatus(downloadId);
		handler.sendMessage(handler.obtainMessage(0, bytesAndStatus[0],
				bytesAndStatus[1], bytesAndStatus[2]));
	}

	/**
	 * @ClassName: MyHandler
	 * @Description: TODO(这里用一句话描述这个类的作用)
	 * @author coney Geng
	 * @date 2014年8月28日 下午6:17:42
	 * 
	 */
	class MyHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				int status = (Integer) msg.obj;
				if (isDownloading(status)) {
					appInstall.setVisibility(View.INVISIBLE);
					appDownload.setVisibility(View.VISIBLE);
					appOpen.setVisibility(View.INVISIBLE);
					if (msg.arg2 < 0) {
//						Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(mActivity,
//								R.anim.loading_animation);
//						// 使用ImageView显示动画
//						appDownload
//								.setBackgroundResource(R.drawable.ing_connect);
//						appDownload.startAnimation(hyperspaceJumpAnimation);
					} else {
						appDownload
								.setBackgroundResource(R.drawable.ing_download);
						appDownload.setProgress(getNotiPercent(msg.arg1,
								msg.arg2));
					}
				} else {
//					appInstall.setVisibility(View.VISIBLE);
//					appDownload.setVisibility(View.INVISIBLE);
//					appOpen.setVisibility(View.INVISIBLE);
					break;
				}
			}
		}

		final DecimalFormat DOUBLE_DECIMAL_FORMAT = new DecimalFormat("0.##");

		public static final int MB_2_BYTE = 1024 * 1024;
		public static final int KB_2_BYTE = 1024;

		/**
		 * @param size
		 * @return
		 */
		public CharSequence getAppSize(long size) {
			if (size <= 0) {
				return "0M";
			}

			if (size >= MB_2_BYTE) {
				return new StringBuilder(16)
						.append(DOUBLE_DECIMAL_FORMAT.format((double) size
								/ MB_2_BYTE)).append("M");
			} else if (size >= KB_2_BYTE) {
				return new StringBuilder(16)
						.append(DOUBLE_DECIMAL_FORMAT.format((double) size
								/ KB_2_BYTE)).append("K");
			} else {
				return size + "B";
			}
		}

		public int getNotiPercent(long progress, long max) {
			int rate = 0;
			if (progress <= 0 || max <= 0) {
				rate = 0;
			} else if (progress > max) {
				rate = 100;
			} else {
				rate = (int) ((double) progress / max * 100);
			}
//			return new StringBuilder(16).append(rate).append("%").toString();
			return rate;
		}

		public boolean isDownloading(int downloadManagerStatus) {
			return downloadManagerStatus == DownloadManager.STATUS_RUNNING
					|| downloadManagerStatus == DownloadManager.STATUS_PAUSED
					|| downloadManagerStatus == DownloadManager.STATUS_PENDING;
		}
	}
}
