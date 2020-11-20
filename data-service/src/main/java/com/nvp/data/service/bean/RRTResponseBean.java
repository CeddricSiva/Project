package com.nvp.data.service.bean;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class RRTResponseBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String rrtNumber;
	private String rrtDescription;
	private String lop;
	private String effectiveDate;
	
	@JsonIgnore
	private String status;
	
	@JsonIgnore
	private String operationFuncGroup;
	
	@JsonIgnore
	private String operationCompGroup;
	
	@JsonIgnore
	private String laberOperationGroup;
	
	@JsonIgnore
	private String laberOperation;

	public String getRrtNumber() {
		return rrtNumber;
	}

	public void setRrtNumber(String rrtNumber) {
		this.rrtNumber = rrtNumber;
	}

	public String getRrtDescription() {
		return rrtDescription;
	}

	public void setRrtDescription(String rrtDescription) {
		this.rrtDescription = rrtDescription;
	}

	public String getLop() {
		return lop;
	}

	public void setLop(String lop) {
		this.lop = lop;
	}

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOperationFuncGroup() {
		return operationFuncGroup;
	}

	public void setOperationFuncGroup(String operationFuncGroup) {
		this.operationFuncGroup = operationFuncGroup;
	}

	public String getOperationCompGroup() {
		return operationCompGroup;
	}

	public void setOperationCompGroup(String operationCompGroup) {
		this.operationCompGroup = operationCompGroup;
	}

	public String getLaberOperationGroup() {
		return laberOperationGroup;
	}

	public void setLaberOperationGroup(String laberOperationGroup) {
		this.laberOperationGroup = laberOperationGroup;
	}

	public String getLaberOperation() {
		return laberOperation;
	}

	public void setLaberOperation(String laberOperation) {
		this.laberOperation = laberOperation;
	}

}
