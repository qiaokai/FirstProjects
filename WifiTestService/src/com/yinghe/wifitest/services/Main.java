package com.yinghe.wifitest.services;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.yinghe.wifitest.services.server.ClientServer;
import com.yinghe.wifitest.services.server.EquipmentServer;

public class Main {
	public static void main(String[] args) {
		try {
			InetAddress addr = InetAddress.getLocalHost();
			String ip = addr.getHostAddress();//获得本机IP       
			System.out.println("当前服务器IP：" + ip);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		ClientServer.getInstanse();
		EquipmentServer.getInstanse();
	}
}
