package com.yinghe.wifitest.client.entity;

public class MsgTag {
	public static final int success = 0;
	public static final int fail = -1;

	public static final int Msg_GetWeather = 0x01;
	public static final int Msg_GetPosition = 0x02;
	public static final int Msg_GetEquipmentId = 0x03;
	public static final int GetEquipmentIp = 0x04;
	public static final int Msg_GetEquipmentVoltage = 0x05;
	public static final int Msg_GetEquipmentElectric = 0x06;
	public static final int Msg_GetScanInfoSuccess = 0x07;
	public static final int buildTCPConnect = 0x11;
	public static final int Request_GetWeather = 0x08;
	public static final int Result_GetWeatherSuccess = 0x09;

	public static final int checkTCPConnect = 0x12;
	public static final int testTCPConnect = 0x13;

	public static final int closeEquipment = 0x14;
	public static final int openEquipment = 0x15;
	public static final int updateEquipmentInfo = 0x16;
}
