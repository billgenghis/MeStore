package com.smartx.bill.mepad.mestore.broadcast;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.smartx.bill.mepad.mestore.util.CommonTools.CommonViewHolder;

public class InstallCompleteReceiver extends BroadcastReceiver {
	private Activity mActivity;
	private String appPackageName;
	private CommonViewHolder mView;

	public InstallCompleteReceiver() {
		Log.i("InstallCompleteReceiver", "InstallCompleteReceiver");
	}

	public InstallCompleteReceiver(Activity mActivity, CommonViewHolder mView,
			String appPackageName) {
		this.mActivity = mActivity;
		this.mView = mView;
		this.appPackageName = appPackageName;
	}

	@Override
	public void onReceive(Context arg0, Intent intent) {
		String data = intent.getStringExtra("packageName");
		if (data.equals(appPackageName)) {
			mView.appInstall.setVisibility(View.INVISIBLE);
			mView.appDownload.setVisibility(View.INVISIBLE);
			mView.appOpen.setVisibility(View.VISIBLE);
			mActivity.unregisterReceiver(this);
		}
	}
}