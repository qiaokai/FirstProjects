package com.yinghe.wifitest.client.entity;

public class MsgTag {
	public static final int success = 0;
	public static final int timeOut = -99;
	public static final int using = -100;
	public static final int fail = -101;

	public static final int Msg_GetWeather = 0x01;
	public static final int Msg_GetPosition = 0x02;
	public static final int Msg_GetScanInfoSuccess = 0x03;

	public static final int Request_GetWeather = 0x04;
	public static final int Result_GetWeatherSuccess = 0x05;

	public static final int searchEquipment = 0x10;
	public static final int checkTCPConnect = 0x11;
	public static final int buildTCPConnect = 0x12;
	public static final int testTCPConnect = 0x13;

	public static final int getEquipmentId = 0x20;
	public static final int getEquipmentState = 0x21;
	public static final int getEquipmentVoltage = 0x22;
	public static final int getEquipmentElectric = 0x23;
	public static final int getCurrentQuantity = 0x24;

	public static final int closeEquipment = 0x30;
	public static final int openEquipment = 0x31;
}
