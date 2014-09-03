package com.smartx.bill.mepad.mestore.Observer;

import android.database.ContentObserver;
import cn.trinea.android.common.util.DownloadManagerPro;

import com.smartx.bill.mepad.mestore.thread.RefreshDownloadUIHandler;

public class DownloadChangeObserver extends ContentObserver {

	DownloadManagerPro downloadManagerPro;
	RefreshDownloadUIHandler handler;
	long downloadId;

	public DownloadChangeObserver(RefreshDownloadUIHandler handler,
			DownloadManagerPro downloadManagerPro,long downloadId) {
		super(handler);
		this.handler = handler;
		this.downloadManagerPro = downloadManagerPro;
		this.downloadId = downloadId;
	}

	@Override
	public void onChange(boolean selfChange) {
		int[] bytesAndStatus = downloadManagerPro
				.getBytesAndStatus(downloadId);
		handler.sendMessage(handler.obtainMessage(0, bytesAndStatus[0],
				bytesAndStatus[1], bytesAndStatus[2]));
	}
}