package com.yinghe.wifitest.services;

import org.json.JSONObject;

import com.yinghe.wifitest.services.server.ClientServer;
import com.yinghe.wifitest.services.server.EquipmentServer;
import com.yinghe.wifitest.services.utils.DLT645_2007Utils;
import com.yinghe.wifitest.services.utils.DigitalUtils;

public class Main {
	public static void main(String[] args) {
		// JSONObject info;
		// try {
		// info = new JSONObject();
		// info.put("equipmentIp", "192.168.1.103");
		// info.put("command", "getEquipmentId");
		// System.out.println(info.toString());
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		// byte result[] = result = DLT645_2007Utils.getDltCode((byte) 0x11, new
		// byte[] { 0x00, 0x00, 0x00, 0x00 });
		// System.out.println(DigitalUtils.getHexStringByBytes(result));

		ClientServer.getInstanse();
		EquipmentServer.getInstanse();
	}
}
