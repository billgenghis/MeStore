package com.smartx.bill.mepad.mestore.dialog;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.smartx.bill.mepad.mestore.R;
import com.smartx.bill.mepad.mestore.adapter.MyViewPagerAdapter;
import com.smartx.bill.mepad.mestore.home.Category;
import com.smartx.bill.mepad.mestore.home.Me;
import com.smartx.bill.mepad.mestore.home.Ranking;
import com.smartx.bill.mepad.mestore.home.Special;
import com.smartx.bill.mepad.mestore.listener.MyHomeTextClickListener;
import com.smartx.bill.mepad.mestore.listener.MyOnPageChangeListener;
import com.smartx.bill.mepad.mestore.matadata.IOStreamDatas;

public class MyAppInfoDialogBuilder extends AlertDialog.Builder {
	private View mDialogView;
	TextView t1, t2, t3, t4;
	LocalActivityManager manager = null;
	ViewPager pager = null;

	private Context mContext;
	private Activity mActivity;
	List<TextView> tViews;

	public MyAppInfoDialogBuilder(Context context, Activity mActivity,
			Bundle savedInstanceState) {
		super(context);
		this.mActivity = mActivity;
		this.mContext = context;
		manager = new LocalActivityManager(mActivity, true);
		manager.dispatchCreate(savedInstanceState);
		mDialogView = View.inflate(context, R.layout.dialog_detail_info, null);
		setView(mDialogView);
		initdatas();
		initPagerViewer();
		initTextView();
	}

	/**
	 * 初始化标题
	 */

	private void initdatas() {
		pager = (ViewPager) mDialogView.findViewById(R.id.dialog_viewpage);
		Log.i("pager", mDialogView.findViewById(R.id.dialog_viewpage)
				.toString());
		// cursor = (ImageView) findViewById(R.id.cursor);
		tViews = new ArrayList<TextView>();
		t1 = (TextView) mDialogView.findViewById(R.id.dialog_detail_info);
		t2 = (TextView) mDialogView.findViewById(R.id.dialog_judge_score);
		t3 = (TextView) mDialogView.findViewById(R.id.dialog_same_developers);
		t4 = (TextView) mDialogView.findViewById(R.id.dialog_detail_permission);

		tViews.add(t1);
		tViews.add(t2);
		tViews.add(t3);
		tViews.add(t4);
	}
	/**
	 * 
	 */
	private void initTextView() {
		t1.setOnClickListener(new MyHomeTextClickListener(0, pager, mActivity,
				tViews, IOStreamDatas.VIEWPAGER_HOME));
		t2.setOnClickListener(new MyHomeTextClickListener(1, pager, mActivity,
				tViews, IOStreamDatas.VIEWPAGER_HOME));
		t3.setOnClickListener(new MyHomeTextClickListener(2, pager, mActivity,
				tViews, IOStreamDatas.VIEWPAGER_HOME));
		t4.setOnClickListener(new MyHomeTextClickListener(3, pager, mActivity,
				tViews, IOStreamDatas.VIEWPAGER_HOME));
	}

	/**
	 * 初始化PageViewer
	 */
	private void initPagerViewer() {
		final ArrayList<View> list = new ArrayList<View>();
		Intent intent = new Intent(mContext, Me.class);
		list.add(getView("Me", intent));
		Intent intent2 = new Intent(mContext, Ranking.class);
		list.add(getView("Ranking", intent2));
		Intent intent3 = new Intent(mContext, Category.class);
		list.add(getView("Category", intent3));
		Intent intent4 = new Intent(mContext, Special.class);
		list.add(getView("Special", intent4));

		pager.setAdapter(new MyViewPagerAdapter(list));
		pager.setCurrentItem(0);
		pager.setOnPageChangeListener(new MyOnPageChangeListener(mActivity,
				tViews, IOStreamDatas.VIEWPAGER_HOME));
	}

	/**
	 * 通过activity获取视图
	 * 
	 * @param id
	 * @param intent
	 * @return
	 */
	private View getView(String id, Intent intent) {
		return manager.startActivity(id, intent).getDecorView();
	}
}