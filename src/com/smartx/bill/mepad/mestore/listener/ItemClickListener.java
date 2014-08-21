package com.smartx.bill.mepad.mestore.listener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.smartx.bill.mepad.mestore.R;
import com.smartx.bill.mepad.mestore.dialog.DialogAppInfo;

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
			mActivity.startActivity(intent);

//			 MyAppInfoDialogBuilder qustomDialogBuilder = new
//			 MyAppInfoDialogBuilder(
//			 mContext, mActivity, savedInstanceState, jsonObject,
//			 mBitmap);
//			 qustomDialogBuilder.show();
//			
//			 WindowManager m = mActivity.getWindowManager();
//			 Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
//			 LayoutParams p = qustomDialogBuilder.getWindow().getAttributes();
//			 // 获取对话框当前的参数值
//			 p.height = (int) (d.getHeight() * 0.6); // 高度设置为屏幕的0.6
//			 p.width = (int) (d.getWidth() * 0.95); // 宽度设置为屏幕的0.95
//			 qustomDialogBuilder.setCancelable(true);
//			 qustomDialogBuilder.getWindow().setAttributes(p); // 设置生效
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
