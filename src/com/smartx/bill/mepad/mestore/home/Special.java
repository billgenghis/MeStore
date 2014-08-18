package com.smartx.bill.mepad.mestore.home;

import java.io.ObjectOutputStream.PutField;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.smartx.bill.mepad.mestore.R;
import com.smartx.bill.mepad.mestore.adapter.SpecialGridviewAdapter;
import com.smartx.bill.mepad.mestore.matadata.IOStreamDatas;
import com.smartx.bill.mepad.mestore.special.SpecialDetail;
import com.smartx.bill.mepad.mestore.uimgloader.AbsListViewBaseActivity;
import com.smartx.bill.mepad.mestore.util.HttpUtil;

public class Special extends AbsListViewBaseActivity {

	// private GridView myGridView;
	private SpecialGridviewAdapter mSpecialAdapter;
	private JSONArray jsonArraySpecial;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_special);
		myGridView = (GridView) findViewById(R.id.special_gridView);
		HttpUtil.get(getDataUrl(IOStreamDatas.SPECIAL_DATA),
				getParams(null, null, null, null,null),
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
		// initDatas();
		// initGridView();
	}

	private void initDatas(JSONArray response) {
		// try {
		// jsonArraySpecial = downLoadDatas.getDatasFromServer(null, null, null,
		// null, IOStreamDatas.SPECIAL_DATA);
		// } catch (IOException | JSONException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		jsonArraySpecial = response;
		mSpecialAdapter = new SpecialGridviewAdapter(this, jsonArraySpecial,
				imageLoader);
	}

	private void initGridView() {
		((GridView) myGridView).setNumColumns(1);
		myGridView.setAdapter(mSpecialAdapter);
		myGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Intent intent = new Intent(Special.this, SpecialDetail.class);
				Bundle mBundle = new Bundle();
				JSONObject mJsonObject;
				try {
					mJsonObject = jsonArraySpecial.getJSONObject(position);
					mBundle.putString("sPicUrl",
							mJsonObject.getString("s_pic_url"));
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

	@Override
	protected void onPause() {
		super.onPause();
	}

	/**
	 * 屏幕切换时进行处理 如果屏幕是竖屏，则显示3列，如果是横屏，则显示4列
	 */
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		int imageCol = 1;
		try {

			super.onConfigurationChanged(newConfig);
			// 如果屏幕是竖屏，则显示3列，如果是横屏，则显示4列
			if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
				imageCol = 1;
				Toast.makeText(Special.this, "现在是横屏" + imageCol, Toast.LENGTH_SHORT)
						.show();
			} else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
				imageCol = 1;
			}
			((GridView) myGridView).setNumColumns(imageCol);
			myGridView.setAdapter(mSpecialAdapter);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
