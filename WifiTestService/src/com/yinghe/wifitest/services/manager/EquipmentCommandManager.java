package com.yinghe.wifitest.services.manager;

import java.util.Collection;

import org.apache.mina.core.session.IoSession;
import org.json.JSONObject;

import com.yinghe.wifitest.services.CommandTag;
import com.yinghe.wifitest.services.server.ClientServer;
import com.yinghe.wifitest.services.utils.DLT645_2007Utils;

public class EquipmentCommandManager {

	public static void execute(IoSession session, Object message) {
		try {
			String clientIp = session.getAttribute("IP").toString();
			String commad = session.getAttribute("command").toString();

			IoSession clientSession = getClientSession(clientIp);
			if (clientSession != null) {
				String data = getData(commad, (byte[]) message);
				JSONObject result = new JSONObject();
				result.put("IP", session.getRemoteAddress().toString());
				result.put("command", commad);
				result.put("data", data);
				clientSession.write(result.toString());
				session.removeAttribute("IP");
			} else {
				session.write("equipmentIs not connected");
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
				break;
			case CommandTag.closeEquipmentId:
				break;
			case CommandTag.getCurrentVoltage:
				break;
			default:
				break;
		}
		return result;
	}
}
