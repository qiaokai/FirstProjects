package com.yinghe.wifitest.client.utils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class Test0725 {
	public static void main(String[] args) {
		ReceiveMsg();
	}

	public static void ReceiveMsg() {
		DatagramSocket socket = null;
		try {
//			socket = new DatagramSocket(8080);// 创建DatagramSocket对象并绑定一个本地端口号
			if (socket == null) {
				socket = new DatagramSocket(null);
				socket.setReuseAddress(true);
				socket.bind(new InetSocketAddress(9000));
			}
			
			while (true) {
				byte[] buf = new byte[4 * 1024];// 创建一个byte类型的数组，用于存放接收到得数据
				DatagramPacket pack = new DatagramPacket(buf, buf.length);// 创建一个DatagramPacket对象，并指定DatagramPacket对象的大小和长度
				socket.receive(pack);// 读取接收到得数据
										// 包,如果客户端没有发送数据包，那么该线程就停滞不继续，这个同样也是阻塞式的
				String str = new String(pack.getData(), 0, pack.getLength(), "UTF-8");// 将接收到的数据包转为字符串输出显示
				String ip = pack.getAddress().getHostAddress();// 得到发送数据包的主机的ip地址
				System.out.println(ip + "发送:" + str);
				// -----------返回数据给客户端------------
				InetAddress address = pack.getAddress();// 得到发送数据包主机的网络地址对象
				byte[] data = "已收到！".getBytes();
				DatagramPacket p = new DatagramPacket(data, data.length, address, 8080);
				socket.send(p);
			}
			// 注意不需要关闭服务器的socket，因为它一直等待接收数据
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
