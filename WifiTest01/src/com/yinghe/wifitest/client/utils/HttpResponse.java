package com.yinghe.wifitest.client.utils;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.json.JSONObject;

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
	}
	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		super.messageReceived(session, message);
		JSONObject response=new JSONObject(message.toString());
		String result=response.getString("data");
		System.out.println(response.toString());
		
		
		
	}
	@Override
	public void sessionClosed(IoSession session) throws Exception {
		super.sessionClosed(session);
	}
	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		super.sessionIdle(session, status);
	}
}
