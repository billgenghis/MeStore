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
import com.smartx.bill.mepad.mestore.util.CommonTools;

public class MainActivity extends MyBaseActivity {

	Context context = null;
	LocalActivityManager manager = null;
	ViewPager pager = null;
	TextView t1, t2, t3, t4;
	List<TextView> tViews;

	// private int offset = 0;// 动画图片偏移量
	// private int currIndex = 0;// 当前页卡编号
	// private int bmpW;// 动画图片宽度
	// private ImageView cursor;// 动画图片

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		context = MainActivity.this;
		manager = new LocalActivityManager(this, true);
		manager.dispatchCreate(savedInstanceState);
		initdatas();
		// InitImageView();
		initPagerViewer();
		initTextView();
		EditText searchText;
		searchText = (EditText)findViewById(R.id.home_search);
		Log.i("searchName1", searchText.getText().toString());
		searchText.setOnEditorActionListener(new SearchOnEditorActionListener(this));
	}

	private void initdatas() {
		pager = (ViewPager) findViewById(R.id.viewpage);
		// cursor = (ImageView) findViewById(R.id.cursor);
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

	// /**
	// * 初始化动画
	// */
	// private void InitImageView() {
	// bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.roller)
	// .getWidth();// 获取图片宽度
	// DisplayMetrics dm = new DisplayMetrics();
	// getWindowManager().getDefaultDisplay().getMetrics(dm);
	// int screenW = dm.widthPixels;// 获取分辨率宽度
	// offset = (screenW / 14 - bmpW) / 2;// 计算偏移量
	// Matrix matrix = new Matrix();
	// matrix.postTranslate(offset, 0);
	// cursor.setImageMatrix(matrix);// 设置动画初始位置
	// }

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// getMenuInflater().inflate(R.menu.activity_main, menu);
	// SearchView searchView = (SearchView)
	// menu.findItem(R.id.my_search_view).getActionView();
	// return true;
	// }

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

	// /**
	// * Pager适配器
	// */
	// public class MyPagerAdapter extends PagerAdapter {
	// List<View> list = new ArrayList<View>();
	//
	// public MyPagerAdapter(ArrayList<View> list) {
	// this.list = list;
	// }
	//
	// @Override
	// public void destroyItem(ViewGroup container, int position, Object object)
	// {
	// ViewPager pViewPager = ((ViewPager) container);
	// pViewPager.removeView(list.get(position));
	// }
	//
	// @Override
	// public boolean isViewFromObject(View arg0, Object arg1) {
	// return arg0 == arg1;
	// }
	//
	// @Override
	// public int getCount() {
	// return list.size();
	// }
	//
	// @Override
	// public Object instantiateItem(View arg0, int arg1) {
	// ViewPager pViewPager = ((ViewPager) arg0);
	// pViewPager.addView(list.get(arg1));
	// return list.get(arg1);
	// }
	//
	// @Override
	// public void restoreState(Parcelable arg0, ClassLoader arg1) {
	//
	// }
	//
	// @Override
	// public Parcelable saveState() {
	// return null;
	// }
	//
	// @Override
	// public void startUpdate(View arg0) {
	// }
	// }
	//
	// /**
	// * 页卡切换监听
	// */
	// public class MyOnPageChangeListener implements OnPageChangeListener {
	//
	// int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
	// int two = one * 2;// 页卡1 -> 页卡3 偏移量
	// int three = one * 3;// 页卡1 -> 页卡4 偏移量
	//
	// @Override
	// public void onPageSelected(int arg0) {
	// Animation animation = null;
	// switch (arg0) {
	// case 0:
	// if (currIndex == 1) {
	// animation = new TranslateAnimation(one, 0, 0, 0);
	// } else if (currIndex == 2) {
	// animation = new TranslateAnimation(two, 0, 0, 0);
	// } else if (currIndex == 3) {
	// animation = new TranslateAnimation(three, 0, 0, 0);
	// }
	// break;
	// case 1:
	// if (currIndex == 0) {
	// animation = new TranslateAnimation(offset, one, 0, 0);
	// } else if (currIndex == 2) {
	// animation = new TranslateAnimation(two, one, 0, 0);
	// } else if (currIndex == 3) {
	// animation = new TranslateAnimation(three, one, 0, 0);
	// }
	// break;
	// case 2:
	// if (currIndex == 0) {
	// animation = new TranslateAnimation(offset, two, 0, 0);
	// } else if (currIndex == 1) {
	// animation = new TranslateAnimation(one, two, 0, 0);
	// } else if (currIndex == 3) {
	// animation = new TranslateAnimation(three, two, 0, 0);
	// }
	// break;
	// case 3:
	// if (currIndex == 0) {
	// animation = new TranslateAnimation(offset, three, 0, 0);
	// } else if (currIndex == 1) {
	// animation = new TranslateAnimation(one, three, 0, 0);
	// } else if (currIndex == 2) {
	// animation = new TranslateAnimation(two, three, 0, 0);
	// }
	// break;
	// }
	// currIndex = arg0;
	// animation.setFillAfter(true);// True:图片停在动画结束位置
	// animation.setDuration(300);
	// cursor.startAnimation(animation);
	// }
	//
	// @Override
	// public void onPageScrollStateChanged(int arg0) {
	//
	// }
	//
	// @Override
	// public void onPageScrolled(int arg0, float arg1, int arg2) {
	//
	// }
	// }
	//
	// /**
	// * 头标点击监听
	// */
	// public class MyOnClickListener implements View.OnClickListener {
	// private int index = 0;
	//
	// public MyOnClickListener(int i) {
	// index = i;
	// }
	//
	// @Override
	// public void onClick(View v) {
	// pager.setCurrentItem(index);
	// }
	// };

	@Override
	protected void onPause() {
		super.onPause();
		manager.dispatchPause(true);
	}

	@Override
	protected void onResume() {
		super.onResume();
		manager.dispatchResume();
	}
}
