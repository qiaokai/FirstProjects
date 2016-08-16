package com.yinghe.wifitest.services;

import com.yinghe.wifitest.services.server.MainServer;

public class Main {
	public static void main(String[] args) {
		// JSONObject info;
		// try {
		// info = new JSONObject();
		// info.put("IP", "192.168.1.103");
		// info.put("order", "getEquipmentId");
		// System.out.println(info.toString());
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		MainServer.getInstanse();
	}
}
