package com.smartx.bill.mepad.mestore.adapter;

import java.sql.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.smartx.bill.mepad.mestore.R;

public class SpecialGridviewAdapter extends MyBaseAdapter {

	private Activity activity;
	private JSONArray specialsInfo = new JSONArray();
	private ImageLoader imageLoader;
	private DisplayImageOptions options;

	public SpecialGridviewAdapter(Activity activity, JSONArray specialsInfo,
			ImageLoader imageLoader) {
		super();
		this.specialsInfo = specialsInfo;
		this.activity = activity;
		this.imageLoader = imageLoader;
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.default_special_item) // resource
				.showImageForEmptyUri(R.drawable.default_special_item) // resource
				.showImageOnFail(R.drawable.default_special_item) // resource
				.resetViewBeforeLoading(false) // default
				.delayBeforeLoading(1000).cacheInMemory(true) // default
				.cacheOnDisk(true) // default
				.considerExifParams(false) // default
				.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default
				.bitmapConfig(Bitmap.Config.ARGB_8888) // default
				.displayer(new SimpleBitmapDisplayer()) // default
				.handler(new Handler()) // default
				.build();
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

	public static class SpecialViewHolder {
		public ImageView imgViewFlag;
		public TextView txtViewTitle;
		public TextView txtViewDscription;
		public TextView txtViewTime;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		SpecialViewHolder view;
		LayoutInflater inflator = activity.getLayoutInflater();

		if (convertView == null) {
			view = new SpecialViewHolder();
			convertView = inflator
					.inflate(R.layout.special_gridview_item, null);
			view.txtViewTitle = (TextView) convertView
					.findViewById(R.id.special_item_title);
			view.imgViewFlag = (ImageView) convertView
					.findViewById(R.id.special_item_pic);
			view.txtViewDscription = (TextView) convertView
					.findViewById(R.id.special_item_description);
			view.txtViewTime = (TextView) convertView
					.findViewById(R.id.special_item_time);
			convertView.setTag(view);
		} else {
			view = (SpecialViewHolder) convertView.getTag();
		}
		view.txtViewTitle.setText(getItemDatas("s_title", position));
		view.txtViewDscription.setText(getItemDatas("s_description", position));
		imageLoader.displayImage(getImageUrl(getItemDatas("image", position)),
				view.imgViewFlag, options);
		long time = Long.parseLong(getItemDatas("update_time", position)
				+ "000");// PHP转化到java需要补后三位
		Date date = new Date(time);
		view.txtViewTime.setText(date.toString());
		return convertView;
	}
}
