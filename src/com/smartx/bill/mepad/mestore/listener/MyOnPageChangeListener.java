package com.smartx.bill.mepad.mestore.listener;

import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.TextView;

import com.smartx.bill.mepad.mestore.R;
import com.smartx.bill.mepad.mestore.matadata.IOStreamDatas;
import com.smartx.bill.mepad.mestore.matadata.LayoutResourcesDatas;

/**
 * 页卡切换监听
 */
public class MyOnPageChangeListener implements OnPageChangeListener {
	private List<TextView> tViews;
	private int whichVP;
	private Activity mActivity;
//	private View mDialogView;

	public MyOnPageChangeListener(Activity mActivity, List<TextView> tViews,
			int whichVP) {
		this.tViews = tViews;
		this.whichVP = whichVP;
		this.mActivity = mActivity;
	}

//	public MyOnPageChangeListener(View mDialogView, List<TextView> tViews,
//			int whichVP) {
//		this.tViews = tViews;
//		this.whichVP = whichVP;
//		this.mDialogView = mDialogView;
//	}

	@Override
	public void onPageSelected(int arg0) {
		setItemColor(arg0);

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	private void setItemColor(int arg0) {
		if (whichVP == IOStreamDatas.VIEWPAGER_HOME) {
			tViews.get(arg0).setTextColor(Color.parseColor("#303030"));
			for (int i = 0; i < tViews.size(); i++) {
				if (i != arg0)
					tViews.get(i).setTextColor(Color.parseColor("#A0A1A2"));
			}
		} else if (whichVP == IOStreamDatas.VIEWPAGER_RECOM) {
			tViews.get(arg0).setTextColor(Color.parseColor("#ffffff"));
			mActivity.findViewById(R.id.me_recom_tab).setBackgroundResource(
					LayoutResourcesDatas.RECOMMENDATION_BACKGROUND[arg0]);
			for (int i = 0; i < tViews.size(); i++) {
				if (i != arg0)
					tViews.get(i).setTextColor(Color.parseColor("#a1a2a3"));
			}

		} else if (whichVP == IOStreamDatas.VIEWPAGER_CATE_RECOM) {
			tViews.get(arg0).setTextColor(Color.parseColor("#ffffff"));
			mActivity.findViewById(R.id.category_recom_tab)
					.setBackgroundResource(
							LayoutResourcesDatas.CATEGORY_BACKGROUND[arg0]);
			for (int i = 0; i < tViews.size(); i++) {
				if (i != arg0)
					tViews.get(i).setTextColor(Color.parseColor("#a1a2a3"));
			}

		} else if (whichVP == IOStreamDatas.VIEWPAGER_DIALOG) {
			tViews.get(arg0).setTextColor(Color.parseColor("#303030"));
			mActivity
					.findViewById(R.id.dialog_appinfo_tab)
					.setBackgroundResource(
							LayoutResourcesDatas.DIALOG_APPINFO_BACKGROUND[arg0]);
			for (int i = 0; i < tViews.size(); i++) {
				if (i != arg0)
					tViews.get(i).setTextColor(Color.parseColor("#a1a2a3"));
			}
		}
	}
}
