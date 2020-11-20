package com.nvp.data.service.bean;

import java.io.Serializable;

public class VinFull17RequestBean implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String vin;
	private String market;
	private String zone;
	private String dealerCode;
	
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

	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	@Override
	public String toString() {
		return "VinFull17RequestBean [vin=" + vin + ", market=" + market + ", zone=" + zone + ", dealerCode="
				+ dealerCode + "]";
	}

}
