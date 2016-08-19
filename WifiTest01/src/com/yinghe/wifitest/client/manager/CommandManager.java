package com.yinghe.wifitest.client.manager;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.yinghe.wifitest.client.entity.CommandInfo;
import com.yinghe.wifitest.client.entity.CommandTag;
import com.yinghe.wifitest.client.entity.ConstantREntity;
import com.yinghe.wifitest.client.entity.MsgTag;
import com.yinghe.wifitest.client.utils.DLT645_2007Utils;
import com.yinghe.wifitest.client.utils.DigitalUtils;
import com.yinghe.wifitest.client.utils.MinaUtils;
import com.yinghe.wifitest.client.utils.SocketUtils;

import android.os.Handler;
import android.os.Message;

public class CommandManager {

	public static void getEquipmentIp(final Handler handler) {
		SocketUtils.sendMsgWithUDPSocket(ConstantREntity.initialIp, ConstantREntity.initialPort, 8080, CommandInfo.getEquipmentId, new Handler() {
			@Override
			public void handleMessage(Message msg) {
				ArrayList<String> result = EquipmentManager.removeSameIp((JSONArray) msg.obj);
				EquipmentManager.updateTempEquipmentIplist(result);
				Message message = new Message();
				message.what = MsgTag.GetEquipmentIp;
				if (result.size() > 0) {
					message.arg1 = MsgTag.success;
				} else {
					message.arg1 = MsgTag.fail;
				}
				handler.sendMessage(message);
			}
		});
	}

	public static void buildTCPConnect(String equipmentIp) {
		String temp = "AT+NETP=TCP,CLIENT," + ConstantREntity.equipmentServerPort + "," + ConstantREntity.serverIp;
		byte[] input = DLT645_2007Utils.getDltCode(temp);
		System.out.println(temp);
		System.out.println(DigitalUtils.getHexStringByBytes(input));
		SocketUtils.sendMsgWithUDPSocket(equipmentIp, ConstantREntity.initialPort, 8080, input, null);
	}

	public static void testTCPConnect(final String iP, Handler handler) {
		try {
			JSONObject info = new JSONObject();
			info.put("IP", iP);
			info.put("command", CommandTag.getEquipmentId);

			MinaUtils.sendMessageWithMina(info.toString(), new Handler() {
				@Override
				public void handleMessage(Message msg) {
					System.out.println(msg.obj.toString());
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
