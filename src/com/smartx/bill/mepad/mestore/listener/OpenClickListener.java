package com.smartx.bill.mepad.mestore.listener;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.View;
import android.view.View.OnClickListener;

public class OpenClickListener implements OnClickListener {

	private String mPackageName;
	private Activity mActivity;
	private PackageManager mPm;

	public OpenClickListener(String mPackageName, Activity mActivity) {
		this.mPackageName = mPackageName;
		this.mActivity = mActivity;
		mPm = mActivity.getPackageManager();
	}

	@Override
	public void onClick(View arg0) {
		Intent intent = new Intent();
		intent = mPm.getLaunchIntentForPackage(mPackageName);
		if (intent != null) {
			mActivity.startActivity(intent);
		}
	}
}
