package com.nvp.data.service.service;

import com.nvp.data.service.bean.WiTechVehicleRequestBean;
import com.nvp.data.service.bean.WiTechVehicleResponseBean;

public interface WiTechVehicleCheckService {

	WiTechVehicleResponseBean isWiTechVehicle(WiTechVehicleRequestBean requestBean);
}
