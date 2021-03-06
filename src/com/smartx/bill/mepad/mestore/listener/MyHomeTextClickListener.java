package com.smartx.bill.mepad.mestore.listener;

import java.util.List;

import com.smartx.bill.mepad.mestore.matadata.IOStreamDatas;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;



/*
 * all of viewPagers share the one Clicklistener, seperated by whichVP 
 */
public class MyHomeTextClickListener implements View.OnClickListener {
	private int index = 0;
	private ViewPager pager = null;
	private List<TextView> tViews;
	private int whichVP;

	public MyHomeTextClickListener(int i, ViewPager pager, Activity mActivity,
			List<TextView> tViews, int whichVP) {
		index = i;
		this.pager = pager;
		this.tViews = tViews;
		this.whichVP = whichVP;
	}

	@Override
	public void onClick(View v) {

		pager.setCurrentItem(index);
		if (whichVP == IOStreamDatas.VIEWPAGER_HOME) {
//			tViews.get(index).setTextColor(Color.parseColor("#303030"));
//			for (int i = 0; i < 4; i++) {
//				if (i != index)
//					tViews.get(i).setTextColor(Color.parseColor("#A0A1A2"));
//			}
		} else if (whichVP == IOStreamDatas.VIEWPAGER_RECOM) {

		} else if (whichVP == IOStreamDatas.VIEWPAGER_CATE_RECOM) {

		}
	}
};