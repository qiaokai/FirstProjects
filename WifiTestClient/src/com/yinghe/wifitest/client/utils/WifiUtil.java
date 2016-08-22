package com.yinghe.wifitest.client.utils;

import android.app.Activity;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public class WifiUtil {
	public static String init(Activity activity) {
		WifiManager wifiManager = (WifiManager) activity.getSystemService(activity.WIFI_SERVICE);
		if (!wifiManager.isWifiEnabled()) {
			wifiManager.setWifiEnabled(true);
		}
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		System.out.println(wifiInfo.toString());

		String maxText = wifiInfo.getMacAddress();
		int ipText = wifiInfo.getIpAddress();
		String status = "";
		String ssid = wifiInfo.getSSID();
		int networkID = wifiInfo.getNetworkId();
		int speed = wifiInfo.getLinkSpeed();
		System.out.println(maxText);
		System.out.println(ipText);
		System.out.println(ssid);
		System.out.println(networkID);
		System.out.println(speed);
		return wifiInfo.toString();
	}

	private static String intToIp(int ip) {
		return (ip & 0xFF) + "." + ((ip >> 8) & 0xFF) + "." + ((ip >> 16) & 0xFF) + "." + ((ip >> 24) & 0xFF);
	}
}
