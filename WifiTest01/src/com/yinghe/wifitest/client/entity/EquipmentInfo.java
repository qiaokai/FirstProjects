package com.yinghe.wifitest.client.entity;

import org.json.JSONException;
import org.json.JSONObject;

public class EquipmentInfo {
	private String IP;// 当前IP
	private String Id;// 设备编号
	private String Name = "WIFI智能电表";// 设备名称
	private boolean IsOpened = false;// 设备状态
	private String Voltage;// 当前设备电压
	private String Electricity = "0.0 A";// 当前设备电流
	private String lastQuantity;// 上次电量
	private String currentQuantity;// 当前电量

	public EquipmentInfo() {
		super();
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public boolean IsOpened() {
		return IsOpened;
	}

	public void IsOpened(boolean state) {
		IsOpened = state;
	}

	public String getVoltage() {
		return Voltage;
	}

	public void setVoltage(String voltage) {
		Voltage = voltage;
	}

	public String getElectricity() {
		return Electricity;
	}

	public void setElectricity(String electricity) {
		Electricity = electricity;
	}

	public String getLastQuantity() {
		return lastQuantity;
	}

	public void setLastQuantity(String lastQuantity) {
		this.lastQuantity = lastQuantity;
	}

	public String getCurrentQuantity() {
		return currentQuantity;
	}

	public void setCurrentQuantity(String currentQuantity) {
		this.currentQuantity = currentQuantity;
	}

	public EquipmentInfo(String jsonString) {
		try {
			JSONObject input = new JSONObject(jsonString);
			if (!input.isNull("IP"))
				IP = input.getString("IP");
			if (!input.isNull("Id"))
				Id = input.getString("Id");
			if (!input.isNull("Name"))
				Name = input.getString("Name");
			if (!input.isNull("State"))
				IsOpened = input.getBoolean("State");
			if (!input.isNull("Voltage"))
				Voltage = input.getString("Voltage");
			if (!input.isNull("Electricity"))
				Electricity = input.getString("Electricity");
			if (!input.isNull("lastQuantity"))
				lastQuantity = input.getString("lastQuantity");
			if (!input.isNull("currentQuantity"))
				currentQuantity = input.getString("currentQuantity");
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	@Override
	public String toString() {
		JSONObject result = new JSONObject();
		try {
			result.put("IP", IP);
			result.put("Id", Id);
			result.put("Name", Name);
			result.put("State", IsOpened);
			result.put("Voltage", Voltage);
			result.put("Electricity", Electricity);
			result.put("lastQuantity", lastQuantity);
			result.put("currentQuantity", currentQuantity);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result.toString();
	}
}
