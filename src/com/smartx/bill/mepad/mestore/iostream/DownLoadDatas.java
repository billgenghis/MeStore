package com.smartx.bill.mepad.mestore.iostream;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.smartx.bill.mepad.mestore.matadata.IOStreamDatas;
import com.smartx.bill.mepad.mestore.util.HttpUtil;

public class DownLoadDatas {

	JSONArray jsonArray;
	Boolean flag;
//	public RequestParams getDatasFromServer(String class_id, String age,
//			String position_id, String keyword, int dataTypes)
//			throws ClientProtocolException, IOException, JSONException {
//		String dataURL = null;
//		if (dataTypes == IOStreamDatas.APP_DATA) {
//			dataURL = IOStreamDatas.SERVER_URL + IOStreamDatas.APPSINFO_URL;
//		} else if (dataTypes == IOStreamDatas.CATEGORY_DATA) {
//			dataURL = IOStreamDatas.SERVER_URL + IOStreamDatas.CATEGORY_URL;
//		} else if (dataTypes == IOStreamDatas.SPECIAL_DATA) {
//			dataURL = IOStreamDatas.SERVER_URL + IOStreamDatas.SPECIAL_URL;
//		}
//		RequestParams params = new RequestParams();
//		params.put("class_id", class_id);
//		params.put("age", age);
//		params.put("position_id", position_id);
//		params.put("keyword", keyword);
//		HttpUtil.get(dataURL, params, new JsonHttpResponseHandler() {
//
//			@Override
//			public void onSuccess(JSONArray timeline) {
//				// Pull out the first event on the public timeline
//				jsonArray = timeline;
//				flag = true;
//				Log.i("jsonArrayin", jsonArray + "");
//				// Do something with the response
//			}
//
//			@Override
//			public void onFailure(Throwable e, JSONArray errorResponse) {
//			}
//		});
//			return jsonArray;
//	}

	/**
	 * 利用Volley获取JSON数据
	 */
	// public JSONArray getDatasFromServer(String class_id, String age,
	// String position_id, String keyword, int dataTypes, Activity
	// mActivity)throws ClientProtocolException, IOException, JSONException {
	// String JSONDataUrl = IOStreamDatas.SERVER_URL
	// + IOStreamDatas.APPSINFO_URL;
	// RequestQueue mRequestQueue = Volley.newRequestQueue(mActivity);
	// JsonArrayRequest JsonArrayRequest = new JsonArrayRequest(JSONDataUrl,
	// new Response.Listener<JSONArray>() {
	// @Override
	// public void onResponse(JSONArray response) {
	// jsonArray = response;
	// Log.i("jsonArray", jsonArray + "");
	// System.out.println("response=" + response);
	// }
	// }, new Response.ErrorListener() {
	// @Override
	// public void onErrorResponse(VolleyError arg0) {
	// System.out.println("sorry,Error");
	// }
	// });
	// return jsonArray;
	// }
	/*
	 * get appsinfo from server
	 */
	// public static JSONArray getDatasFromServer(String class_id, String age,
	// String position_id, String keyword, int dataTypes)
	// throws ClientProtocolException, IOException, JSONException {
	// String dateURL = null;
	// if (dataTypes == IOStreamDatas.APP_DATA) {
	// dateURL = IOStreamDatas.SERVER_URL + IOStreamDatas.APPSINFO_URL;
	// } else if (dataTypes == IOStreamDatas.CATEGORY_DATA) {
	// dateURL = IOStreamDatas.SERVER_URL + IOStreamDatas.CATEGORY_URL;
	// } else if (dataTypes == IOStreamDatas.SPECIAL_DATA) {
	// dateURL = IOStreamDatas.SERVER_URL + IOStreamDatas.SPECIAL_URL;
	// }
	// UrlEncodedFormEntity entity;
	// HttpClient httpclient = new DefaultHttpClient();
	// httpclient.getParams().setParameter(
	// CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
	// List<NameValuePair> params = new ArrayList<NameValuePair>();
	// StringBuilder builder = new StringBuilder();
	//
	// params.add(new BasicNameValuePair("class_id", class_id));
	// params.add(new BasicNameValuePair("age", age));
	// params.add(new BasicNameValuePair("position_id", position_id));
	// params.add(new BasicNameValuePair("keyword", keyword));
	// entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
	// HttpPost postRequest = new HttpPost(dateURL);
	// postRequest.setEntity(entity);
	// HttpResponse response = httpclient.execute(postRequest);
	// HttpEntity resEntity = response.getEntity();
	// if (resEntity != null) {
	// BufferedReader reader = new BufferedReader(new InputStreamReader(
	// resEntity.getContent()));
	// for (String s = reader.readLine(); s != null; s = reader.readLine()) {
	// builder.append(s);
	// }
	// JSONArray jsonArray = new JSONArray(builder.toString());
	// httpclient.getConnectionManager().shutdown();
	// return jsonArray;
	// }
	// return null;
	// }

	// public static Bitmap getImageFromServer(String image)
	// throws ClientProtocolException, IOException, JSONException {
	// String url = image;
	// Bitmap bmp = null;
	// try {
	// URL myurl = new URL(url);
	// // 获得连接
	// HttpURLConnection conn = (HttpURLConnection) myurl.openConnection();
	// conn.setConnectTimeout(6000);// 设置超时
	// conn.setDoInput(true);
	// conn.setUseCaches(false);// 不缓存
	// conn.connect();
	// InputStream is = conn.getInputStream();// 获得图片的数据流
	// bmp = BitmapFactory.decodeStream(is);
	// is.close();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return bmp;
	// }
}
