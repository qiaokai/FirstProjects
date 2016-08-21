package com.yinghe.wifitest.services.manager;

import java.util.Collection;

import org.apache.mina.core.session.IoSession;
import org.json.JSONException;
import org.json.JSONObject;

import com.yinghe.wifitest.services.CommandTag;
import com.yinghe.wifitest.services.server.EquipmentServer;
import com.yinghe.wifitest.services.utils.DLT645_2007Utils;
import com.yinghe.wifitest.services.utils.DigitalUtils;

public class ClientCommandManager {

	public static void execute(IoSession session, String string) {
		try {
			JSONObject result = new JSONObject();

			JSONObject info = new JSONObject(string);
			String equipmentIp = info.getString("IP");
			String commad = info.getString("command");

			System.out.println("ClientCommandManager: start to excute. command is " + commad);
			IoSession equipmentSession = getEquipmentSession(equipmentIp);
			if (equipmentSession != null) {
				if (equipmentSession.isClosing()) {
					result.put("state", "closed");
					result.put("IP", equipmentIp);
					session.write(result.toString());
					System.out.println("ClientCommandManager: send result to client. resulr is " + result.toString());
				} else if (equipmentSession.getAttribute("IP") == null) {
					equipmentSession.setAttribute("command", commad);
					equipmentSession.setAttribute("IP", session.getRemoteAddress().toString());
					byte[] commandInfo = getConamdInfo(info);
					equipmentSession.write(commandInfo);
					System.out.println("ClientCommandManager: send command to Equipment. command is "
							+ DigitalUtils.getHexStringByBytes(commandInfo));
				} else {
					result.put("data", "设备正在被使用，请稍后" + equipmentSession.getAttribute("command"));
					result.put("state", "using");
					result.put("IP", equipmentIp);
					session.write(result.toString());
					new Thread().sleep(2000);
					equipmentSession.removeAttribute("IP");
					System.out.println("ClientCommandManager: send result to client. resulr is " + result.toString());
				}
			} else {
				result.put("state", "fail");
				result.put("data", "设备未连接");
				result.put("IP", equipmentIp);
				session.write(result.toString());
				System.out.println("ClientCommandManager: send result to client. resulr is " + result.toString());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static IoSession getEquipmentSession(String ip) {
		IoSession result = null;
		Collection<IoSession> sessionList = EquipmentServer.getClientSessions();
		for (IoSession ioSession : sessionList) {
			if (ioSession.getRemoteAddress().toString().indexOf(ip) > 0) {
				result = ioSession;
			}
		}
		return result;
	}

	private static byte[] getConamdInfo(JSONObject info) {
		byte[] result = null;
		try {
			String commad = info.getString("command");
			switch (commad) {
			case CommandTag.getEquipmentId:
				result = DLT645_2007Utils.getEquipmentIdCommand();
				break;
			case CommandTag.openEquipment:
				result = DLT645_2007Utils.openEquipmentCommand();
				break;
			case CommandTag.closeEquipment:
				result = DLT645_2007Utils.closeEquipmentCommand();
				break;
			case CommandTag.getCurrentVoltage:
				result = DLT645_2007Utils.getCurrentVoltageCommand();
				break;
			case CommandTag.getCurrentElectric:
				result = DLT645_2007Utils.getCurrentElectricCommand();
				break;
			case CommandTag.getCurrentQuantity:
				result = DLT645_2007Utils.getCurrentQuantityCommand();
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
