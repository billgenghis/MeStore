package com.smartx.bill.mepad.mestore.thread;

import android.app.Activity;
import android.app.DownloadManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.smartx.bill.mepad.mestore.R;
import com.smartx.bill.mepad.mestore.util.CommonTools.CommonViewHolder;

/**
 * @ClassName: MyHandler
 * @Description: 安装过程中的UI刷新
 * @author coney Geng
 * @date 2014年8月28日 下午6:17:42
 * 
 */
public class RefreshDownloadUIHandler extends Handler {
	private CommonViewHolder mView;
	private Activity mActivity;
	private boolean animationFlag;
	private String appName;

	public RefreshDownloadUIHandler(CommonViewHolder mView, String appName,
			Activity mActivity) {
		this.mView = mView;
		this.mActivity = mActivity;
		animationFlag = false;
		this.appName = appName;
	}

	// public RefreshDownloadUIHandler(CommonViewHolder mView, Activity
	// mActivity) {
	// this.mView = mView;
	// this.mActivity = mActivity;
	// animationFlag = false;
	// }

	@Override
	public void handleMessage(Message msg) {
		super.handleMessage(msg);
		if (!appName.equals(mView.txtViewTitle.getTag())) {
			msg.what = 1;
		}
		switch (msg.what) {
		case 0:
			int downloadManagerStatus = (Integer) msg.obj;
			Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
					mActivity, R.anim.loading_animation);
			if (downloadManagerStatus == DownloadManager.STATUS_RUNNING) {
				Log.i("downloadManagerStatus", "STATUS_RUNNING");
				mView.appDownload
						.setBackgroundResource(R.drawable.ing_download);
				mView.appInstall.setVisibility(View.INVISIBLE);
				mView.appOpen.setVisibility(View.INVISIBLE);
				if (msg.arg2 < 0) {
					mView.appDownload.setVisibility(View.INVISIBLE);
					mView.appDownloadConnect.setVisibility(View.VISIBLE);
					if (!animationFlag) {
						mView.appDownloadConnect
								.startAnimation(hyperspaceJumpAnimation);
						animationFlag = true;
					}
				} else {
					mView.appDownloadConnect.clearAnimation();
					mView.appDownloadConnect.setVisibility(View.INVISIBLE);
					mView.appDownload.setVisibility(View.VISIBLE);
					mView.appDownload
							.setBackgroundResource(R.drawable.ing_download);
					mView.appDownload.setProgress(getNotiPercent(msg.arg1,
							msg.arg2));
				}
			} else if (downloadManagerStatus == DownloadManager.STATUS_PAUSED) {
				Log.i("downloadManagerStatus", "STATUS_PAUSED");
				mView.appDownloadConnect.clearAnimation();
				mView.appDownloadConnect.setVisibility(View.INVISIBLE);
				mView.appDownload.setBackgroundResource(R.drawable.ing_pause);
				break;
			} else if (downloadManagerStatus == DownloadManager.STATUS_PENDING) {
				Log.i("downloadManagerStatus", "STATUS_PENDING");
				mView.appInstall.setVisibility(View.INVISIBLE);
				mView.appOpen.setVisibility(View.INVISIBLE);
				mView.appDownload.setVisibility(View.INVISIBLE);
				// mView.appDownloadConnect.setVisibility(View.VISIBLE);
				// mView.appDownloadConnect
				// .startAnimation(hyperspaceJumpAnimation);
				break;
			} else if (downloadManagerStatus == DownloadManager.STATUS_FAILED) {
				Log.i("downloadManagerStatus", "STATUS_FAILED");
				mView.appInstall.setVisibility(View.VISIBLE);
				mView.appOpen.setVisibility(View.INVISIBLE);
				mView.appDownloadConnect.clearAnimation();
				mView.appDownload.setVisibility(View.INVISIBLE);
				// mView.appDownloadConnect.setVisibility(View.INVISIBLE);
				// } else {
				// mView.appInstall.setVisibility(View.VISIBLE);
				// mView.appOpen.setVisibility(View.INVISIBLE);
				// mView.appDownloadConnect.clearAnimation();
				// mView.appDownload.setVisibility(View.INVISIBLE);
			}
		}
	}

	private int getNotiPercent(long progress, long max) {
		int rate = 0;
		if (progress <= 0 || max <= 0) {
			rate = 0;
		} else if (progress > max) {
			rate = 100;
		} else {
			rate = (int) ((double) progress / max * 100);
		}
		// return new StringBuilder(16).append(rate).append("%").toString();
		return rate;
	}
}