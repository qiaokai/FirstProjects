package com.yinghe.wifitest.client.entity;

public class EquipmentInfo {
	private String IP;// 当前IP
	private String equipmentId;// 设备编号
	private String equipmentName;// 设备名称
	private String equipmentState;// 设备状态
	private String currentVoltage;// 当前设备电压
	private String currentElectricity;// 当前设备电流
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

	public String getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}

	public String getEquipmentName() {
		return equipmentName;
	}

	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}

	public String getEquipmentState() {
		return equipmentState;
	}

	public void setEquipmentState(String equipmentState) {
		this.equipmentState = equipmentState;
	}

	public String getCurrentVoltage() {
		return currentVoltage;
	}

	public void setCurrentVoltage(String currentVoltage) {
		this.currentVoltage = currentVoltage;
	}

	public String getCurrentElectricity() {
		return currentElectricity;
	}

	public void setCurrentElectricity(String currentElectricity) {
		this.currentElectricity = currentElectricity;
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
