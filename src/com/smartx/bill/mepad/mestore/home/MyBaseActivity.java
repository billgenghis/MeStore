package com.smartx.bill.mepad.mestore.home;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;

import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.smartx.bill.mepad.mestore.R;
import com.smartx.bill.mepad.mestore.dialog.MyAppInfoDialogBuilder;
import com.smartx.bill.mepad.mestore.listener.MyGestureListener;
import com.smartx.bill.mepad.mestore.matadata.IOStreamDatas;

public class MyBaseActivity extends Activity {

	protected ImageLoader imageLoader = ImageLoader.getInstance();

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.item_clear_memory_cache:
			imageLoader.clearMemoryCache();
			return true;
		case R.id.item_clear_disc_cache:
			imageLoader.clearDiscCache();
			return true;
		default:
			return false;
		}
	}

	protected String getDataUrl(int dataTypes) {
		String dataURL = null;
		if (dataTypes == IOStreamDatas.APP_DATA) {
			dataURL = IOStreamDatas.SERVER_URL + IOStreamDatas.APPSINFO_URL;
		} else if (dataTypes == IOStreamDatas.CATEGORY_DATA) {
			dataURL = IOStreamDatas.SERVER_URL + IOStreamDatas.CATEGORY_URL;
		} else if (dataTypes == IOStreamDatas.SPECIAL_DATA) {
			dataURL = IOStreamDatas.SERVER_URL + IOStreamDatas.SPECIAL_URL;
		}
		return dataURL;
	}

	protected RequestParams getParams(String class_id, String age,
			String position_id, String keyword, String special_id,
			String developer) {
		RequestParams params = new RequestParams();
		params.put("class_id", class_id);
		params.put("age", age);
		params.put("position_id", position_id);
		params.put("keyword", keyword);
		params.put("special_id", special_id);
		params.put("developer", developer);
		return params;
	}
}
