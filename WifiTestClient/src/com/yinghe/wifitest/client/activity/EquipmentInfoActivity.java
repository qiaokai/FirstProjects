package com.yinghe.wifitest.client.activity;

import com.example.wifitest01.R;
import com.yinghe.wifitest.client.entity.EquipmentList;
import com.yinghe.wifitest.client.entity.MsgTag;
import com.yinghe.wifitest.client.manager.EquipmentManager;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class EquipmentInfoActivity extends Activity implements OnClickListener {

	private static TextView equipmentId;
	private static TextView equipmentName;
	private static TextView equipmentState;
	private static TextView equipmentVoltage;
	private static TextView equipmentElectricity;
	private static TextView currentQuantity;
	static int posion = -99;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_equipment);

		posion = getIntent().getIntExtra("posion", -1);

		initView();
		upDateText();

		EquipmentManager.getEquipmentInfoByMina(EquipmentList.Instance().get(posion).getIP(), MsgTag.getEquipmentId, handler);
		TextView a = (TextView) findViewById(R.id.text_timer_setting);
		a.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void initView() {
		equipmentId = (TextView) findViewById(R.id.text_equipmentId);
		equipmentName = (TextView) findViewById(R.id.text_equipmentName);
		equipmentState = (TextView) findViewById(R.id.text_equipmentState);
		equipmentVoltage = (TextView) findViewById(R.id.text_currentVoltage);
		equipmentElectricity = (TextView) findViewById(R.id.text_currentElectric);
		currentQuantity = (TextView) findViewById(R.id.text_currentQuantity);

		equipmentId.setOnClickListener(this);
		equipmentName.setOnClickListener(this);
		equipmentState.setOnClickListener(this);
		equipmentVoltage.setOnClickListener(this);
		equipmentElectricity.setOnClickListener(this);
		currentQuantity.setOnClickListener(this);

	}

	private static void upDateText() {
		if (posion > -1) {
			if (EquipmentList.Instance().get(posion).IsOpened()) {
				equipmentState.setText("设备已开启");
			} else {
				equipmentState.setText("设备已关闭");
			}

			equipmentId.setText(EquipmentList.Instance().get(posion).getId());
			equipmentName.setText(EquipmentList.Instance().get(posion).getName());
			equipmentVoltage.setText(EquipmentList.Instance().get(posion).getVoltage());
			equipmentElectricity.setText(EquipmentList.Instance().get(posion).getElectricity());
			currentQuantity.setText(EquipmentList.Instance().get(posion).getCurrentQuantity());
		}
	}

	static Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == MsgTag.getEquipmentId && msg.arg1 == MsgTag.success) {
				EquipmentManager.getEquipmentInfoByMina(EquipmentList.Instance().get(posion).getIP(), MsgTag.getEquipmentVoltage, handler);
			} else if (msg.what == MsgTag.getEquipmentVoltage && msg.arg1 == MsgTag.success) {
				EquipmentManager.getEquipmentInfoByMina(EquipmentList.Instance().get(posion).getIP(), MsgTag.getEquipmentElectric, handler);
			} else if (msg.what == MsgTag.getEquipmentElectric && msg.arg1 == MsgTag.success) {
				EquipmentManager.getEquipmentInfoByMina(EquipmentList.Instance().get(posion).getIP(), MsgTag.getCurrentQuantity, handler);
			}
			upDateText();
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.text_equipmentId:
			EquipmentManager.getEquipmentInfoByMina(EquipmentList.Instance().get(posion).getIP(), MsgTag.getEquipmentId, handler);
			break;
		case R.id.text_equipmentName:

			break;
		case R.id.text_equipmentState:
			EquipmentManager.getEquipmentInfoByMina(EquipmentList.Instance().get(posion).getIP(), MsgTag.getEquipmentState, handler);
			break;
		case R.id.text_currentVoltage:
			EquipmentManager.getEquipmentInfoByMina(EquipmentList.Instance().get(posion).getIP(), MsgTag.getEquipmentVoltage, handler);
			break;
		case R.id.text_currentElectric:
			EquipmentManager.getEquipmentInfoByMina(EquipmentList.Instance().get(posion).getIP(), MsgTag.getEquipmentElectric, handler);
			break;
		case R.id.text_currentQuantity:
			EquipmentManager.getEquipmentInfoByMina(EquipmentList.Instance().get(posion).getIP(), MsgTag.getCurrentQuantity, handler);
			break;
		default:
			break;
		}

	}
}
