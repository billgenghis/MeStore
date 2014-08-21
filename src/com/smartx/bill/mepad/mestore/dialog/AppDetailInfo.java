package com.smartx.bill.mepad.mestore.dialog;

import java.sql.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils.TruncateAt;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.smartx.bill.mepad.mestore.R;
import com.smartx.bill.mepad.mestore.adapter.HorizontalGridViewAdapter;
import com.smartx.bill.mepad.mestore.matadata.LayoutResourcesDatas;
import com.smartx.bill.mepad.mestore.uimgloader.AbsListViewBaseActivity;
import com.smartx.bill.mepad.mestore.util.CommonTools;

public class AppDetailInfo extends AbsListViewBaseActivity {

	// private MyGridView mRankingGridView;
	private JSONArray jsonArrayImage;
	private TextView appDescription;
	private TextView appDeveloper;
	private TextView appUpdateTime;
	private TextView appUpLog;
	private TextView appVersion;
	private ImageView descriShowAll;
	private JSONObject appInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_app_info);
		this.savedInstanceState = savedInstanceState;
		mActivity = this;
		mContext = this;
		try {
			appInfo = new JSONObject(getIntent().getStringExtra("appInfo"));
			initDatas();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initGridView();
	}

	private void initDatas() throws NumberFormatException, JSONException {
		myGridView = (GridView) findViewById(R.id.dishtype);
		appDescription = (TextView) findViewById(R.id.app_description);
		descriShowAll = (ImageView) findViewById(R.id.dialog_app_show_all);
		appDeveloper = (TextView) findViewById(R.id.dialog_app_developer);
		appUpdateTime = (TextView) findViewById(R.id.dialog_app_update);
		appVersion = (TextView) findViewById(R.id.dialog_app_version);
		appUpLog = (TextView) findViewById(R.id.dialog_app_uplog);

		appDescription.setText(CommonTools.getHtmlText(appInfo
				.getString("content")));
		appDeveloper.setText("开发者:" + appInfo.getString("developer"));
		long time = Long.parseLong(appInfo.getString("update_time") + "000");// PHP转化到java需要补后三位
		Date date = new Date(time);
		appUpdateTime.setText("更新于:" + date.toString());
		new myAsyncTask().execute();
	}

	private void initGridView() {
		ViewGroup.LayoutParams params = myGridView.getLayoutParams();
		// int dishtypes = jsonArrayImage.length();
		int dishtypes = 5;

		params.width = LayoutResourcesDatas.APP_REVIEWIMAGE_WIDTH * dishtypes;
		myGridView.setLayoutParams(params);
		// 设置列数为得到的list长度
		((GridView) myGridView).setNumColumns(dishtypes);
		myGridView.setAdapter(new HorizontalGridViewAdapter(mActivity,
				jsonArrayImage, imageLoader));
		myGridView.setOnScrollListener(new PauseOnScrollListener(imageLoader,
				true, true));
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	private class myAsyncTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPostExecute(final Void result) {
			super.onPostExecute(result);
			Log.i("lines", appDescription.getLineCount() + "");
			if (appDescription.getLineCount() >= LayoutResourcesDatas.APP_DESCRIPATION_LINES) {
				appDescription.setEllipsize(TruncateAt.END);
				appDescription
						.setLines(LayoutResourcesDatas.APP_DESCRIPATION_LINES);
				descriShowAll.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						appDescription.setEllipsize(null);
						appDescription.setSingleLine(false);
						descriShowAll.setVisibility(View.GONE);
					}
				});
			} else {
				descriShowAll.setVisibility(View.GONE);
			}
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			return null;
		}

	}
}
