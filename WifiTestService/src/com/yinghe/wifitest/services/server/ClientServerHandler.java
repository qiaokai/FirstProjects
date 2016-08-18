package com.yinghe.wifitest.services.server;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.yinghe.wifitest.services.manager.ClientCommandManager;

public class ClientServerHandler implements IoHandler {

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		cause.printStackTrace();
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		System.out.println("session opened " + session.getRemoteAddress().toString());
		// 拿到所有的客户端Session

	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		ClientCommandManager.execute(session, message.toString());
	}

	@Override
	public void messageSent(IoSession arg0, Object arg1) throws Exception {

	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		System.out.println("sessionClosed " + session.getRemoteAddress().toString());
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus arg1) throws Exception {
	}

}
