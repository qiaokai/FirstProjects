package com.yinghe.wifitest.client.entity;

import java.util.ArrayList;

import org.json.JSONArray;

@SuppressWarnings("serial")
public class EquipmentList extends ArrayList<EquipmentInfo> {

	private static EquipmentList Instance;

	private EquipmentList() {
	}

	public static synchronized EquipmentList Instance() {
		if (Instance == null) {
			Instance = new EquipmentList();
		}
		return Instance;
	}

	public ArrayList<String> IpList() {
		ArrayList<String> IpList = new ArrayList<String>();
		for (EquipmentInfo info : Instance) {
			if (!IpList.contains(info.getIP())) {
				IpList.add(info.getIP());
			}
		}
		return IpList;
	}

	@Override
	public String toString() {
		JSONArray result = new JSONArray();
		for (EquipmentInfo equipmentInfo : Instance) {
			result.put(equipmentInfo.toString());
		}
		return result.toString();
	}

}
