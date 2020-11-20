package com.nvp.data.service.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;

/**
 * Custom ServletOutputStream to write Jwt signed response in
 * ServletOutputStream
 *
 */
public class ByteArrayServletStream extends ServletOutputStream {
	ByteArrayOutputStream baos;

	ByteArrayServletStream(ByteArrayOutputStream baos) {
		this.baos = baos;
	}

	@Override
	public boolean isReady() {
		return false;
	}

	@Override
	public void setWriteListener(WriteListener listener) {
		// default implementation ignored

	}

	@Override
	public void write(int param) throws IOException {
		baos.write(param);
	}

}
