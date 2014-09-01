package com.smartx.bill.mepad.mestore.dialog;

import org.apache.http.Header;
import org.json.JSONArray;

import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.smartx.bill.mepad.mestore.R;
import com.smartx.bill.mepad.mestore.adapter.DialogSameDeveAdapter;
import com.smartx.bill.mepad.mestore.adapter.RankingGridviewAdapter;
import com.smartx.bill.mepad.mestore.listener.ItemClickListener;
import com.smartx.bill.mepad.mestore.matadata.IOStreamDatas;
import com.smartx.bill.mepad.mestore.uimgloader.AbsListViewBaseActivity;
import com.smartx.bill.mepad.mestore.util.HttpUtil;

public class SameDevelpersApp extends AbsListViewBaseActivity {

	// private MyGridView mRankingGridView;
	private DialogSameDeveAdapter mDialogAdapter;
	private JSONArray jsonArrayApp;
	private String developer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.common_gridview);
		this.savedInstanceState = savedInstanceState;
		mActivity = this;
		mContext = this;
		developer = getIntent().getStringExtra("developer");
		Log.i("developer", developer);
		HttpUtil.get(getDataUrl(IOStreamDatas.APP_DATA),
				getParams(null, null, null, null, null, developer, null),
				new JsonHttpResponseHandler() {

					@Override
					public void onSuccess(int statusCode, Header[] headers,JSONArray response) {
						initDatas(response);
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,Throwable e, JSONArray errorResponse) {
					}
				});
		// initDatas();
		// initGridView();
	}

	private void initDatas(JSONArray response) {
		jsonArrayApp = response;
		Log.i("jsonArrayApp", jsonArrayApp.toString() + "");
		mDialogAdapter = new DialogSameDeveAdapter(this, jsonArrayApp,
				imageLoader);
		// mRankingGridView = (MyGridView) findViewById(R.id.ranking_gridView);
		myGridView = (GridView) findViewById(R.id.common_gridView);

		((GridView) myGridView).setNumColumns(2);
		myGridView.setAdapter(mDialogAdapter);
		myGridView.setOnScrollListener(new PauseOnScrollListener(imageLoader,
				true, true));
		myGridView.setOnItemClickListener(new ItemClickListener(mContext,
				mActivity, savedInstanceState, response));
	}

	@Override
	protected void onPause() {
		super.onPause();
	}
}
