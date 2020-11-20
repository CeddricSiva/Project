package com.nvp.data.service.service;

import com.nvp.data.service.bean.DealerRequestBean;
import com.nvp.data.service.bean.DealerResponseBean;

public interface DealerService {

	DealerResponseBean getDealerInfo(DealerRequestBean requestBean);
}
