package com.nvp.data.service.service;

import com.nvp.data.service.bean.CheckListResponseBean;
import com.nvp.data.service.request.bean.RequestBean;

public interface CheckListDataService {

	CheckListResponseBean fetchCheckListData(RequestBean requestBean);
}
