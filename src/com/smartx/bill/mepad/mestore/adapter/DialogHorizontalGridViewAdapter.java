package com.smartx.bill.mepad.mestore.adapter;

import java.util.ArrayList;

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

public class DialogHorizontalGridViewAdapter extends MyBaseAdapter {

	private Activity activity;
	private ArrayList<String> imagesInfo;
	private ImageLoader imageLoader;

	public DialogHorizontalGridViewAdapter(Activity activity,
			ArrayList<String> imagesInfo, ImageLoader imageLoader) {
		super();
		this.imagesInfo = imagesInfo;
		this.activity = activity;
		this.imageLoader = imageLoader;
		if(imagesInfo.size() == 0){
			imagesInfo.add("test");
			imagesInfo.add("test");
			imagesInfo.add("test");
			imagesInfo.add("test");
			imagesInfo.add("test");
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return imagesInfo.size();
	}

	@Override
	public String getItem(int position) {
		// TODO Auto-generated method stub
		return imagesInfo.get(position);
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
		imageLoader.displayImage(getImageUrl(getItem(position)),
				view.imgViewFlag, appDialogOptions);
		return convertView;
	}
}
