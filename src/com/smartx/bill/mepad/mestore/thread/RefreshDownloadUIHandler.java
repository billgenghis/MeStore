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

	public RefreshDownloadUIHandler(CommonViewHolder mView, Activity mActivity) {
		this.mView = mView;
		this.mActivity = mActivity;
	}

	@Override
	public void handleMessage(Message msg) {
		super.handleMessage(msg);
		switch (msg.what) {
		case 0:
			int downloadManagerStatus = (Integer) msg.obj;
			Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
					mActivity, R.anim.loading_animation);
			if (downloadManagerStatus == DownloadManager.STATUS_RUNNING) {
				mView.appInstall.setVisibility(View.INVISIBLE);
				mView.appOpen.setVisibility(View.INVISIBLE);
				if (msg.arg2 < 0) {
					mView.appDownload.setVisibility(View.INVISIBLE);
					mView.appDownloadConnect.setVisibility(View.VISIBLE);
					mView.appDownloadConnect
							.startAnimation(hyperspaceJumpAnimation);
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
				mView.appDownloadConnect.clearAnimation();
				mView.appDownloadConnect.setVisibility(View.INVISIBLE);
				mView.appDownload
						.setBackgroundResource(R.drawable.ing_download);
				break;
			} else if (downloadManagerStatus == DownloadManager.STATUS_PENDING) {
				mView.appInstall.setVisibility(View.INVISIBLE);
				mView.appOpen.setVisibility(View.INVISIBLE);
				mView.appDownload.setVisibility(View.INVISIBLE);
				// mView.appDownloadConnect.setVisibility(View.VISIBLE);
				// mView.appDownloadConnect
				// .startAnimation(hyperspaceJumpAnimation);
				break;
			} else if (downloadManagerStatus == DownloadManager.STATUS_FAILED) {
				mView.appInstall.setVisibility(View.VISIBLE);
				mView.appOpen.setVisibility(View.INVISIBLE);
				mView.appDownloadConnect.clearAnimation();
				mView.appDownload.setVisibility(View.INVISIBLE);
				// mView.appDownloadConnect.setVisibility(View.INVISIBLE);
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