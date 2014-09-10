package com.smartx.bill.mepad.mestore.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.smartx.bill.mepad.mestore.matadata.MyBroadcast;

/**
 * @ClassName: PackageAddReceiver
 * @Description: 动态注册接收不到安装完成的广播，因此静态注册此广播，折中的方案
 * @author coney Geng
 * @date 2014年9月4日 下午4:36:21
 * 
 */
public class PackageAddReceiver extends BroadcastReceiver {

	public PackageAddReceiver() {

	}

	@Override
	public void onReceive(Context context, Intent arg1) {
		Log.i("sendBroadcast", "sendBroadcast");
		String broadcastFilter = MyBroadcast.MESTORE_INSTALL_FINASH;
		Intent intent = new Intent(broadcastFilter);
		String data = arg1.getDataString();
		intent.putExtra("packageName", data.substring(8));
		context.sendBroadcast(intent);
	}

}
