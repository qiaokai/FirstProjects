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
import com.yinghe.wifitest.client.utils.DigitalUtils;
import com.yinghe.wifitest.client.utils.SocketUtils;

import android.R.integer;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

//				 String a ="AT+NETP=TCP,SERVER,8899,192.168.1.107";
////					String a = "AT+NETP=TCP,SERVER,5566,192.168.10.1";
//
//					System.out.println(DigitalUtils.StringToAsciiBytes(a));
//					byte[] tempByte = DigitalUtils.AsciiBytesTo645Bytes(DigitalUtils.StringToAsciiBytes(a));
//					String temp = "";
//					for (int i = 0; i < tempByte.length; i++) {
//						temp += Integer.toHexString(tempByte[i] & 0xFF) + " ";
//					}
//
//					System.out.println(temp);
//					
//				DigitalUtils.StringToAsciiBytes("FE FE FE FE 68 AA AA AA AA AA AA 68 13 00 DF 16");
//				byte[] input = new byte[] { (byte) 0xFE, (byte) 0xFE, (byte) 0xFE, (byte) 0xFE, 0x68, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA,
//						0x68, 0x13, 0x00, (byte) 0xDF, 0x16 };
//				// SocketUtils.sendMsgWithTCPSocket("192.168.1.109", 9000,
//				// input);
//				System.out.println("***************");
//				// SocketUtils.getUDPReceived(8080, handler);
//				SocketUtils.sendMsgWithUDPSocket("192.168.1.109", 9000, 8080, input, handler);
			}
		});

		btnSend.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {

				// sendMessage(editSend.getText().toString());
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

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			String result = "";
			String temp = msg.obj.toString();
			for (int i = 0; i < temp.length(); i++) {
				int a = temp.charAt(i);
				result += Integer.toHexString(a) + " ";
			}
			textView.setText(temp);
//			textView.setText(DigitalUtils.StringToAsciiString(temp));
		}
	};
}