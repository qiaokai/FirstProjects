package com.yinghe.wifitest.client.entity;

public class EquipmentInfo {
	private String IP;// 当前IP
	private String Id;// 设备编号
	private String Name;// 设备名称
	private String State;// 设备状态
	private String Voltage;// 当前设备电压
	private String Electricity;// 当前设备电流
	private int lastElectricQuantity;// 上次电量
	private int currentElectricQuantity;// 当前电量
	private int maxElectricQuantity;// 最大电量
	private int availableQuantity;// 可用电量

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

	public String getState() {
		return State;
	}

	public void setState(String state) {
		State = state;
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

	public int getLastElectricQuantity() {
		return lastElectricQuantity;
	}

	public void setLastElectricQuantity(int lastElectricQuantity) {
		this.lastElectricQuantity = lastElectricQuantity;
	}

	public int getCurrentElectricQuantity() {
		return currentElectricQuantity;
	}

	public void setCurrentElectricQuantity(int currentElectricQuantity) {
		this.currentElectricQuantity = currentElectricQuantity;
	}

	public int getMaxElectricQuantity() {
		return maxElectricQuantity;
	}

	public void setMaxElectricQuantity(int maxElectricQuantity) {
		this.maxElectricQuantity = maxElectricQuantity;
	}

	public int getAvailableQuantity() {
		return availableQuantity;
	}

	public void setAvailableQuantity(int availableQuantity) {
		this.availableQuantity = availableQuantity;
	}

}
