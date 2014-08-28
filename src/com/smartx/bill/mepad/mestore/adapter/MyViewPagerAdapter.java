package com.smartx.bill.mepad.mestore.adapter;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Pager适配器
 */
public class MyViewPagerAdapter extends PagerAdapter {
	List<View> list = new ArrayList<View>();

	public MyViewPagerAdapter(ArrayList<View> list) {
		this.list = list;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		ViewPager pViewPager = ((ViewPager) container);
		pViewPager.removeView(list.get(position));
		Log.i("nothing", "nothing");
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;

	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object instantiateItem(View arg0, int arg1) {
		ViewPager pViewPager = ((ViewPager) arg0);
		pViewPager.addView(list.get(arg1));
		return list.get(arg1);
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {

	}

	@Override
	public Parcelable saveState() {
		return null;
	}

	@Override
	public void startUpdate(View arg0) {
	}
}
