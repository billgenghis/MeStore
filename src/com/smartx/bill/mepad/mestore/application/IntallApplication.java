package com.smartx.bill.mepad.mestore.application;

import android.app.Application;
import android.app.DownloadManager;

public class IntallApplication extends Application {
	// 共享变量
	private DownloadManager downloadManager;

	// set方法
	public void setDownloadManager(DownloadManager downloadManager) {
		this.downloadManager = downloadManager;
	}

	// get方法
	public DownloadManager getDownloadManager() {
		return downloadManager;
	}

}
