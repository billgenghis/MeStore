package com.smartx.bill.mepad.mestore.home;

import java.util.ArrayList;
import java.util.List;

import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.smartx.bill.mepad.mestore.R;
import com.smartx.bill.mepad.mestore.adapter.MyViewPagerAdapter;
import com.smartx.bill.mepad.mestore.listener.MyHomeTextClickListener;
import com.smartx.bill.mepad.mestore.listener.MyOnPageChangeListener;
import com.smartx.bill.mepad.mestore.listener.SearchOnEditorActionListener;
import com.smartx.bill.mepad.mestore.matadata.IOStreamDatas;

@SuppressWarnings("deprecation")
public class MainActivity extends MyBaseActivity {

	Context context = null;
	LocalActivityManager manager = null;
	ViewPager pager = null;
	TextView t1, t2, t3, t4;
	List<TextView> tViews;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		context = MainActivity.this;
		manager = new LocalActivityManager(this, true);
		manager.dispatchCreate(savedInstanceState);
		initdatas();
		initPagerViewer();
		initTextView();
		EditText searchText;
		searchText = (EditText) findViewById(R.id.home_search);
		searchText.setOnEditorActionListener(new SearchOnEditorActionListener(
				this));
	}

	private void initdatas() {
		pager = (ViewPager) findViewById(R.id.viewpage);
		tViews = new ArrayList<TextView>();
		t1 = (TextView) findViewById(R.id.home_me);
		t2 = (TextView) findViewById(R.id.home_ranking);
		t3 = (TextView) findViewById(R.id.home_category);
		t4 = (TextView) findViewById(R.id.home_special);
		t1.setTextColor(Color.parseColor("#303030"));

		tViews.add(t1);
		tViews.add(t2);
		tViews.add(t3);
		tViews.add(t4);
	}

	/**
	 * 初始化标题
	 */
	private void initTextView() {
		t1.setOnClickListener(new MyHomeTextClickListener(0, pager, this,
				tViews, IOStreamDatas.VIEWPAGER_HOME));
		t2.setOnClickListener(new MyHomeTextClickListener(1, pager, this,
				tViews, IOStreamDatas.VIEWPAGER_HOME));
		t3.setOnClickListener(new MyHomeTextClickListener(2, pager, this,
				tViews, IOStreamDatas.VIEWPAGER_HOME));
		t4.setOnClickListener(new MyHomeTextClickListener(3, pager, this,
				tViews, IOStreamDatas.VIEWPAGER_HOME));
	}

	/**
	 * 初始化PageViewer
	 */
	private void initPagerViewer() {
		final ArrayList<View> list = new ArrayList<View>();
		Intent intent = new Intent(context, Me.class);
		list.add(getView("Me", intent));
		Intent intent2 = new Intent(context, Ranking.class);
		list.add(getView("Ranking", intent2));
		Intent intent3 = new Intent(context, Category.class);
		list.add(getView("Category", intent3));
		Intent intent4 = new Intent(context, Special.class);
		list.add(getView("Special", intent4));

		pager.setAdapter(new MyViewPagerAdapter(list));
		pager.setCurrentItem(0);
		pager.setOnPageChangeListener(new MyOnPageChangeListener(this, tViews,
				IOStreamDatas.VIEWPAGER_HOME));
	}

	@Override
	public void onBackPressed() {
		imageLoader.stop();
		super.onBackPressed();
	}

	/**
	 * 通过activity获取视图
	 * 
	 * @param id
	 * @param intent
	 * @return
	 */
	private View getView(String id, Intent intent) {
		return manager.startActivity(id, intent).getDecorView();
	}

	@Override
	protected void onPause() {
		super.onPause();
		manager.dispatchPause(true);
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.i("manager", manager.toString());
		manager.dispatchResume();
	}
}
