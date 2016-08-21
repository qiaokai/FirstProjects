package com.yinghe.wifitest.client.manager;

import org.json.JSONException;
import org.json.JSONObject;

import com.yinghe.wifitest.client.entity.CommandInfo;
import com.yinghe.wifitest.client.entity.ConstantEntity;
import com.yinghe.wifitest.client.entity.MsgTag;
import com.yinghe.wifitest.client.utils.DLT645_2007Utils;
import com.yinghe.wifitest.client.utils.MinaUtils;
import com.yinghe.wifitest.client.utils.SocketUtils;

import android.os.Handler;
import android.os.Message;

public class CommandManager {

	public static void getEquipmentIp(final Handler handler) {
		SocketUtils.sendMsgWithUDPSocket(ConstantEntity.initialIp, ConstantEntity.initialPort, 8080, CommandInfo.getEquipmentId, new Handler() {
			@Override
			public void handleMessage(Message msg) {
				Message message = new Message();
				message.what = msg.what;
				message.arg1 = MsgTag.fail;
				if (msg.what == MsgTag.success) {
					try {
						JSONObject imput = new JSONObject(msg.obj.toString());
						if (!imput.isNull("IP")) {
							String IP = imput.getString("IP");
							message.arg1 = MsgTag.success;
							message.obj = IP;
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				if (handler != null) {
					handler.sendMessage(message);
				}
			}
		});
	}

	public static void buildTCPConnect(String equipmentIp) {
		String temp = "AT+NETP=TCP,CLIENT," + ConstantEntity.equipmentServerPort + "," + ConstantEntity.serverIp;
		byte[] input = DLT645_2007Utils.getDltCode(temp);
		SocketUtils.sendMsgWithUDPSocket(equipmentIp, ConstantEntity.initialPort, 8080, input, null);
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
