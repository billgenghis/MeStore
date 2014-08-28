package com.smartx.bill.mepad.mestore.home;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.smartx.bill.mepad.mestore.R;
import com.smartx.bill.mepad.mestore.adapter.CategoryGridviewAdapter;
import com.smartx.bill.mepad.mestore.category.CategoryDetail;
import com.smartx.bill.mepad.mestore.matadata.IOStreamDatas;
import com.smartx.bill.mepad.mestore.myview.MyGridView;
import com.smartx.bill.mepad.mestore.util.HttpUtil;

public class Category extends MyBaseActivity {

	private MyGridView mCategoryGridView01;
	private MyGridView mCategoryGridView02;
	private MyGridView mCategoryGridView03;
	private CategoryGridviewAdapter mCategoryAdapter01;
	private CategoryGridviewAdapter mCategoryAdapter02;
	private CategoryGridviewAdapter mCategoryAdapter03;
	private JSONArray jsonArrayCategory01;
	private JSONArray jsonArrayCategory02;
	private JSONArray jsonArrayCategory03;
	private JSONArray jsonArrayCategory;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_category);
		HttpUtil.get(getDataUrl(IOStreamDatas.CATEGORY_DATA),
				getParams(null, null, null, null, null, null, null),
				new JsonHttpResponseHandler() {

					@Override
					public void onSuccess(JSONArray response) {
						try {
							initDatas(response);
							initGridView();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					@Override
					public void onFailure(Throwable e, JSONArray errorResponse) {
					}
				});
		// try {
		// initDatas();
		// } catch (JSONException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// initGridView();
	}

	private void initDatas(JSONArray response) throws JSONException {
		mCategoryGridView01 = (MyGridView) findViewById(R.id.category_gridView01);
		mCategoryGridView02 = (MyGridView) findViewById(R.id.category_gridView02);
		mCategoryGridView03 = (MyGridView) findViewById(R.id.category_gridView03);
		// try {
		// jsonArrayCategory = downLoadDatas.getDatasFromServer(null, null,
		// null, null, IOStreamDatas.CATEGORY_DATA);
		// } catch (IOException | JSONException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		jsonArrayCategory = response;
		jsonArrayCategory01 = seperateCategory(IOStreamDatas.CATEGORY_STUDY);
		jsonArrayCategory02 = seperateCategory(IOStreamDatas.CATEGORY_GAME);
		jsonArrayCategory03 = seperateCategory(IOStreamDatas.CATEGORY_TOOLS);
		mCategoryAdapter01 = new CategoryGridviewAdapter(this,
				jsonArrayCategory01, imageLoader);
		mCategoryAdapter02 = new CategoryGridviewAdapter(this,
				jsonArrayCategory02, imageLoader);
		mCategoryAdapter03 = new CategoryGridviewAdapter(this,
				jsonArrayCategory03, imageLoader);
	}

	private void initGridView() {
		mCategoryGridView01.setNumColumns(5);
		mCategoryGridView01.setAdapter(mCategoryAdapter01);
		mCategoryGridView01.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Intent intent = new Intent(Category.this, CategoryDetail.class);
				Bundle mBundle = new Bundle();
				JSONObject mJsonObject;
				try {
					mJsonObject = jsonArrayCategory01.getJSONObject(position);
					mBundle.putString("name", mJsonObject.getString("name"));
					mBundle.putString("classId",
							mJsonObject.getString("class_id"));
					mBundle.putInt("recomType", IOStreamDatas.TAB_EXCELLENT);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				intent.putExtra("CategoryInfo", mBundle);
				startActivity(intent);
			}
		});
		mCategoryGridView02.setNumColumns(5);
		mCategoryGridView02.setAdapter(mCategoryAdapter02);
		mCategoryGridView02.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Intent intent = new Intent(Category.this, CategoryDetail.class);
				Bundle mBundle = new Bundle();
				JSONObject mJsonObject;
				try {
					mJsonObject = jsonArrayCategory02.getJSONObject(position);
					mBundle.putString("name", mJsonObject.getString("name"));
					mBundle.putString("classId",
							mJsonObject.getString("class_id"));
					mBundle.putInt("recomType", IOStreamDatas.TAB_RANKING);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				intent.putExtra("CategoryInfo", mBundle);
				startActivity(intent);
			}
		});
		mCategoryGridView03.setNumColumns(5);
		mCategoryGridView03.setAdapter(mCategoryAdapter03);
		mCategoryGridView03.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Intent intent = new Intent(Category.this, CategoryDetail.class);
				Bundle mBundle = new Bundle();
				JSONObject mJsonObject;
				try {
					mJsonObject = jsonArrayCategory03.getJSONObject(position);
					mBundle.putString("name", mJsonObject.getString("name"));
					mBundle.putString("classId",
							mJsonObject.getString("class_id"));
					mBundle.putInt("recomType", IOStreamDatas.TAB_NEW);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				intent.putExtra("CategoryInfo", mBundle);
				startActivity(intent);
			}
		});
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	private JSONArray seperateCategory(String myType) throws JSONException {
		JSONArray categorysInfo = new JSONArray();
		JSONObject categoryInfo;
		for (int i = 0; i < jsonArrayCategory.length(); i++) {
			categoryInfo = jsonArrayCategory.getJSONObject(i);
			if (categoryInfo.get("class_id").equals(myType)) {
				i++;
				for (int m = i; m < jsonArrayCategory.length(); m++) {
					categoryInfo = jsonArrayCategory.getJSONObject(m);
					if (categoryInfo.get("type").equals(
							IOStreamDatas.CATEGORY_APP_TYPE)) {
						categorysInfo.put(categoryInfo);
					} else {
						i = m;
						break;
					}
				}
			}
		}
		return categorysInfo;
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
