package com.yinghe.wifitest.client.utils;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class HttpResponse extends IoHandlerAdapter {
	private static HttpResponse instance = null;

	private HttpResponse() {
	};

	public synchronized static HttpResponse getInstance() {
		if (instance == null) {
			instance = new HttpResponse();
		}
		return instance;
	}
	
	@Override
	public void sessionOpened(IoSession session) throws Exception {
		super.sessionOpened(session);
		System.out.println("session opened.");
	}
	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		super.messageReceived(session, message);
		System.out.println(message.toString());
	}
	
}
