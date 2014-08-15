package com.smartx.bill.mepad.mestore.listener;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.smartx.bill.mepad.mestore.home.Search;

public class SearchOnEditorActionListener implements OnEditorActionListener {
	private Activity mAcitivity;
	private String searchName;

	public SearchOnEditorActionListener(Activity mAcitivity) {
		this.mAcitivity = mAcitivity;
	}

	@Override
	public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
		searchName = arg0.getText().toString().replaceAll(" ", "").replaceAll("　", "");
		if (searchName.length() > 0) {// 判断是否全为空格
			Intent intent = new Intent(mAcitivity, Search.class);
			intent.putExtra("searchName", searchName);
			mAcitivity.startActivity(intent);
		}else{
			return true;
		}
		return false;
	}
}
