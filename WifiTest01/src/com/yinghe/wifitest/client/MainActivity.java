package com.yinghe.wifitest.client;

import java.io.IOException;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

import com.example.wifitest01.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	/* 服务器地址 */
	private final String SERVER_HOST_IP = "255.255.255.255";

	/* 服务器端口 */
	private final int SERVER_HOST_PORT = 9000;

	private Button btnConnect;
	private Button btnSend;
	private EditText editSend;
	private Socket socket;
	private PrintStream output;
	TextView textView;

	public void toastText(String message) {
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}

	public void handleException(Exception e, String prefix) {
		e.printStackTrace();
		toastText(prefix + e.toString());
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initView();

		btnConnect.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// initClientSocket();
				new Timer().schedule(new TimerTask() {

					@Override
					public void run() {
						// TODO Auto-generated method stub

						UDP_send("FE FE FE FE 68 AA AA AA AA AA AA 68 13 00 DF 16");
					}
				}, 100);
			}
		});

		btnSend.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {

				sendMessage(editSend.getText().toString());
			}
		});
	}

	public void initView() {
		btnConnect = (Button) findViewById(R.id.btnConnect);
		btnSend = (Button) findViewById(R.id.btnSend);
		editSend = (EditText) findViewById(R.id.sendMsg);
		textView = (TextView) findViewById(R.id.textView1);
		btnSend.setEnabled(false);
		editSend.setEnabled(false);
	}

	public void closeSocket() {
		try {
			output.close();
			socket.close();
		} catch (IOException e) {
			handleException(e, "close exception: ");
		}
	}

	int count = 0;

	public void UDP_send(String msg) {
		DatagramSocket socket = null;
		try {
			if (socket == null) {
				socket = new DatagramSocket(null);
				socket.setReuseAddress(true);
				socket.bind(new InetSocketAddress(8080));
			}
			byte[] data = msg.getBytes();// 把字符串转为字节数组
			InetAddress serverAddress = InetAddress.getByName("10.20.73.16");//
			// 得到ip或主机名为192.168.1.100的网络地址对象
			DatagramPacket pack = new DatagramPacket(data, data.length, serverAddress, 8080);//
			// 参数分别为：发送数据的字节数组对象、数据的长度、目标主机的网络地址、目标主机端口号，发送数据时一定要指定接收方的网络地址和端口号
			socket.send(pack);// 发送数据包
			// -----------接收服务器返回的数据-------------
			while (true) {
				byte[] b = new byte[4 * 1024];// 创建一个byte类型的数组，用于存放接收到得数据
				DatagramPacket pack2 = new DatagramPacket(b, b.length);//
				// 定义一个DatagramPacket对象用来存储接收的数据包，并指定大小和长度
				socket.receive(pack2);// 接收数据包

				System.out.println("000000000000000000002");
				// data.getData()是得到接收到的数据的字节数组对象，0为起始位置，pack.getLength()得到数据的长度

				String result = new String(pack2.getData(), pack2.getOffset(), pack2.getLength());// 把返回的数据转换为字符串
				textView.setText(result + ":" + count);
				Toast.makeText(getApplicationContext(), result + ":" + count, Toast.LENGTH_SHORT).show();
				count = count + 1;
				socket.close();// 释放资源
			}
			// 在线程中更新UI
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void initClientSocket() {
		try {
			/* 连接服务器 */
			socket = new Socket(SERVER_HOST_IP, SERVER_HOST_PORT);
			/* 获取输出流 */
			output = new PrintStream(socket.getOutputStream(), true, "utf-8");

			btnConnect.setEnabled(false);
			editSend.setEnabled(true);
			btnSend.setEnabled(true);
		} catch (UnknownHostException e) {
			handleException(e, "unknown host exception: " + e.toString());
		} catch (IOException e) {
			handleException(e, "io exception: " + e.toString());
		}
	}

	private void sendMessage(String msg) {
		output.print("FE FE FE FE 68 AA AA AA AA AA AA 68 13 00 DF 16");
	}
}