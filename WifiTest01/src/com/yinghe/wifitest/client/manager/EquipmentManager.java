package com.yinghe.wifitest.client.manager;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;

import com.yinghe.wifitest.client.entity.CommandTag;
import com.yinghe.wifitest.client.entity.EquipmentInfo;
import com.yinghe.wifitest.client.entity.EquipmentList;
import com.yinghe.wifitest.client.entity.MsgTag;
import com.yinghe.wifitest.client.entity.TempEquipmentIpList;

public class EquipmentManager {

	/**
	 * 
	 * @param handler
	 */
	public static void searchEquipment(Handler handler) {
		CommandManager.getEquipmentIp(handler);
	}

	@SuppressWarnings("static-access")
	public static void buildTCPConnect(final String IP, final Handler handler) {
		CommandManager.buildTCPConnect(IP);
		if (handler != null) {
			try {
				new Thread().sleep(3000);
				Message message = new Message();
				message.what = MsgTag.buildTCPConnect;
				message.arg1 = MsgTag.success;
				message.obj = IP;
				handler.sendMessage(message);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void checkTCPConnect(String IP, final Handler handler) {
		CommandManager.excute(IP, CommandTag.getEquipmentId, new Handler() {
			@Override
			public void handleMessage(Message msg) {
				responseCheckResult(MsgTag.checkTCPConnect, msg, handler);
			}
		});
	}

	public static void testTcpConnect(String IP, final Handler handler) {
		CommandManager.excute(IP, CommandTag.getEquipmentId, new Handler() {
			@Override
			public void handleMessage(Message msg) {
				responseCheckResult(MsgTag.testTCPConnect, msg, handler);
			}
		});
	}

	public static void updateEquilMentList(ArrayList<String> IpList) {
		for (String IP : IpList) {
			EquipmentInfo temp = new EquipmentInfo();
			temp.setIP(IP);
			EquipmentList.Instance().add(temp);
		}
	}

	public static String updateTempEquipmentIplist(Message msg) {
		String result = "";
		try {
			JSONObject imput = new JSONObject(msg.obj.toString());
			if (!imput.isNull("IP")) {
				String IP = imput.getString("IP");
				if (!EquipmentList.Instance().IpList().contains(IP) && !TempEquipmentIpList.Instance().contains(IP)) {
					TempEquipmentIpList.Instance().add(IP);
					result = IP;
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void addEquipment(String IP) {
		if (!EquipmentList.Instance().IpList().contains(IP)) {
			EquipmentInfo equip = new EquipmentInfo();
			equip.setIP(IP);
			EquipmentList.Instance().add(equip);
		}
	}

	protected static void responseCheckResult(int msgTag, Message msg, Handler handler) {
		if (handler != null) {
			Message message = new Message();
			message.what = msgTag;
			String state = "";
			try {
				JSONObject response = new JSONObject(msg.obj.toString());
				if (!response.isNull("IP"))
					message.obj = response.getString("IP");
				if (!response.isNull("state"))
					state = response.getString("state");

			} catch (JSONException e) {
			}
			if (msg.arg1 == MsgTag.success && "success".equalsIgnoreCase(state)) {
				message.arg1 = MsgTag.success;
			} else {
				message.arg1 = MsgTag.fail;
			}
			handler.sendMessage(message);
		}
	}

	public static void closeEquipment(String iP, final Handler handler) {
		CommandManager.excute(iP, CommandTag.closeEquipment, new Handler() {
			@Override
			public void handleMessage(Message msg) {
				Message message = new Message();
				message.what = MsgTag.closeEquipment;
				message.arg1 = msg.arg1;
				message.obj = msg.obj;
				handler.sendMessage(message);
			}
		});

	}

	public static void openEquipment(String iP, final Handler handler) {
		CommandManager.excute(iP, CommandTag.openEquipment, new Handler() {
			@Override
			public void handleMessage(Message msg) {
				Message message = new Message();
				message.what = MsgTag.openEquipment;
				message.arg1 = msg.arg1;
				message.obj = msg.obj;
				handler.sendMessage(message);
			}
		});
	}

	public static void upDateEquipmentList(Message msg) {
		EquipmentInfo info = new EquipmentInfo();
		String IP = "";
		try {
			JSONObject temp = new JSONObject(msg.obj.toString());
			if (temp.isNull("IP"))
				return;
			IP = temp.getString("IP");
			if (!temp.isNull("State")) {
				info.setState(temp.getString("State"));
			}
			if (!temp.isNull("State")) {
				info.setState(temp.getString("State"));
			}
			if (!temp.isNull("Id")) {
				info.setId(temp.getString("Id"));
			}
			if (!temp.isNull("Name")) {
				info.setName(temp.getString("Name"));
			}
			if (!temp.isNull("Voltage")) {
				info.setVoltage(temp.getString("Voltage"));
			}
			if (!temp.isNull("Electricity")) {
				info.setElectricity(temp.getString("Electricity"));
			}
			for (EquipmentInfo tempInfo : EquipmentList.Instance()) {
				if (tempInfo.getIP().equals(IP)) {
					EquipmentList.Instance().remove(tempInfo);
				}
			}
			EquipmentList.Instance().add(info);
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	public static void getEquipmentId(String ip, final Handler handler) {
		CommandManager.excute(ip, CommandTag.getEquipmentId, new Handler() {
			@Override
			public void handleMessage(Message msg) {
				Message message = new Message();
				message.what = MsgTag.updateEquipmentInfo;
				message.arg1 = msg.arg1;
				message.obj = msg.obj;
				handler.sendMessage(message);
			}
		});

	}
}
