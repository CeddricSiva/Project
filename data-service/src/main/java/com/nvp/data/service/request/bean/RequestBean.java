package com.nvp.data.service.request.bean;

import java.io.Serializable;
import java.util.List;

public class RequestBean implements Serializable{

	/**
	 * 
	 */	
	private static final long serialVersionUID = 1L;
	
	private String prtitn;
	private String vehicleSan;
	private String vinLast8;
	private String vinFirst9;
	private String dealerCode;
	private String market;
	private String zone;
	private String modelYear;
	private String state;
	private String famCode;
	private String languageCode; 
	private String dealerLanguage;
	private List<String> salesCodeOtC;
	private List<String> salesCodeDtoJ;
	private List<String> salesCodeKtoP;
	private List<String> salesCodeQtoZ;
	private List<String> salesCode0to9;
	
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
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public String getMarket() {
		return market;
	}
	public void setMarket(String market) {
		this.market = market;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public String getModelYear() {
		return modelYear;
	}
	public void setModelYear(String modelYear) {
		this.modelYear = modelYear;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getFamCode() {
		return famCode;
	}
	public void setFamCode(String famCode) {
		this.famCode = famCode;
	}
	public String getLanguageCode() {
		return languageCode;
	}
	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}
	public String getDealerLanguage() {
		return dealerLanguage;
	}
	public void setDealerLanguage(String dealerLanguage) {
		this.dealerLanguage = dealerLanguage;
	}
	public final List<String> getSalesCodeOtC() {
		return salesCodeOtC;
	}
	public final void setSalesCodeOtC(List<String> salesCodeOtC) {
		this.salesCodeOtC = salesCodeOtC;
	}
	public final List<String> getSalesCodeDtoJ() {
		return salesCodeDtoJ;
	}
	public final void setSalesCodeDtoJ(List<String> salesCodeDtoJ) {
		this.salesCodeDtoJ = salesCodeDtoJ;
	}
	public final List<String> getSalesCodeKtoP() {
		return salesCodeKtoP;
	}
	public final void setSalesCodeKtoP(List<String> salesCodeKtoP) {
		this.salesCodeKtoP = salesCodeKtoP;
	}
	public final List<String> getSalesCodeQtoZ() {
		return salesCodeQtoZ;
	}
	public final void setSalesCodeQtoZ(List<String> salesCodeQtoZ) {
		this.salesCodeQtoZ = salesCodeQtoZ;
	}
	public final List<String> getSalesCode0to9() {
		return salesCode0to9;
	}
	public final void setSalesCode0to9(List<String> salesCode0to9) {
		this.salesCode0to9 = salesCode0to9;
	}
}
