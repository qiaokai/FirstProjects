package com.yinghe.wifitest.services.server;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.yinghe.wifitest.services.manager.EquipmentCommandManager;

public class EquipmentHandler implements IoHandler {

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		cause.printStackTrace();
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		System.out.println("Equipment session opened " + session.getRemoteAddress().toString());
	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		EquipmentCommandManager.execute(session, message);
	}

	@Override
	public void messageSent(IoSession arg0, Object arg1) throws Exception {

	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		session.removeAttribute("IP");
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus arg1) throws Exception {
	}

}
