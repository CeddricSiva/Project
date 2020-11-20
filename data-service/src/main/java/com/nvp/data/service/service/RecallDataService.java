package com.nvp.data.service.service;

import java.util.List;

import com.nvp.data.service.bean.RecallResponseBean;
import com.nvp.data.service.request.bean.VinRequestBean;

public interface RecallDataService {

	public List<RecallResponseBean> getRecallData(VinRequestBean requestBean);
	
}
