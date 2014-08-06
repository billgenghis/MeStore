package com.smartx.bill.mepad.dialog;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.smartx.bill.mepad.R;
import com.smartx.bill.mepad.adapter.MyPagerAdapter;
import com.smartx.bill.mepad.home.Me;
import com.smartx.bill.mepad.listener.MyHomeTextClickListener;
import com.smartx.bill.mepad.listener.MyOnPageChangeListener;

public class MyAppInfoDialogBuilder extends AlertDialog.Builder {
	private View mDialogView;
	TextView t1, t2, t3, t4;
	LocalActivityManager manager = null;
	ViewPager pager = null;

	private int offset = 0;// 动画图片偏移量
//	private int currIndex = 0;// 当前页卡编号
	private int bmpW;// 动画图片宽度
	private ImageView cursor;// 动画图片
	private Context mContext;
	private Activity mActivity;

	public MyAppInfoDialogBuilder(Context context,Activity mActivity,Bundle savedInstanceState) {
		super(context);
		this.mActivity = mActivity;
		this.mContext = context;
		manager = new LocalActivityManager(mActivity, true);
		manager.dispatchCreate(savedInstanceState);
		mDialogView = View
				.inflate(context, R.layout.dialog_detail_info, null);
		setView(mDialogView);
		InitImageView();
		initPagerViewer();
		initTextView();
	}
	/**
	 * 初始化标题
	 */
	private void initTextView() {
		t1 = (TextView) mActivity.findViewById(R.id.dialog_detail_info);
		t2 = (TextView) mActivity.findViewById(R.id.dialog_judge_score);
		t3 = (TextView) mActivity.findViewById(R.id.dialog_same_developers);
		t4 = (TextView) mActivity.findViewById(R.id.dialog_detail_permission);

//		t1.setOnClickListener(new MyHomeTextClickListener(0,pager));
//		t2.setOnClickListener(new MyHomeTextClickListener(1,pager));
//		t3.setOnClickListener(new MyHomeTextClickListener(2,pager));
//		t4.setOnClickListener(new MyHomeTextClickListener(3,pager));

	}

	/**
	 * 初始化PageViewer
	 */
	private void initPagerViewer() {
		pager = (ViewPager) mActivity.findViewById(R.id.dialog_viewpage);
		final ArrayList<View> list = new ArrayList<View>();
//		Intent intent = new Intent(mContext, Me.class);
//		list.add(getView("Me", intent));
//		Intent intent2 = new Intent(mContext, Me.class);
//		list.add(getView("Me", intent2));
//		Intent intent3 = new Intent(mContext, Me.class);
//		list.add(getView("Me", intent3));
//		Intent intent4 = new Intent(mContext, Me.class);
//		list.add(getView("Me", intent4));
//
//		pager.setAdapter(new MyPagerAdapter(list));
//		pager.setCurrentItem(0);
//		pager.setOnPageChangeListener(new MyOnPageChangeListener(cursor,this,));
	}

	/**
	 * 初始化动画
	 */
	private void InitImageView() {
		cursor = (ImageView) mActivity.findViewById(R.id.cursor);
		bmpW = BitmapFactory.decodeResource(mActivity.getResources(), R.drawable.roller)
				.getWidth();// 获取图片宽度
		DisplayMetrics dm = new DisplayMetrics();
		mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;// 获取分辨率宽度
		offset = (screenW / 14 - bmpW) / 2;// 计算偏移量
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		cursor.setImageMatrix(matrix);// 设置动画初始位置
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		getMenuInflater().inflate(R.menu.activity_main, menu);
//		return true;
//	}

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
