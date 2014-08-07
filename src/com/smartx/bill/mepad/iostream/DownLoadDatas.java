package com.smartx.bill.mepad.iostream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

import com.smartx.bill.mepad.matadata.IOStreamDatas;

public class DownLoadDatas {
	/*
	 * 
	 */
	public static JSONArray getDatasFromServer(String class_id, String age, String position_id,String keyword)
			throws ClientProtocolException, IOException, JSONException {
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
		HttpPost postRequest = new HttpPost(IOStreamDatas.SERVER_URL);
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
//			JSONObject jsonObject = jsonArray.getJSONObject(0);
//			Log.i("JsonArray",	jsonArray.length() + "");
//			Log.i("jsonObject",	jsonObject.get("title").toString());
			httpclient.getConnectionManager().shutdown();
			return jsonArray;
		}
		return null;
	}
}
