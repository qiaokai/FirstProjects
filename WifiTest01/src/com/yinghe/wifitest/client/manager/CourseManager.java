package com.yinghe.wifitest.client.manager;

import org.json.JSONException;
import org.json.JSONObject;

import com.yinghe.wifitest.client.utils.DLT645_2007Utils;
import com.yinghe.wifitest.client.utils.DigitalUtils;
import com.yinghe.wifitest.client.utils.SocketUtils;

import android.os.Handler;
import android.os.Message;

public class CourseManager {
	public static void getEquipmentId(String serverIp, int serverPort, int clentport, final Handler handler) {
		byte[] input = new byte[] { (byte) 0xFE, (byte) 0xFE, (byte) 0xFE, (byte) 0xFE, 0x68, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA, 0x68,
				0x13, 0x00, (byte) 0xDF, 0x16 };
		SocketUtils.sendMsgWithUDPSocket(serverIp, serverPort, clentport, input, new Handler() {
			@Override
			public void handleMessage(Message msg) {
				JSONObject response = (JSONObject) msg.obj;
				DLT645_2007Utils.parseEquipmentId(response, handler);
			}
		});
	}

	public static void upDateWifi(String serverIp, int serverPort, int clientPort, Handler handler) {
		String temp = "AT+NETP=TCP,CLIENT," + 8088 + "," + "192.168.1.107";
		byte[] input = DLT645_2007Utils.getDltCode(temp);
		SocketUtils.sendMsgWithUDPSocket(serverIp, serverPort, clientPort, input, handler);
	}

	public static void tetWifi(String serverIp, int serverPort, final Handler handler) {
		byte[] input = new byte[] { (byte) 0xFE, (byte) 0xFE, (byte) 0xFE, (byte) 0xFE, 0x68, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA, 0x68,
				0x13, 0x00, (byte) 0xDF, 0x16 };
		SocketUtils.sendMsgWithTCPServer(serverPort, input, new Handler() {
			@Override
			public void handleMessage(Message msg) {
				JSONObject response = (JSONObject) msg.obj;
				DLT645_2007Utils.parseEquipmentId(response, handler);
			}
		});
	}

	public static void changeType(String serverIp, int serverPort, Handler handler) {
		String temp = "AT+WMODE=AP";
		byte[] input = DLT645_2007Utils.getDltCode(temp);
		SocketUtils.sendMsgWithTCPSocket(serverIp, serverPort, input, new Handler() {
			@Override
			public void handleMessage(Message msg) {
				JSONObject response = (JSONObject) msg.obj;
				try {
					System.out.println(response.get("data".toString()));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// DLT645_2007Utils.parseEquipmentElectric(response, handler);
			}
		});
	}

	public static void setWifi(String serverIp, int serverPort, int clientPort, Handler handler) {
		// String temp = "AT+WSSSID=FAST_30E8";
		// byte[] temp1 = DigitalUtils.StringToAsciiBytes(temp);
		// System.out.println(DigitalUtils.asciiByteToAsciiString(temp1));
		// byte[] input = DigitalUtils.AsciiBytesTo645Bytes(temp1);
		// System.out.println(DigitalUtils.asciiByteToAsciiString(input));
		// SocketUtils.sendMsgWithUDPSocket(serverIp, serverPort, clientPort,
		// input, handler);
	}

	/**
	 * 获取电压
	 * 
	 * @param serverIp
	 * @param serverPort
	 * @param handler
	 */
	public static void getVoltage(String serverIp, int serverPort, final Handler handler) {
		byte[] input = new byte[] { (byte) 0xFE, (byte) 0xFE, (byte) 0xFE, (byte) 0xFE, 0x68, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA, 0x68,
				0x11, 0x04, 0x33, 0x34, 0x34, 0x35, (byte) 0xB1, 0x16 };
		SocketUtils.sendMsgWithTCPSocket(serverIp, serverPort, input, new Handler() {
			@Override
			public void handleMessage(Message msg) {
				JSONObject response = (JSONObject) msg.obj;
				DLT645_2007Utils.parseEquipmentVoltage(response, handler);
			}
		});

	}

	/**
	 * 获取电流
	 * 
	 * @param serverIp
	 * @param serverPort
	 * @param handler
	 */
	public static void getElectric(String serverIp, int serverPort, final Handler handler) {
		byte[] input = new byte[] { (byte) 0xFE, (byte) 0xFE, (byte) 0xFE, (byte) 0xFE, 0x68, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA, 0x68,
				0x11, 0x04, 0x33, 0x34, 0x35, 0x35, (byte) 0xB2, 0x16 };
		SocketUtils.sendMsgWithTCPSocket(serverIp, serverPort, input, new Handler() {
			@Override
			public void handleMessage(Message msg) {
				JSONObject response = (JSONObject) msg.obj;
				DLT645_2007Utils.parseEquipmentElectric(response, handler);
			}
		});
	}

	public static void getServerIp(String serverIp, int serverPort, int clientPort, final Handler handler) {
		String temp = "AT+NETP";
		byte[] input = DLT645_2007Utils.getDltCode(temp);
		SocketUtils.sendMsgWithUDPSocket(serverIp, serverPort, clientPort, input, new Handler() {
			@Override
			public void handleMessage(Message msg) {
				JSONObject response = (JSONObject) msg.obj;
				try {
					System.out.println(response.get("data".toString()));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// DLT645_2007Utils.parseEquipmentElectric(response, handler);
			}
		});

	}

	public static void changeType_STA(String serverIp, int serverPort, Handler handler) {
		String temp = "AT+WMODE=STA";
		byte[] input = DLT645_2007Utils.getDltCode(temp);
		SocketUtils.sendMsgWithUDPSocket(serverIp, serverPort, 8080, input, new Handler() {
			@Override
			public void handleMessage(Message msg) {
				JSONObject response = (JSONObject) msg.obj;
				try {
					System.out.println(response.get("data".toString()));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// DLT645_2007Utils.parseEquipmentElectric(response, handler);
			}
		});
	}

	public static void setWJAP(String serverIp, int serverPort, int j, Handler handler) {
		String temp = "AT+WJAP=FAST_30E8,nishijinshandema?0or1";
		byte[] input = DLT645_2007Utils.getDltCode(temp);
		SocketUtils.sendMsgWithTCPSocket(serverIp, serverPort, input, new Handler() {
			@Override
			public void handleMessage(Message msg) {
				JSONObject response = (JSONObject) msg.obj;
				try {
					System.out.println(response.get("data".toString()));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// DLT645_2007Utils.parseEquipmentElectric(response, handler);
			}
		});
	}
}
