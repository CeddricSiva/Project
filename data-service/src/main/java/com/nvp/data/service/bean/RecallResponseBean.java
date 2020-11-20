package com.nvp.data.service.bean;

import java.io.Serializable;

public class RecallResponseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String number = "";
	private String description = "";
	private String launchDate;
	private String partNum1 = "";
	private String partNum2 = "";
	private String partNum3 = "";
	private String partType = "";
	private boolean partNumDtl05 = false;
	private boolean noPartFlag = false;
	private boolean recallFlag = false;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLaunchDate() {
		return launchDate;
	}

	public void setLaunchDate(String launchDate) {
		this.launchDate = launchDate;
	}

	public String getPartNum1() {
		return partNum1;
	}

	public void setPartNum1(String partNum1) {
		this.partNum1 = partNum1;
	}

	public String getPartNum2() {
		return partNum2;
	}

	public void setPartNum2(String partNum2) {
		this.partNum2 = partNum2;
	}

	public String getPartNum3() {
		return partNum3;
	}

	public void setPartNum3(String partNum3) {
		this.partNum3 = partNum3;
	}

	public String getPartType() {
		return partType;
	}

	public void setPartType(String partType) {
		this.partType = partType;
	}

	public boolean isPartNumDtl05() {
		return partNumDtl05;
	}

	public void setPartNumDtl05(boolean partNumDtl05) {
		this.partNumDtl05 = partNumDtl05;
	}

	public boolean isNoPartFlag() {
		return noPartFlag;
	}

	public void setNoPartFlag(boolean noPartFlag) {
		this.noPartFlag = noPartFlag;
	}

	public boolean isRecallFlag() {
		return recallFlag;
	}

	public void setRecallFlag(boolean recallFlag) {
		this.recallFlag = recallFlag;
	}
}
