package com.nvp.data.service.config.exception;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

public class GenericRestException extends RuntimeException implements Serializable{

	private static final long serialVersionUID = 1905122041950251207L;
	
	private HttpStatus httpStatus;
	private Object[] payloads;
	
	public GenericRestException(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}
	
	public GenericRestException(HttpStatus httpStatus, String message) {
		super(message);
		this.httpStatus = httpStatus;
	}
	
	public GenericRestException(HttpStatus httpStatus, String message, Object... payloads) {
		this(httpStatus, message);
		this.payloads=payloads;
	}
	
	public HttpStatus getHttpStatus() {
		return this.httpStatus;
	}
	
	public Object[] getPayloads() {
		return this.payloads;
	}
}
