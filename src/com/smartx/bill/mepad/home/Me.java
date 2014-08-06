package com.smartx.bill.mepad.home;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.smartx.bill.mepad.R;
import com.smartx.bill.mepad.adapter.GridviewAdapter;
import com.smartx.bill.mepad.dialog.QustomDialogBuilder;

public class Me extends Activity {

	private GridviewAdapter mAdapter;
	private GridView gridView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_me);
		initGridView();
	}

	private void initGridView() {

		// prepared arraylist and passed it to the Adapter class
		mAdapter = new GridviewAdapter(this,
				new ArrayList<HashMap<String, Object>>());

		// Set custom adapter to gridview
		gridView = (GridView) findViewById(R.id.gridView1);
		gridView.setNumColumns(3);
		gridView.setAdapter(mAdapter);
		gridView.setFocusable(false);
		// Implement On Item click listener
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

				Toast.makeText(Me.this, mAdapter.getItem(position),
						Toast.LENGTH_SHORT).show();
				Resources resources = arg1.getContext().getResources();

				int indentify = resources.getIdentifier(mAdapter
						.getItem(position), "drawable", arg1.getContext()
						.getPackageName());
				Log.i("drawable", mAdapter.getItem(position) + indentify + ""
						+ arg1.getContext().getPackageName());
				if (indentify > 0) {
					QustomDialogBuilder qustomDialogBuilder = new QustomDialogBuilder(
							arg1.getContext())
							.setMessage(
									"                                                         ")
							.setCustomView(R.layout.dialog, arg1.getContext())
							.setIcon(getResources().getDrawable(indentify));
					qustomDialogBuilder.show();
				}
			}
		});
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	/**
	 * 屏幕切换时进行处理 如果屏幕是竖屏，则显示3列，如果是横屏，则显示4列
	 */
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		int imageCol = 3;
		try {

			super.onConfigurationChanged(newConfig);
			// 如果屏幕是竖屏，则显示3列，如果是横屏，则显示4列
			if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
				imageCol = 4;
				Toast.makeText(Me.this, "现在是横屏", Toast.LENGTH_SHORT).show();
			} else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
				imageCol = 3;
			}
			gridView.setNumColumns(imageCol);
			gridView.setAdapter(mAdapter);
			// ia.notifyDataSetChanged();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
