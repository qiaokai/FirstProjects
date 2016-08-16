package com.yinghe.wifitest.services.server;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.json.JSONObject;

public class ServerHandler implements IoHandler {

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		cause.printStackTrace();
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		System.out.println("session opened " + session.getRemoteAddress().toString());

	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		System.out.println(session.getRemoteAddress().toString() + ":   " + message.toString());

		try {
			JSONObject info = new JSONObject(message.toString());
			System.out.println("IP:" + info.getString("IP"));
			System.out.println("order:" + info.getString("order"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// JSONObject temp=(JSONObject) message;

		// System.out.println(temp.toString());
		// String msg=message.toString();
		// ArrayList<String> temp=(ArrayList<String>) message;
		// System.out.println(temp.get(index));
		//
		//
		//// String
		session.write("fuck you too.");
	}

	@Override
	public void messageSent(IoSession arg0, Object arg1) throws Exception {

	}

	@Override
	public void sessionClosed(IoSession arg0) throws Exception {

	}

	@Override
	public void sessionCreated(IoSession arg0) throws Exception {

	}

	@Override
	public void sessionIdle(IoSession arg0, IdleStatus arg1) throws Exception {

	}

}
