package com.nvp.data.service.bean;

import java.io.Serializable;

public class WiTechDealerResponseBean implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String businessCenter;
	private String effectiveStartDate;
	private String effectiveEndDate;
	private String status;
	private String authPlanMax;
	private String authPlanMin;
	
	public String getBusinessCenter() {
		return businessCenter;
	}
	public void setBusinessCenter(String businessCenter) {
		this.businessCenter = businessCenter;
	}
	public String getEffectiveStartDate() {
		return effectiveStartDate;
	}
	public void setEffectiveStartDate(String effectiveStartDate) {
		this.effectiveStartDate = effectiveStartDate;
	}
	public String getEffectiveEndDate() {
		return effectiveEndDate;
	}
	public void setEffectiveEndDate(String effectiveEndDate) {
		this.effectiveEndDate = effectiveEndDate;
	}
	public String getAuthPlanMax() {
		return authPlanMax;
	}
	public void setAuthPlanMax(String authPlanMax) {
		this.authPlanMax = authPlanMax;
	}
	public String getAuthPlanMin() {
		return authPlanMin;
	}
	public void setAuthPlanMin(String authPlanMin) {
		this.authPlanMin = authPlanMin;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
