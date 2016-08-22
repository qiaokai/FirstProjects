package com.yinghe.wifitest.client.utils.codeutil;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

public class BufferDecoder implements ProtocolDecoder {

	public void decode(IoSession paramIoSession, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
		byte[] b = new byte[in.limit()];
		in.get(b);
		out.write(new String(b));
	}

	@Override
	public void dispose(IoSession arg0) throws Exception {

	}

	@Override
	public void finishDecode(IoSession arg0, ProtocolDecoderOutput arg1) throws Exception {

	}
}
