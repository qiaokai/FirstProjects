package com.yinghe.wifitest.client.utils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;

public class SocketUtils {

	/**
	 * 通过TCP方式向服务器发送请求
	 * 
	 * @param serverIp
	 * @param serverPort
	 * @param input
	 * @return 服务器返回数据
	 */
	public static void sendMsgWithTCPSocket(final String serverIp, final int serverPort, final byte[] input, final Handler handler) {
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				String recept = "";
				Socket socket = null;
				DataOutputStream dataOutputStream = null;
				InputStream inputStream = null;
				try {
					if (socket == null)
						socket = new Socket(serverIp, serverPort);
					if (dataOutputStream == null)
						dataOutputStream = new DataOutputStream(socket.getOutputStream());
					if (inputStream == null)
						inputStream = socket.getInputStream();

					dataOutputStream.write(input);
					dataOutputStream.flush();

					ArrayList<Byte> data = new ArrayList<Byte>();
					byte[] response = new byte[1024];
					int length = -1;
					while ((length = inputStream.read(response)) != -1) {
						for (int i = 0; i < length; i++) {
							data.add(response[i]);
							recept += Integer.toHexString(response[i] & 0xFF) + " ";
						}
						if (length < 1024) {
							break;
						}
					}

					JSONObject result = new JSONObject();
					result.put("data", data);
					if (handler != null) {
						Message message = new Message();
						message.obj = result;
						handler.sendMessage(message);
					}
					socket.close();
					dataOutputStream.close();
					inputStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 10);

	}

	/**
	 * 通过UDP方式向服务器发送请求
	 * 
	 * @param serverIp
	 * @param serverPort
	 * @param input
	 * @param handler
	 * @return 服务器返回数据
	 */
	public static void sendMsgWithUDPSocket(final String serverIp, final int serverPort, final int clientPort, final byte[] input, final Handler handler) {
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					DatagramSocket socket = null;
					if (socket == null) {
						socket = new DatagramSocket(null);
						socket.setReuseAddress(true);
						socket.bind(new InetSocketAddress(clientPort));
					}
					InetAddress serverAddress = InetAddress.getByName(serverIp);//
					DatagramPacket pack = new DatagramPacket(input, input.length, serverAddress, serverPort);//
					socket.send(pack);// 发送数据包

					byte[] receive = new byte[4 * 1024];// 创建一个byte类型的数组，用于存放接收到得数据
					DatagramPacket receivePack = new DatagramPacket(receive, receive.length);// 创建一个DatagramPacket对象，并指定DatagramPacket对象的大小和长度
					socket.receive(receivePack);// 读取接收到得数据

					ArrayList<Byte> data = new ArrayList<Byte>();
					for (int i = 0; i < receivePack.getLength(); i++) {
						data.add(receivePack.getData()[i]);
					}
					JSONObject result = new JSONObject();
					result.put("IP", receivePack.getAddress().getHostAddress());
					result.put("data", data);

					if (handler != null) {
						Message message = new Message();
						message.obj = result;
						handler.sendMessage(message);
					}
					socket.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 100);
	}

	public static void sendMsgWithTCPServer(final int serverPort, final byte[] input, final Handler handler) {
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				String recept = "";
				Socket socket = null;
				DataOutputStream dataOutputStream = null;
				InputStream inputStream = null;
				ServerSocket listen=null;
				try {
					if(listen==null){
						listen = new ServerSocket(8088);
//						listen.setReuseAddress(true);
//						listen.bind(new InetSocketAddress(8088));
						}
					while (true) {

//						if (socket == null) {
//							socket = new Socket("192.168.1.107", 8088);
						
					System.out.println("OKOKOKOKOKOK");
					System.out.println(listen.getLocalSocketAddress());
					System.out.println(listen.getChannel());
							 socket = listen.accept();
							 System.out.println(socket.getInetAddress());
//						}
						if (dataOutputStream == null)
							dataOutputStream = new DataOutputStream(socket.getOutputStream());
						if (inputStream == null)
							inputStream = socket.getInputStream();

						dataOutputStream.write(input);
						dataOutputStream.flush();

						ArrayList<Byte> data = new ArrayList<Byte>();
						byte[] response = new byte[1024];
						int length = -1;
						while ((length = inputStream.read(response)) != -1) {
							for (int i = 0; i < length; i++) {
								data.add(response[i]);
								recept += Integer.toHexString(response[i] & 0xFF) + " ";
							}
							if (length < 1024) {
								break;
							}
						}
						System.out.println("OKOKOKOKOK");
						System.out.println(recept);
						JSONObject result = new JSONObject();
						result.put("data", data);
						if (handler != null) {
							Message message = new Message();
							message.obj = result;
							handler.sendMessage(message);
						}
						socket.close();
						dataOutputStream.close();
						inputStream.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 10);

	}

}
