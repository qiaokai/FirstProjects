package com.yinghe.wifitest.client.manager;

import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.yinghe.wifitest.client.entity.CommandInfo;
import com.yinghe.wifitest.client.entity.ConstantREntity;
import com.yinghe.wifitest.client.entity.MsgTag;
import com.yinghe.wifitest.client.utils.DLT645_2007Utils;
import com.yinghe.wifitest.client.utils.MinaUtils;
import com.yinghe.wifitest.client.utils.SocketUtils;

public class CommandManager {

	public static void getEquipmentIp(final Handler handler) {
		SocketUtils.sendMsgWithUDPSocket(ConstantREntity.initialIp, ConstantREntity.initialPort, 8080, CommandInfo.getEquipmentId, new Handler() {
			@Override
			public void handleMessage(Message msg) {
				Message message = new Message();
				message.what = MsgTag.GetEquipmentIp;
				boolean isError = true;
				if (msg.what == MsgTag.success) {
					String IP = EquipmentManager.updateTempEquipmentIplist(msg);
					if (!TextUtils.isEmpty(IP)) {
						message.arg1 = MsgTag.success;
						message.obj = IP;
						handler.sendMessage(message);
					}
				} else if (isError) {
					message.arg1 = MsgTag.fail;
					handler.sendMessage(message);
				}
			}
		});
	}

	public static void buildTCPConnect(String equipmentIp) {
		String temp = "AT+NETP=TCP,CLIENT," + ConstantREntity.equipmentServerPort + "," + ConstantREntity.serverIp;
		byte[] input = DLT645_2007Utils.getDltCode(temp);
		SocketUtils.sendMsgWithUDPSocket(equipmentIp, ConstantREntity.initialPort, 8080, input, null);
	}

	public static void excute(String ip, String command, Handler handler) {
		try {
			JSONObject info = new JSONObject();
			info.put("IP", ip);
			info.put("command", command);
			MinaUtils.sendMessageWithMina(info.toString(), handler);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
