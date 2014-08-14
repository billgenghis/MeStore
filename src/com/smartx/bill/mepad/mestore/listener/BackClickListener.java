package com.smartx.bill.mepad.mestore.listener;

import android.app.Activity;
import android.view.View;

public class BackClickListener implements View.OnClickListener{

	private Activity mActivity;
	public BackClickListener (Activity mActivity){
		this.mActivity = mActivity;
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		mActivity.finish();
	}

}
