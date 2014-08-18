package com.smartx.bill.mepad.mestore.category;

import org.json.JSONArray;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.smartx.bill.mepad.mestore.R;
import com.smartx.bill.mepad.mestore.adapter.RecomGridviewAdapter;
import com.smartx.bill.mepad.mestore.home.MyBaseActivity;
import com.smartx.bill.mepad.mestore.matadata.IOStreamDatas;
import com.smartx.bill.mepad.mestore.util.HttpUtil;

public class CategoryRanking extends MyBaseActivity {

	private GridView mRakingGridView;
	private RecomGridviewAdapter mRecomGridviewAdapter;
	private JSONArray jsonArrayTop;
	private String classId; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_ranking);
		this.classId = getIntent().getStringExtra("classId");
		HttpUtil.get(getDataUrl(IOStreamDatas.APP_DATA), getParams(classId, null, null,
				null,null), new JsonHttpResponseHandler() {
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
//			jsonArrayTop = downLoadDatas.getDatasFromServer(classId, null, IOStreamDatas.POSITION_NEW,
//					null, IOStreamDatas.APP_DATA);
//		} catch (IOException | JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		jsonArrayTop = response;
		mRecomGridviewAdapter = new RecomGridviewAdapter(this, jsonArrayTop,imageLoader);
		mRakingGridView = (GridView) findViewById(R.id.ranking_gridView);
	}

	private void initGridView() {
		mRakingGridView.setNumColumns(2);
		mRakingGridView.setAdapter(mRecomGridviewAdapter);
		mRakingGridView.setOnScrollListener(new PauseOnScrollListener(imageLoader, true, true));  
		mRakingGridView.setOnItemClickListener(new OnItemClickListener() {
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
				Toast.makeText(CategoryRanking.this, "现在是横屏", Toast.LENGTH_SHORT)
						.show();
			} else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
				imageCol = 3;
			}
			mRakingGridView.setNumColumns(imageCol);
			mRakingGridView.setAdapter(mRecomGridviewAdapter);
			// ia.notifyDataSetChanged();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
