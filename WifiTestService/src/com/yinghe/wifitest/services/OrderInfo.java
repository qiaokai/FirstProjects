package com.yinghe.wifitest.services;

public class OrderInfo {
	String Ip;
	String Order;

	public OrderInfo(String Ip, String Order) {
		this.Ip = Ip;
		this.Order = Order;
	}

	public String getIp() {
		return Ip;
	}

	public void setIp(String ip) {
		Ip = ip;
	}

	public String getOrder() {
		return Order;
	}

	public void setOrder(String order) {
		Order = order;
	}
	
	
}
