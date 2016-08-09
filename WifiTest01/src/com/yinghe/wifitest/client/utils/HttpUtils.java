package com.yinghe.wifitest.client.utils;

import java.net.InetSocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

//import comxg.test.ClientHandler;

public class HttpUtils {
	NioSocketConnector connector = new NioSocketConnector();
	DefaultIoFilterChainBuilder chain = connector.getFilterChain();
	private static HttpUtils instance = null;
	private static int lastPort = 0;

	private HttpUtils(int port) {
		lastPort = port;
		chain.addLast("myChin", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
		// connector.setHandler(ClientHandler.getInstances());
		connector.setConnectTimeout(30);
		ConnectFuture cf = connector.connect(new InetSocketAddress("localhost", port));
	}

	public static HttpUtils getInstances(int port) {
		if (null == instance || lastPort != port) {
			instance = new HttpUtils(port);
		}
		return instance;
	}

	public void getEquipmentId() {
	};
}
