package com.yinghe.wifitest.client.entity;

public class CommandInfo {
	public static final byte[] getEquipmentId = new byte[] { (byte) 0xFE, (byte) 0xFE, (byte) 0xFE, (byte) 0xFE, 0x68, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA,
			(byte) 0xAA, (byte) 0xAA, 0x68, 0x13, 0x00, (byte) 0xDF, 0x16 };
	
	public static String openEquipment = "openEquipment";
	public static String closeEquipmentId = "closeEquipmentId";
	public static String getEquipmentState = "getEquipmentState";
	public static String getCurrentVoltage = "getCurrentVoltage";
	public static String getCurrentElectric = "getCurrentElectric";
	public static String getLastElectricQuantity = "getLastElectricQuantity";
	public static String getCurrentElectricQuantity = "getCurrentElectricQuantity";
	public static String getMaxElectricQuantity = "getMaxElectricQuantity";

}
