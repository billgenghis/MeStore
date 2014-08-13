package com.smartx.bill.mepad.mestore.adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.smartx.bill.mepad.mestore.R;

public class RecomGridviewAdapter extends MyBaseAdapter {
	private Activity activity;
	private JSONArray appsInfo;
	private ImageLoader imageLoader;
	private int count = 30;// 模拟数据时使用

	public RecomGridviewAdapter(Activity activity, JSONArray appsInfo,
			ImageLoader imageLoader) {
		super();
		this.appsInfo = appsInfo;
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

	public static class RankingViewHolder {
		public ImageView imgViewFlag;
		public TextView txtViewTitle;
		public TextView downloadCount;
		public RatingBar appScore;
		public Button appInstall;
		public RelativeLayout mRelativeLayout;
		public TextView appDescription;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		int rankingCount = position + 1;
		if (position > 9) {
			position = position % 9;
		}

		RankingViewHolder view;
		LayoutInflater inflator = activity.getLayoutInflater();

		if (convertView == null) {
			view = new RankingViewHolder();
			convertView = inflator
					.inflate(R.layout.me_recom_gridview_item, null);
			view.txtViewTitle = (TextView) convertView
					.findViewById(R.id.app_title);
			view.imgViewFlag = (ImageView) convertView
					.findViewById(R.id.app_icon);
			view.appScore = (RatingBar) convertView
					.findViewById(R.id.app_score);
			view.downloadCount = (TextView) convertView
					.findViewById(R.id.app_download_count);
			view.appInstall = (Button) convertView
					.findViewById(R.id.app_install);
			view.appDescription = (TextView) convertView
					.findViewById(R.id.app_description);
			view.mRelativeLayout = (RelativeLayout) convertView
					.findViewById(R.id.relative_item);
			convertView.setTag(view);
		} else {
			view = (RankingViewHolder) convertView.getTag();
		}
		view.txtViewTitle.setText(rankingCount + "."
				+ getItemDatas("title", position));
		view.downloadCount.setText(getItemDatas("downloads", position) + "次下载");
		view.appScore.setRating(Float
				.parseFloat(getItemDatas("score", position)));
		imageLoader.displayImage(getItemDatas("image", position),
				view.imgViewFlag, options);
		view.appDescription.setText(getItemDatas("description", position));
		// setLayout(position,view);
		return convertView;
	}

	private void setLayout(int position, RankingViewHolder view) {
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		// lp.addRule(RelativeLayout.CENTER_IN_PARENT);
		// lp.addRule(RelativeLayout.CENTER_VERTICAL);
		// lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
		if (position % 2 == 1) {
			lp.setMargins(30, 0, 12, 0);
			view.imgViewFlag.setLayoutParams(lp);
		} else {
			lp.setMargins(20, 0, 20, 0);
			view.appInstall.setLayoutParams(lp);
		}
	}

}
