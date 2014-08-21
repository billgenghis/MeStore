package com.smartx.bill.mepad.mestore.dialog;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.smartx.bill.mepad.mestore.R;
import com.smartx.bill.mepad.mestore.adapter.MyViewPagerAdapter;
import com.smartx.bill.mepad.mestore.listener.MyHomeTextClickListener;
import com.smartx.bill.mepad.mestore.listener.MyOnPageChangeListener;
import com.smartx.bill.mepad.mestore.matadata.IOStreamDatas;

public class MyAppInfoDialogBuilder extends Dialog {
	private View mDialogView;
	TextView t1, t2, t3, t4;
	LocalActivityManager manager = null;
	ViewPager pager = null;

	private Context mContext;
	private Activity mActivity;
	private JSONObject appInfo;
	private ImageView imgViewFlag;
	private TextView txtViewTitle;
	private TextView downloadCount;
	private RatingBar appScore;
	private Button appInstall;
	private Bitmap mBitmap;
	List<TextView> tViews;

	public MyAppInfoDialogBuilder(Context context, Activity mActivity,
			Bundle savedInstanceState, JSONObject jsonObject, Bitmap mBitmap)
			throws JSONException {
		super(context);
		this.mActivity = mActivity;
		this.mContext = context;
		this.appInfo = jsonObject;
		this.mBitmap = mBitmap;
		manager = new LocalActivityManager(mActivity, true);
		manager.dispatchCreate(savedInstanceState);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		mDialogView = View.inflate(context, R.layout.dialog_detail_info, null);
		setContentView(mDialogView);
		initdatas();
		initPagerViewer();
		initTextView();
	}

	/**
	 * 初始化标题
	 * 
	 * @throws JSONException
	 */

	private void initdatas() throws JSONException {
		pager = (ViewPager) mDialogView.findViewById(R.id.dialog_viewpage);
		// cursor = (ImageView) findViewById(R.id.cursor);
		tViews = new ArrayList<TextView>();
		t1 = (TextView) mDialogView.findViewById(R.id.dialog_detail_info);
		t2 = (TextView) mDialogView.findViewById(R.id.dialog_judge_score);
		t3 = (TextView) mDialogView.findViewById(R.id.dialog_same_developers);
		t4 = (TextView) mDialogView.findViewById(R.id.dialog_detail_permission);
		txtViewTitle = (TextView) mDialogView.findViewById(R.id.app_title);
		imgViewFlag = (ImageView) mDialogView.findViewById(R.id.app_icon);
		appScore = (RatingBar) mDialogView.findViewById(R.id.app_score);
		downloadCount = (TextView) mDialogView
				.findViewById(R.id.app_download_count);
		appInstall = (Button) mDialogView.findViewById(R.id.app_install);

		txtViewTitle.setText(appInfo.getString("title"));
		downloadCount.setText(appInfo.getString("downloads") + "次下载");
		appScore.setRating(Float.parseFloat(appInfo.getString("score")));
		imgViewFlag.setImageBitmap(mBitmap);
		imgViewFlag.setDrawingCacheEnabled(false);
		appScore.setFocusable(false);
		
		mDialogView.findViewById(R.id.dialog_appinfo_tab).setVisibility(View.GONE);
		t1.setVisibility(View.GONE);
		t2.setVisibility(View.GONE);
		t3.setVisibility(View.GONE);
		t4.setVisibility(View.GONE);
//		tViews.add(t1);
//		tViews.add(t2);
//		tViews.add(t3);
//		tViews.add(t4);
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
	 * 
	 * @throws JSONException
	 */
	private void initPagerViewer() throws JSONException {
		final ArrayList<View> list = new ArrayList<View>();
		Intent intent = new Intent(mContext, AppDetailInfo.class);
		intent.putExtra("appInfo", appInfo.toString());
		list.add(getView("AppDetailInfo", intent));

//		Intent intent2 = new Intent(mContext, AppScore.class);
//		list.add(getView("AppScore", intent2));
//
//		Intent intent3 = new Intent(mContext, SameDevelpersApp.class);
//		intent3.putExtra("developer", appInfo.getString("developer"));
//		list.add(getView("SameDevelpersApp", intent3));
//
//		Intent intent4 = new Intent(mContext, AppPermission.class);
//		list.add(getView("AppPermission", intent4));

		pager.setAdapter(new MyViewPagerAdapter(list));
		pager.setCurrentItem(0);
//		pager.setOnPageChangeListener(new MyOnPageChangeListener(mDialogView,
//				tViews, IOStreamDatas.VIEWPAGER_DIALOG));
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