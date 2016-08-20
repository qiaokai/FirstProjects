package com.yinghe.wifitest.client.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
import com.example.wifitest01.R;
import com.yinghe.wifitest.client.entity.MsgTag;
import com.yinghe.wifitest.client.entity.TempEquipmentIpList;
import com.yinghe.wifitest.client.manager.EquipmentManager;

public class EquipmentSetActivity extends Activity implements OnClickListener {

	TextView searchEquipment;
	TextView checkTCPConnect;
	TextView buildTCPConnect;
	TextView testTCPConnect;
	TextView waitConnectSuccess;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_equipment_setting);

		initView();

		EquipmentManager.searchEquipment(handler);
		searchEquipment.setEnabled(true);

	}

	private void initView() {
		searchEquipment = (TextView) findViewById(R.id.text_searchEquipment);
		checkTCPConnect = (TextView) findViewById(R.id.text_checkTCPConnect);
		buildTCPConnect = (TextView) findViewById(R.id.text_buildTCPConnect);
		testTCPConnect = (TextView) findViewById(R.id.text_testTCPConnect);
		waitConnectSuccess = (TextView) findViewById(R.id.text_waitConnectSuccess);

		searchEquipment.setOnClickListener(this);
		checkTCPConnect.setOnClickListener(this);
		buildTCPConnect.setOnClickListener(this);
		testTCPConnect.setOnClickListener(this);
		waitConnectSuccess.setOnClickListener(this);

	}

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		@SuppressWarnings("static-access")
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case MsgTag.GetEquipmentIp:
				String equipmentIp = (String) msg.obj;
				if (msg.arg1 == MsgTag.success) {
					searchEquipment.setSelected(true);
					checkTCPConnect.setEnabled(true);
					checkTCPConnect.setSelected(true);
					EquipmentManager.checkTCPConnect(equipmentIp, handler);
				} else {
					Toast.makeText(getApplicationContext(), "未搜索到新增设备", Toast.LENGTH_SHORT).show();
				}
				break;
			case MsgTag.checkTCPConnect:
				String checkedIp = (String) msg.obj;
				if (msg.arg1 == MsgTag.success) {
					TempEquipmentIpList.Instance().remove(checkedIp);
					EquipmentManager.addEquipment(checkedIp);
					if (TempEquipmentIpList.Instance().isEmpty()) {
						buildTCPConnect.setSelected(true);
						testTCPConnect.setSelected(true);
						waitConnectSuccess.setSelected(true);
						finish();
					}
				} else if (!TextUtils.isEmpty(checkedIp)) {
					buildTCPConnect.setEnabled(true);
					EquipmentManager.buildTCPConnect(checkedIp, handler);
				}
				break;
			case MsgTag.buildTCPConnect:
				buildTCPConnect.setSelected(true);
				if (msg.arg1 == MsgTag.success) {
					testTCPConnect.setEnabled(true);
					EquipmentManager.testTcpConnect(msg.obj.toString(), handler);
				}
				break;
			case MsgTag.testTCPConnect:
				String testedIp = msg.obj.toString();
				buildTCPConnect.setSelected(true);
				testTCPConnect.setSelected(true);
				waitConnectSuccess.setEnabled(true);
				if (msg.arg1 == MsgTag.success) {
					waitConnectSuccess.setSelected(true);
					TempEquipmentIpList.Instance().remove(testedIp);
					EquipmentManager.addEquipment(testedIp);
					if (TempEquipmentIpList.Instance().isEmpty()) {
						finish();
					}
				} else {
					for (int i = 0; i < 3; i++) {
						try {
							new Thread().sleep(1000);
							for (String IP : TempEquipmentIpList.Instance()) {
								EquipmentManager.testTcpConnect(IP, handler);
							}
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					finish();
				}
				break;
			default:
				Toast.makeText(getApplicationContext(), msg.obj.toString(), Toast.LENGTH_SHORT).show();
				break;
			}

		}

	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.text_searchEquipment:
			EquipmentManager.searchEquipment(handler);
			break;
		case R.id.text_checkTCPConnect:
			for (String IP : TempEquipmentIpList.Instance()) {
				EquipmentManager.checkTCPConnect(IP, handler);
			}
			break;
		case R.id.text_buildTCPConnect:
			for (String IP : TempEquipmentIpList.Instance()) {
				EquipmentManager.buildTCPConnect(IP, handler);
			}
			break;
		case R.id.text_testTCPConnect:
			break;
		case R.id.text_waitConnectSuccess:
			for (String IP : TempEquipmentIpList.Instance()) {
				EquipmentManager.testTcpConnect(IP, handler);
			}
			break;

		default:
			break;
		}

	}
}
