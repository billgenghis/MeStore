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
import com.smartx.bill.mepad.mestore.util.CommonTools;
import com.smartx.bill.mepad.mestore.util.CommonTools.CommonViewHolder;

public class ItemClickListener implements OnItemClickListener {

	private Context mContext;
	private Activity mActivity;
	private Bundle savedInstanceState;
	private JSONArray jsonArray;
	private Bitmap mBitmap;

	public ItemClickListener(Context mContext, Activity mActivity,
			Bundle savedInstanceState, JSONArray response) {
		this.mActivity = mActivity;
		this.mContext = mContext;
		this.savedInstanceState = savedInstanceState;
		this.jsonArray = response;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		JSONObject jsonObject;
		mBitmap = arg1.findViewById(R.id.app_icon).getDrawingCache();
		try {
			jsonObject = jsonArray.getJSONObject(arg2);
			Intent intent = new Intent(mContext, DialogAppInfo.class);
			intent.putExtra("jsonObject", jsonObject.toString());
			intent.putExtra("mBitmap", mBitmap);

			CommonViewHolder view = new CommonViewHolder();
			CommonTools.setViewById(view, arg1);
			MySynchroBroadcast syschroReceiver = new MySynchroBroadcast(
					mActivity, view,jsonObject.getString("title"));
			String broadcastFilter = MyBroadcast.MESTORE_BROADCAST_TITLE
					+ jsonObject.getString("title");
			mActivity.registerReceiver(syschroReceiver, new IntentFilter(
					broadcastFilter));

			mActivity.startActivity(intent);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
