package com.yinghe.wifitest.client.entity;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class TempEquipmentIpList extends ArrayList<String> {

	private static TempEquipmentIpList Instance;

	private TempEquipmentIpList() {
	}

	public static TempEquipmentIpList Instance() {
		if (Instance == null) {
			Instance = new TempEquipmentIpList();
		}
		return Instance;
	}
}
