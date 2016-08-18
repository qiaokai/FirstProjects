package com.yinghe.wifitest.services;

import org.json.JSONObject;

import com.yinghe.wifitest.services.server.ClientServer;
import com.yinghe.wifitest.services.server.EquipmentServer;

public class Main {
	public static void main(String[] args) {
//		JSONObject info;
//		try {
//			info = new JSONObject();
//			info.put("equipmentIp", "192.168.1.103");
//			info.put("command", "getEquipmentId");
//			System.out.println(info.toString());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		ClientServer.getInstanse();
		EquipmentServer.getInstanse();
	}
}
