package com.yinghe.wifitest.services.utils;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class BufferCoderFactory implements ProtocolCodecFactory {
	private ProtocolEncoder encoder;
	private ProtocolDecoder decoder;

	@Override
	public ProtocolDecoder getDecoder(IoSession arg0) throws Exception {
		decoder = new BufferDecoder();
		return decoder;
	}

	@Override
	public ProtocolEncoder getEncoder(IoSession arg0) throws Exception {
		encoder = new BufferEncoder();
		return encoder;
	}
}