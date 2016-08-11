package com.yinghe.wifitest.services.utils;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class BufferEncoder implements ProtocolEncoder {

	public void encode(IoSession session, Object in, ProtocolEncoderOutput out) throws Exception {
		if (in instanceof String) {
			byte[] bytes = ((String) in).getBytes("utf-8");
			IoBuffer buffer = IoBuffer.allocate(bytes.length);
			buffer.put(bytes, 0, bytes.length);
			buffer.flip();
			session.write(buffer);
			buffer.free();
		} else {
			System.out.println("message is not a String instance.");
		}
	}

	public void dispose(IoSession paramIoSession) throws Exception {

	}
}