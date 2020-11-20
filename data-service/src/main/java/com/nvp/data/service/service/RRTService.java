package com.nvp.data.service.service;

import java.util.List;

import com.nvp.data.service.bean.RRTResponseBean;
import com.nvp.data.service.request.bean.VinRequestBean;

public interface RRTService {
	
	public List<RRTResponseBean> fetchRRTDetails(VinRequestBean requestBean);
}
