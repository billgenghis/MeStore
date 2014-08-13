package com.smartx.bill.mepad.mestore.recommend;

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

public class Recommendation extends Activity {
	private Context context = null;
	private LocalActivityManager manager = null;
	private ViewPager pager = null;
	private TextView t1, t2;
	private List<TextView> tViews;
	private String recomType;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.me_recommendtion);

		recomType = getIntent().getStringExtra("recomType");
		context = Recommendation.this;
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
		t1 = (TextView) findViewById(R.id.me_recom_excellent);
		t2 = (TextView) findViewById(R.id.me_recom_new);
		tViews.add(t1);
		tViews.add(t2);
	}

	/**
	 * 初始化标题
	 */
	private void initTextView() {
		t1.setOnClickListener(new MyHomeTextClickListener(0, pager, this,
				tViews, IOStreamDatas.VIEWPAGER_RECOM));
		t2.setOnClickListener(new MyHomeTextClickListener(1, pager, this,
				tViews, IOStreamDatas.VIEWPAGER_RECOM));
	}

	/**
	 * 初始化PageViewer
	 */
	private void initPagerViewer() {
		final ArrayList<View> list = new ArrayList<View>();
		Intent intent = new Intent(context, RecomExcellent.class);
		list.add(getView("RecomExcellent", intent));
		Intent intent2 = new Intent(context, RecomNew.class);
		list.add(getView("RecomNew", intent2));

		pager.setAdapter(new MyViewPagerAdapter(list));
		if (recomType.equals(IOStreamDatas.POSITION_EXCELLENT)) {
			pager.setCurrentItem(0);
			findViewById(R.id.me_recom_tab)
			.setBackgroundResource(R.drawable.tab_left);
			((TextView) findViewById(R.id.me_recom_excellent)).setTextColor(Color.parseColor("#ffffff"));
			((TextView) findViewById(R.id.me_recom_new)).setTextColor(Color.parseColor("#a1a2a3"));
		}else if(recomType.equals(IOStreamDatas.POSITION_NEW)){
			pager.setCurrentItem(1);
			findViewById(R.id.me_recom_tab)
			.setBackgroundResource(R.drawable.tab_right);
			((TextView) findViewById(R.id.me_recom_excellent)).setTextColor(Color.parseColor("#a1a2a3"));
			((TextView) findViewById(R.id.me_recom_new)).setTextColor(Color.parseColor("#ffffff"));
		}
		pager.setOnPageChangeListener(new MyOnPageChangeListener(this, tViews,
				IOStreamDatas.VIEWPAGER_RECOM));
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
