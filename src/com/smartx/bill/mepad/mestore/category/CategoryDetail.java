package com.smartx.bill.mepad.mestore.category;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.smartx.bill.mepad.mestore.R;
import com.smartx.bill.mepad.mestore.adapter.MyViewPagerAdapter;
import com.smartx.bill.mepad.mestore.listener.MyHomeTextClickListener;
import com.smartx.bill.mepad.mestore.listener.MyOnPageChangeListener;
import com.smartx.bill.mepad.mestore.matadata.IOStreamDatas;

public class CategoryDetail extends Activity {
	private Context context = null;
	private LocalActivityManager manager = null;
	private ViewPager pager = null;
	private TextView t1, t2, t3;
	private TextView categoryName;
	private List<TextView> tViews;
	private Bundle mBundle;
	private String className;
	private String classId;
	private int recomType;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.category_detail_info);

		mBundle = getIntent().getBundleExtra("CategoryInfo");
		className = mBundle.getString("name");
		classId = mBundle.getString("classId");
		recomType = mBundle.getInt("recomType");
		context = CategoryDetail.this;
		manager = new LocalActivityManager(this, true);
		manager.dispatchCreate(savedInstanceState);
		initdatas();
		// InitImageView();
		initPagerViewer();
		initTextView();
	}

	private void initdatas() {
		pager = (ViewPager) findViewById(R.id.me_recom_viewpage);
		// cursor = (ImageView) findViewById(R.id.cursor);
		tViews = new ArrayList<TextView>();
		t1 = (TextView) findViewById(R.id.category_recom_excellent);
		t2 = (TextView) findViewById(R.id.category_recom_ranking);
		t3 = (TextView) findViewById(R.id.category_recom_new);
		categoryName = (TextView) findViewById(R.id.category_name);
		categoryName.setText(className);
		tViews.add(t1);
		tViews.add(t2);
		tViews.add(t3);
	}

	/**
	 * 初始化标题
	 */
	private void initTextView() {
		t1.setOnClickListener(new MyHomeTextClickListener(0, pager, this,
				tViews, IOStreamDatas.VIEWPAGER_CATE_RECOM));
		t2.setOnClickListener(new MyHomeTextClickListener(1, pager, this,
				tViews, IOStreamDatas.VIEWPAGER_CATE_RECOM));
		t3.setOnClickListener(new MyHomeTextClickListener(2, pager, this,
				tViews, IOStreamDatas.VIEWPAGER_CATE_RECOM));
	}

	/**
	 * 初始化PageViewer
	 */
	private void initPagerViewer() {
		final ArrayList<View> list = new ArrayList<View>();
		Intent intent = new Intent(context, CategoryExcellent.class);
		list.add(getView("CategoryExcellent", intent));
		Intent intent2 = new Intent(context, CategoryRanking.class);
		list.add(getView("CategoryRanking", intent2));
		Intent intent3 = new Intent(context, CategoryNew.class);
		list.add(getView("CategoryNew", intent3));

		pager.setAdapter(new MyViewPagerAdapter(list));
		if (recomType == IOStreamDatas.TAB_EXCELLENT) {
			pager.setCurrentItem(0);
			findViewById(R.id.category_recom_tab).setBackgroundResource(
					R.drawable.tab2_left);
			((TextView) findViewById(R.id.category_recom_excellent))
					.setTextColor(Color.parseColor("#ffffff"));
			((TextView) findViewById(R.id.category_recom_ranking))
					.setTextColor(Color.parseColor("#a1a2a3"));
			((TextView) findViewById(R.id.category_recom_new))
					.setTextColor(Color.parseColor("#a1a2a3"));
		} else if (recomType == IOStreamDatas.TAB_RANKING) {
			pager.setCurrentItem(1);
			findViewById(R.id.category_recom_tab).setBackgroundResource(
					R.drawable.tab2_middle);
			((TextView) findViewById(R.id.category_recom_excellent))
					.setTextColor(Color.parseColor("#ffffff"));
			((TextView) findViewById(R.id.category_recom_ranking))
					.setTextColor(Color.parseColor("#a1a2a3"));
			((TextView) findViewById(R.id.category_recom_new))
					.setTextColor(Color.parseColor("#a1a2a3"));
		} else if (recomType == IOStreamDatas.TAB_NEW) {
			pager.setCurrentItem(2);
			findViewById(R.id.category_recom_tab).setBackgroundResource(
					R.drawable.tab2_right);
			((TextView) findViewById(R.id.category_recom_excellent))
					.setTextColor(Color.parseColor("#ffffff"));
			((TextView) findViewById(R.id.category_recom_ranking))
					.setTextColor(Color.parseColor("#a1a2a3"));
			((TextView) findViewById(R.id.category_recom_new))
					.setTextColor(Color.parseColor("#a1a2a3"));
		}
		pager.setOnPageChangeListener(new MyOnPageChangeListener(this, tViews,
				IOStreamDatas.VIEWPAGER_CATE_RECOM));
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
		manager.dispatchResume();
	}
}
