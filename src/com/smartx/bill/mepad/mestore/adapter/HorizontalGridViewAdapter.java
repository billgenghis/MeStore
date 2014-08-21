package com.smartx.bill.mepad.mestore.adapter;

import org.json.JSONArray;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.smartx.bill.mepad.mestore.R;

public class HorizontalGridViewAdapter extends MyBaseAdapter {

	private Activity activity;
	private JSONArray specialsInfo;
	private ImageLoader imageLoader;
	private String[] imageLocation;

	private int[] images = { R.drawable.special_01, R.drawable.special_02,
			R.drawable.special_03, R.drawable.special_04 };// 数据源

	public HorizontalGridViewAdapter(Activity activity, JSONArray specialsInfo,
			ImageLoader imageLoader) {
		super();
		this.specialsInfo = specialsInfo;
		this.activity = activity;
		this.imageLoader = imageLoader;
		imageLocation = new String[5];
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return images.length + 1;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return images[position];
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
		// view.imgViewFlag.setImageResource(images[position]);
		return convertView;
	}
}
