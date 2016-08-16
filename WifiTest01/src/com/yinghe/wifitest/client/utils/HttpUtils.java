package com.yinghe.wifitest.client.utils;

import java.net.InetSocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.json.JSONObject;

import com.yinghe.wifitest.client.utils.codeutil.BufferCoderFactory;

//import comxg.test.ClientHandler;

public class HttpUtils {
	NioSocketConnector connector = new NioSocketConnector();
	DefaultIoFilterChainBuilder chain = connector.getFilterChain();
	private static HttpUtils instance = null;
	private static int lastPort = 0;
	private IoSession session;

	@SuppressWarnings("deprecation")
	private HttpUtils(String serverIp, final int port) {
		lastPort = port;
		chain.addLast("codec", new ProtocolCodecFilter(new BufferCoderFactory()));
		connector.setHandler(HttpResponse.getInstance());
		connector.setConnectTimeout(30);
		ConnectFuture cf = connector.connect(new InetSocketAddress(serverIp, port));
		// 等待连接成功
		cf.awaitUninterruptibly();
		session = cf.getSession();

	}

	public synchronized static HttpUtils getInstances(String serverIp, int port) {
		if (null == instance || lastPort != port) {
			instance = new HttpUtils(serverIp, port);
		}
		return instance;
	}

	public void getEquipmentId() {
		JSONObject info;
		try {
			info = new JSONObject();
			info.put("IP", "192.168.1.103");
			info.put("order", "getEquipmentId");
			session.write(info.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		OrderInfo info=new OrderInfo("192.168.1.103","getEquipmentId");
//		String output = "order:getEquipmentId&&IP:192.168.1.103";
	};
}
