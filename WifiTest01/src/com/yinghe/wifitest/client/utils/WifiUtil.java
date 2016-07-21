package com.yinghe.wifitest.client.utils;

import android.app.Activity;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public class WifiUtil {
	public static void init(Activity activity) {
		WifiManager wifiManager = (WifiManager) activity.getSystemService(activity.WIFI_SERVICE);
		if (!wifiManager.isWifiEnabled()) {
			wifiManager.setWifiEnabled(true);
		}
		WifiInfo wifiinfo = wifiManager.getConnectionInfo();
		System.out.println(wifiinfo.toString());
	}

}
