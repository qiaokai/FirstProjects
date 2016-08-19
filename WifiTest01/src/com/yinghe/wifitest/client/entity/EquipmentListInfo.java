package com.yinghe.wifitest.client.entity;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class EquipmentListInfo extends ArrayList<EquipmentInfo> {

	private static EquipmentListInfo Instance;

	private EquipmentListInfo() {
	}

	public static synchronized EquipmentListInfo Instance() {
		if (Instance == null) {
			Instance = new EquipmentListInfo();
		}
		return Instance;
	}
}
