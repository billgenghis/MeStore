package com.smartx.bill.mepad.mestore.application;

import java.util.HashMap;

import android.app.Application;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class MyApplication extends Application {
	// 共享变量
	private DownloadManager downloadManager;
	private HashMap<String, BroadcastReceiver> broadcastMap;

	@Override
	public void onCreate() {
		super.onCreate();
		broadcastMap = new HashMap<String, BroadcastReceiver>();
		initImageLoader(getApplicationContext());
	}

	public static void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you
		// may tune some of them,
		// or you can create default configuration by
		// ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				// .writeDebugLogs() // Remove for release app
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
		
		
	}

	// set方法
	public void setDownloadManager(DownloadManager downloadManager) {
		this.downloadManager = downloadManager;
	}

	// get方法
	public DownloadManager getDownloadManager() {
		return downloadManager;
	}

	// set方法
	public void setBroadCast(String downloadId,BroadcastReceiver BroadcastReceiver) {
		this.broadcastMap.put(downloadId, BroadcastReceiver);
	}

	// get方法
	public HashMap<String, BroadcastReceiver> getBroadcastReceiver() {
		return broadcastMap;
	}

}
