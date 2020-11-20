package com.nvp.data.service.bean;

import java.io.Serializable;

public class WiTechVehicleResponseBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean isWiTechVehicle;
	private String additionalInfo;

	public boolean getIsWiTechVehicle() {
		return isWiTechVehicle;
	}

	public void setIsWiTechVehicle(boolean isWiTechVehicle) {
		this.isWiTechVehicle = isWiTechVehicle;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}
}
