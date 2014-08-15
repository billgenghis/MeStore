package com.smartx.bill.mepad.mestore.recommend;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.smartx.bill.mepad.mestore.R;
import com.smartx.bill.mepad.mestore.adapter.RecomGridviewAdapter;
import com.smartx.bill.mepad.mestore.home.MyBaseActivity;
import com.smartx.bill.mepad.mestore.iostream.DownLoadDatas;
import com.smartx.bill.mepad.mestore.matadata.IOStreamDatas;
import com.smartx.bill.mepad.mestore.myview.MyGridView;
import com.smartx.bill.mepad.mestore.util.HttpUtil;

public class RecomNew extends MyBaseActivity {

	private MyGridView mRecomGridView;
	private RecomGridviewAdapter mRecomGridviewAdapter;
	private JSONArray jsonArrayTop;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_ranking);
		HttpUtil.get(getDataUrl(IOStreamDatas.APP_DATA),
				getParams(null, null, IOStreamDatas.POSITION_NEW, null),
				new JsonHttpResponseHandler() {

					@Override
					public void onSuccess(JSONArray response) {
						initDatas(response);
						initGridView();
					}

					@Override
					public void onFailure(Throwable e, JSONArray errorResponse) {
					}
				});
//		initDatas();
//		initGridView();
	}

	private void initDatas(JSONArray response) {
//		try {
//			jsonArrayTop = downLoadDatas.getDatasFromServer(null, null, IOStreamDatas.POSITION_NEW,
//					null, IOStreamDatas.APP_DATA);
//		} catch (IOException | JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		jsonArrayTop = response;
		mRecomGridviewAdapter = new RecomGridviewAdapter(this, jsonArrayTop,imageLoader);
		mRecomGridView = (MyGridView) findViewById(R.id.ranking_gridView);
	}

	private void initGridView() {
		mRecomGridView.setNumColumns(2);
		mRecomGridView.setAdapter(mRecomGridviewAdapter);
		mRecomGridView.setOnScrollListener(new PauseOnScrollListener(imageLoader, true, true));  
		mRecomGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
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
				Toast.makeText(RecomNew.this, "现在是横屏", Toast.LENGTH_SHORT)
						.show();
			} else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
				imageCol = 3;
			}
			mRecomGridView.setNumColumns(imageCol);
			mRecomGridView.setAdapter(mRecomGridviewAdapter);
			// ia.notifyDataSetChanged();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
