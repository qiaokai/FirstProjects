package com.yinghe.wifitest.client.manager;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;

import android.os.Handler;
import android.os.Message;

import com.yinghe.wifitest.client.entity.EquipmentInfo;
import com.yinghe.wifitest.client.entity.EquipmentListInfo;
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

	public static void buildTCPConnect(final ArrayList<String> IpList, final Handler handler) {
		for (String IP : IpList) {
			CommandManager.buildTCPConnect(IP);
		}
		if (handler != null) {
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					Message message = new Message();
					message.what = MsgTag.buildTCPConnect;
					message.arg1 = MsgTag.success;
					message.obj = IpList;
					handler.sendMessage(message);
				}
			}, 3000);
		}
	}

	public static void checkTCPConnect(Handler handler) {
		for (String IP : TempEquipmentIpList.Instance()) {
			CommandManager.testTCPConnect(IP, new Handler() {
				@Override
				public void handleMessage(Message msg) {
					super.handleMessage(msg);
				}
			});
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

	public static void updateTempEquipmentIplist(ArrayList<String> IpList) {
		for (String IP : IpList) {
			if (!TempEquipmentIpList.Instance().contains(IP)) {
				TempEquipmentIpList.Instance().add(IP);
			}
		}
	}

}
