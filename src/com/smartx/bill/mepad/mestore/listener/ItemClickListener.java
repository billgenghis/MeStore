package com.smartx.bill.mepad.mestore.listener;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.smartx.bill.mepad.mestore.dialog.MyAppInfoDialogBuilder;


public class ItemClickListener implements OnItemClickListener{

	private Context mContext;
	private Activity mActivity;
	private Bundle savedInstanceState;
	public ItemClickListener(Context mContext, Activity mActivity,
			Bundle savedInstanceState) {
		this.mActivity = mActivity;
		this.mContext = mContext;
		this.savedInstanceState = savedInstanceState;
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		MyAppInfoDialogBuilder qustomDialogBuilder = new MyAppInfoDialogBuilder(
				mContext, mActivity, savedInstanceState);
		qustomDialogBuilder.show();
		Toast.makeText(mContext, "现在是横屏", Toast.LENGTH_SHORT)
		.show();
	}

}
