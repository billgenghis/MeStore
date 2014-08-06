package com.smartx.bill.mepad.listener;

import java.util.List;

import android.R;
import android.app.Activity;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

public class MyHomeTextClickListener implements View.OnClickListener {
	private int index = 0;
	private ViewPager pager = null;
	private Activity mActivity;
	List<TextView> tViews;

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
		try {
			XmlResourceParser xrp = mActivity.getResources().getXml(
					R.color.black);
			ColorStateList csl = ColorStateList.createFromXml(
					mActivity.getResources(), xrp);
			tViews.get(index).setTextColor(csl);
			xrp = mActivity.getResources().getXml(R.color.darker_gray);
			csl = ColorStateList.createFromXml(mActivity.getResources(), xrp);
			for (int i = 0; i < 4; i++) {
				if (i != index)
					tViews.get(index).setTextColor(csl);
			}
		} catch (Exception e) {
		}
	}
};