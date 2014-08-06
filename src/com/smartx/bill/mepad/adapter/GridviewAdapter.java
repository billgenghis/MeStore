package com.smartx.bill.mepad.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.smartx.bill.mepad.R;

public class GridviewAdapter extends BaseAdapter
{
//	private ArrayList<String> listCountry;
//	private ArrayList<Integer> listFlag;
	private Activity activity;
	private ArrayList<HashMap<String, Object>> appsInfo;
	
//	public GridviewAdapter(Activity activity,ArrayList<String> listCountry, ArrayList<Integer> listFlag) {
//		super();
//		this.listCountry = listCountry;
//		this.listFlag = listFlag;
//		this.activity = activity;
//	}
	public GridviewAdapter(Activity activity,ArrayList<HashMap<String, Object>> appsInfo) {
		super();
		this.appsInfo = appsInfo;
		this.activity = activity;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return appsInfo.size();
	}

	@Override
	public String getItem(int position) {
		// TODO Auto-generated method stub
		return appsInfo.get(position).toString();
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public static class ViewHolder
	{
		public ImageView imgViewFlag;
		public TextView txtViewTitle;
		public TextView downloadCount;
		public RatingBar appScore;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder view;
		LayoutInflater inflator = activity.getLayoutInflater();
		
		if(convertView==null)
		{
			view = new ViewHolder();
			convertView = inflator.inflate(R.layout.gridview_item, null);
			
			view.txtViewTitle = (TextView) convertView.findViewById(R.id.app_title);
			view.imgViewFlag = (ImageView) convertView.findViewById(R.id.app_icon);
			view.appScore = (RatingBar)convertView.findViewById(R.id.app_score);
			view.downloadCount = (TextView)convertView.findViewById(R.id.app_download_count);
			convertView.setTag(view);
		}
		else
		{
			view = (ViewHolder) convertView.getTag();
		}
		HashMap<String, Object> items = appsInfo.get(position);
		view.txtViewTitle.setText(items.get("title").toString());
		view.downloadCount.setText(items.get("downloadCount").toString());
		view.appScore.setNumStars(Integer.parseInt(items.get("appScore").toString()));
//		view.imgViewFlag.setImageResource(items.get("title"));
		
		return convertView;
	}

}
