package com.yinghe.wifitest.services.server;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.yinghe.wifitest.services.utils.BufferCoderFactory;

public class MainServer {
	public static NioSocketAcceptor acceptor;
	public final int serverPort = 8067;
	// database info
	public static String dbHost = "192.168.1.103", dbName = "test", username = "work", password = "123456";
	public static int dbPort = 3306;

	private static MainServer instanse = null;

	private MainServer() {
		acceptor = new NioSocketAcceptor();
		acceptor.setReuseAddress(true);
		acceptor.getSessionConfig().setKeepAlive(true);
		acceptor.setHandler(new ServerHandler());

		DefaultIoFilterChainBuilder chain = acceptor.getFilterChain(); // 设置过滤器
		chain.addLast("codec", new ProtocolCodecFilter(new BufferCoderFactory()));// 明码字符串
		try {
			acceptor.bind(new InetSocketAddress(serverPort));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public synchronized static MainServer getInstanse() {
		if (instanse == null) {
			instanse = new MainServer();
		}
		return instanse;

	}
}
