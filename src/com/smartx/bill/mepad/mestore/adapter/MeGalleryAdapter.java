package com.smartx.bill.mepad.mestore.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.smartx.bill.mepad.mestore.R;

public class MeGalleryAdapter extends BaseAdapter {
	private int[] images = { R.drawable.special_01, R.drawable.special_02,
			R.drawable.special_03, R.drawable.special_04 };// 数据源
	private Activity activity;

	public MeGalleryAdapter(Activity activity, int[] images) {
		super();
		this.activity = activity;
		// this.images = images;
	}

	// 数量
	public int getCount() {

		return images.length;
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public long getItemId(int position) {

		return position;
	}

	public static class MeGalleryViewHolder {
		public ImageView galleyImage;
	}

	// 显示的View
	public View getView(int position, View convertView, ViewGroup parent) {

		MeGalleryViewHolder view;
		LayoutInflater inflator = activity.getLayoutInflater();

		if (convertView == null) {
			view = new MeGalleryViewHolder();
			convertView = inflator.inflate(R.layout.me_galley_item, null);
			view.galleyImage = (ImageView) convertView
					.findViewById(R.id.galley_image);
			view.galleyImage.setImageResource(images[position]);
			convertView.setTag(view);
		} else {
			view = (MeGalleryViewHolder) convertView.getTag();
			
			view.galleyImage.setImageResource(images[position]);
		}
		return convertView;
	}

}