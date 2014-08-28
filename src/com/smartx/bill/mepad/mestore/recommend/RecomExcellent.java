package com.smartx.bill.mepad.mestore.recommend;

import org.json.JSONArray;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.smartx.bill.mepad.mestore.R;
import com.smartx.bill.mepad.mestore.adapter.RecomGridviewAdapter;
import com.smartx.bill.mepad.mestore.listener.ItemClickListener;
import com.smartx.bill.mepad.mestore.matadata.IOStreamDatas;
import com.smartx.bill.mepad.mestore.uimgloader.AbsListViewBaseActivity;

public class RecomExcellent extends AbsListViewBaseActivity {

	private RecomGridviewAdapter mRecomGridviewAdapter;

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
						new GetDataTask(
								mGridViewJSON,
								mRecomGridviewAdapter,
								mPullRefreshGridView,
								getParams(
										null,
										null,
										IOStreamDatas.POSITION_EXCELLENT,
										null,
										null,
										String.valueOf(IOStreamDatas.meExcellentPages)),
								IOStreamDatas.ME_EXCELLENT_GRIDVIEW).execute();
					}

				});
		// 数据初始化
		initGridView();
		IOStreamDatas.meExcellentPages = 0;
		new GetDataTask(mGridViewJSON, mRecomGridviewAdapter,
				mPullRefreshGridView, getParams(null, null,
						IOStreamDatas.POSITION_EXCELLENT, null, null,
						String.valueOf(IOStreamDatas.meExcellentPages)),
				IOStreamDatas.ME_EXCELLENT_GRIDVIEW).execute();
	}

	private void initGridView() {
		mRecomGridviewAdapter = new RecomGridviewAdapter(this, mGridViewJSON,
				imageLoader);
		((GridView) myGridView).setNumColumns(2);
		myGridView.setAdapter(mRecomGridviewAdapter);
		myGridView.setOnScrollListener(new PauseOnScrollListener(imageLoader,
				true, true));
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
				Toast.makeText(RecomExcellent.this, "现在是横屏", Toast.LENGTH_SHORT)
						.show();
			} else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
				imageCol = 3;
			}
			((GridView) myGridView).setNumColumns(imageCol);
			myGridView.setAdapter(mRecomGridviewAdapter);
			// ia.notifyDataSetChanged();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
