package com.smartx.bill.mepad.mestore.home;

import org.json.JSONArray;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.smartx.bill.mepad.mestore.R;
import com.smartx.bill.mepad.mestore.adapter.RankingGridviewAdapter;
import com.smartx.bill.mepad.mestore.adapter.RecomGridviewAdapter;
import com.smartx.bill.mepad.mestore.listener.ItemClickListener;
import com.smartx.bill.mepad.mestore.matadata.IOStreamDatas;
import com.smartx.bill.mepad.mestore.uimgloader.AbsListViewBaseActivity;

public class Ranking extends AbsListViewBaseActivity {

	// private MyGridView mRankingGridView;
	private RankingGridviewAdapter mRankingAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.common_gridview);
		initCommonDatas(this, this, savedInstanceState);
		initDatas();
	}

	private void initDatas() {
		initPullRefreshGridView();
		mGridViewJSON = new JSONArray();
		mPullRefreshGridView
				.setOnRefreshListener(new OnRefreshListener2<GridView>() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<GridView> refreshView) {
					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<GridView> refreshView) {
						new GetDataTask(mGridViewJSON, mRankingAdapter,
								mPullRefreshGridView,
								getParams(null, null, null, null, null, String
										.valueOf(IOStreamDatas.rankingPages)),
								IOStreamDatas.RANKING_GRIDVIEW).execute();
					}

				});
		// 数据初始化
		initGridView();
	}

	private void initGridView() {
		mRankingAdapter = new RankingGridviewAdapter(this, mGridViewJSON,
				imageLoader);
		((GridView) myGridView).setNumColumns(2);
		myGridView.setAdapter(mRankingAdapter);
		IOStreamDatas.rankingPages = 0;
		new GetDataTask(mGridViewJSON, mRankingAdapter, mPullRefreshGridView,
				getParams(null, null, null, null, null,
						String.valueOf(IOStreamDatas.rankingPages)),
				IOStreamDatas.RANKING_GRIDVIEW).execute();
		myGridView.setOnItemClickListener(new ItemClickListener(mContext,
				mActivity, savedInstanceState, mGridViewJSON));
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
				Toast.makeText(Ranking.this, "现在是横屏", Toast.LENGTH_SHORT)
						.show();
			} else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
				imageCol = 3;
			}
			((GridView) myGridView).setNumColumns(imageCol);
			myGridView.setAdapter(mRankingAdapter);
			// ia.notifyDataSetChanged();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
