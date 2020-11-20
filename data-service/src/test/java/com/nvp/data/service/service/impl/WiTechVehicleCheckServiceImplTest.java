package com.nvp.data.service.service.impl;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nvp.data.service.bean.VehicleCheckDTO;
import com.nvp.data.service.bean.WiTechVehicleRequestBean;
import com.nvp.data.service.bean.WiTechVehicleResponseBean;
import com.nvp.data.service.config.DataServiceConfig;
import com.nvp.data.service.config.JwtAuthenticationEntryPoint;
import com.nvp.data.service.util.Constants;
import com.nvp.data.service.util.JwtUtil;

@RunWith(SpringRunner.class)
@WebMvcTest(value = { WiTechVehicleCheckServiceImpl.class, DataServiceConfig.class })
class WiTechVehicleCheckServiceImplTest {

	@Autowired
	WiTechVehicleCheckServiceImpl wiTechVehicleCheckServiceImpl;

	@Autowired
	DataServiceConfig queryPropertyConfig;

	@Autowired
	ObjectMapper mapper;

	@MockBean
	private JdbcTemplate jdbcTemplate;

	@MockBean
	JwtUtil util;
	@MockBean
	JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@SuppressWarnings("unchecked")
	@Test
	void IsWiTechVehicleTest() {

		WiTechVehicleRequestBean requestBean = new WiTechVehicleRequestBean();
		requestBean.setFamilyCode("WL");
		requestBean.setModelYear("2018");
		requestBean.getZone();
		requestBean.getFamilyCode();
		requestBean.getMarket();
		requestBean.setFamilyCode("*");
		requestBean.setMarket("U");
		VehicleCheckDTO dto = new VehicleCheckDTO();
		dto.setFromYear("2018");
		dto.setToYear("*");
		dto.setMarket("U");
		dto.setAdditionalInfo("GS");
		List<VehicleCheckDTO> dataList = new ArrayList<VehicleCheckDTO>();
		dataList.add(dto);
		when(jdbcTemplate.query(
				ArgumentMatchers.contains(queryPropertyConfig.getPropertyData(Constants.WITECH_VEHICLE_CHECK_QUERY)),
				ArgumentMatchers.<Object[]>any(), ArgumentMatchers.<ResultSetExtractor>any())).thenReturn(dataList);
		WiTechVehicleResponseBean actual = wiTechVehicleCheckServiceImpl.isWiTechVehicle(requestBean);
		assertTrue(actual.getIsWiTechVehicle());
	}

}
