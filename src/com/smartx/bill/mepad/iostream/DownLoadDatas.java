package com.smartx.bill.mepad.iostream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.smartx.bill.mepad.matadata.IOStreamDatas;

public class DownLoadDatas {
	/*
	 * get appsinfo from server
	 */
	public static JSONArray getDatasFromServer(String class_id, String age,
			String position_id, String keyword, int dataTypes)
			throws ClientProtocolException, IOException, JSONException {
		String dateURL = null;
		if (dataTypes == IOStreamDatas.APP_DATA) {
			dateURL = IOStreamDatas.SERVER_URL + IOStreamDatas.APPSINFO_URL;
		} else if (dataTypes == IOStreamDatas.CATEGORY_DATA) {
			dateURL = IOStreamDatas.SERVER_URL + IOStreamDatas.CATEGORY_URL;
		} else if (dataTypes == IOStreamDatas.SPECIAL_DATA) {
			dateURL = IOStreamDatas.SERVER_URL + IOStreamDatas.SPECIAL_URL;
		}
		UrlEncodedFormEntity entity;
		HttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setParameter(
				CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		StringBuilder builder = new StringBuilder();

		params.add(new BasicNameValuePair("class_id", class_id));
		params.add(new BasicNameValuePair("age", age));
		params.add(new BasicNameValuePair("position_id", position_id));
		params.add(new BasicNameValuePair("age", age));
		entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
		HttpPost postRequest = new HttpPost(dateURL);
		postRequest.setEntity(entity);
		HttpResponse response = httpclient.execute(postRequest);
		HttpEntity resEntity = response.getEntity();
		if (resEntity != null) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					resEntity.getContent()));
			for (String s = reader.readLine(); s != null; s = reader.readLine()) {
				builder.append(s);
			}
			JSONArray jsonArray = new JSONArray(builder.toString());
			httpclient.getConnectionManager().shutdown();
			return jsonArray;
		}
		return null;
	}

	public static Bitmap getImageFromServer(String image)
			throws ClientProtocolException, IOException, JSONException {
		String url = image;
		Bitmap bmp = null;
		try {
			URL myurl = new URL(url);
			// 获得连接
			HttpURLConnection conn = (HttpURLConnection) myurl.openConnection();
			conn.setConnectTimeout(6000);// 设置超时
			conn.setDoInput(true);
			conn.setUseCaches(false);// 不缓存
			conn.connect();
			InputStream is = conn.getInputStream();// 获得图片的数据流
			bmp = BitmapFactory.decodeStream(is);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bmp;
	}
}
