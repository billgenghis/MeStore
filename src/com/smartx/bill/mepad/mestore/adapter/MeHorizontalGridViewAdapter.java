package com.smartx.bill.mepad.mestore.adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.smartx.bill.mepad.mestore.R;

public class MeHorizontalGridViewAdapter extends MyBaseAdapter {

	private Activity activity;
	private JSONArray specialsInfo;
	private ImageLoader imageLoader;

	public MeHorizontalGridViewAdapter(Activity activity, JSONArray specialsInfo,
			ImageLoader imageLoader) {
		super();
		this.specialsInfo = specialsInfo;
		this.activity = activity;
		this.imageLoader = imageLoader;
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
		return specialsInfo.length();
	}

	@Override
	public JSONObject getItem(int position) {
		// TODO Auto-generated method stub
		try {
			return specialsInfo.getJSONObject(position);
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

	public static class HorizonViewHolder {
		public ImageView imgViewFlag;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		HorizonViewHolder view;
		LayoutInflater inflator = activity.getLayoutInflater();

		if (convertView == null) {
			view = new HorizonViewHolder();
			convertView = inflator.inflate(
					R.layout.dialog_horizon_gridview_item, null);
			view.imgViewFlag = (ImageView) convertView
					.findViewById(R.id.galley_image);
			convertView.setTag(view);
		} else {
			view = (HorizonViewHolder) convertView.getTag();
		}
		imageLoader.displayImage(
				getImageUrl(getItemDatas("image_small", position)),
				view.imgViewFlag, meSpecialOptions);
		return convertView;
	}
}
