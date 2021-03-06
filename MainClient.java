package comxg.test;

import java.net.InetSocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class MainClient {
	private static MainClient mainClient = null;
	NioSocketConnector connector = new NioSocketConnector();
	DefaultIoFilterChainBuilder chain = connector.getFilterChain();

	public static MainClient getInstances() {
		if (null == mainClient) {
			mainClient = new MainClient();
		}
		return mainClient;
	}

	private MainClient() {
		chain.addLast("myChin", new ProtocolCodecFilter(
			new ObjectSerializationCodecFactory()));
		connector.setHandler(ClientHandler.getInstances());
		connector.setConnectTimeout(30);
		ConnectFuture cf = connector.connect(new InetSocketAddress("localhost",
			8887));
	}

	public static void main(String args[]) {
		MainClient.getInstances();
	}
}
