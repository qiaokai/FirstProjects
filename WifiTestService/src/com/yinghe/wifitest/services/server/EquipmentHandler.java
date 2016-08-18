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
		System.out.println("session opened " + session.getRemoteAddress().toString());
		// 拿到所有的客户端Session

	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		System.out.println("equipment: " + session.getRemoteAddress().toString() + ":   " + message.toString());
		System.out.println("equipment: " + session.getAttribute("command"));
		System.out.println("equipment: " + session.getAttribute("IP"));
		EquipmentCommandManager.execute(session, message);
		//		String result = CommandManager.getResult(session,message.toString());

		//		session.write(result);
		// JSONObject temp=(JSONObject) message;

		// System.out.println(temp.toString());
		// String msg=message.toString();
		// ArrayList<String> temp=(ArrayList<String>) message;
		// System.out.println(temp.get(index));
		//
		//
		//// String
		//		session.write("fuck you too.");
	}

	@Override
	public void messageSent(IoSession arg0, Object arg1) throws Exception {

	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus arg1) throws Exception {
	}

}
