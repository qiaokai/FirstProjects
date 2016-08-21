package com.yinghe.wifitest.client.manager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yinghe.wifitest.client.callback.YHLog;
import com.yinghe.wifitest.client.entity.CommandTag;
import com.yinghe.wifitest.client.entity.EquipmentInfo;
import com.yinghe.wifitest.client.entity.EquipmentList;
import com.yinghe.wifitest.client.entity.MsgTag;
import com.yinghe.wifitest.client.entity.TempEquipmentIpList;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.os.Message;

public class EquipmentManager {

	/**
	 * 
	 * @param handler
	 */
	public static void searchEquipment(final Handler handler) {
		YHLog.i("start to searchEquipment");
		TempEquipmentIpList.Instance().clear();
		CommandManager.getEquipmentIp(new Handler() {
			@Override
			public void handleMessage(Message msg) {
				Message message = new Message();
				message.what = MsgTag.searchEquipment;
				if (msg.what == MsgTag.success) {
					String IP = msg.obj.toString();
					if (!equipmentIpHasUsed(IP)) {
						message.arg1 = MsgTag.success;
						message.obj = IP;
						handler.sendMessage(message);
					}
				} else {
					message.arg1 = MsgTag.fail;
					handler.sendMessage(message);
				}
			}
		});
	}

	@SuppressWarnings("static-access")
	public static void buildTCPConnect(final String IP, final Handler handler) {
		YHLog.i("start to buildTCPConnect. IP is " + IP);
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

	public static void checkTCPConnect(final String IP, final Handler handler) {
		YHLog.i("start to checkTCPConnect. IP is " + IP);
		getEquipmentInfoByMina(IP, MsgTag.getEquipmentState, new Handler() {
			@Override
			public void handleMessage(Message msg) {
				Message message = new Message();
				message.what = MsgTag.checkTCPConnect;
				message.arg1 = MsgTag.fail;
				if (msg.arg1 == MsgTag.success || msg.arg1 == MsgTag.using) {
					message.arg1 = MsgTag.success;
				}
				if (handler != null) {
					message.obj = IP;
					handler.sendMessage(message);
				}
			}
		});

	}

	public static void testTcpConnect(final String IP, final Handler handler) {
		YHLog.i("start to testTcpConnect");
		CommandManager.excute(IP, CommandTag.getCurrentElectric, new Handler() {
			@Override
			public void handleMessage(Message msg) {
				Message message = new Message();
				message.what = MsgTag.testTCPConnect;
				message.arg1 = MsgTag.fail;
				if (msg.arg1 == MsgTag.success || msg.arg1 == MsgTag.using) {
					message.arg1 = MsgTag.success;
				}
				if (handler != null) {
					message.obj = IP;
					handler.sendMessage(message);
				}

			}
		});
	}

	public static boolean equipmentIpHasUsed(String IP) {
		if (EquipmentList.Instance().IpList().contains(IP)) {
			return true;
		}
		if (TempEquipmentIpList.Instance().contains(IP)) {
			return true;
		}
		TempEquipmentIpList.Instance().add(IP);
		return false;
	}

	public static void saveEquipmentList(Activity activity) {
		SharedPreferences preferences = activity.getSharedPreferences("IFENG_SP", Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString("EquipmentList", EquipmentList.Instance().toString());
		editor.commit();

	}

	public static void parseEquipmentList(Activity activity) {
		YHLog.i("start to parseEquipmentList. ");
		SharedPreferences preferences = activity.getSharedPreferences("IFENG_SP", Context.MODE_PRIVATE);
		String equipmentListStr = preferences.getString("EquipmentList", "");
		try {
			JSONArray temp = new JSONArray(equipmentListStr);
			for (int i = 0; i < temp.length(); i++) {
				EquipmentInfo info = new EquipmentInfo(temp.get(i).toString());
				if (!EquipmentList.Instance().IpList().contains(info.getIP())) {
					EquipmentList.Instance().add(info);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	public static void getCurrentQuantity(String ip, final Handler handler) {
		CommandManager.excute(ip, CommandTag.getCurrentQuantity, new Handler() {
			@Override
			public void handleMessage(Message msg) {
				minaMsgParse(msg, MsgTag.getCurrentQuantity, handler);
			}
		});
	}

	public static void getEquipmentInfoByMina(String ip, final int msgTag, final Handler handler) {
		String commandTag = "";
		switch (msgTag) {
		case MsgTag.openEquipment:
			commandTag = CommandTag.openEquipment;
			break;
		case MsgTag.closeEquipment:
			commandTag = CommandTag.closeEquipment;
			break;
		case MsgTag.getEquipmentState:
			commandTag = CommandTag.getCurrentElectric;
			break;
		case MsgTag.getEquipmentId:
			commandTag = CommandTag.getEquipmentId;
			break;
		case MsgTag.getEquipmentVoltage:
			commandTag = CommandTag.getCurrentVoltage;
			break;
		case MsgTag.getEquipmentElectric:
			commandTag = CommandTag.getCurrentElectric;
			break;
		case MsgTag.getCurrentQuantity:
			commandTag = CommandTag.getCurrentQuantity;
			break;
		default:
			break;
		}

		CommandManager.excute(ip, commandTag, new Handler() {
			@Override
			public void handleMessage(Message msg) {
				minaMsgParse(msg, msgTag, handler);
			}
		});
	}

	private static void minaMsgParse(Message msg, int msgTag, Handler handler) {
		Message message = new Message();
		message.what = msgTag;
		message.arg1 = MsgTag.fail;
		try {
			JSONObject temp = new JSONObject(msg.obj.toString());
			if (!temp.isNull("IP") && !temp.isNull("state")) {
				String IP = temp.getString("IP");
				String state = temp.getString("state");
				if ("success".equals(state) && !temp.isNull("data")) {
					String data = temp.getString("data");
					upDateEquipmentList(IP, msgTag, data);
					message.arg1 = MsgTag.success;
					message.obj = data;
				} else if ("using".equals(state)) {
					message.arg1 = MsgTag.using;
					message.obj = IP;
				} else if ("fail".equals(state) && !temp.isNull("data")) {
					if ("设备未连接".equals(temp.get("data").toString())) {
						buildTCPConnect(IP, handler);
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if (handler != null) {
			handler.sendMessage(message);
		}

	}

	private static void upDateEquipmentList(String iP, int msgTag, String data) {
		EquipmentInfo info = new EquipmentInfo();
		info.setIP(iP);
		if (!EquipmentList.Instance().IpList().contains(iP)) {
			EquipmentList.Instance().add(info);
		}
		for (int i = 0; i < EquipmentList.Instance().size(); i++) {
			if (EquipmentList.Instance().get(i).getIP().equals(iP)) {
				switch (msgTag) {
				case MsgTag.getEquipmentId:
					EquipmentList.Instance().get(i).setId(data);
					break;
				case MsgTag.getEquipmentState:
					if ("0.0A".equals(data)) {
						EquipmentList.Instance().get(i).IsOpened(false);
					} else {
						EquipmentList.Instance().get(i).IsOpened(true);
					}
					break;
				case MsgTag.openEquipment:
					EquipmentList.Instance().get(i).IsOpened(true);
					break;
				case MsgTag.closeEquipment:
					EquipmentList.Instance().get(i).IsOpened(false);
					break;
				case MsgTag.getEquipmentVoltage:
					EquipmentList.Instance().get(i).setVoltage(data);
					break;
				case MsgTag.getEquipmentElectric:
					EquipmentList.Instance().get(i).setElectricity(data);
					break;
				case MsgTag.getCurrentQuantity:
					EquipmentList.Instance().get(i).setCurrentQuantity(data);
					break;
				default:
					break;
				}
			}
		}
	}

	// public static boolean upDateEquipmentList(Message msg) {
	// EquipmentInfo info = new EquipmentInfo();
	// String IP = "";
	// String data = "";
	// String state = "";
	// try {
	// JSONObject temp = new JSONObject(msg.obj.toString());
	// if (temp.isNull("IP"))
	// return true;
	// IP = temp.getString("IP");
	// info.setIP(IP);
	// if (!temp.isNull("data")) {
	// data = temp.getString("data");
	// }
	// if (!temp.isNull("state")) {
	// state = temp.getString("state");
	// }
	// System.out.println("state: " + state);
	// System.out.println("data: " + data);
	// if (!EquipmentList.Instance().IpList().contains(IP)) {
	// EquipmentList.Instance().add(info);
	// }
	//
	// for (int i = 0; i < EquipmentList.Instance().size(); i++) {
	// if ("success".equals(state) &&
	// EquipmentList.Instance().get(i).getIP().equals(IP)) {
	// switch (msg.what) {
	// case MsgTag.getEquipmentId:
	// EquipmentList.Instance().get(i).setId(data);
	// break;
	// case MsgTag.getEquipmentState:
	// if ("0.0A".equals(data)) {
	// EquipmentList.Instance().get(i).IsOpened(false);
	// } else {
	// EquipmentList.Instance().get(i).IsOpened(true);
	// }
	// break;
	// case MsgTag.openEquipment:
	// EquipmentList.Instance().get(i).IsOpened(true);
	// break;
	// case MsgTag.closeEquipment:
	// EquipmentList.Instance().get(i).IsOpened(false);
	// break;
	// case MsgTag.getEquipmentVoltage:
	// EquipmentList.Instance().get(i).setVoltage(data);
	// break;
	// case MsgTag.getEquipmentElectric:
	// EquipmentList.Instance().get(i).setElectricity(data);
	// break;
	// case MsgTag.getCurrentQuantity:
	// EquipmentList.Instance().get(i).setCurrentQuantity(data);
	// break;
	// default:
	// break;
	// }
	// }
	//
	// }
	// } catch (JSONException e) {
	// e.printStackTrace();
	// }
	// return true;
	//
	// }

}
