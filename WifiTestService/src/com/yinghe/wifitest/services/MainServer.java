package com.yinghe.wifitest.services;

import java.net.InetSocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class MainServer extends Thread {
	// the mina info
	public boolean isAlive = true;
	public MainHandler handler;
	public static NioSocketAcceptor socketAcceptor;
	public final int port = 8067;
	// database info
	public static String dbHost = "192.168.1.103", dbName = "test", username = "work", password = "123456";
	public static int dbPort = 3306;

	public static void main(String[] args) {
		// start mina and database
		MainServer main = new MainServer();
		main.startAcceptor();
		System.out.println("server started finished .");
	}

	public MainServer() {
		socketAcceptor = new NioSocketAcceptor();
		socketAcceptor.setReuseAddress(true);
		socketAcceptor.getSessionConfig().setKeepAlive(true);
		handler = new MainHandler();
		socketAcceptor.setHandler(handler);
		
		System.out.println(socketAcceptor.getManagedSessions());
		// 设置过滤器
		DefaultIoFilterChainBuilder chain = socketAcceptor.getFilterChain();
		chain.addLast("codec", new ProtocolCodecFilter(new BufferCoderFactory()));// 明码字符串
//		chain.addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
		// 启动数据库
		//		startDataBaseDriver(dbHost, dbPort, dbName, username, password);
	}

	public void run() {
		while (isAlive) {
			try {
				sleep(3000);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		System.exit(0);
	}

	private void startDataBaseDriver(String host, int port, String dbName, String username, String password) {
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void startAcceptor() {
		try {
			socketAcceptor.bind(new InetSocketAddress(port));
			this.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
