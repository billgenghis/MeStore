package com.smartx.bill.mepad.mestore.dialog;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.DownloadManager;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;
import cn.trinea.android.common.util.DownloadManagerPro;
import cn.trinea.android.common.util.PreferencesUtils;

import com.smartx.bill.mepad.mestore.R;
import com.smartx.bill.mepad.mestore.Observer.DownloadChangeObserver;
import com.smartx.bill.mepad.mestore.adapter.MyViewPagerAdapter;
import com.smartx.bill.mepad.mestore.application.MyApplication;
import com.smartx.bill.mepad.mestore.broadcast.DownloadCompleteReceiver;
import com.smartx.bill.mepad.mestore.broadcast.InstallCompleteReceiver;
import com.smartx.bill.mepad.mestore.home.MyBaseActivity;
import com.smartx.bill.mepad.mestore.listener.InstallClickListener;
import com.smartx.bill.mepad.mestore.listener.MyHomeTextClickListener;
import com.smartx.bill.mepad.mestore.listener.MyOnPageChangeListener;
import com.smartx.bill.mepad.mestore.listener.OpenClickListener;
import com.smartx.bill.mepad.mestore.matadata.IOStreamDatas;
import com.smartx.bill.mepad.mestore.matadata.MyBroadcast;
import com.smartx.bill.mepad.mestore.thread.RefreshDownloadUIHandler;
import com.smartx.bill.mepad.mestore.util.CommonTools;
import com.smartx.bill.mepad.mestore.util.CommonTools.CommonViewHolder;

@SuppressWarnings("deprecation")
public class DialogAppInfo extends MyBaseActivity {

	TextView t1, t2, t3, t4;
	LocalActivityManager manager = null;
	ViewPager pager = null;
	private JSONObject appInfo;
	private CommonViewHolder mView;
	private Bitmap mBitmap;
	private List<TextView> tViews;
	private String downloadUrl;
	private String appName;
	private String appPackageName;
	private DownloadCompleteReceiver completeReceiver;
	private int progress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_detail_info);
		manager = new LocalActivityManager(this, true);
		manager.dispatchCreate(savedInstanceState);
		this.mBitmap = getIntent().getParcelableExtra("mBitmap");
		progress = getIntent().getIntExtra("progress", -1);
		setLayout();
		try {
			this.appInfo = new JSONObject(getIntent().getStringExtra(
					"jsonObject"));
			downloadUrl = appInfo.getString("download_url");
			appName = appInfo.getString("title");
			appPackageName = appInfo.getString("package_name");
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
	 * @return void 返回类型
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
		tViews = new ArrayList<TextView>();
		t1 = (TextView) findViewById(R.id.dialog_detail_info);
		t2 = (TextView) findViewById(R.id.dialog_judge_score);
		t3 = (TextView) findViewById(R.id.dialog_same_developers);
		t4 = (TextView) findViewById(R.id.dialog_detail_permission);
		mView = new CommonViewHolder();
		CommonTools.setViewById(mView, getWindow().getDecorView());

		mView.appDescription.setVisibility(View.GONE);
		mView.txtViewTitle.setText(appInfo.getString("title"));
		mView.txtViewTitle.setTag(appInfo.getString("title"));
		mView.downloadCount.setText(appInfo.getString("downloads") + "次下载");
		mView.appScore.setRating(Float.parseFloat(appInfo.getString("score")));
		mView.imgViewFlag.setImageBitmap(mBitmap);
		// view.appScore.setFocusable(false);

		mView.appInstall.setOnClickListener(new InstallClickListener(this,
				mView, downloadUrl, appName, appPackageName));
		mView.appOpen.setOnClickListener(new OpenClickListener(appPackageName,
				this));
		mView.appOpen.setVisibility(View.INVISIBLE);
		mView.appDownload.setVisibility(View.INVISIBLE);

		getAppForPackage();
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
	 * @Title: getAppForPackage
	 * @Description: 判断app是否已经安装在平板中
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void getAppForPackage() {
		PackageManager mPm = getPackageManager();
		PackageInfo pkgInfo;
		try {
			pkgInfo = mPm.getPackageInfo(appPackageName, 0);
		} catch (NameNotFoundException e) {
			pkgInfo = null;
			return;
		}
		if (pkgInfo != null) {
			mView.appInstall.setVisibility(View.INVISIBLE);
			mView.appOpen.setVisibility(View.VISIBLE);
		}
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
	 * @Title: initInstallStatus
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void initInstallStatus() {
		long downloadId = PreferencesUtils.getLong(this, appPackageName, -1);
		if (downloadId != -1) {
			RefreshDownloadUIHandler handler = new RefreshDownloadUIHandler(
					mView, appName, this);
			MyApplication installApplication = (MyApplication) getApplication();
			DownloadManager downloadManager = installApplication
					.getDownloadManager();
			DownloadManagerPro downloadManagerPro = new DownloadManagerPro(
					downloadManager);
			DownloadChangeObserver downloadObserver = new DownloadChangeObserver(
					handler, downloadManagerPro, downloadId);
			getContentResolver().registerContentObserver(
					DownloadManagerPro.CONTENT_URI, true, downloadObserver);
			completeReceiver = new DownloadCompleteReceiver(downloadId, this,
					downloadObserver, mView, appName, appPackageName);
			IntentFilter intentFilter = new IntentFilter();
			intentFilter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
			registerReceiver(completeReceiver, intentFilter);
		}
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

	public void onStop() {
		// 取消广播接收器
		// if (completeReceiver != null)
		// unregisterReceiver(completeReceiver);
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
