package com.smartx.bill.mepad.mestore.dialog;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.DownloadManager;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.smartx.bill.mepad.mestore.R;
import com.smartx.bill.mepad.mestore.R.id;
import com.smartx.bill.mepad.mestore.adapter.MyViewPagerAdapter;
import com.smartx.bill.mepad.mestore.home.MyBaseActivity;
import com.smartx.bill.mepad.mestore.listener.InstallClickListener;
import com.smartx.bill.mepad.mestore.listener.MyHomeTextClickListener;
import com.smartx.bill.mepad.mestore.listener.MyOnPageChangeListener;
import com.smartx.bill.mepad.mestore.matadata.IOStreamDatas;
import com.smartx.bill.mepad.mestore.myview.MyRoundProgressBar;

@SuppressWarnings("deprecation")
public class DialogAppInfo extends MyBaseActivity {

	TextView t1, t2, t3, t4;
	LocalActivityManager manager = null;
	ViewPager pager = null;
	private JSONObject appInfo;
	private ImageView imgViewFlag;
	private TextView txtViewTitle;
	private TextView downloadCount;
	private RatingBar appScore;
	private Button appInstall;
	private Button appOpen;
	private MyRoundProgressBar appDownload;
	private Bitmap mBitmap;
	private Context mContext;
	List<TextView> tViews;

	private String downloadUrl;
	private String appName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_detail_info);
		manager = new LocalActivityManager(this, true);
		manager.dispatchCreate(savedInstanceState);
		this.mBitmap = getIntent().getParcelableExtra("mBitmap");
		mContext = this;
		WindowManager m = getWindowManager();
		Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
		LayoutParams p = getWindow().getAttributes();
		// 获取对话框当前的参数值
		p.height = (int) (d.getHeight() * 0.54); // 高度设置为屏幕的0.54
		p.width = (int) (d.getWidth() * 0.90); // 宽度设置为屏幕的0.9
		getWindow().setAttributes(p); // 设置生效
		try {
			this.appInfo = new JSONObject(getIntent().getStringExtra(
					"jsonObject"));
			downloadUrl = appInfo.getString("download_url");
			appName = appInfo.getString("title");
			initdatas();
			initPagerViewer();
			initTextView();
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 初始化标题
	 * 
	 * @throws JSONException
	 */

	private void initdatas() throws JSONException {
		pager = (ViewPager) findViewById(R.id.dialog_viewpage);
		// cursor = (ImageView) findViewById(R.id.cursor);
		tViews = new ArrayList<TextView>();
		t1 = (TextView) findViewById(R.id.dialog_detail_info);
		t2 = (TextView) findViewById(R.id.dialog_judge_score);
		t3 = (TextView) findViewById(R.id.dialog_same_developers);
		t4 = (TextView) findViewById(R.id.dialog_detail_permission);
		txtViewTitle = (TextView) findViewById(R.id.app_title);
		imgViewFlag = (ImageView) findViewById(R.id.app_icon);
		appScore = (RatingBar) findViewById(R.id.app_score);
		downloadCount = (TextView) findViewById(R.id.app_download_count);
		appInstall = (Button) findViewById(R.id.app_install);
		appOpen = (Button) findViewById(R.id.app_open);
		appDownload = (MyRoundProgressBar) findViewById(R.id.app_download);
		findViewById(id.app_description).setVisibility(View.GONE);

		txtViewTitle.setText(appInfo.getString("title"));
		downloadCount.setText(appInfo.getString("downloads") + "次下载");
		appScore.setRating(Float.parseFloat(appInfo.getString("score")));
		imgViewFlag.setImageBitmap(mBitmap);
		imgViewFlag.setDrawingCacheEnabled(false);
		appScore.setFocusable(false);
		appInstall.setOnClickListener(new InstallClickListener(this, null,
				downloadUrl, appName));
		appOpen.setVisibility(View.INVISIBLE);
		appDownload.setVisibility(View.INVISIBLE);
		
		findViewById(R.id.dialog_appinfo_tab).setVisibility(View.GONE);
		t1.setVisibility(View.GONE);
		t2.setVisibility(View.GONE);
		t3.setVisibility(View.GONE);
		t4.setVisibility(View.GONE);
		// tViews.add(t1);
		// tViews.add(t2);
		// tViews.add(t3);
		// tViews.add(t4);
	}

	/**
	 * 
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
	 * 
	 * @throws JSONException
	 */
	private void initPagerViewer() throws JSONException {
		final ArrayList<View> dialogList = new ArrayList<View>();
		Intent intent = new Intent(this, AppDetailInfo.class);
		intent.putExtra("appInfo", appInfo.toString());
		dialogList.add(getView("AppDetailInfo", intent));

		// Intent intent2 = new Intent(this, DownLoadApk.class);
		// dialogList.add(getView("DownLoadApk", intent2));
		//
		// Intent intent3 = new Intent(this, SameDevelpersApp.class);
		// intent3.putExtra("developer", appInfo.getString("developer"));
		// dialogList.add(getView("SameDevelpersApp", intent3));
		//
		// Intent intent4 = new Intent(this, AppPermission.class);
		// dialogList.add(getView("AppPermission", intent4));

		pager.setAdapter(new MyViewPagerAdapter(dialogList));
		pager.setCurrentItem(0);
		pager.setOnPageChangeListener(new MyOnPageChangeListener(this, tViews,
				IOStreamDatas.VIEWPAGER_DIALOG));
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

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
