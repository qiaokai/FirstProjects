package com.test00;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Test0725 {
	public static void main(String[] args) {
		ReceiveMsg();
	}

	public static void ReceiveMsg() {
		new Thread(new Runnable() {
			public void run() {
				String recept = "";
				Socket socket = null;
				DataOutputStream dataOutputStream = null;
				InputStream inputStream = null;
				ServerSocket listen = null;
				try {
					if (listen == null) {
						listen = new ServerSocket(8088);
						// listen.setReuseAddress(true);
						// listen.bind(new InetSocketAddress(8088));
					}
					while (true) {

						// if (socket == null) {
						// socket = new Socket("192.168.1.107", 8088);

						socket = listen.accept();
						// }
						if (dataOutputStream == null)
							dataOutputStream = new DataOutputStream(socket.getOutputStream());
						if (inputStream == null)
							inputStream = socket.getInputStream();
						byte[] input = new byte[] { (byte) 0xFE, (byte) 0xFE, (byte) 0xFE, (byte) 0xFE, 0x68, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA, 0x68,
								0x13, 0x00, (byte) 0xDF, 0x16 };
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
						// JSONObject result = new JSONObject();
						// result.put("data", data);
						// if (handler != null) {
						// Message message = new Message();
						// message.obj = result;
						// handler.sendMessage(message);
						// }
						socket.close();
						dataOutputStream.close();
						inputStream.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}
