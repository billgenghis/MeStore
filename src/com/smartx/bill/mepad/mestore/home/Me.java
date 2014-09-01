package com.smartx.bill.mepad.mestore.home;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.smartx.bill.mepad.mestore.R;
import com.smartx.bill.mepad.mestore.R.id;
import com.smartx.bill.mepad.mestore.adapter.MeGridviewAdapter;
import com.smartx.bill.mepad.mestore.adapter.MeHorizontalGridViewAdapter;
import com.smartx.bill.mepad.mestore.listener.ItemClickListener;
import com.smartx.bill.mepad.mestore.matadata.IOStreamDatas;
import com.smartx.bill.mepad.mestore.matadata.LayoutResourcesDatas;
import com.smartx.bill.mepad.mestore.myview.MyGalleryView;
import com.smartx.bill.mepad.mestore.myview.MyGridView;
import com.smartx.bill.mepad.mestore.recommend.Recommendation;
import com.smartx.bill.mepad.mestore.special.SpecialDetail;
import com.smartx.bill.mepad.mestore.uimgloader.AbsListViewBaseActivity;
import com.smartx.bill.mepad.mestore.util.HttpUtil;

public class Me extends AbsListViewBaseActivity {

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
	private JSONArray jsonArrayExcellent;
	private JSONArray jsonArrayNew;
	private JSONArray jsonArraySpecial;

	// DisplayImageOptions options;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_me);
		initCommonDatas(this, this, savedInstanceState);
		initDatas();
		downloadDatas();
	}

	private void downloadDatas() {
		HttpUtil.get(
				getDataUrl(IOStreamDatas.APP_DATA),
				getParams(null, null, IOStreamDatas.POSITION_EXCELLENT, null,
						null, null, null), new JsonHttpResponseHandler() {

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							JSONArray response) {
						initExcellentDatas(response);
						cancelDialog(true);
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							Throwable e, JSONObject errorResponse) {
						Toast.makeText(Me.this, "数据下载失败，请检查网络连接后重启!",
								Toast.LENGTH_SHORT).show();
						cancelDialog(false);
					}
				});
		HttpUtil.get(
				getDataUrl(IOStreamDatas.APP_DATA),
				getParams(null, null, IOStreamDatas.POSITION_NEW, null, null,
						null, null), new JsonHttpResponseHandler() {

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							JSONArray response) {
						initNewDatas(response);
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							Throwable e, JSONArray errorResponse) {
					}
				});
		HttpUtil.get(getDataUrl(IOStreamDatas.SPECIAL_DATA),
				getParams(null, null, null, null, null, null, null),
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers,
							JSONArray response) {
						initSpecialDatas(response);
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							Throwable e, JSONArray errorResponse) {
					}
				});
	}

	private void initDatas() {
		mName = (TextView) findViewById(R.id.me_name);
		mComIntroduce = (TextView) findViewById(R.id.me_competitve_introduce);
		mComMore = (TextView) findViewById(R.id.me_competitve_more);

		mHeadPic = (ImageView) findViewById(R.id.me_head_pic);
		mSetting = (ImageView) findViewById(R.id.me_setting);
		mUpdateApps = (TextView) findViewById(R.id.me_update_apps);
		mAllApps = (TextView) findViewById(R.id.me_all_apps);

		mName = (TextView) findViewById(R.id.me_name);
		mNewIntroduce = (TextView) findViewById(R.id.me_new_introduce);
		mNewMore = (TextView) findViewById(R.id.me_new_more);

		mCompetitiveGridView = (MyGridView) findViewById(R.id.me_competitive_girdview);
		mNewGridView = (MyGridView) findViewById(R.id.me_new_girdview);
		myGridView = (GridView) findViewById(R.id.me_special);
		setListener();
	}

	private void initExcellentDatas(JSONArray response) {

		jsonArrayExcellent = response;
		mCompetitiveAdapter = new MeGridviewAdapter(this, jsonArrayExcellent,
				imageLoader);
		mCompetitiveGridView.setNumColumns(3);
		mCompetitiveGridView.setAdapter(mCompetitiveAdapter);
		mCompetitiveGridView.setOnTouchListener(myGestureListener);
		mCompetitiveGridView.setOnItemClickListener(new ItemClickListener(
				mContext, mActivity, savedInstanceState, response));
	}

	private void initNewDatas(JSONArray response) {
		jsonArrayNew = response;
		mNewAdapter = new MeGridviewAdapter(this, jsonArrayNew, imageLoader);
		mNewGridView.setNumColumns(3);
		mNewGridView.setAdapter(mNewAdapter);
		mNewGridView.setFocusable(false);
		mNewGridView.setOnTouchListener(myGestureListener);
		mNewGridView.setOnItemClickListener(new ItemClickListener(mContext,
				mActivity, savedInstanceState, response));
	}

	private void initSpecialDatas(JSONArray response) {
		ViewGroup.LayoutParams params = myGridView.getLayoutParams();
		jsonArraySpecial = response;
		int dishtypes = jsonArraySpecial.length();

		params.width = LayoutResourcesDatas.ME_REVIEWIMAGE_WIDTH * dishtypes;
		myGridView.setLayoutParams(params);
		// 设置列数为得到的list长度
		((GridView) myGridView).setNumColumns(dishtypes);
		myGridView.setAdapter(new MeHorizontalGridViewAdapter(mActivity,
				jsonArraySpecial, imageLoader));
		myGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Intent intent = new Intent(Me.this, SpecialDetail.class);
				Bundle mBundle = new Bundle();
				JSONObject mJsonObject;
				try {
					mJsonObject = jsonArraySpecial.getJSONObject(position);
					mBundle.putString("image", mJsonObject.getString("image"));
					mBundle.putString("specialId",
							mJsonObject.getString("special_id"));
					mBundle.putString("specialTitle",
							mJsonObject.getString("s_title"));
					mBundle.putString("specialDescription",
							mJsonObject.getString("s_description"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				intent.putExtra("SpecialInfo", mBundle);
				startActivity(intent);
			}
		});
	}

	private void setListener() {
		// setGridViewListener();
		mComMore.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Me.this, Recommendation.class);
				intent.putExtra("recomType", IOStreamDatas.POSITION_EXCELLENT);
				startActivity(intent);
			}
		});
		mNewMore.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Me.this, Recommendation.class);
				intent.putExtra("recomType", IOStreamDatas.POSITION_NEW);
				startActivity(intent);
			}
		});
	}

	@Override
	protected void onPause() {
		super.onPause();
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
