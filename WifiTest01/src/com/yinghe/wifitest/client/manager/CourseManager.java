package com.yinghe.wifitest.client.manager;

import com.yinghe.wifitest.client.utils.DLT645_2007Utils;
import com.yinghe.wifitest.client.utils.DigitalUtils;
import com.yinghe.wifitest.client.utils.SocketUtils;

import android.R.string;
import android.os.Handler;

public class CourseManager {
	public static void getEquipmentId(String serverIp, int serverPort, int clentport, Handler handler) {
		byte[] input = new byte[] { (byte) 0xFE, (byte) 0xFE, (byte) 0xFE, (byte) 0xFE, 0x68, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA, 0x68,
				0x13, 0x00, (byte) 0xDF, 0x16 };
		SocketUtils.sendMsgWithUDPSocket(serverIp, serverPort, clentport, input, handler);
	}

	public static void upDateWifi(String serverIp, int serverPort, int clientPort, Handler handler) {
		String temp = "AT+NETP=TCP,SERVER," + clientPort + "," + serverIp;
		byte[] input = DLT645_2007Utils.getDLTOrder(temp);
		SocketUtils.sendMsgWithUDPSocket(serverIp, serverPort, clientPort, input, handler);
	}

	public static void tetWifi(String serverIp, int serverPort, Handler handler) {
		byte[] input = new byte[] { (byte) 0xFE, (byte) 0xFE, (byte) 0xFE, (byte) 0xFE, 0x68, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA, 0x68,
				0x13, 0x00, (byte) 0xDF, 0x16 };
		SocketUtils.sendMsgWithTCPSocket(serverIp, serverPort, input, handler);
	}

	public static void changeType(String serverIp, int serverPort, int clientPort, Handler handler) {
		String temp = "AT+WMODE=AP";
		// String temp = "AT+WMODE=STA";
		// System.out.println("changeType");
		// String temp ="AT+Z";

		byte[] temp1 = DigitalUtils.getHexBytes(temp);
		System.out.println("changeType:" + DigitalUtils.asciiByteToAsciiString(temp1));
		byte[] input = DigitalUtils.AsciiBytesTo645Bytes(temp1);

		// System.out.println("changeType:" +
		// DigitalUtils.asciiByteToAsciiString(input));
		// SocketUtils.sendMsgWithUDPSocket(serverIp, serverPort, clientPort,
		// input, handler);
	}

	public static void setWifi(String serverIp, int serverPort, int clientPort, Handler handler) {
//		String temp = "AT+WSSSID=FAST_30E8";
//		byte[] temp1 = DigitalUtils.StringToAsciiBytes(temp);
//		System.out.println(DigitalUtils.asciiByteToAsciiString(temp1));
//		byte[] input = DigitalUtils.AsciiBytesTo645Bytes(temp1);
//		System.out.println(DigitalUtils.asciiByteToAsciiString(input));
//		SocketUtils.sendMsgWithUDPSocket(serverIp, serverPort, clientPort, input, handler);
	}
}
