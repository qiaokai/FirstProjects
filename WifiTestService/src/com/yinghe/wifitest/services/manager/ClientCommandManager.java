package com.yinghe.wifitest.services.manager;

import java.util.Collection;

import org.apache.mina.core.session.IoSession;
import org.json.JSONException;
import org.json.JSONObject;

import com.yinghe.wifitest.services.CommandTag;
import com.yinghe.wifitest.services.server.EquipmentServer;
import com.yinghe.wifitest.services.utils.DigitalUtils;

public class ClientCommandManager {

	public static void execute(IoSession session, String string) {
		try {
			JSONObject result = new JSONObject();

			JSONObject info = new JSONObject(string);
			String equipmentIp = info.getString("IP");
			String commad = info.getString("command");

			IoSession equipmentSession = getEquipmentSession(equipmentIp);
			if (equipmentSession != null) {
				if (equipmentSession.getAttribute("IP") == null) {
					equipmentSession.setAttribute("command", commad);
					equipmentSession.setAttribute("IP", session.getRemoteAddress().toString());
					byte[] commandInfo = getConamdInfo(info);
					equipmentSession.write(commandInfo);

					//					if (commad.equals(CommandTag.closeEquipment) || commad.equals(CommandTag.openEquipment)) {
					//						equipmentSession.removeAttribute("IP");
					//						result.put("state", "success");
					//						result.put("IP", equipmentIp);
					//						session.write(result.toString());
					//					}
				} else {
					result.put("data", "设备正在被使用，请稍后");
					result.put("state", "success");
					result.put("IP", equipmentIp);
					session.write(result.toString());
				}
			} else {
				result.put("data", "设备未连接");
				result.put("IP", equipmentIp);
				session.write(result.toString());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static IoSession getEquipmentSession(String ip) {
		IoSession result = null;
		int count = 0;
		Collection<IoSession> sessionList = EquipmentServer.getClientSessions();
		for (IoSession ioSession : sessionList) {
			if (ioSession.getRemoteAddress().toString().indexOf(ip) > 0) {
				count++;
				result = ioSession;
			}
		}
		if (count == 1) {
			return result;
		}
		return result;
	}

	private static byte[] getConamdInfo(JSONObject info) {
		byte[] result = null;
		try {
			String commad = info.getString("command");
			switch (commad) {
				case CommandTag.getEquipmentId:
					result = new byte[] { (byte) 0xFE, (byte) 0xFE, (byte) 0xFE, (byte) 0xFE, 0x68, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA,
						0x68, 0x13, 0x00, (byte) 0xDF, 0x16 };
					break;
				case CommandTag.openEquipment:
					result = new byte[] { (byte) 0xFE, (byte) 0xFE, (byte) 0xFE, (byte) 0xFE, 0x68, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA,
						(byte) 0xAA, (byte) 0xAA, 0x68, 0x1C, 0x10, 0x37, 0x44, 0x44, 0x44, 0x34, (byte) 0x89, 0x67, 0x45, 0x4D, 0x33, 0x47, 0x77, 0x3B, 0x3A, 0x44, 0x3C, (byte) 0x98, 0x16 };
					break;
				case CommandTag.closeEquipment:
					result = new byte[] { (byte) 0xFE, (byte) 0xFE, (byte) 0xFE, (byte) 0xFE, 0x68, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA,
						(byte) 0xAA, (byte) 0xAA, 0x68, 0x1C, 0x10, 0x37, 0x44, 0x44, 0x44, 0x34, (byte) 0x89, 0x67, 0x45, 0x4D, 0x33, 0x47, 0x77, 0x3B, 0x3A, 0x44, 0x3C, (byte) 0x97, 0x16 };
					break;
				case CommandTag.getCurrentVoltage:
					result = new byte[] { (byte) 0xFE, (byte) 0xFE, (byte) 0xFE, (byte) 0xFE, 0x68, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA,
						0x68, 0x13, 0x00, (byte) 0xDF, 0x16 };
					break;
				case CommandTag.getCurrentElectric:
					result = new byte[] { (byte) 0xFE, (byte) 0xFE, (byte) 0xFE, (byte) 0xFE, 0x68, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA, 0x68,
						0x11, 0x04, 0x33, 0x34, 0x35, 0x35, (byte) 0xB2, 0x16 };
					break;
				default:
					break;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}

}
