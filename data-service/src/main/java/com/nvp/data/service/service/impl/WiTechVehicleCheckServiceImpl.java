package com.nvp.data.service.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;

import com.nvp.data.service.bean.VehicleCheckDTO;
import com.nvp.data.service.bean.WiTechVehicleRequestBean;
import com.nvp.data.service.bean.WiTechVehicleResponseBean;
import com.nvp.data.service.config.DataServiceConfig;
import com.nvp.data.service.service.WiTechVehicleCheckService;
import com.nvp.data.service.util.Constants;

@Service
public class WiTechVehicleCheckServiceImpl implements WiTechVehicleCheckService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	DataServiceConfig dataServiceConfig;

	private static final Logger logger = LoggerFactory.getLogger(WiTechVehicleCheckServiceImpl.class);

	@Override
	public WiTechVehicleResponseBean isWiTechVehicle(WiTechVehicleRequestBean requestBean) {
		logger.info("Inside isWiTechVehicle");
		WiTechVehicleResponseBean responseBean = new WiTechVehicleResponseBean();
		String query = dataServiceConfig.getPropertyData(Constants.WITECH_VEHICLE_CHECK_QUERY);
		Object[] parameters = { requestBean.getMarket(), requestBean.getModelYear(), requestBean.getModelYear(),
				requestBean.getFamilyCode(), requestBean.getSalesCode(), requestBean.getZone(),
				requestBean.getDealerCode() };
		List<VehicleCheckDTO> dataList = jdbcTemplate.query(query, parameters,
				new ResultSetExtractor<List<VehicleCheckDTO>>() {
					@Override
					public List<VehicleCheckDTO> extractData(ResultSet rs) throws SQLException {
						List<VehicleCheckDTO> dtoList = new ArrayList<>();
						while (rs.next()) {
							VehicleCheckDTO dto = new VehicleCheckDTO();
							dto.setFromYear(rs.getString(1));
							dto.setToYear(rs.getString(2));
							dto.setMarket(rs.getString(3));
							dto.setAdditionalInfo(rs.getString(4));
							dtoList.add(dto);
						}
						return dtoList;
					}
				});

		if (dataList != null && !dataList.isEmpty()) {
			for (VehicleCheckDTO dto : dataList) {
				if (dto.getFromYear() != null && ("*".equals(dto.getFromYear().trim())
						|| requestBean.getModelYear().equals(dto.getFromYear())
						|| (!"*".equals(dto.getToYear().trim())
								&& (Integer.parseInt(requestBean.getModelYear()) >= Integer.parseInt(dto.getFromYear())
										&& Integer.parseInt(requestBean.getModelYear()) <= Integer
												.parseInt(dto.getToYear()))))) {
					responseBean.setAdditionalInfo(dto.getAdditionalInfo());
					responseBean.setIsWiTechVehicle(true);
					break;
				}
			}
		}
		logger.info("Exit isWiTechVehicle");
		return responseBean;
	}

}
