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
import android.widget.RatingBar;
import android.widget.TextView;

import com.smartx.bill.mepad.R;
import com.smartx.bill.mepad.iostream.DownLoadDatas;

public class RankingGridviewAdapter extends BaseAdapter
{
	private Activity activity;
	private JSONArray appsInfo;
	private int count = 50;//模拟数据时使用
	
//	}
	public RankingGridviewAdapter(Activity activity,JSONArray appsInfo) {
		super();
		this.appsInfo = appsInfo;
		this.activity = activity;
	}
	private String getItemDatas(String key, int position){
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
		return count;
	}

	@Override
	public JSONObject getItem(int position) {
		// TODO Auto-generated method stub
		try {
			return appsInfo.getJSONObject(position);
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

	public static class RankingViewHolder
	{
		public ImageView imgViewFlag;
		public TextView txtViewTitle;
		public TextView downloadCount;
		public RatingBar appScore;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(position > 9){
			position = position % 9;
		}
		
		RankingViewHolder view;
		LayoutInflater inflator = activity.getLayoutInflater();
		
		if(convertView==null)
		{
			view = new RankingViewHolder();
			convertView = inflator.inflate(R.layout.ranking_gridview_item, null);
			view.txtViewTitle = (TextView) convertView.findViewById(R.id.app_title);
			view.imgViewFlag = (ImageView) convertView.findViewById(R.id.app_icon);
			view.appScore = (RatingBar)convertView.findViewById(R.id.app_score);
			view.downloadCount = (TextView)convertView.findViewById(R.id.app_download_count);
			convertView.setTag(view);
		}
		else
		{
			view = (RankingViewHolder) convertView.getTag();
		}
		view.txtViewTitle.setText(getItemDatas("title", position));
		view.downloadCount.setText(getItemDatas("downloads", position)+ "次下载");
		view.appScore.setRating(Float
				.parseFloat(getItemDatas("score", position)));
		try {
			view.imgViewFlag.setImageBitmap(DownLoadDatas
					.getImageFromServer(getItemDatas("image", position)));
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return convertView;
	}

}
