package com.yinghe.wifitest.services.utils;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class AppCoderFactory implements ProtocolCodecFactory {
	private ProtocolEncoder encoder;
	private ProtocolDecoder decoder;

	@Override
	public ProtocolDecoder getDecoder(IoSession arg0) throws Exception {
		decoder = new AppBufferDecoder();
		return decoder;
	}

	@Override
	public ProtocolEncoder getEncoder(IoSession arg0) throws Exception {
		encoder = new AppBufferEncoder();
		return encoder;
	}
}

class AppBufferDecoder implements ProtocolDecoder {

	public void decode(IoSession paramIoSession, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
		byte[] bytes = new byte[in.limit()];
		in.get(bytes);
		out.write(new String(bytes, "UTF-8"));
	}

	@Override
	public void dispose(IoSession arg0) throws Exception {
	}

	@Override
	public void finishDecode(IoSession arg0, ProtocolDecoderOutput arg1) throws Exception {
	}
}

class AppBufferEncoder implements ProtocolEncoder {

	public void encode(IoSession session, Object input, ProtocolEncoderOutput out) throws Exception {
		if (input instanceof String) {
			byte[] bytes = ((String) input).getBytes("utf-8");
			IoBuffer buffer = IoBuffer.allocate(bytes.length);
			buffer.put(bytes, 0, bytes.length);
			buffer.flip();
			session.write(buffer);
			buffer.free();
		}
	}

	public void dispose(IoSession paramIoSession) throws Exception {
	}
}