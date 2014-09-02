package com.smartx.bill.mepad.mestore.util;

import java.util.ArrayList;
import java.util.List;

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
import com.smartx.bill.mepad.mestore.myview.MyRoundProgressBar;

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
		public Button appOpen;
		public MyRoundProgressBar appDownload;
		public View verticalLine;
		public ImageView appDownloadConnect;
	}

	public static void setLayout(int position, CommonViewHolder view) {
		if (position % 2 == 1) {
			view.mRelativeLayout.setPadding(40, 50, 60, 50);
		} else {
			view.mRelativeLayout.setPadding(60, 50, 40, 50);
		}
	}

	public static void setViewById(CommonViewHolder view, View convertView) {
		view.txtViewTitle = (TextView) convertView.findViewById(R.id.app_title);
		view.imgViewFlag = (ImageView) convertView.findViewById(R.id.app_icon);
		view.appScore = (RatingBar) convertView.findViewById(R.id.app_score);
		view.downloadCount = (TextView) convertView
				.findViewById(R.id.app_download_count);
		view.appInstall = (Button) convertView.findViewById(R.id.app_install);
		view.appReview = (ImageView) convertView.findViewById(R.id.app_review);
		view.mRelativeLayout = (RelativeLayout) convertView
				.findViewById(R.id.relative_item);
		view.appDescription = (TextView) convertView
				.findViewById(R.id.app_description);
		view.appOpen = (Button) convertView.findViewById(R.id.app_open);
		view.appDownload = (MyRoundProgressBar) convertView
				.findViewById(R.id.app_download);
		view.appDownloadConnect = (ImageView) convertView
				.findViewById(R.id.app_download_connect);
		view.verticalLine = (View) convertView.findViewById(R.id.vertical_line);
	}

	public static String getHtmlText(String content) {
		Document doc = Jsoup.parse(Html.fromHtml(content).toString());
		doc.body().getElementsByAttribute("src").remove();// delete text by
															// attribute src
		return Html.fromHtml(doc.body().toString()).toString();
	}

	public static ArrayList<String> getHtmlImage(String content) {
		ArrayList<String> imagesList = new ArrayList<String>();
		Document doc = Jsoup.parse(Html.fromHtml(content).toString());
		Elements imagesElements = doc.body().getElementsByAttribute("src");
		for (int i = 0; i < imagesElements.size(); i++) {
			imagesList.add(imagesElements.get(i).attr("src"));
		}
		return imagesList;
	}

	public static String getImageText(String content) {
		Document doc = Jsoup.parse(Html.fromHtml(content).toString());
		Elements elements = doc.body().getElementsByAttribute("src");
		return Html.fromHtml(elements.toString()).toString();
	}
}
