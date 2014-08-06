package com.smartx.bill.mepad.home;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.smartx.bill.mepad.R;
import com.smartx.bill.mepad.adapter.GridviewAdapter;

public class Ranking extends Activity {


	private GridView mRankingGridView;
	private GridviewAdapter mRankingAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_ranking);
		initGridView();
	}

	private void initGridView() {

		mRankingAdapter = new GridviewAdapter(this,
				new ArrayList<HashMap<String, Object>>());
		mRankingGridView = (GridView) findViewById(R.id.ranking_gridView);
		mRankingGridView.setNumColumns(3);
		mRankingGridView.setAdapter(mRankingAdapter);
		mRankingGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Toast.makeText(Ranking.this, mRankingAdapter.getItem(position),
						Toast.LENGTH_SHORT).show();
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
				Toast.makeText(Ranking.this, "现在是横屏", Toast.LENGTH_SHORT).show();
			} else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
				imageCol = 3;
			}
			mRankingGridView.setNumColumns(imageCol);
			mRankingGridView.setAdapter(mRankingAdapter);
			// ia.notifyDataSetChanged();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}