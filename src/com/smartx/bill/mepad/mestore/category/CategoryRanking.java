package com.smartx.bill.mepad.mestore.category;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.smartx.bill.mepad.mestore.R;
import com.smartx.bill.mepad.mestore.adapter.RankingGridviewAdapter;
import com.smartx.bill.mepad.mestore.adapter.RecomGridviewAdapter;
import com.smartx.bill.mepad.mestore.home.MyBaseActivity;
import com.smartx.bill.mepad.mestore.iostream.DownLoadDatas;
import com.smartx.bill.mepad.mestore.matadata.IOStreamDatas;
import com.smartx.bill.mepad.mestore.myview.MyGridView;

public class CategoryRanking extends MyBaseActivity {

	private MyGridView mRcomGridView;
	private RecomGridviewAdapter mRecomGridviewAdapter;
	private JSONArray jsonArrayTop;
	private String classId; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_ranking);
		this.classId = getIntent().getStringExtra("classId");
		initDatas();
		initGridView();
	}

	private void initDatas() {
		try {
			jsonArrayTop = DownLoadDatas.getDatasFromServer(classId, null, null,
					null, IOStreamDatas.APP_DATA);
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mRecomGridviewAdapter = new RecomGridviewAdapter(this, jsonArrayTop,imageLoader);
		mRcomGridView = (MyGridView) findViewById(R.id.ranking_gridView);
	}

	private void initGridView() {
		mRcomGridView.setNumColumns(2);
		mRcomGridView.setAdapter(mRecomGridviewAdapter);
		mRcomGridView.setOnScrollListener(new PauseOnScrollListener(imageLoader, true, true));  
		mRcomGridView.setOnItemClickListener(new OnItemClickListener() {
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
			mRcomGridView.setNumColumns(imageCol);
			mRcomGridView.setAdapter(mRecomGridviewAdapter);
			// ia.notifyDataSetChanged();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
