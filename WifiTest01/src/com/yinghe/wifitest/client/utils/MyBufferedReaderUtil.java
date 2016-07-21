package com.yinghe.wifitest.client.utils;

import java.io.FileReader;
import java.io.IOException;

public class MyBufferedReaderUtil {
	private FileReader fr;
	private char[] buf = new char[1024];
	private int count = 0;
	private int pos = 0;

	public MyBufferedReaderUtil(FileReader f) {
		this.fr = f;
	}

	public int myRead() throws IOException {
		if (count == 0) {
			count = fr.read(buf);
			pos = 0;
		}
		if (count < 0)
			return -1;
		int ch = buf[pos++];
		count--;
		return ch;
	}

	public String myReadLine() throws IOException {
		StringBuilder sb = new StringBuilder();
		int ch = 0;
		while ((ch = myRead()) != -1) {
			if (ch == '\r')
				continue;
			if (ch == '\n')
				return sb.toString();
			sb.append((char) ch);
			if (count == 0)
				return sb.toString();
		}
		return null;
	}

	public void myClose() throws IOException {
		fr.close();
	}
}
