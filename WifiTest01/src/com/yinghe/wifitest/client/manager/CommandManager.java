package com.yinghe.wifitest.client.manager;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;

import com.yinghe.wifitest.client.entity.CommandInfo;
import com.yinghe.wifitest.client.entity.ConstantREntity;
import com.yinghe.wifitest.client.entity.MsgTag;
import com.yinghe.wifitest.client.utils.DLT645_2007Utils;
import com.yinghe.wifitest.client.utils.HttpUtils;
import com.yinghe.wifitest.client.utils.SocketUtils;

public class CommandManager {

	public static void getEquipmentIp(final Handler handler) {
		SocketUtils.sendMsgWithUDPSocket(ConstantREntity.initialIp, ConstantREntity.initialPort, 8080, CommandInfo.getEquipmentId, new Handler() {
			@Override
			public void handleMessage(Message msg) {
				ArrayList<String> result = EquipmentManager.removeSameIp((JSONArray) msg.obj);
				EquipmentManager.updateEquilMentList(result);
				EquipmentManager.buildTCPConnect(result);
				Message message = new Message();
				message.what = MsgTag.GetEquipmentIp;
				if (result.size() > 0) {
					message.arg1 = MsgTag.success;
				} else {
					message.arg1 = MsgTag.fail;
				}
				message.obj = result;
				handler.sendMessage(message);
			}
		});
	}

	public static void buildTCPConnect(String equipmentIp) {
		String temp = "AT+NETP=TCP,CLIENT," + ConstantREntity.serverPort + "," + ConstantREntity.serverIp;
		byte[] input = DLT645_2007Utils.getDltCode(temp);
		SocketUtils.sendMsgWithUDPSocket(equipmentIp, ConstantREntity.initialPort, 8080, input, null);
	}

	public static void testTCPConnect(Handler handler) {
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				HttpUtils.getInstances().getEquipmentId();
			}
		}, 10);
	}
}
