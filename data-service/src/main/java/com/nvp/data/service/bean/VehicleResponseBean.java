package com.nvp.data.service.bean;

import java.io.Serializable;

public class VehicleResponseBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String prtitn;
	private String vehicleSan;
	private String familyCode;
	private String modelYear;
	private String makeYear;
	private String vinLast8;
	private String vinFirst9;
	private String vehicleSoldDate;
	private String line;
	private String series;
	private String bodyStyle;
	
	public String getPrtitn() {
		return prtitn;
	}
	public void setPrtitn(String prtitn) {
		this.prtitn = prtitn;
	}
	public String getVehicleSan() {
		return vehicleSan;
	}
	public void setVehicleSan(String vehicleSan) {
		this.vehicleSan = vehicleSan;
	}
	public String getFamilyCode() {
		return familyCode;
	}
	public void setFamilyCode(String familyCode) {
		this.familyCode = familyCode;
	}
	public String getModelYear() {
		return modelYear;
	}
	public void setModelYear(String modelYear) {
		this.modelYear = modelYear;
	}
	public String getMakeYear() {
		return makeYear;
	}
	public void setMakeYear(String makeYear) {
		this.makeYear = makeYear;
	}
	public String getVinLast8() {
		return vinLast8;
	}
	public void setVinLast8(String vinLast8) {
		this.vinLast8 = vinLast8;
	}
	public String getVinFirst9() {
		return vinFirst9;
	}
	public void setVinFirst9(String vinFirst9) {
		this.vinFirst9 = vinFirst9;
	}
	public String getVehicleSoldDate() {
		return vehicleSoldDate;
	}
	public void setVehicleSoldDate(String vehicleSoldDate) {
		this.vehicleSoldDate = vehicleSoldDate;
	}
	public String getLine() {
		return line;
	}
	public void setLine(String line) {
		this.line = line;
	}
	public String getSeries() {
		return series;
	}
	public void setSeries(String series) {
		this.series = series;
	}
	public String getBodyStyle() {
		return bodyStyle;
	}
	public void setBodyStyle(String bodyStyle) {
		this.bodyStyle = bodyStyle;
	}
	
}
