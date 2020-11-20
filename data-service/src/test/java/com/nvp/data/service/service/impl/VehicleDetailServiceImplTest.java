package com.nvp.data.service.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nvp.data.service.bean.ParmTextDTO;
import com.nvp.data.service.bean.VehicleCheckDTO;
import com.nvp.data.service.bean.VehicleResponseBean;
import com.nvp.data.service.bean.VinFull17RequestBean;
import com.nvp.data.service.bean.WiTechDealerResponseBean;
import com.nvp.data.service.bean.WiTechVehicleResponseBean;
import com.nvp.data.service.config.DataServiceConfig;
import com.nvp.data.service.config.JwtAuthenticationEntryPoint;
import com.nvp.data.service.config.exception.GenericRestException;
import com.nvp.data.service.util.Constants;
import com.nvp.data.service.util.JwtUtil;

@RunWith(SpringRunner.class)
@WebMvcTest(value = { VehicleDetailServiceImpl.class, DataServiceConfig.class })
class VehicleDetailServiceImplTest {

	@Autowired
	VehicleDetailServiceImpl vehicleDetailServiceImpl;

	@Autowired
	DataServiceConfig dataServiceConfig;

	@Autowired
	ObjectMapper mapper;

	@MockBean
	private JdbcTemplate jdbcTemplate;

	@MockBean
	JwtUtil util;
	@MockBean
	JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@MockBean
	Connection conn;
	@MockBean
	DataSource ds;
	@MockBean
	CallableStatement cstmt;

	@SuppressWarnings("unchecked")
	@Test
	void GetVehicleDetailsTest() throws SQLException {

		VinFull17RequestBean requestBean = new VinFull17RequestBean();
		VehicleResponseBean dummy = new VehicleResponseBean();
		requestBean.setVin("1C4HJWFG2EL230779");
		VehicleResponseBean expected = new VehicleResponseBean();
		expected.setBodyStyle("74");
		expected.setFamilyCode("JK");
		dummy.getPrtitn();
		dummy.getVehicleSan();
		dummy.getFamilyCode();
		dummy.getMakeYear();
		dummy.getVinLast8();
		dummy.getVinLast8();
		dummy.getVehicleSoldDate();
		dummy.getLine();
		dummy.getSeries();
		dummy.getBodyStyle();
		expected.setLine("J");
		expected.setMakeYear("2014");
		expected.setModelYear("2014");
		expected.setPrtitn("1");
		expected.setSeries("S");
		expected.setVehicleSan("294861681");
		expected.setVehicleSoldDate("2014-03-24");
		expected.setVinFirst9("1C4HJWFG2");
		dummy.getVinFirst9();
		expected.setVinLast8("EL230779");

		List<VehicleResponseBean> vehicleDetailList = new ArrayList<VehicleResponseBean>();
		vehicleDetailList.add(expected);
		when(jdbcTemplate.query(
				ArgumentMatchers.contains(dataServiceConfig.getPropertyData(Constants.GET_VEH_FULL)),
				ArgumentMatchers.<Object[]>any(), ArgumentMatchers.<ResultSetExtractor>any()))
						.thenReturn(vehicleDetailList);

		when(cstmt.getString(9)).thenReturn("outputFirstResult");

		VehicleResponseBean actual = vehicleDetailServiceImpl.getVehicleDetails(requestBean);

		assertEquals(expected.getModelYear(), actual.getModelYear());

	}

	@SuppressWarnings("unchecked")
	@Test
	void DuplicateVinTest() throws SQLException {

		VinFull17RequestBean requestBean = new VinFull17RequestBean();

		requestBean.setVin("EL230779");
		VehicleResponseBean expected = new VehicleResponseBean();
		expected.setBodyStyle("74");
		expected.setFamilyCode("JK");
		expected.setLine("J");
		expected.setMakeYear("2014");
		expected.setModelYear("2014");
		expected.setPrtitn("1");
		expected.setSeries("S");
		expected.setVehicleSan("294861681");
		new ParmTextDTO().getOrderNum();
		new ParmTextDTO().getTextType();
		expected.setVehicleSoldDate("2014-03-24");
		new WiTechVehicleResponseBean().getAdditionalInfo();
		expected.setVinFirst9("1C4HJWFG2");
		expected.setVinLast8("EL230779" + "");
		List<VehicleResponseBean> vehicleDetailList = new ArrayList<VehicleResponseBean>();
		vehicleDetailList.add(expected);
		WiTechDealerResponseBean dummy = new WiTechDealerResponseBean();
		dummy.getEffectiveEndDate();
		dummy.getEffectiveStartDate();
		dummy.getAuthPlanMax();
		dummy.getAuthPlanMin();
		dummy.getStatus();
		when(jdbcTemplate.getDataSource()).thenReturn(ds);
		when(ds.getConnection()).thenReturn(conn);
		when(cstmt.getString(9)).thenReturn(
				"8Y4PJ2DK6B11002022011JEEP CHEROKEE PREMIUM 4X4                                                       SVEHW   I¦8Y4PL2FK7B11002022011JEEP CHEROKEE 4X4                                                               SVEHW   I¦                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         ");
		when(cstmt.getString(10)).thenReturn("0000 000");
		when(cstmt.getString(11)).thenReturn("");
		when(conn.prepareCall(Mockito.anyString())).thenReturn(cstmt);
		when(jdbcTemplate.query(
				ArgumentMatchers.contains(dataServiceConfig.getPropertyData(Constants.GET_VEH_LAST8)),
				ArgumentMatchers.<Object[]>any(), ArgumentMatchers.<ResultSetExtractor>any()))
						.thenReturn(vehicleDetailList);
		Assertions.assertThrows(GenericRestException.class,
				() -> vehicleDetailServiceImpl.getVehicleDetails(requestBean));

	}

	@SuppressWarnings("unchecked")
	@Test
	void DuplicateVinTest2() throws SQLException {

		VinFull17RequestBean requestBean = new VinFull17RequestBean();

		requestBean.setVin("EL230779");
		VehicleResponseBean expected = new VehicleResponseBean();
		expected.setBodyStyle("74");
		expected.setFamilyCode("JK");
		expected.setLine("J");
		expected.setMakeYear("2014");
		expected.setModelYear("2014");
		expected.setPrtitn("1");
		expected.setSeries("S");
		expected.setVehicleSan("294861681");
		expected.setVehicleSoldDate("2014-03-24");
		expected.setVinFirst9("1C4HJWFG2");
		expected.setVinLast8("EL230779" + "");
		List<VehicleResponseBean> vehicleDetailList = new ArrayList<VehicleResponseBean>();
		vehicleDetailList.add(expected);
		new VehicleCheckDTO().getMarket();
		new VehicleCheckDTO().getToYear();
		when(jdbcTemplate.getDataSource()).thenReturn(ds);
		when(ds.getConnection()).thenReturn(conn);
		when(cstmt.getString(10)).thenReturn(
				"8Y4PJ2DK6B11002022011JEEP CHEROKEE PREMIUM 4X4                                                       SVEHW   I¦8Y4PL2FK7B11002022011JEEP CHEROKEE 4X4                                                               SVEHW   I¦                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         ");
		when(cstmt.getString(9)).thenReturn("0000 000");
		when(cstmt.getString(11)).thenReturn("");
		when(conn.prepareCall(Mockito.anyString())).thenReturn(cstmt);
		when(jdbcTemplate.query(
				ArgumentMatchers.contains(dataServiceConfig.getPropertyData(Constants.GET_VEH_LAST8)),
				ArgumentMatchers.<Object[]>any(), ArgumentMatchers.<ResultSetExtractor>any()))
						.thenReturn(vehicleDetailList);
		Assertions.assertThrows(GenericRestException.class,
				() -> vehicleDetailServiceImpl.getVehicleDetails(requestBean));

	}
}
