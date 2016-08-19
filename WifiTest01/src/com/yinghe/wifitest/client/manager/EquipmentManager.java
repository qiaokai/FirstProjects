package com.yinghe.wifitest.client.manager;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import android.os.Handler;

import com.yinghe.wifitest.client.entity.EquipmentInfo;
import com.yinghe.wifitest.client.entity.EquipmentListInfo;

public class EquipmentManager {

	/**
	 * 
	 * @param handler
	 */
	public static void searchEquipment(Handler handler) {
		CommandManager.getEquipmentIp(handler);
	}

	public static void buildTCPConnect(ArrayList<String> IpList) {
		for (String IP : IpList) {
			CommandManager.buildTCPConnect(IP);
		}
	}

	public static ArrayList<String> removeSameIp(JSONArray input) {
		ArrayList<String> result = new ArrayList<String>();
		ArrayList<String> equilmentIpList = new ArrayList<String>();

		for (EquipmentInfo equipment : EquipmentListInfo.Instance()) {
			if (!equilmentIpList.contains(equipment.getIP())) {
				equilmentIpList.add(equipment.getIP());
			}
		}

		try {
			for (int i = 0; i < input.length(); i++) {
				String ip = input.getJSONObject(i).getString("IP");
				if (!result.contains(ip) && (!equilmentIpList.contains(ip))) {
					result.add(ip);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void updateEquilMentList(ArrayList<String> IpList) {
		for (String IP : IpList) {
			EquipmentInfo temp = new EquipmentInfo();
			temp.setIP(IP);
			EquipmentListInfo.Instance().add(temp);
		}
	}
}
