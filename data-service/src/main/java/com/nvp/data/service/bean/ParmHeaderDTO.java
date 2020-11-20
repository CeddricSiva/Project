package com.nvp.data.service.bean;

import java.io.Serializable;

public class ParmHeaderDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String headerData;
	private int orderNum;
	
	public final String getHeaderData() {
		return headerData;
	}
	public final void setHeaderData(String headerData) {
		this.headerData = headerData;
	}
	public final int getOrderNum() {
		return orderNum;
	}
	public final void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}
}
