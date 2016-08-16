package com.yinghe.wifitest.services;

import java.util.HashMap;

import com.yinghe.wifitest.services.server.MainServer;

public class Main {
	public static void main(String[] args) {

//		HashMap<String, Object> temp=new HashMap<String, Object>();
//		temp.put("order", "getId");
//		System.out.println(temp.toString());
		MainServer.getInstanse();
	}
}
