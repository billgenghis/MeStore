package com.smartx.bill.mepad.mestore.special;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.http.Header;
import org.json.JSONArray;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.smartx.bill.mepad.mestore.R;
import com.smartx.bill.mepad.mestore.adapter.RecomGridviewAdapter;
import com.smartx.bill.mepad.mestore.listener.BackClickListener;
import com.smartx.bill.mepad.mestore.listener.ItemClickListener;
import com.smartx.bill.mepad.mestore.matadata.IOStreamDatas;
import com.smartx.bill.mepad.mestore.matadata.LayoutResourcesDatas;
import com.smartx.bill.mepad.mestore.uimgloader.AbsListViewBaseActivity;
import com.smartx.bill.mepad.mestore.util.CommonTools;
import com.smartx.bill.mepad.mestore.util.HttpUtil;

public class SpecialDetail extends AbsListViewBaseActivity {

	// private MyGridView mRankingGridView;
	private RecomGridviewAdapter mRecomGridviewAdapter;
	private JSONArray jsonArraySpecial;
	private Bundle mBundle;
	private String sPicUrl;
	private String specialTitle;
	private String specialId;
	private Bitmap mBitmap;
	private TextView specialTitleView;
	private ImageView specialImageView;
	protected DisplayImageOptions options;
	private String specialDescription;
	private TextView specialDescriptionView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.special_detail_info);
		initDatas();
		initCommonDatas(this, this, savedInstanceState);
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.default_special_detail)
				.showImageForEmptyUri(R.drawable.default_special_detail)
				.showImageOnFail(R.drawable.default_special_detail)
				.resetViewBeforeLoading(false) // default
				.delayBeforeLoading(1000).cacheInMemory(true) // default
				.cacheOnDisk(true) // default
				.considerExifParams(false) // default
				.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default
				.bitmapConfig(Bitmap.Config.ARGB_8888) // default
				.displayer(new SimpleBitmapDisplayer()) // default
				.handler(new Handler()) // default
				.build();

		CommonTools.onSearchClick(this);
		initDatas();
		HttpUtil.get(getDataUrl(IOStreamDatas.APP_DATA),
				getParams(null, null, null, null, specialId, null, sPicUrl),
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers,JSONArray response) {
						initDatas(response);
						if (dialog != null) {
							ScheduledExecutorService executor = Executors
									.newSingleThreadScheduledExecutor();
							Runnable runner = new Runnable() {
								public void run() {
									dialog.dismiss();
								}
							};
							executor.schedule(runner,
									LayoutResourcesDatas.DELAY_TIME,
									TimeUnit.MILLISECONDS);
						}
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,Throwable e, JSONArray errorResponse) {
					}
				});
	}

	private void initDatas() {
		mBundle = getIntent().getBundleExtra("SpecialInfo");
		sPicUrl = mBundle.getString("image");
		specialId = mBundle.getString("specialId");
		specialTitle = mBundle.getString("specialTitle");
		specialDescription = mBundle.getString("specialDescription");
		myGridView = (GridView) findViewById(R.id.special_gridView);
		myGridView.setOnTouchListener(myGestureListener);
		specialTitleView = (TextView) findViewById(R.id.special_item_title);
		specialDescriptionView = (TextView) findViewById(R.id.special_item_description);
		specialImageView = (ImageView) findViewById(R.id.special_item_pic);
	}

	private void initDatas(JSONArray response) {
		jsonArraySpecial = response;
		mRecomGridviewAdapter = new RecomGridviewAdapter(this,
				jsonArraySpecial, imageLoader);
		specialTitleView.setText(specialTitle);
		specialDescriptionView.setText(specialDescription);
		imageLoader.displayImage(IOStreamDatas.SERVER_IP + sPicUrl,
				specialImageView, options);
		((GridView) myGridView).setNumColumns(2);
		myGridView.setAdapter(mRecomGridviewAdapter);
		myGridView.setOnItemClickListener(new ItemClickListener(mContext,
				mActivity, savedInstanceState, response));

		TextView backText = (TextView) findViewById(R.id.common_backhome);
		ImageView backArray = (ImageView) findViewById(R.id.common_back_array);
		backText.setOnClickListener(new BackClickListener(this));
		backArray.setOnClickListener(new BackClickListener(this));
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	/**
	 * 屏幕切换时进行处理 如果屏幕是竖屏，则显示3列，如果是横屏，则显示4列
	 */
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		int imageCol = 3;
		try {

			super.onConfigurationChanged(newConfig);
			// 如果屏幕是竖屏，则显示3列，如果是横屏，则显示4列
			if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
				imageCol = 4;
				Toast.makeText(SpecialDetail.this, "现在是横屏", Toast.LENGTH_SHORT)
						.show();
			} else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
				imageCol = 3;
			}
			((GridView) myGridView).setNumColumns(imageCol);
			myGridView.setAdapter(mRecomGridviewAdapter);
			// ia.notifyDataSetChanged();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
