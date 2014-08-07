package com.smartx.bill.mepad.listener;

import java.util.List;

import android.app.Activity;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.TextView;

/**
 * 页卡切换监听
 */
public class MyOnPageChangeListener implements OnPageChangeListener {
	private Activity mActivity;
	private List<TextView> tViews;

	public MyOnPageChangeListener(Activity mActivity, List<TextView> tViews) {
		this.mActivity = mActivity;
		this.tViews = tViews;
	}

	@Override
	public void onPageSelected(int arg0) {
		tViews.get(arg0).setTextColor(
				mActivity.getResources().getColor(android.R.color.black));
		for (int i = 0; i < 4; i++) {
			if (i != arg0)
				tViews.get(i).setTextColor(
						mActivity.getResources().getColor(
								android.R.color.darker_gray));
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}
}
