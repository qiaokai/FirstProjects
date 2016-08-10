package com.yinghe.wifitest.services;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class MainHandler extends IoHandlerAdapter {
	public void sessionOpened(IoSession session) throws Exception {
		System.out.println("session opened " + session.getRemoteAddress().toString());
		System.out.println(MainServer.socketAcceptor.getManagedSessions());
	}

	public void messageReceived(IoSession session, Object message) throws Exception {
//		String str = new String((byte[]) message, "utf8");
		System.out.println(session.getRemoteAddress().toString()+":   "+message.toString());
//		String str = new String((byte[])message);
		// 收到客户端发来的数据
//		System.out.println("received message: " + str);
		// 向客户端发送数据
		session.write("fuck you too.");
//		session.w
	}

	public void sessionClosed(IoSession session) throws Exception {
		System.out.println("session closed " + session.getRemoteAddress().toString());
	}

	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		System.out.println("exceptionCaught");
		cause.printStackTrace();
	}
}
