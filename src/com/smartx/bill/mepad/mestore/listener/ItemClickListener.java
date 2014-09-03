package com.smartx.bill.mepad.mestore.listener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;

import com.smartx.bill.mepad.mestore.R;
import com.smartx.bill.mepad.mestore.R.id;
import com.smartx.bill.mepad.mestore.broadcast.MySynchroBroadcast;
import com.smartx.bill.mepad.mestore.dialog.DialogAppInfo;
import com.smartx.bill.mepad.mestore.matadata.MyBroadcast;
import com.smartx.bill.mepad.mestore.myview.MyRoundProgressBar;
import com.smartx.bill.mepad.mestore.util.CommonTools;
import com.smartx.bill.mepad.mestore.util.CommonTools.CommonViewHolder;

public class ItemClickListener implements OnItemClickListener {

	private Context mContext;
	private Activity mActivity;
	private JSONArray jsonArray;
	private Bitmap mBitmap;

	public ItemClickListener(Context mContext, Activity mActivity,
			Bundle savedInstanceState, JSONArray response) {
		this.mActivity = mActivity;
		this.mContext = mContext;
		this.jsonArray = response;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		JSONObject jsonObject;
		arg1.findViewById(R.id.app_icon).setDrawingCacheEnabled(true);
		mBitmap = arg1.findViewById(R.id.app_icon).getDrawingCache();
		try {
			jsonObject = jsonArray.getJSONObject(arg2);
			Intent intent = new Intent(mContext, DialogAppInfo.class);

			MyRoundProgressBar myRoundProgressBar = (MyRoundProgressBar) arg1
					.findViewById(R.id.app_download);
			if (myRoundProgressBar!= null && myRoundProgressBar.getVisibility() == 0) {
				intent.putExtra("progress", myRoundProgressBar.getProgress());
			} else {
				intent.putExtra("progress", -1);// 代表进度为-1，没有进行下载
			}
			intent.putExtra("jsonObject", jsonObject.toString());
			intent.putExtra("mBitmap", mBitmap);
			mActivity.startActivity(intent);
			arg1.findViewById(R.id.app_icon).setDrawingCacheEnabled(false);
			setBrodacast(arg1, jsonObject.get("package_name").toString(),
					jsonObject.get("title").toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void setBrodacast(View convertView, String appPackageName,
			String appName) {
		CommonViewHolder view = new CommonViewHolder();
		CommonTools.setViewById(view, convertView);
		MySynchroBroadcast syschroReceiver = new MySynchroBroadcast(mActivity,
				view, appPackageName, appName);
		String broadcastFilter = MyBroadcast.MESTORE_BROADCAST_TITLE
				+ appPackageName;
		mActivity.registerReceiver(syschroReceiver, new IntentFilter(
				broadcastFilter));
	}

}
