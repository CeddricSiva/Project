package com.nvp.data.service.service;

import java.util.List;

import com.nvp.data.service.bean.WiTechDealerRequestBean;
import com.nvp.data.service.bean.WiTechDealerResponseBean;

public interface WiTechDealerEnrollService {

	List<WiTechDealerResponseBean> getDealerEnrollment(WiTechDealerRequestBean requestBean);
}
