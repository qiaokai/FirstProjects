package com.yinghe.wifitest.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.AttributeKey;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class BufferEncoder implements ProtocolEncoder {
	private final AttributeKey DEFLATER = new AttributeKey(getClass(), "deflater");
	private static int buffersize = 1024;

	public void encode(IoSession session, Object in, ProtocolEncoderOutput out) throws Exception {
		if (in instanceof String) {
			byte[] bytes = ((String) in).getBytes("utf-8");
			System.out.println(in);
//			System.out.println("压缩前：" + bytes.length);
//			bytes = compress(session, bytes);
//			System.out.println("压缩后：" + bytes.length);
			IoBuffer buffer = IoBuffer.allocate(bytes.length );
//			buffer.putInt(bytes.length);
			buffer.put(bytes,0,bytes.length);
			buffer.flip();
			session.write(buffer);
			buffer.free();
		} else {
			System.out.println("message is not a String instance.");
		}
	}

	private byte[] compress(IoSession session, byte[] inputs) {
		Deflater deflater = (Deflater) session.getAttribute(DEFLATER);
		if (deflater == null) {
			deflater = new Deflater();
			session.setAttribute(DEFLATER, deflater);
		}
		deflater.reset();
		deflater.setInput(inputs);
		deflater.finish();
		byte[] outputs = new byte[0];
		ByteArrayOutputStream stream = new ByteArrayOutputStream(inputs.length);
		byte[] bytes = new byte[buffersize];
		int value;
		while (!deflater.finished()) {
			value = deflater.deflate(bytes);
			stream.write(bytes, 0, value);
		}
		outputs = stream.toByteArray();
		try {
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return outputs;
	}

	public void dispose(IoSession paramIoSession) throws Exception {

	}
}