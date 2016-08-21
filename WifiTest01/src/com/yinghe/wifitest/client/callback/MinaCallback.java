package com.yinghe.wifitest.client.callback;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import com.yinghe.wifitest.client.entity.MsgTag;

import android.os.Handler;
import android.os.Message;

public class MinaCallback extends IoHandlerAdapter {

	private Handler handler;

	public MinaCallback(Handler handler) {
		this.handler = handler;
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		if (handler != null) {
			Message msg = new Message();
			msg.what = MsgTag.getEquipmentState;
			msg.arg1 = MsgTag.fail;
			msg.obj = "out time";
			handler.sendMessage(msg);
		}
	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		super.messageReceived(session, message);
		if (handler != null) {
			Message msg = new Message();
			msg.obj = message.toString();
			handler.sendMessage(msg);
		}
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		super.sessionClosed(session);
	}

}
