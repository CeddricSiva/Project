package com.nvp.data.service.bean;

import java.io.Serializable;

public class ParmTextDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String textData;
	private int orderNum;
	private String textType;
	
	public final String getTextData() {
		return textData;
	}
	public final void setTextData(String textData) {
		this.textData = textData;
	}
	public final int getOrderNum() {
		return orderNum;
	}
	public final void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}
	public final String getTextType() {
		return textType;
	}
	public final void setTextType(String textType) {
		this.textType = textType;
	}
}
