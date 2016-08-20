package com.yinghe.wifitest.client.utils;

import java.net.InetSocketAddress;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.yinghe.wifitest.client.callback.MinaCallback;
import com.yinghe.wifitest.client.entity.ConstantREntity;
import com.yinghe.wifitest.client.entity.MsgTag;
import com.yinghe.wifitest.client.utils.codeutil.BufferCoderFactory;

import android.os.Handler;
import android.os.Message;

public class MinaUtils {

	public static void sendMessageWithMina(final String input, final Handler handler) {
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				NioSocketConnector connector = new NioSocketConnector();
				connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new BufferCoderFactory()));
				connector.setHandler(new MinaCallback(handler));

				ConnectFuture connectFuture = connector.connect(new InetSocketAddress(ConstantREntity.serverIp, ConstantREntity.serverPort));
				connectFuture.awaitUninterruptibly();// 等待连接成功
				if (connectFuture.isConnected()) {
					IoSession session = connectFuture.getSession();
					session.write(input);
				} else {
					if (handler != null) {
						Message msg = new Message();
						msg.arg1 = MsgTag.fail;
						msg.obj = "服务器未连接";
						handler.sendMessage(msg);
					}
				}

			}
		}, 0);
	}
}
