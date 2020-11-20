package com.nvp.data.service.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;

/**
 * Custom ServletOutputStream to write Jwt signed response in
 * ServletOutputStream
 *
 */
public class ByteArrayPrinter {

	private ByteArrayOutputStream baos = new ByteArrayOutputStream();

	private PrintWriter pw = new PrintWriter(baos);

	private ServletOutputStream sos = new ByteArrayServletStream(baos);

	public PrintWriter getWriter() {
		return pw;
	}

	public ServletOutputStream getStream() {
		return sos;
	}

	public byte[] toByteArray() {
		return baos.toByteArray();
	}
}
