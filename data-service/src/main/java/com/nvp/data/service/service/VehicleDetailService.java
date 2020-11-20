package com.nvp.data.service.service;

import com.nvp.data.service.bean.VehicleResponseBean;
import com.nvp.data.service.bean.VinFull17RequestBean;

public interface VehicleDetailService {

	VehicleResponseBean getVehicleDetails(VinFull17RequestBean requestBean);
}
