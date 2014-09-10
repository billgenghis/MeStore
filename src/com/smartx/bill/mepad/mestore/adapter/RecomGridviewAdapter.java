package com.smartx.bill.mepad.mestore.adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.util.Log;
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
import com.smartx.bill.mepad.mestore.util.CommonTools;
import com.smartx.bill.mepad.mestore.util.CommonTools.CommonViewHolder;

public class RecomGridviewAdapter extends MyBaseAdapter {
	private Activity activity;
	private JSONArray appsInfo;
	private ImageLoader imageLoader;

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
		if (appsInfo.length() % 2 == 1) {
			return appsInfo.length() + 1;
		} else {
			return appsInfo.length();
		}
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

	// public static class CommonViewHolder {
	// public ImageView imgViewFlag;
	// public TextView txtViewTitle;
	// public TextView downloadCount;
	// public RatingBar appScore;
	// public Button appInstall;
	// public RelativeLayout mRelativeLayout;
	// public TextView appDescription;
	// }

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		int rankingCount = position + 1;
		CommonViewHolder view;
		LayoutInflater inflator = activity.getLayoutInflater();

		if (convertView == null) {
			view = new CommonViewHolder();
			convertView = inflator.inflate(R.layout.me_recom_gridview_item,
					null);
			CommonTools.setViewById(view, convertView);
			convertView.setTag(view);
		} else {
			view = (CommonViewHolder) convertView.getTag();
		}
		if (position == getCount() - 1 && appsInfo.length() < getCount()) {
			view.mRelativeLayout.setVisibility(View.INVISIBLE);
			view.imgViewFlag.setVisibility(View.INVISIBLE);
			view.verticalLine.setVisibility(View.INVISIBLE);
		} else {
			view.mRelativeLayout.setVisibility(View.VISIBLE);
			view.imgViewFlag.setVisibility(View.VISIBLE);
			view.verticalLine.setVisibility(View.VISIBLE);
			view.appInstall.setVisibility(View.VISIBLE);
			view.appOpen.setVisibility(View.INVISIBLE);

			view.txtViewTitle.setText(rankingCount + "."
					+ getItemDatas("title", position));
			view.txtViewTitle.setTag(getItemDatas("title", position));
			view.downloadCount.setText(getItemDatas("downloads", position)
					+ "次下载");
			view.appScore.setRating(Float.parseFloat(getItemDatas("score",
					position)));
			imageLoader.displayImage(getItemDatas("image", position),
					view.imgViewFlag, options);
			view.appDescription.setText(getItemDatas("description", position));
			setInstallClick(activity, view,
					getItemDatas("download_url", position),
					getItemDatas("title", position),
					getItemDatas("package_name", position));
		}
		CommonTools.setLayout(position, view);
		return convertView;
	}
}
