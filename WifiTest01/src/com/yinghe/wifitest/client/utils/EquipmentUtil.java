package com.yinghe.wifitest.client.utils;

import android.os.Handler;

import com.yinghe.wifitest.client.manager.EquipmentManager;

public class EquipmentUtil {

	public static void closeEquipment(String iP, Handler handler) {
		EquipmentManager.closeEquipment(iP, handler);

	}

	public static void openEquipment(String iP, Handler handler) {
		EquipmentManager.openEquipment(iP, handler);

	}

	public static boolean isClosed(String iP, Handler handler) {
		closeEquipment(iP, handler);
		return true;
	}

	public static boolean isOpened(String iP, Handler handler) {
		openEquipment(iP, handler);
		return true;
	}

}
