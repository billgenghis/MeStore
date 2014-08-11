package com.smartx.bill.mepad.adapter;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.smartx.bill.mepad.R;
import com.smartx.bill.mepad.iostream.DownLoadDatas;

public class SpecialGridviewAdapter extends BaseAdapter {

	private Activity activity;
	private JSONArray specialsInfo = new JSONArray();

	public SpecialGridviewAdapter(Activity activity, JSONArray specialsInfo){
		super();
		this.specialsInfo = specialsInfo;
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
//		view.txtViewTime.setText(getItemDatas("time", position));
//		try {
//			view.imgViewFlag.setImageBitmap(DownLoadDatas
//					.getImageFromServer(getItemDatas("s_pic_url", position)));
//		} catch (IOException | JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return convertView;
	}

}
