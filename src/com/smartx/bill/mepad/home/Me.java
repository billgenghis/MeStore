package com.smartx.bill.mepad.home;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.smartx.bill.mepad.R;
import com.smartx.bill.mepad.adapter.GridviewAdapter;
import com.smartx.bill.mepad.dialog.MyAppInfoDialogBuilder;
import com.smartx.bill.mepad.dialog.QustomDialogBuilder;

public class Me extends Activity {

	private GridviewAdapter mCompetitiveAdapter;
	private GridviewAdapter mNewAdapter;
	private GridView mCompetitiveGridView;
	private GridView mNewGridView;
	private TextView mName;
	private TextView mComIntroduce;
	private TextView mComMore;
	private TextView mNewIntroduce;
	private TextView mNewMore;
	private ImageView mHeadPic;
	private ImageView mSetting;
	private Button mUpdateAppsButton;
	private Button mAllAppsButton;
	private Bundle savedInstanceState;
	private Activity mActivity;
	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_me);
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
		mUpdateAppsButton = (Button) findViewById(R.id.me_update_apps_button);
		mAllAppsButton = (Button) findViewById(R.id.me_all_apps_button);
		mCompetitiveAdapter = new GridviewAdapter(this,
				new ArrayList<HashMap<String, Object>>());
		mCompetitiveGridView = (GridView) findViewById(R.id.me_competitive_girdview);

		mNewAdapter = new GridviewAdapter(this,
				new ArrayList<HashMap<String, Object>>());
		mNewGridView = (GridView) findViewById(R.id.me_new_girdview);
	}

	private void initGridView() {

		mCompetitiveGridView.setNumColumns(3);
		mCompetitiveGridView.setAdapter(mCompetitiveAdapter);
		mCompetitiveGridView.setFocusable(false);

		mNewGridView.setNumColumns(3);
		mNewGridView.setAdapter(mNewAdapter);
		mNewGridView.setFocusable(false);
		setGridViewListener();
	}

	private void setGridViewListener() {
		mCompetitiveGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

				Resources resources = arg1.getContext().getResources();
				int indentify = resources.getIdentifier(mCompetitiveAdapter
						.getItem(position), "drawable", arg1.getContext()
						.getPackageName());
				if (indentify > 0) {
					MyAppInfoDialogBuilder qustomDialogBuilder = new MyAppInfoDialogBuilder(
							mContext, mActivity, savedInstanceState);
					qustomDialogBuilder.show();
				}
			}
		});
		mNewGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

				Resources resources = arg1.getContext().getResources();
				int indentify = resources.getIdentifier(mNewAdapter
						.getItem(position), "drawable", arg1.getContext()
						.getPackageName());
				Log.i("drawable", mNewAdapter.getItem(position) + indentify
						+ "" + arg1.getContext().getPackageName());
				if (indentify > 0) {
					QustomDialogBuilder qustomDialogBuilder = new QustomDialogBuilder(
							arg1.getContext())
							.setMessage(
									"                                                         ")
							.setCustomView(R.layout.dialog_detail_info,
									arg1.getContext())
							.setIcon(getResources().getDrawable(indentify));
					qustomDialogBuilder.show();
				}
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
