package com.nvp.data.service.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nvp.data.service.bean.CheckListResponseBean;
import com.nvp.data.service.bean.DealerResponseBean;
import com.nvp.data.service.bean.ErrorDetail;
import com.nvp.data.service.bean.ParmDTO;
import com.nvp.data.service.config.DataServiceConfig;
import com.nvp.data.service.config.JwtAuthenticationEntryPoint;
import com.nvp.data.service.mapper.MasterParmDetailsExtractor;
import com.nvp.data.service.mapper.ParmDetailsMapper;
import com.nvp.data.service.request.bean.RequestBean;
import com.nvp.data.service.util.Constants;
import com.nvp.data.service.util.JwtUtil;

@RunWith(SpringRunner.class)
@WebMvcTest(value = { CheckListDataServiceImpl.class, DataServiceConfig.class })
class CheckListDataServiceImplTest {

	@Autowired
	CheckListDataServiceImpl checkListDataServiceImpl;

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
	void fetchCheckListDataTest() throws JsonProcessingException {
		RequestBean requestBean = new RequestBean();
		requestBean.setDealerCode("99970");
		requestBean.setMarket("*");
		requestBean.setZone("1C");
		requestBean.setModelYear("2011");
		requestBean.setFamCode("*");
		requestBean.setState("*");
		requestBean.setPrtitn("1");
		requestBean.setVehicleSan("91");

		when(jdbcTemplate.queryForObject(
				Mockito.eq(queryPropertyConfig.getPropertyData(Constants.DEALER_GET_LANGUAGE_QUERY)),
				Mockito.eq(String.class), Mockito.any())).thenReturn("010");

		ParmDTO expectedParm = new ParmDTO();
		expectedParm.setLanguage("010");
		expectedParm.setMasterParmId("NVC");
		expectedParm.setZone("*");
		expectedParm.setMarketCode("U");
		expectedParm.getMarketCode();
		expectedParm.getVhclSales09();
		expectedParm.getVhclSalesDj();
		expectedParm.getVhclSalesKp();
		expectedParm.getVhclSalesOtC();
		expectedParm.getVhclSalesQz();
		expectedParm.getZone();
		when(jdbcTemplate.query(
				ArgumentMatchers.contains(queryPropertyConfig.getPropertyData(Constants.PARM_GET_MASTERPARM_QUERY)),
				ArgumentMatchers.<Object[]>any(), ArgumentMatchers.<MasterParmDetailsExtractor>any()))
						.thenReturn(expectedParm);

		when(jdbcTemplate.queryForObject(
				Mockito.eq(queryPropertyConfig.getPropertyData(Constants.PARM_LANGUAGE_CODE_PARM_QUERY)),
				Mockito.eq(String.class), Mockito.any())).thenReturn("en_US");

		List<ParmDTO> expected = new ArrayList<>();
		expectedParm.setItemOrderNum(105);
		expectedParm.setSalescode("GXD");
		expectedParm.setGroupID("6");
		expectedParm.setItemType("C");
		expectedParm.setText("Remote Proximity");
		expected.add(expectedParm);
		when(jdbcTemplate.query(
				ArgumentMatchers.contains(queryPropertyConfig.getPropertyData(Constants.PARM_GET_PARM_QUERY)),
				ArgumentMatchers.<Object[]>any(), ArgumentMatchers.<ParmDetailsMapper>any())).thenReturn(expected);
		Map<String, List<String>> salesCode = new HashMap<>();
		List<String> salesCodeOtc = new ArrayList<>();
		salesCodeOtc.add("*M5 -D5 ADBPADCPAPASARNPAWHPBARSBCESBGKSCADPCFNSCGTSCKDPCKNSCKTPCKVPCLEPCSASCSCPCSRPCUFP");
		List<String> salesCodeDj = new ArrayList<>();
		salesCodeDj.add("DGB");
		salesCodeDj.add("GXD");
		List<String> salesCodeKp = new ArrayList<>();
		salesCodeKp.add("KBXPKGESKRBSKTBSK4APK5APLAGPLBBSLBCSLBDSLCDSLDASLDBSLMBSMFZPMHASMWGPNAASNF5SNHMPPW7 ");
		List<String> salesCodeQz = new ArrayList<>();
		salesCodeQz.add(
				"QW7SRAACRASPRCDPSBASSCGPSFAPSGASSUAPTBB TBLSTRN TZAPWJ5PWLYCXEVSYAAAYALAYBBAYEPAYGGAZJQPZTFPZ6AS");
		List<String> salesCode09 = new ArrayList<>();
		salesCodeQz.add("1ASS115S2TEA26E 3AGA3VRA4AS 4BAA4HA 4M1P4XBA4XZ 4ZD ");
		salesCode.put(Constants.SALESCODEOTC, salesCodeOtc);
		salesCode.put(Constants.SALESCODEDJ, salesCodeDj);
		salesCode.put(Constants.SALESCODEKP, salesCodeKp);
		salesCode.put(Constants.SALESCODEQZ, salesCodeQz);
		salesCode.put(Constants.SALESCODE09, salesCode09);
		when(jdbcTemplate.query(
				ArgumentMatchers.contains(queryPropertyConfig.getPropertyData(Constants.SALES_CODE_QUERY)),
				ArgumentMatchers.<Object[]>any(), ArgumentMatchers.<ResultSetExtractor>any())).thenReturn(salesCode);
		CheckListResponseBean actual = checkListDataServiceImpl.fetchCheckListData(requestBean);
		assertEquals(expectedParm.getText(), actual.getParmText().get(6).get(0).getTextData());
	}

	@SuppressWarnings("unchecked")
	@Test
	void fetchCheckListDataTest2() throws JsonProcessingException {
		RequestBean requestBean = new RequestBean();
		requestBean.setDealerCode("99970");
		requestBean.setMarket("*");
		requestBean.setZone("1C");
		requestBean.setModelYear("2011");
		requestBean.setFamCode("*");
		requestBean.setState("*");
		requestBean.setPrtitn("1");
		requestBean.setVehicleSan("91");

		when(jdbcTemplate.queryForObject(
				Mockito.eq(queryPropertyConfig.getPropertyData(Constants.DEALER_GET_LANGUAGE_QUERY)),
				Mockito.eq(String.class), Mockito.any())).thenReturn("010");

		ParmDTO expectedParm = new ParmDTO();
		expectedParm.setLanguage("010");
		expectedParm.setMasterParmId("NVC");
		DealerResponseBean dummyRes = new DealerResponseBean();
		dummyRes.setDealerCode("99970");
		dummyRes.getDealerCode();
		dummyRes.setDealerName("dealerName");
		dummyRes.getClass();
		dummyRes.setMarket("U");
		dummyRes.getMarket();
		dummyRes.setZone("*");
		dummyRes.getDealerName();
		dummyRes.getZone();
		expectedParm.setZone("*");
		expectedParm.setMarketCode("U");
		when(jdbcTemplate.query(
				ArgumentMatchers.contains(queryPropertyConfig.getPropertyData(Constants.PARM_GET_MASTERPARM_QUERY)),
				ArgumentMatchers.<Object[]>any(), ArgumentMatchers.<MasterParmDetailsExtractor>any()))
						.thenReturn(expectedParm);

		when(jdbcTemplate.queryForObject(
				Mockito.eq(queryPropertyConfig.getPropertyData(Constants.PARM_LANGUAGE_CODE_PARM_QUERY)),
				Mockito.eq(String.class), Mockito.any())).thenReturn("en_US");

		List<ParmDTO> expected = new ArrayList<>();
		expectedParm.setItemOrderNum(105);
		expectedParm.setSalescode("GXD");
		expectedParm.setGroupID("6");
		expectedParm.setItemType("H");
		expectedParm.setText("Remote Proximity");
		expected.add(expectedParm);
		RequestBean dummy = new RequestBean();
		when(jdbcTemplate.query(
				ArgumentMatchers.contains(queryPropertyConfig.getPropertyData(Constants.PARM_GET_PARM_QUERY)),
				ArgumentMatchers.<Object[]>any(), ArgumentMatchers.<ParmDetailsMapper>any())).thenReturn(expected);
		Map<String, List<String>> salesCode = new HashMap<>();
		List<String> salesCodeOtc = checkListDataServiceImpl.refactorSalesCodes(
				"*M5 -D5 ADBPADCPAPASARNPAWHPBARSBCESBGKSCADPCFNSCGTSCKDPCKNSCKTPCKVPCLEPCSASCSCPCSRPCUFP");

		dummy.setSalesCodeOtC(salesCodeOtc);
		List<String> salesCodeDj = new ArrayList<>();
		salesCodeDj.add("DGB");
		salesCodeDj.add("GXD");
		dummy.setSalesCodeDtoJ(salesCodeDj);
		dummy.getSalesCodeOtC();
		dummy.getSalesCode0to9();
		dummy.getSalesCodeDtoJ();
		dummy.getSalesCodeKtoP();
		dummy.getSalesCodeQtoZ();
		dummy.getLanguageCode();
		dummy.setDealerLanguage("dealerLanguage");
		dummy.setLanguageCode("languageCode");
		List<String> salesCodeKp = checkListDataServiceImpl.refactorSalesCodes(
				"KBXPKGESKRBSKTBSK4APK5APLAGPLBBSLBCSLBDSLCDSLDASLDBSLMBSMFZPMHASMWGPNAASNF5SNHMPPW7");
		dummy.setSalesCodeKtoP(salesCodeKp);
		List<String> salesCodeQz = checkListDataServiceImpl.refactorSalesCodes(
				"QW7SRAACRASPRCDPSBASSCGPSFAPSGASSUAPTBB TBLSTRN TZAPWJ5PWLYCXEVSYAAAYALAYBBAYEPAYGGAZJQPZTFPZ6AS");
		dummy.setSalesCodeQtoZ(salesCodeQz);
		List<String> salesCode09 = checkListDataServiceImpl
				.refactorSalesCodes("1ASS115S2TEA26E 3AGA3VRA4AS 4BAA4HA 4M1P4XBA4XZ 4ZD ");
		dummy.getDealerLanguage();
		dummy.setVinFirst9("");
		dummy.getVinFirst9();
		dummy.setVinLast8("CT100312");
		dummy.getVinLast8();
		dummy.setSalesCode0to9(salesCode09);
		salesCode.put(Constants.SALESCODEOTC, salesCodeOtc);
		salesCode.put(Constants.SALESCODEDJ, salesCodeDj);
		salesCode.put(Constants.SALESCODEKP, salesCodeKp);
		salesCode.put(Constants.SALESCODEQZ, salesCodeQz);
		salesCode.put(Constants.SALESCODE09, salesCode09);
		when(jdbcTemplate.query(
				ArgumentMatchers.contains(queryPropertyConfig.getPropertyData(Constants.SALES_CODE_QUERY)),
				ArgumentMatchers.<Object[]>any(), ArgumentMatchers.<ResultSetExtractor>any())).thenReturn(salesCode);
		CheckListResponseBean actual = checkListDataServiceImpl.fetchCheckListData(requestBean);
		assertNotNull(actual.getParmText());
	}

	@SuppressWarnings("unchecked")
	@Test
	void fetchCheckListDataTest3() throws JsonProcessingException {
		RequestBean requestBean = new RequestBean();
		requestBean.setDealerCode("99970");
		requestBean.setMarket("*");
		requestBean.setZone("1C");
		requestBean.setModelYear("2011");
		requestBean.setFamCode("*");
		requestBean.setState("*");
		requestBean.setPrtitn("1");
		requestBean.setVehicleSan("91");

		when(jdbcTemplate.queryForObject(
				Mockito.eq(queryPropertyConfig.getPropertyData(Constants.DEALER_GET_LANGUAGE_QUERY)),
				Mockito.eq(String.class), Mockito.any())).thenReturn("010");

		ParmDTO expectedParm = new ParmDTO();
		expectedParm.setLanguage("010");
		expectedParm.setMasterParmId("NVC");
		expectedParm.setZone("*");
		expectedParm.setMarketCode("U");
		when(jdbcTemplate.query(
				ArgumentMatchers.contains(queryPropertyConfig.getPropertyData(Constants.PARM_GET_MASTERPARM_QUERY)),
				ArgumentMatchers.<Object[]>any(), ArgumentMatchers.<MasterParmDetailsExtractor>any()))
						.thenReturn(expectedParm);

		when(jdbcTemplate.queryForObject(
				Mockito.eq(queryPropertyConfig.getPropertyData(Constants.PARM_LANGUAGE_CODE_PARM_QUERY)),
				Mockito.eq(String.class), Mockito.any())).thenReturn("en_US");

		List<ParmDTO> expected = new ArrayList<>();
		expectedParm.setItemOrderNum(105);
		expectedParm.setSalescode("GXD");
		expectedParm.setGroupID("6");
		expectedParm.setItemType("M");
		expectedParm.setText("Remote Proximity");
		expected.add(expectedParm);
		when(jdbcTemplate.query(
				ArgumentMatchers.contains(queryPropertyConfig.getPropertyData(Constants.PARM_GET_PARM_QUERY)),
				ArgumentMatchers.<Object[]>any(), ArgumentMatchers.<ParmDetailsMapper>any())).thenReturn(expected);
		Map<String, List<String>> salesCode = new HashMap<>();
		List<String> salesCodeOtc = new ArrayList<>();
		salesCodeOtc.add("*M5 -D5 ADBPADCPAPASARNPAWHPBARSBCESBGKSCADPCFNSCGTSCKDPCKNSCKTPCKVPCLEPCSASCSCPCSRPCUFP");
		List<String> salesCodeDj = new ArrayList<>();
		salesCodeDj.add("DGB");
		salesCodeDj.add("GXD");
		List<String> salesCodeKp = new ArrayList<>();
		salesCodeKp.add("KBXPKGESKRBSKTBSK4APK5APLAGPLBBSLBCSLBDSLCDSLDASLDBSLMBSMFZPMHASMWGPNAASNF5SNHMPPW7 ");
		List<String> salesCodeQz = new ArrayList<>();
		salesCodeQz.add(
				"QW7SRAACRASPRCDPSBASSCGPSFAPSGASSUAPTBB TBLSTRN TZAPWJ5PWLYCXEVSYAAAYALAYBBAYEPAYGGAZJQPZTFPZ6AS");
		List<String> salesCode09 = new ArrayList<>();
		salesCodeQz.add("1ASS115S2TEA26E 3AGA3VRA4AS 4BAA4HA 4M1P4XBA4XZ 4ZD ");
		salesCode.put(Constants.SALESCODEOTC, salesCodeOtc);
		salesCode.put(Constants.SALESCODEDJ, salesCodeDj);
		salesCode.put(Constants.SALESCODEKP, salesCodeKp);
		salesCode.put(Constants.SALESCODEQZ, salesCodeQz);
		salesCode.put(Constants.SALESCODE09, salesCode09);
		ErrorDetail error = new ErrorDetail();
		error.setDetail("Unauthorized");
		error.getDetail();
		error.setStatus(401);
		error.getStatus();
		error.setTimeStamp("string");
		error.getTimeStamp();
		error.setTitle("title");
		error.getTitle();
		when(jdbcTemplate.query(
				ArgumentMatchers.contains(queryPropertyConfig.getPropertyData(Constants.SALES_CODE_QUERY)),
				ArgumentMatchers.<Object[]>any(), ArgumentMatchers.<ResultSetExtractor>any())).thenReturn(salesCode);
		CheckListResponseBean actual = checkListDataServiceImpl.fetchCheckListData(requestBean);
		assertNotNull(actual.getParmText());
	}

}
