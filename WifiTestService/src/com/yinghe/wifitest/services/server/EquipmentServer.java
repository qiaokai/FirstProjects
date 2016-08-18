package com.yinghe.wifitest.services.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Collection;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.yinghe.wifitest.services.utils.EquipmentCoderFactory;

public class EquipmentServer {
	private static NioSocketAcceptor acceptor;
	private final int serverPort = 8087;

	private static EquipmentServer instanse = null;

	private EquipmentServer() {
		acceptor = new NioSocketAcceptor();
		acceptor.setReuseAddress(true);
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10); // 读写通道10秒内无操作进入空闲状态   
		acceptor.setHandler(new EquipmentHandler());

		DefaultIoFilterChainBuilder chain = acceptor.getFilterChain(); // 设置过滤器
		chain.addLast("codec", new ProtocolCodecFilter(new EquipmentCoderFactory()));// 明码字符串
		try {
			acceptor.bind(new InetSocketAddress(serverPort));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public synchronized static EquipmentServer getInstanse() {
		if (instanse == null) {
			instanse = new EquipmentServer();
		}
		return instanse;
	}

	public static synchronized Collection<IoSession> getClientSessions() {
		return acceptor.getManagedSessions().values();
	}
}
