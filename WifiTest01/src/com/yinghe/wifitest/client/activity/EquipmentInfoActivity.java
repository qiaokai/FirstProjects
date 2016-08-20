package com.yinghe.wifitest.client.activity;

import com.example.wifitest01.R;
import com.yinghe.wifitest.client.entity.EquipmentList;
import com.yinghe.wifitest.client.entity.MsgTag;
import com.yinghe.wifitest.client.manager.EquipmentManager;
import com.yinghe.wifitest.client.utils.EquipmentUtil;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class EquipmentInfoActivity extends Activity implements OnClickListener {

	private TextView equipmentId;
	private TextView equipmentName;
	private TextView equipmentState;
	private TextView equipmentVoltage;
	private TextView equipmentElectricity;
	int posion = -99;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_equipment);

		posion = getIntent().getIntExtra("posion", -1);

		initView();
		upDateText();

		updateEquipmentInfo();
		TextView a = (TextView) findViewById(R.id.text_timer_setting);
		a.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void updateEquipmentInfo() {
		EquipmentManager.getEquipmentId(EquipmentList.Instance().get(posion).getIP(), handler);

	}

	private void initView() {
		equipmentId = (TextView) findViewById(R.id.text_equipmentId);
		equipmentName = (TextView) findViewById(R.id.text_equipmentName);
		equipmentState = (TextView) findViewById(R.id.text_equipmentState);
		equipmentVoltage = (TextView) findViewById(R.id.text_currentVoltage);
		equipmentElectricity = (TextView) findViewById(R.id.text_currentElectric);

		equipmentId.setOnClickListener(this);
		equipmentName.setOnClickListener(this);
		equipmentState.setOnClickListener(this);
		equipmentVoltage.setOnClickListener(this);
		equipmentElectricity.setOnClickListener(this);

	}

	private void upDateText() {
		if (posion > 0) {
			equipmentId.setText(EquipmentList.Instance().get(posion).getId());
			equipmentName.setText(EquipmentList.Instance().get(posion).getName());
			equipmentState.setText(EquipmentList.Instance().get(posion).getState());
			equipmentVoltage.setText(EquipmentList.Instance().get(posion).getVoltage());
			equipmentElectricity.setText(EquipmentList.Instance().get(posion).getElectricity());
		}
	}

	static Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MsgTag.openEquipment:
				EquipmentManager.upDateEquipmentList(msg);
				break;
			case MsgTag.closeEquipment:
				System.out.println("closeEquipment");
				break;
			case MsgTag.buildTCPConnect:
				break;
			case MsgTag.testTCPConnect:
				break;
			default:
				break;
			}
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.text_equipmentId:

			break;
		case R.id.text_equipmentName:

			break;
		case R.id.text_equipmentState:

			break;

		case R.id.text_currentVoltage:

			break;
		case R.id.text_currentElectric:

			break;

		default:
			break;
		}

	}
}
