package com.nvp.data.service.bean;

import java.io.Serializable;

public class ParmDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String groupID;
	private int itemOrderNum;
	private String itemType;
	private String text;
	private String salescode;
	
	private String masterParmId;
	private String marketCode;
	private String zone;
	private String language;
	
	private String vhclSalesOtC;
	private String vhclSalesDj;
	private String vhclSalesKp;
	private String vhclSalesQz;
	private String vhclSales09;
	
	public final String getGroupID() {
		return groupID;
	}
	public final void setGroupID(String groupID) {
		this.groupID = groupID;
	}
	public final int getItemOrderNum() {
		return itemOrderNum;
	}
	public final void setItemOrderNum(int itemOrderNum) {
		this.itemOrderNum = itemOrderNum;
	}
	public final String getItemType() {
		return itemType;
	}
	public final void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public final String getText() {
		return text;
	}
	public final void setText(String text) {
		this.text = text;
	}
	public final String getSalescode() {
		return salescode;
	}
	public final void setSalescode(String salescode) {
		this.salescode = salescode;
	}
	public final String getMasterParmId() {
		return masterParmId;
	}
	public final void setMasterParmId(String masterParmId) {
		this.masterParmId = masterParmId;
	}
	public final String getMarketCode() {
		return marketCode;
	}
	public final void setMarketCode(String marketCode) {
		this.marketCode = marketCode;
	}
	public final String getZone() {
		return zone;
	}
	public final void setZone(String zone) {
		this.zone = zone;
	}
	public final String getLanguage() {
		return language;
	}
	public final void setLanguage(String language) {
		this.language = language;
	}
	public final String getVhclSalesOtC() {
		return vhclSalesOtC;
	}
	public final void setVhclSalesOtC(String vhclSalesOtC) {
		this.vhclSalesOtC = vhclSalesOtC;
	}
	public final String getVhclSalesDj() {
		return vhclSalesDj;
	}
	public final void setVhclSalesDj(String vhclSalesDj) {
		this.vhclSalesDj = vhclSalesDj;
	}
	public final String getVhclSalesKp() {
		return vhclSalesKp;
	}
	public final void setVhclSalesKp(String vhclSalesKp) {
		this.vhclSalesKp = vhclSalesKp;
	}
	public final String getVhclSalesQz() {
		return vhclSalesQz;
	}
	public final void setVhclSalesQz(String vhclSalesQz) {
		this.vhclSalesQz = vhclSalesQz;
	}
	public final String getVhclSales09() {
		return vhclSales09;
	}
	public final void setVhclSales09(String vhclSales09) {
		this.vhclSales09 = vhclSales09;
	}	
}
