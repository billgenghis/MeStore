package com.smartx.bill.mepad.mestore.listener;

import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.TextView;

import com.smartx.bill.mepad.mestore.R;
import com.smartx.bill.mepad.mestore.matadata.IOStreamDatas;

/**
 * 页卡切换监听
 */
public class MyOnPageChangeListener implements OnPageChangeListener {
	private List<TextView> tViews;
	private int whichVP;
	private Activity mActivity;

	public MyOnPageChangeListener(Activity mActivity, List<TextView> tViews,
			int whichVP) {
		this.tViews = tViews;
		this.whichVP = whichVP;
		this.mActivity = mActivity;
	}

	@Override
	public void onPageSelected(int arg0) {
		if (whichVP == IOStreamDatas.VIEWPAGER_HOME) {
			tViews.get(arg0).setTextColor(Color.parseColor("#303030"));
			for (int i = 0; i < 4; i++) {
				if (i != arg0)
					tViews.get(i).setTextColor(Color.parseColor("#A0A1A2"));
			}
		} else if (whichVP == IOStreamDatas.VIEWPAGER_RECOM) {
			if (arg0 == 0) {
				mActivity.findViewById(R.id.me_recom_tab)
						.setBackgroundResource(R.drawable.tab_left);
				((TextView) mActivity.findViewById(R.id.me_recom_excellent))
						.setTextColor(Color.parseColor("#ffffff"));
				((TextView) mActivity.findViewById(R.id.me_recom_new))
						.setTextColor(Color.parseColor("#a1a2a3"));
			} else if (arg0 == 1) {
				mActivity.findViewById(R.id.me_recom_tab)
						.setBackgroundResource(R.drawable.tab_right);
				((TextView) mActivity.findViewById(R.id.me_recom_excellent))
						.setTextColor(Color.parseColor("#a1a2a3"));
				((TextView) mActivity.findViewById(R.id.me_recom_new))
						.setTextColor(Color.parseColor("#ffffff"));
			}
		} else if (whichVP == IOStreamDatas.VIEWPAGER_CATE_RECOM) {
			if (arg0 == 0) {
				mActivity.findViewById(R.id.category_recom_tab)
						.setBackgroundResource(R.drawable.tab2_left);
				((TextView) mActivity
						.findViewById(R.id.category_recom_excellent))
						.setTextColor(Color.parseColor("#ffffff"));
				((TextView) mActivity.findViewById(R.id.category_recom_ranking))
						.setTextColor(Color.parseColor("#a1a2a3"));
				((TextView) mActivity.findViewById(R.id.category_recom_new))
						.setTextColor(Color.parseColor("#a1a2a3"));
			} else if (arg0 == 1) {
				mActivity.findViewById(R.id.category_recom_tab)
						.setBackgroundResource(R.drawable.tab2_middle);
				((TextView) mActivity
						.findViewById(R.id.category_recom_excellent))
						.setTextColor(Color.parseColor("#a1a2a3"));
				((TextView) mActivity.findViewById(R.id.category_recom_ranking))
						.setTextColor(Color.parseColor("#ffffff"));
				((TextView) mActivity.findViewById(R.id.category_recom_new))
						.setTextColor(Color.parseColor("#a1a2a3"));
			} else if (arg0 == 2) {
				mActivity.findViewById(R.id.category_recom_tab)
						.setBackgroundResource(R.drawable.tab2_right);
				((TextView) mActivity
						.findViewById(R.id.category_recom_excellent))
						.setTextColor(Color.parseColor("#a1a2a3"));
				((TextView) mActivity.findViewById(R.id.category_recom_ranking))
						.setTextColor(Color.parseColor("#a1a2a3"));
				((TextView) mActivity.findViewById(R.id.category_recom_new))
						.setTextColor(Color.parseColor("#ffffff"));
			}
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}
}
