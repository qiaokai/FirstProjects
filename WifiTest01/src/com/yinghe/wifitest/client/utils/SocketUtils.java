package com.yinghe.wifitest.client.utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import android.widget.Toast;

public class SocketUtils {

	/**
	 * 通过TCP方式向服务器发送请求
	 * 
	 * @param serverIp
	 * @param serverPort
	 * @param input
	 * @return 服务器返回数据
	 */
	public static String sendMsgWithTCPSocket(String serverIp, int serverPort, byte[] input) {
		String recept = "";
		Socket socket = null;
		DataOutputStream dataOutputStream = null;
		DataInputStream dataInputStream = null;
		try {
			if (socket == null)
				socket = new Socket(serverIp, serverPort);
			if (dataOutputStream == null)
				dataOutputStream = new DataOutputStream(socket.getOutputStream());
			if (dataInputStream == null)
				dataInputStream = new DataInputStream(socket.getInputStream());
			Thread.sleep(50, 0); // 实际中刚连上后发数据需要一定延时 确保双方链接初始化完成
			dataOutputStream.write(input);
			dataOutputStream.flush();

			int backLength = dataInputStream.available();
			char[] buffer = new char[backLength];
			for (int j = 0; j < backLength; ++j) {
				buffer[j] = (char) dataInputStream.readByte();
				buffer[j] = (char) (buffer[j] + '0');
			}
			recept = new String(buffer);

			socket.close();
			dataOutputStream.close();
			dataInputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return recept;
	}

	/**
	 * 通过UDP方式向服务器发送请求
	 * 
	 * @param serverIp
	 * @param serverPort
	 * @param input
	 * @return 服务器返回数据
	 */
	public static String sendMsgWithUDPSocket(String serverIp, int serverPort, int clientPort, byte[] input) {
		DatagramSocket socket = null;
		String result = "";
		try {
			if (socket == null) {
				socket = new DatagramSocket(null);
				socket.setReuseAddress(true);
				socket.bind(new InetSocketAddress(clientPort));
			}
			InetAddress serverAddress = InetAddress.getByName(serverIp);//
			DatagramPacket pack = new DatagramPacket(input, input.length, serverAddress, serverPort);//
			socket.send(pack);// 发送数据包
			while (true) {
				byte[] b = new byte[4 * 1024];// 创建一个byte类型的数组，用于存放接收到得数据
				DatagramPacket pack2 = new DatagramPacket(b, b.length);//
				socket.receive(pack2);// 接收数据包
				result = new String(pack2.getData(), pack2.getOffset(), pack2.getLength());// 把返回的数据转换为字符串
				socket.close();// 释放资源
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}
}
