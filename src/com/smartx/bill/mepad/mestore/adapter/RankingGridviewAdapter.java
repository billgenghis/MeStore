package com.smartx.bill.mepad.mestore.adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.smartx.bill.mepad.mestore.R;
import com.smartx.bill.mepad.mestore.util.CommonTools;
import com.smartx.bill.mepad.mestore.util.CommonTools.CommonViewHolder;

public class RankingGridviewAdapter extends MyBaseAdapter {
	private Activity activity;
	private JSONArray appsInfo;
	private ImageLoader imageLoader;

	// private int count = 30;// 模拟数据时使用

	// }
	public RankingGridviewAdapter(Activity activity, JSONArray appsInfo,
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
//		if (appsInfo.length() % 2 == 1) {
//			return appsInfo.length() + 1;
//		} else {
			return appsInfo.length();
//		}
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		int rankingCount = position + 1;

		CommonViewHolder view;
		LayoutInflater inflator = activity.getLayoutInflater();

		if (convertView == null) {
			view = new CommonViewHolder();
			convertView = inflator
					.inflate(R.layout.ranking_gridview_item, null);
			CommonTools.setViewById(view, convertView);
			convertView.setTag(view);
		} else {
			view = (CommonViewHolder) convertView.getTag();
		}
//		if (position == getCount() - 1 && appsInfo.length() == getCount() - 1) {
//			if (parent.getChildAt(position) != null) {
//				parent.getChildAt(position).findViewById(R.id.relative_item)
//						.setVisibility(View.INVISIBLE);
//				parent.getChildAt(position).findViewById(R.id.app_icon)
//						.setVisibility(View.INVISIBLE);
//				parent.getChildAt(position).findViewById(R.id.vertical_line)
//						.setVisibility(View.INVISIBLE);
//			}
//		} else {
			view.txtViewTitle.setText(rankingCount + "."
					+ getItemDatas("title", position));
			view.downloadCount.setText(getItemDatas("downloads", position)
					+ "次下载");
			view.appScore.setRating(Float.parseFloat(getItemDatas("score",
					position)));
			view.appScore.setFocusable(false);
			imageLoader.displayImage(getItemDatas("image", position),
					view.imgViewFlag, options);
			setInstallClick(activity, view,
					getItemDatas("download_url", position),
					getItemDatas("title", position));
//		}
		CommonTools.setLayout(position, view);
		view.appDescription.setVisibility(TextView.GONE);
		return convertView;
	}
}
