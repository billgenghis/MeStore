package com.smartx.bill.mepad.mestore.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.smartx.bill.mepad.mestore.R;
import com.smartx.bill.mepad.mestore.listener.SearchOnEditorActionListener;

public class CommonTools {

	/*
	 * 
	 */
	public static void onSearchClick(Activity mAcitivity) {
		EditText searchText;
		searchText = (EditText) mAcitivity.findViewById(R.id.home_search);
		searchText.setOnEditorActionListener(new SearchOnEditorActionListener(
				mAcitivity));
	}

	/*
	 * 
	 */
	public static void CommonFunctions(Activity mAcitivity) {
		EditText searchText;
		searchText = (EditText) mAcitivity.findViewById(R.id.home_search);
		searchText.setOnEditorActionListener(new SearchOnEditorActionListener(
				mAcitivity));
	}

	/*
	 * 
	 */
	public static class CommonViewHolder {
		public ImageView imgViewFlag;
		public TextView txtViewTitle;
		public TextView downloadCount;
		public RatingBar appScore;
		public Button appInstall;
		public RelativeLayout mRelativeLayout;
		public ImageView appReview;
		public TextView appDescription;
	}

	public static void setLayout(int position, CommonViewHolder view) {
		if (position % 2 == 1) {
			view.mRelativeLayout.setPadding(40, 50, 60, 50);
		} else {
			view.mRelativeLayout.setPadding(60, 50, 40, 50);
		}
	}

	// public static float getTextWidth(TextView view) {
	// Paint textPaint = new Paint();
	// textPaint.setTextSize(view.getTextSize());
	// float titleWidth = textPaint.measureText(view.getText().toString());
	// return titleWidth/3;
	// }

	public static void setViewById(CommonViewHolder view, View convertView) {
		view.txtViewTitle = (TextView) convertView.findViewById(R.id.app_title);
		view.imgViewFlag = (ImageView) convertView.findViewById(R.id.app_icon);
		view.imgViewFlag.setDrawingCacheEnabled(true);
		view.appScore = (RatingBar) convertView.findViewById(R.id.app_score);
		view.downloadCount = (TextView) convertView
				.findViewById(R.id.app_download_count);
		view.appInstall = (Button) convertView.findViewById(R.id.app_install);
		view.appReview = (ImageView) convertView.findViewById(R.id.app_review);
		view.mRelativeLayout = (RelativeLayout) convertView
				.findViewById(R.id.relative_item);
		view.appDescription = (TextView) convertView
				.findViewById(R.id.app_description);
	}

	public static String getHtmlText(String content) {
		Document doc = Jsoup.parse(Html.fromHtml(content).toString());
		doc.body().getElementsByAttribute("src").remove();
		return Html.fromHtml(doc.body().toString()).toString();
	}

	public static String getImageText(String content) {
		Document doc = Jsoup.parse(Html.fromHtml(content).toString());
		Elements elements = doc.body().getElementsByAttribute("src");
		return Html.fromHtml(elements.toString()).toString();
	}
}
