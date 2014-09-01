package com.smartx.bill.mepad.mestore.dialog;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.DownloadManager;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.trinea.android.common.util.DownloadManagerPro;
import cn.trinea.android.common.util.PreferencesUtils;

import com.smartx.bill.mepad.mestore.R;
import com.smartx.bill.mepad.mestore.Observer.DownloadChangeObserver;
import com.smartx.bill.mepad.mestore.R.id;
import com.smartx.bill.mepad.mestore.adapter.MyViewPagerAdapter;
import com.smartx.bill.mepad.mestore.application.MyApplication;
import com.smartx.bill.mepad.mestore.broadcast.DownloadCompleteReceiver;
import com.smartx.bill.mepad.mestore.broadcast.MySynchroBroadcast;
import com.smartx.bill.mepad.mestore.home.MyBaseActivity;
import com.smartx.bill.mepad.mestore.listener.InstallClickListener;
import com.smartx.bill.mepad.mestore.listener.MyHomeTextClickListener;
import com.smartx.bill.mepad.mestore.listener.MyOnPageChangeListener;
import com.smartx.bill.mepad.mestore.matadata.IOStreamDatas;
import com.smartx.bill.mepad.mestore.matadata.MyBroadcast;
import com.smartx.bill.mepad.mestore.myview.MyRoundProgressBar;
import com.smartx.bill.mepad.mestore.thread.RefreshDownloadUIHandler;
import com.smartx.bill.mepad.mestore.util.CommonTools;
import com.smartx.bill.mepad.mestore.util.CommonTools.CommonViewHolder;

@SuppressWarnings("deprecation")
public class DialogAppInfo extends MyBaseActivity {

	TextView t1, t2, t3, t4;
	LocalActivityManager manager = null;
	ViewPager pager = null;
	private JSONObject appInfo;
	private CommonViewHolder view;
	private Bitmap mBitmap;
	private Context mContext;
	private List<TextView> tViews;
	private String downloadUrl;
	private String appName;
	private MySynchroBroadcast syschroReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_detail_info);
		manager = new LocalActivityManager(this, true);
		manager.dispatchCreate(savedInstanceState);
		this.mBitmap = getIntent().getParcelableExtra("mBitmap");
		mContext = this;
		setLayout();
		try {
			this.appInfo = new JSONObject(getIntent().getStringExtra(
					"jsonObject"));
			downloadUrl = appInfo.getString("download_url");
			appName = appInfo.getString("title");
			initdatas();
			initInstallStatus();
			initPagerViewer();
			initTextView();
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
	}

	/** 
	* @Title: setBrodacast 
	* @Description: 注册广播
	* @return void    返回类型 
	* @throws 
	*/

	private void setLayout() {
		// TODO Auto-generated method stub
		WindowManager m = getWindowManager();
		Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
		LayoutParams p = getWindow().getAttributes();
		p.height = (int) (d.getHeight() * 0.54); // 高度设置为屏幕的0.54
		p.width = (int) (d.getWidth() * 0.90); // 宽度设置为屏幕的0.9
		getWindow().setAttributes(p); // 设置生效
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
		view = new CommonViewHolder();
		CommonTools.setViewById(view, getWindow().getDecorView());

		view.appDescription.setVisibility(View.GONE);
		view.txtViewTitle.setText(appInfo.getString("title"));
		view.downloadCount.setText(appInfo.getString("downloads") + "次下载");
		view.appScore.setRating(Float.parseFloat(appInfo.getString("score")));
		view.imgViewFlag.setImageBitmap(mBitmap);
		view.imgViewFlag.setDrawingCacheEnabled(false);
		view.appScore.setFocusable(false);

		view.appInstall.setOnClickListener(new InstallClickListener(this, view,
				downloadUrl, appName));
		view.appOpen.setVisibility(View.INVISIBLE);
		view.appDownload.setVisibility(View.INVISIBLE);

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
	* @Title: aa 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	private void initInstallStatus(){
		long downloadId = PreferencesUtils.getLong(this, appName,
				0);
		RefreshDownloadUIHandler handler = new RefreshDownloadUIHandler(view, this);
		MyApplication installApplication = (MyApplication)getApplication();
		DownloadManager downloadManager = installApplication.getDownloadManager();
		DownloadManagerPro downloadManagerPro = new DownloadManagerPro(downloadManager);
		DownloadChangeObserver downloadObserver = new DownloadChangeObserver(
				handler, downloadManagerPro, downloadId);
		getContentResolver().registerContentObserver(
				DownloadManagerPro.CONTENT_URI, true, downloadObserver);
		DownloadCompleteReceiver completeReceiver = new DownloadCompleteReceiver(
				downloadId, this, downloadObserver, view);
		registerReceiver(completeReceiver, new IntentFilter(
				DownloadManager.ACTION_DOWNLOAD_COMPLETE));
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
	public void onStop(){
        //取消广播接收器
        super.onStop();
    }
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
