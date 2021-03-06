package com.smartx.bill.mepad.mestore.home;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;

import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.smartx.bill.mepad.mestore.R;
import com.smartx.bill.mepad.mestore.matadata.IOStreamDatas;
import com.smartx.bill.mepad.mestore.uimgloader.Constants.Config;

public class MyBaseActivity extends Activity {

	protected ImageLoader imageLoader = ImageLoader.getInstance();

	public void initImageLoader(Context context) {
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.item_clear_memory_cache:
			imageLoader.clearMemoryCache();
			return true;
		case R.id.item_clear_disc_cache:
			imageLoader.clearDiscCache();
			return true;
		default:
			return false;
		}
	}

	protected String getDataUrl(int dataTypes) {
		String dataURL = null;
		if (dataTypes == IOStreamDatas.APP_DATA) {
			dataURL = IOStreamDatas.SERVER_URL + IOStreamDatas.APPSINFO_URL;
		} else if (dataTypes == IOStreamDatas.CATEGORY_DATA) {
			dataURL = IOStreamDatas.SERVER_URL + IOStreamDatas.CATEGORY_URL;
		} else if (dataTypes == IOStreamDatas.SPECIAL_DATA) {
			dataURL = IOStreamDatas.SERVER_URL + IOStreamDatas.SPECIAL_URL;
		}
		return dataURL;
	}

	protected RequestParams getParams(String class_id, String age,
			String position_id, String keyword, String special_id,
			String developer, String page) {
		RequestParams params = new RequestParams();
		params.put("class_id", class_id);
		params.put("age", age);
		params.put("position_id", position_id);
		params.put("keyword", keyword);
		params.put("special_id", special_id);
		params.put("developer", developer);
		params.put("page", page);
		return params;
	}

	protected List<NameValuePair> getParams(String class_id, String age,
			String position_id, String keyword, String special_id, String page) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("class_id", class_id));
		params.add(new BasicNameValuePair("age", age));
		params.add(new BasicNameValuePair("position_id", position_id));
		params.add(new BasicNameValuePair("special_id", special_id));
		params.add(new BasicNameValuePair("keyword", keyword));
		params.add(new BasicNameValuePair("page", page));
		return params;
	}
}
