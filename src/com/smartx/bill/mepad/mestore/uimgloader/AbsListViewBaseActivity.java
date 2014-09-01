/*******************************************************************************
 * Copyright 2011-2013 Sergey Tarasevich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.smartx.bill.mepad.mestore.uimgloader;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.smartx.bill.mepad.mestore.R;
import com.smartx.bill.mepad.mestore.R.id;
import com.smartx.bill.mepad.mestore.home.MyBaseActivity;
import com.smartx.bill.mepad.mestore.iostream.DownLoadDatas;
import com.smartx.bill.mepad.mestore.listener.MyGestureListener;
import com.smartx.bill.mepad.mestore.matadata.IOStreamDatas;
import com.smartx.bill.mepad.mestore.matadata.LayoutResourcesDatas;

/**
 * 
 * 
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 */
public class AbsListViewBaseActivity extends MyBaseActivity {

	protected static final String STATE_PAUSE_ON_SCROLL = "STATE_PAUSE_ON_SCROLL";
	protected static final String STATE_PAUSE_ON_FLING = "STATE_PAUSE_ON_FLING";
	protected MyGestureListener myGestureListener;

	protected AbsListView myGridView;
	protected JSONArray mGridViewJSON;
	protected PullToRefreshGridView mPullRefreshGridView;

	protected boolean pauseOnScroll = false;
	protected boolean pauseOnFling = true;
	protected Activity mActivity;
	protected Context mContext;
	protected Bundle savedInstanceState;
	protected ProgressDialog dialog;

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		pauseOnScroll = savedInstanceState.getBoolean(STATE_PAUSE_ON_SCROLL,
				false);
		pauseOnFling = savedInstanceState
				.getBoolean(STATE_PAUSE_ON_FLING, true);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// or implement in activity or component. When your not assigning to a
		// child component.
		return myGestureListener.getDetector().onTouchEvent(event);
	}

	@Override
	public void onResume() {
		super.onResume();
		applyScrollListener();
	}

	private void applyScrollListener() {
		myGridView.setOnScrollListener(new PauseOnScrollListener(imageLoader,
				pauseOnScroll, pauseOnFling));
		myGridView.setOnTouchListener(myGestureListener);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putBoolean(STATE_PAUSE_ON_SCROLL, pauseOnScroll);
		outState.putBoolean(STATE_PAUSE_ON_FLING, pauseOnFling);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		MenuItem pauseOnScrollItem = menu.findItem(R.id.item_pause_on_scroll);
		pauseOnScrollItem.setVisible(true);
		pauseOnScrollItem.setChecked(pauseOnScroll);

		MenuItem pauseOnFlingItem = menu.findItem(R.id.item_pause_on_fling);
		pauseOnFlingItem.setVisible(true);
		pauseOnFlingItem.setChecked(pauseOnFling);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.item_pause_on_scroll:
			pauseOnScroll = !pauseOnScroll;
			item.setChecked(pauseOnScroll);
			applyScrollListener();
			return true;
		case R.id.item_pause_on_fling:
			pauseOnFling = !pauseOnFling;
			item.setChecked(pauseOnFling);
			applyScrollListener();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	protected void initCommonDatas(Activity mActivity, Context mContext,
			Bundle savedInstanceState) {
		this.savedInstanceState = savedInstanceState;
		this.mActivity = mActivity;
		this.mContext = mContext;
		setDialog();
		myGestureListener = new MyGestureListener(mContext);
	}

	protected void initPullRefreshGridView() {
		mPullRefreshGridView = (PullToRefreshGridView) findViewById(R.id.common_gridView);
		ILoadingLayout startLabels = this.mPullRefreshGridView
				.getLoadingLayoutProxy();
		startLabels.setPullLabel(""); // 刚下拉时，显示的提示
		startLabels.setRefreshingLabel(""); // 刷新时
		startLabels.setReleaseLabel(""); // 下来达到一定距离时，显示的
		myGridView = mPullRefreshGridView.getRefreshableView();
	}

	protected class GetDataTask extends AsyncTask<Void, Void, JSONArray> {
		JSONArray mResponse;
		JSONArray mGridViewJSON;
		BaseAdapter mAdapter;
		PullToRefreshGridView mPullRefreshGridView;
		List<NameValuePair> httpParams;
		int types;

		public GetDataTask(JSONArray mGridViewJSON, BaseAdapter mAdapter,
				PullToRefreshGridView mPullRefreshGridView,
				List<NameValuePair> httpParams, int gridTypes) {
			this.types = gridTypes;
			this.mAdapter = mAdapter;
			this.mGridViewJSON = mGridViewJSON;
			this.mPullRefreshGridView = mPullRefreshGridView;
			this.httpParams = httpParams;
		}

		@Override
		protected JSONArray doInBackground(Void... params) {
			// Simulates a background job.
			try {
				mResponse = DownLoadDatas.getDatasFromServer(
						getDataUrl(IOStreamDatas.APP_DATA), httpParams);
			} catch (IOException | JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return mResponse;
		}

		@Override
		protected void onPostExecute(JSONArray result) {
			if (mResponse == null) {
				cancelDialog(false);
			}else if (mResponse.length() == 0) {
				Toast.makeText(AbsListViewBaseActivity.this, "已经到底了!",
						Toast.LENGTH_SHORT).show();
				mPullRefreshGridView.setMode(Mode.DISABLED);
				mPullRefreshGridView.noRefreshData();
			} else {
				for (int i = 0; i < mResponse.length(); i++) {
					try {
						mGridViewJSON.put(mResponse.get(i));
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				mAdapter.notifyDataSetChanged();
				mPullRefreshGridView.onRefreshComplete();
				switch (types) {
				case IOStreamDatas.RANKING_GRIDVIEW:
					IOStreamDatas.rankingPages += 1;
					break;
				case IOStreamDatas.ME_EXCELLENT_GRIDVIEW:
					IOStreamDatas.meExcellentPages += 1;
					break;
				case IOStreamDatas.ME_NEW_GRIDVIEW:
					IOStreamDatas.meNewPages += 1;
					break;
				case IOStreamDatas.CATEGORY_EXCELLENT_GRIDVIEW:
					IOStreamDatas.categoryExcellentPages += 1;
					break;
				case IOStreamDatas.CATEGORY_NEW_GRIDVIEW:
					IOStreamDatas.categoryNewPages += 1;
					break;
				case IOStreamDatas.CATEGORY_RANKING_GRIDVIEW:
					IOStreamDatas.categoryRankingPages += 1;
					break;
				}
			}
			if (dialog != null) {
				cancelDialog(true);
			}
			super.onPostExecute(result);
		}
	}

	protected void setDialog() {
		dialog = new ProgressDialog(this, R.style.welcome_dialog);
		dialog.setCancelable(false);
		dialog.show();
		LayoutInflater inflater = LayoutInflater.from(this);
		View v = inflater.inflate(R.layout.wlecome_dialog, null);// 得到加载view
		LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
		// main.xml中的ImageView
		ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
		TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
		// 加载动画
		Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(this,
				R.anim.loading_animation);
		// 使用ImageView显示动画
		spaceshipImage.startAnimation(hyperspaceJumpAnimation);
		// tipTextView.setText(msg);// 设置加载信息
		dialog.setContentView(layout);
	}
	protected void cancelDialog(boolean status){
		if(status){
			findViewById(id.me_topView).setVisibility(
					View.VISIBLE);
		}else if(!status){
			findViewById(id.me_topView).setVisibility(
					View.INVISIBLE);
		}
		ScheduledExecutorService executor = Executors
				.newSingleThreadScheduledExecutor();
		Runnable runner = new Runnable() {
			public void run() {
				dialog.dismiss();
			}
		};
		executor.schedule(runner,
				LayoutResourcesDatas.DELAY_TIME,
				TimeUnit.MILLISECONDS);
	}
}
