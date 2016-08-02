package com.yinghe.wifitest.client.entity;

public class MsgTag {
	public static final int success = 0;
	public static final int fail = -1;

	public static final int Msg_GetWeather = 0x01;
	public static final int Msg_GetPosition = 0x02;
	public static final int Msg_GetEquipmentId = 0x03;
	public static final int Msg_GetEquipmentVoltage = 0x04;
	public static final int Msg_GetEquipmentElectric = 0x05;
	public static final int Msg_GetScanInfoSuccess = 0x06;

	public static final int Request_GetWeather = 0x07;
	public static final int Result_GetWeatherSuccess = 0x08;
}
