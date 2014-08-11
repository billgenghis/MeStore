package com.smartx.bill.mepad.home;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.smartx.bill.mepad.R;
import com.smartx.bill.mepad.adapter.MeGalleryAdapter;
import com.smartx.bill.mepad.adapter.MeGridviewAdapter;
import com.smartx.bill.mepad.dialog.MyAppInfoDialogBuilder;
import com.smartx.bill.mepad.iostream.DownLoadDatas;
import com.smartx.bill.mepad.matadata.IOStreamDatas;
import com.smartx.bill.mepad.myview.MyGalleryView;
import com.smartx.bill.mepad.myview.MyGridView;

public class Me extends Activity {

	private MeGridviewAdapter mCompetitiveAdapter;
	private MeGridviewAdapter mNewAdapter;
	private MyGridView mCompetitiveGridView;
	private MyGridView mNewGridView;
	private MyGalleryView mySpecialGallery;
	private TextView mUpdateApps;
	private TextView mAllApps;
	private TextView mName;
	private TextView mComIntroduce;
	private TextView mComMore;
	private TextView mNewIntroduce;
	private TextView mNewMore;
	private ImageView mHeadPic;
	private ImageView mSetting;
	private Bundle savedInstanceState;
	private Activity mActivity;
	private Context mContext;
	private JSONArray jsonArrayExcellent;
	private JSONArray jsonArrayNew;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_me);
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		this.savedInstanceState = savedInstanceState;
		mActivity = this;
		mContext = this;
		initdatas();
		initGridView();
	}

	private void initdatas() {
		mName = (TextView) findViewById(R.id.me_name);
		mComIntroduce = (TextView) findViewById(R.id.me_competitve_introduce);
		mComMore = (TextView) findViewById(R.id.me_competitve_more);
		mNewIntroduce = (TextView) findViewById(R.id.me_new_introduce);
		mNewMore = (TextView) findViewById(R.id.me_new_more);

		mHeadPic = (ImageView) findViewById(R.id.me_head_pic);
		mSetting = (ImageView) findViewById(R.id.me_setting);
		mUpdateApps = (TextView) findViewById(R.id.me_update_apps);
		mAllApps = (TextView) findViewById(R.id.me_all_apps);
		mySpecialGallery = (MyGalleryView) findViewById(R.id.me_special_gallery);

		try {
			jsonArrayExcellent = DownLoadDatas.getDatasFromServer(null, null,
					IOStreamDatas.POSITION_EXCELLENT, null,
					IOStreamDatas.APP_DATA);
			jsonArrayNew = DownLoadDatas.getDatasFromServer(null, null,
					IOStreamDatas.POSITION_NEW, null, IOStreamDatas.APP_DATA);
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mCompetitiveAdapter = new MeGridviewAdapter(this, jsonArrayExcellent);
		mCompetitiveGridView = (MyGridView) findViewById(R.id.me_competitive_girdview);

		mNewAdapter = new MeGridviewAdapter(this, jsonArrayNew);
		mNewGridView = (MyGridView) findViewById(R.id.me_new_girdview);

		mySpecialGallery.setAdapter(new MeGalleryAdapter(this, null));// 暂时没有数据
	}

	private void initGridView() {

		mCompetitiveGridView.setNumColumns(3);
		mCompetitiveGridView.setAdapter(mCompetitiveAdapter);
		mCompetitiveGridView.setFocusable(false);

		mNewGridView.setNumColumns(3);
		mNewGridView.setAdapter(mNewAdapter);
		mNewGridView.setFocusable(false);
		// setGridViewListener();
	}

	private void setGridViewListener() {
		mCompetitiveGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				MyAppInfoDialogBuilder qustomDialogBuilder = new MyAppInfoDialogBuilder(
						mContext, mActivity, savedInstanceState);
				qustomDialogBuilder.show();
			}
		});
		mNewGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				MyAppInfoDialogBuilder qustomDialogBuilder = new MyAppInfoDialogBuilder(
						mContext, mActivity, savedInstanceState);
				qustomDialogBuilder.show();
			}
		});
		mySpecialGallery.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				MyAppInfoDialogBuilder qustomDialogBuilder = new MyAppInfoDialogBuilder(
						mContext, mActivity, savedInstanceState);
				qustomDialogBuilder.show();
			}
		});
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	/**
	 * 屏幕切换时进行处理 如果屏幕是竖屏，则显示3列，如果是横屏，则显示4列
	 */
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		int imageCol = 3;
		try {

			super.onConfigurationChanged(newConfig);
			// 如果屏幕是竖屏，则显示3列，如果是横屏，则显示4列
			if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
				imageCol = 4;
				Toast.makeText(Me.this, "现在是横屏", Toast.LENGTH_SHORT).show();
			} else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
				imageCol = 3;
			}
			mCompetitiveGridView.setNumColumns(imageCol);
			mCompetitiveGridView.setAdapter(mCompetitiveAdapter);
			mNewGridView.setNumColumns(imageCol);
			mNewGridView.setAdapter(mNewAdapter);
			// ia.notifyDataSetChanged();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
