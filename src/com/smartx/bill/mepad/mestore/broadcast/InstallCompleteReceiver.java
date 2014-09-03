package com.smartx.bill.mepad.mestore.broadcast;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.smartx.bill.mepad.mestore.util.CommonTools.CommonViewHolder;

public class InstallCompleteReceiver extends BroadcastReceiver {
	private static final int PACKAGE_NAME_START_INDEX = 8;
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
		String data = intent.getDataString();
		Log.i("InstallCompleteReceiver", "onReceive");
		Log.i("PACKAGE_NAME_START_INDEX", data);
		Log.i("PACKAGE_NAME_START_INDEX",
				data.substring(PACKAGE_NAME_START_INDEX));
		if (data.equals(appPackageName))
			// TODO Auto-generated method stub
			mView.appInstall.setVisibility(View.INVISIBLE);
		mView.appDownload.setVisibility(View.INVISIBLE);
		mView.appOpen.setVisibility(View.VISIBLE);
		mActivity.unregisterReceiver(this);
	}
}