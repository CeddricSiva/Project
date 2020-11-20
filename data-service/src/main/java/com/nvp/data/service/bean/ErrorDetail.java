package com.nvp.data.service.bean;

public class ErrorDetail {

	private String title;
	private int status;
	private String detail;
	private String timeStamp;
     
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String string) {
		this.timeStamp = string;
	}
}
