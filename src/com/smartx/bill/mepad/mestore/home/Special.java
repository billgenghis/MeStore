package com.smartx.bill.mepad.mestore.home;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.smartx.bill.mepad.mestore.R;
import com.smartx.bill.mepad.mestore.adapter.SpecialGridviewAdapter;
import com.smartx.bill.mepad.mestore.iostream.DownLoadDatas;
import com.smartx.bill.mepad.mestore.matadata.IOStreamDatas;

public class Special extends Activity {

	private GridView mSpecialGridView;
	private SpecialGridviewAdapter mSpecialAdapter;
	private JSONArray jsonArraySpecial;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_special);
		initDatas();
		initGridView();
	}

	private void initDatas() {
		try {
			jsonArraySpecial = DownLoadDatas.getDatasFromServer(null, null, null,
					null, IOStreamDatas.SPECIAL_DATA);
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mSpecialAdapter = new SpecialGridviewAdapter(this, jsonArraySpecial);
		mSpecialGridView = (GridView) findViewById(R.id.special_gridView);
	}

	private void initGridView() {
		mSpecialGridView.setNumColumns(1);
		mSpecialGridView.setAdapter(mSpecialAdapter);
		mSpecialGridView.setOnItemClickListener(new OnItemClickListener() {
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
		int imageCol = 1;
		try {

			super.onConfigurationChanged(newConfig);
			// 如果屏幕是竖屏，则显示3列，如果是横屏，则显示4列
			if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
				imageCol = 1;
				Toast.makeText(Special.this, "现在是横屏", Toast.LENGTH_SHORT)
						.show();
			} else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
				imageCol = 1;
			}
			mSpecialGridView.setNumColumns(imageCol);
			mSpecialGridView.setAdapter(mSpecialAdapter);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
