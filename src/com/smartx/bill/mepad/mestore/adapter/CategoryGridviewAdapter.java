package com.smartx.bill.mepad.mestore.adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.smartx.bill.mepad.mestore.R;
import com.smartx.bill.mepad.mestore.matadata.IOStreamDatas;

public class CategoryGridviewAdapter extends MyBaseAdapter {
	private Activity activity;
	private JSONArray categorysInfo;
	private ImageLoader imageLoader;

	// }
	public CategoryGridviewAdapter(Activity activity, JSONArray categorysInfo,
			String myType,ImageLoader imageLoader) throws JSONException {
		super();
//		for (int i = 0; i < categorysInfo.length(); i++) {
//			categoryInfo = categorysInfo.getJSONObject(i);
//			if (categoryInfo.get("class_id").equals(myType)) {
//				i++;
//				for (int m = i; m < categorysInfo.length(); m++) {
//					categoryInfo = categorysInfo.getJSONObject(m);
//					if (categoryInfo.get("type").equals(
//							IOStreamDatas.CATEGORY_APP_TYPE)) {
//						this.categorysInfo.put(categoryInfo);
//					} else {
//						i = m;
//						break;
//					}
//				}
//			}
//		}
		this.categorysInfo = categorysInfo;
		this.imageLoader = imageLoader;
		this.activity = activity;
	}

	private String getItemDatas(String key, int position) {
		try {
			return this.getItem(position).getString(key);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return categorysInfo.length();
	}

	@Override
	public JSONObject getItem(int position) {
		// TODO Auto-generated method stub
		try {
			return categorysInfo.getJSONObject(position);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public static class CategoryViewHolder {
		public ImageView imgViewFlag;
		public TextView txtViewTitle;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		CategoryViewHolder view;
		LayoutInflater inflator = activity.getLayoutInflater();

		if (convertView == null) {
			view = new CategoryViewHolder();
			convertView = inflator.inflate(R.layout.category_gridview_item,
					null);
			view.txtViewTitle = (TextView) convertView
					.findViewById(R.id.category_name);
			view.imgViewFlag = (ImageView) convertView
					.findViewById(R.id.category_pic);
			convertView.setTag(view);
		} else {
			view = (CategoryViewHolder) convertView.getTag();
		}
		view.txtViewTitle.setText(getItemDatas("name", position));
//		imageLoader.displayImage(getItemDatas("image", position), view.imgViewFlag, options);
		return convertView;
	}

}
