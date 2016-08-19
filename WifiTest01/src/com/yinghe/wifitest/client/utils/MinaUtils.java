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
import com.yinghe.wifitest.client.utils.codeutil.BufferCoderFactory;

import android.os.Handler;

public class MinaUtils {

	public static void sendMessageWithMina(final String input, final Handler handler) {
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				NioSocketConnector connector = new NioSocketConnector();
				connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new BufferCoderFactory()));
				connector.setConnectTimeoutMillis(3000); // 设置连接超时
				connector.setHandler(new MinaCallback(handler));

				ConnectFuture connectFuture = connector.connect(new InetSocketAddress(ConstantREntity.serverIp, ConstantREntity.serverPort));
				connectFuture.awaitUninterruptibly();// 等待连接成功
				IoSession session = connectFuture.getSession();
				session.write(input);

			}
		}, 0);
	}
}
