package com.smartx.bill.mepad.listener;

import java.util.List;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MyHomeTextClickListener implements View.OnClickListener {
	private int index = 0;
	private ViewPager pager = null;
	private Activity mActivity;
	private List<TextView> tViews;

	public MyHomeTextClickListener(int i, ViewPager pager, Activity mActivity,
			List<TextView> tViews) {
		index = i;
		this.pager = pager;
		this.tViews = tViews;
		this.mActivity = mActivity;
	}

	@Override
	public void onClick(View v) {
		pager.setCurrentItem(index);
			tViews.get(index).setTextColor(mActivity.getResources().getColor(android.R.color.black));
			for (int i = 0; i < 4; i++) {
				if (i != index)
					tViews.get(i).setTextColor(mActivity.getResources().getColor(android.R.color.darker_gray));
			}
	}
};