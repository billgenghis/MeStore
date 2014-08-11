package com.smartx.bill.mepad.mestore.home;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.smartx.bill.mepad.mestore.R;
import com.smartx.bill.mepad.mestore.adapter.CategoryGridviewAdapter;
import com.smartx.bill.mepad.mestore.iostream.DownLoadDatas;
import com.smartx.bill.mepad.mestore.matadata.IOStreamDatas;
import com.smartx.bill.mepad.mestore.myview.MyGridView;

public class Category extends Activity {

	private MyGridView mCategoryGridView01;
	private MyGridView mCategoryGridView02;
	private MyGridView mCategoryGridView03;
	private CategoryGridviewAdapter mCategoryAdapter01;
	private CategoryGridviewAdapter mCategoryAdapter02;
	private CategoryGridviewAdapter mCategoryAdapter03;
	private JSONArray jsonArrayCategory;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_category);
		try {
			initDatas();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initGridView();
	}

	private void initDatas() throws JSONException {
		mCategoryGridView01 = (MyGridView) findViewById(R.id.category_gridView01);
		mCategoryGridView02 = (MyGridView) findViewById(R.id.category_gridView02);
		mCategoryGridView03 = (MyGridView) findViewById(R.id.category_gridView03);
		try {
			jsonArrayCategory = DownLoadDatas.getDatasFromServer(null, null,
					null, null, IOStreamDatas.CATEGORY_DATA);
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.i("category", jsonArrayCategory.toString());
		mCategoryAdapter01 = new CategoryGridviewAdapter(this,
				jsonArrayCategory,"7");
		mCategoryAdapter02 = new CategoryGridviewAdapter(this,
				jsonArrayCategory,"8");
		mCategoryAdapter03 = new CategoryGridviewAdapter(this,
				jsonArrayCategory,"9");
	}

	private void initGridView() {
		mCategoryGridView01.setNumColumns(5);
		mCategoryGridView01.setAdapter(mCategoryAdapter01);
		mCategoryGridView01.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
			}
		});
		mCategoryGridView02.setNumColumns(5);
		mCategoryGridView02.setAdapter(mCategoryAdapter02);
		mCategoryGridView02.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
			}
		});
		mCategoryGridView03.setNumColumns(5);
		mCategoryGridView03.setAdapter(mCategoryAdapter03);
		mCategoryGridView03.setOnItemClickListener(new OnItemClickListener() {
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
				Toast.makeText(Category.this, "现在是横屏", Toast.LENGTH_SHORT)
						.show();
			} else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
				imageCol = 3;
			}
			mCategoryGridView01.setNumColumns(5);
			mCategoryGridView01.setAdapter(mCategoryAdapter01);
			mCategoryGridView02.setNumColumns(5);
			mCategoryGridView02.setAdapter(mCategoryAdapter02);
			mCategoryGridView03.setNumColumns(5);
			mCategoryGridView03.setAdapter(mCategoryAdapter03);
			// ia.notifyDataSetChanged();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
