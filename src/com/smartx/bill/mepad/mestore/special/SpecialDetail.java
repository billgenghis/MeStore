package com.smartx.bill.mepad.mestore.special;

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

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.smartx.bill.mepad.mestore.R;
import com.smartx.bill.mepad.mestore.adapter.RecomGridviewAdapter;
import com.smartx.bill.mepad.mestore.listener.BackClickListener;
import com.smartx.bill.mepad.mestore.myview.MyGridView;
import com.smartx.bill.mepad.mestore.uimgloader.AbsListViewBaseActivity;
import com.smartx.bill.mepad.mestore.util.CommonTools;

public class SpecialDetail extends AbsListViewBaseActivity {

	// private MyGridView mRankingGridView;
	private RecomGridviewAdapter mRecomGridviewAdapter;
	private JSONArray jsonArraySpecial;
	private Bundle mBundle;
	private String sPicUrl;
	private String specialTitle;
	private String specialId;
	private TextView specialTitleView;
	private ImageView specialImageView;
	protected DisplayImageOptions options;
	private String specialDescription;
	private TextView specialDescriptionView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.special_detail_info);
		mBundle = getIntent().getBundleExtra("SpecialInfo");
		sPicUrl = mBundle.getString("sPicUrl");
		specialId = mBundle.getString("specialId");
		specialTitle = mBundle.getString("specialTitle");
		specialDescription = mBundle.getString("specialDescription");
		initDatas();
		initGridView();

		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.empty_icon) // resource or
															// drawable
				.showImageForEmptyUri(R.drawable.empty_icon) // resource or
																// drawable
				.showImageOnFail(R.drawable.empty_icon) // resource or drawable
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
	}

	private void initDatas() {
		// try {
		// jsonArraySpecial = DownLoadDatas.getDatasFromServer(specialId, null,
		// null,
		// null, IOStreamDatas.APP_DATA);
		// } catch (IOException | JSONException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		jsonArraySpecial = new JSONArray();
		mRecomGridviewAdapter = new RecomGridviewAdapter(this,
				jsonArraySpecial, imageLoader);
		myGridView = (MyGridView) findViewById(R.id.special_gridView);
		specialTitleView = (TextView) findViewById(R.id.special_item_title);
		specialTitleView.setText(specialTitle);
		specialDescriptionView = (TextView) findViewById(R.id.special_item_description);
		specialDescriptionView.setText(specialDescription);
		specialImageView = (ImageView) findViewById(R.id.special_item_pic);
		TextView backText = (TextView) findViewById(R.id.common_backhome);
		ImageView backArray = (ImageView) findViewById(R.id.common_back_array);
		backText.setOnClickListener(new BackClickListener(this));
		backArray.setOnClickListener(new BackClickListener(this));
		// imageLoader.displayImage(sPicUrl, specialImageView, options);
	}

	private void initGridView() {
		((GridView) myGridView).setNumColumns(2);
		myGridView.setAdapter(mRecomGridviewAdapter);
		myGridView.setOnScrollListener(new PauseOnScrollListener(imageLoader,
				true, true));
		myGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
			}
		});
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
