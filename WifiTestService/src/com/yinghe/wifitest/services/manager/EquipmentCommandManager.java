package com.yinghe.wifitest.services.manager;

import java.util.Collection;

import org.apache.mina.core.session.IoSession;
import org.json.JSONObject;

import com.yinghe.wifitest.services.CommandTag;
import com.yinghe.wifitest.services.server.ClientServer;
import com.yinghe.wifitest.services.utils.DLT645_2007Utils;
import com.yinghe.wifitest.services.utils.DigitalUtils;

public class EquipmentCommandManager {

	public static void execute(IoSession session, Object message) {
		try {
			System.out.println("EquipmentCommandManager: start to excute. ");
			JSONObject result = new JSONObject();
			if (session.getAttribute("IP") == null) {
				return;
			}
			String clientIp = session.getAttribute("IP").toString();
			String commad = session.getAttribute("command").toString();

			System.out.println("EquipmentCommandManager: command is " + commad);
			IoSession clientSession = getClientSession(clientIp);
			if (clientSession != null) {
				String data = getData(commad, (byte[]) message);
				String IP = session.getRemoteAddress().toString().replaceAll("/", "").split(":")[0];
				result.put("state", "success");
				result.put("IP", IP);
				result.put("command", commad);
				result.put("data", data);
				clientSession.write(result.toString());
				session.removeAttribute("IP");
				System.out.println("EquipmentCommandManager: send result to client. result is  " + result.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static IoSession getClientSession(String clientIp) {
		IoSession result = null;
		Collection<IoSession> sessionList = ClientServer.getClientSessions();
		for (IoSession ioSession : sessionList) {
			if (ioSession.getRemoteAddress().toString().equals(clientIp)) {
				result = ioSession;
				break;
			}
		}
		return result;
	}

	private static String getData(String commad, byte[] message) {
		String result = "";
		switch (commad) {
		case CommandTag.getEquipmentId:
			result = DLT645_2007Utils.parseEquipmentId(message);
			break;
		case CommandTag.openEquipment:
			// result = DLT645_2007Utils.parseEquipmentVoltage(message);
			break;
		case CommandTag.closeEquipment:
			break;
		case CommandTag.getCurrentVoltage:
			result = DLT645_2007Utils.parseEquipmentVoltage(message);
			break;
		case CommandTag.getCurrentElectric:
			result = DLT645_2007Utils.parseEquipmentElectric(message);
			break;
		case CommandTag.getCurrentQuantity:
			result = DLT645_2007Utils.parseEquipmentQuantity(message);
			break;
		default:
			break;
		}
		return result;
	}
}
