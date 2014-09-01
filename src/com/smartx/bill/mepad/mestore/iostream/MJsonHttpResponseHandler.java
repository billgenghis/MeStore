package com.smartx.bill.mepad.mestore.iostream;

import com.loopj.android.http.JsonHttpResponseHandler;
 
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
 
import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
 
import java.io.UnsupportedEncodingException;
 
/**
 * 复写 AndroidAsyncHttp 1.4.4 开源库的 JsonHttpResponseHandler 类
 * 当 1.4.5 released 后失效
 *
 * Created by ZHOUZ on 2014/3/22.
 */
public class MJsonHttpResponseHandler extends JsonHttpResponseHandler
{
    
}