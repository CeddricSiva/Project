package com.nvp.data.service.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class CheckListResponseBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Map<Integer, List<ParmHeaderDTO>> header;
	private Map<Integer, List<ParmTextDTO>> parmText;
	private List<String> specialMessage;
	private String flagDetails;
	
	public final Map<Integer, List<ParmHeaderDTO>> getHeader() {
		return header;
	}
	public final void setHeader(Map<Integer, List<ParmHeaderDTO>> header) {
		this.header = header;
	}
	public final Map<Integer, List<ParmTextDTO>> getParmText() {
		return parmText;
	}
	public final void setParmText(Map<Integer, List<ParmTextDTO>> parmText) {
		this.parmText = parmText;
	}
	public final List<String> getSpecialMessage() {
		return specialMessage;
	}
	public final void setSpecialMessage(List<String> specialMessage) {
		this.specialMessage = specialMessage;
	}
	public final String getFlagDetails() {
		return flagDetails;
	}
	public final void setFlagDetails(String flagDetails) {
		this.flagDetails = flagDetails;
	}
}
