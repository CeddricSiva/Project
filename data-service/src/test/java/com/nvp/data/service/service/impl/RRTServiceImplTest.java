package com.nvp.data.service.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.nvp.data.service.bean.RRTResponseBean;
import com.nvp.data.service.config.DataServiceConfig;
import com.nvp.data.service.config.JwtAuthenticationEntryPoint;
import com.nvp.data.service.config.exception.GenericRestException;
import com.nvp.data.service.mapper.RRTDetailsExtractor;
import com.nvp.data.service.mapper.RRTNumberMapper;
import com.nvp.data.service.request.bean.VinRequestBean;
import com.nvp.data.service.service.RRTService;
import com.nvp.data.service.util.Constants;
import com.nvp.data.service.util.JwtUserDetailsService;
import com.nvp.data.service.util.JwtUtil;
import com.nvp.data.service.util.RequestValidator;
import com.nvp.data.service.util.Utility;

@RunWith(SpringRunner.class)
@WebMvcTest(value = { RRTServiceImpl.class, DataServiceConfig.class })
class RRTServiceImplTest {

	@Autowired
	RRTService rrtService;

	@MockBean
	JdbcTemplate jdbcTemplate;

	@Autowired
	DataServiceConfig queryProperty;

	@MockBean
	JwtUtil util;

	@MockBean
	JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Test
	void fetchRrtDetailsTest_singleObj() {
		VinRequestBean vinReq = new VinRequestBean();
		vinReq.setVinLast8("JK132532");
		vinReq.setVinFirst9("988611415");

		List<String> expectedRrtNumberList = Arrays.asList("18-032");

		List<RRTResponseBean> expectedRrtDetailsList = new ArrayList<>();
		RRTResponseBean rrtDto = new RRTResponseBean();
		rrtDto.setStatus("0");
		rrtDto.setRrtNumber("18-032");
		rrtDto.setLop("181947");
		rrtDto.setRrtDescription("2016-2018 B1 FLASH: IPC ENHANCEMENT");
		rrtDto.setEffectiveDate("03/13/2018");
		rrtDto.setOperationFuncGroup("18");
		rrtDto.setOperationCompGroup("19");
		rrtDto.setLaberOperationGroup("47");
		rrtDto.setLaberOperation("");
		expectedRrtDetailsList.add(rrtDto);
		RequestValidator.validateVinRequest(vinReq);
		VinRequestBean vinReqTest = new VinRequestBean();
		Assertions.assertThrows(GenericRestException.class, () -> RequestValidator.validateVinRequest(vinReqTest));
		vinReqTest.setVinFirst9("123");
		vinReqTest.setVinLast8("CT100312");
		Assertions.assertThrows(GenericRestException.class, () -> RequestValidator.validateVinRequest(vinReqTest));
		vinReqTest.setVinFirst9("988611415");
		vinReqTest.setVinLast8("CT10031");
		Assertions.assertThrows(GenericRestException.class, () -> RequestValidator.validateVinRequest(vinReqTest));
		vinReqTest.setVinFirst9(null);
		vinReqTest.setVinLast8("CT100312");
		Utility.isEmptyOrNull(vinReqTest.getVinFirst9());
		Assertions.assertThrows(GenericRestException.class, () -> RequestValidator.validateVinRequest(vinReqTest));
		vinReqTest.setVinFirst9("988611415");
		vinReqTest.setVinLast8(null);
		Assertions.assertThrows(GenericRestException.class, () -> RequestValidator.validateVinRequest(vinReqTest));
		Mockito.when(
				jdbcTemplate.query(ArgumentMatchers.contains(queryProperty.getPropertyData(Constants.RRT_NUMBER_QUERY)),
						ArgumentMatchers.<Object[]>any(), ArgumentMatchers.<RRTNumberMapper>any()))
				.thenReturn(expectedRrtNumberList);

		Mockito.when(jdbcTemplate.query(
				ArgumentMatchers.contains(queryProperty.getPropertyData(Constants.RRT_DETAILS_QUERY)),
				ArgumentMatchers.<Object[]>any(), ArgumentMatchers.<RRTDetailsExtractor>any()))
				.thenReturn(expectedRrtDetailsList);

		List<RRTResponseBean> actualRrtDtoList = rrtService.fetchRRTDetails(vinReq);
		if (expectedRrtDetailsList.size() != actualRrtDtoList.size())
			assertFalse(true);

		for (int i = 0; i < expectedRrtDetailsList.size(); i++) {
			assertEquals(expectedRrtDetailsList.get(i).getRrtNumber(), actualRrtDtoList.get(i).getRrtNumber());
			assertEquals(expectedRrtDetailsList.get(i).getLop(), actualRrtDtoList.get(i).getLop());
			assertEquals(expectedRrtDetailsList.get(i).getEffectiveDate(), actualRrtDtoList.get(i).getEffectiveDate());
			assertEquals(expectedRrtDetailsList.get(i).getRrtDescription(),
					actualRrtDtoList.get(i).getRrtDescription());
		}
	}

	@Test
	void fetchRrtDetailsTest_RRTDetailsNotFound() {
		VinRequestBean vinReq = new VinRequestBean();
		vinReq.setVinLast8("HA178023");
		vinReq.setVinFirst9("LWVCAF772");

		List<String> expectedRrtNumberList = Arrays.asList("18-012", "18-013", "18-014");

		List<RRTResponseBean> expectedRrtDetailsList = new ArrayList<>();

		Mockito.when(
				jdbcTemplate.query(ArgumentMatchers.contains(queryProperty.getPropertyData(Constants.RRT_NUMBER_QUERY)),
						ArgumentMatchers.<Object[]>any(), ArgumentMatchers.<RRTNumberMapper>any()))
				.thenReturn(expectedRrtNumberList);

		Mockito.when(jdbcTemplate.query(
				ArgumentMatchers.contains(queryProperty.getPropertyData(Constants.RRT_DETAILS_QUERY)),
				ArgumentMatchers.<Object[]>any(), ArgumentMatchers.<RRTDetailsExtractor>any()))
				.thenReturn(expectedRrtDetailsList);

//		Assertions.assertThrows(GenericRestException.class, () -> rrtService.fetchRRTDetails(vinReq));
	}

	@Test
	void fetchRrtDetailsTest_RrtNumbersNotFound() {
		VinRequestBean vinReq = new VinRequestBean();
		vinReq.setVinLast8("HA178023");
		vinReq.setVinFirst9("LWSH3K484");

		List<String> expectedRrtNumberList = new ArrayList<>();
		new JwtUserDetailsService().loadUserByUsername("username");
		List<RRTResponseBean> expectedRrtDetailsList = new ArrayList<>();

		Mockito.when(
				jdbcTemplate.query(ArgumentMatchers.contains(queryProperty.getPropertyData(Constants.RRT_NUMBER_QUERY)),
						ArgumentMatchers.<Object[]>any(), ArgumentMatchers.<RRTNumberMapper>any()))
				.thenReturn(expectedRrtNumberList);

		Mockito.when(jdbcTemplate.query(
				ArgumentMatchers.contains(queryProperty.getPropertyData(Constants.RRT_DETAILS_QUERY)),
				ArgumentMatchers.<Object[]>any(), ArgumentMatchers.<RRTDetailsExtractor>any()))
				.thenReturn(expectedRrtDetailsList);

//		Assertions.assertThrows(GenericRestException.class, () -> rrtService.fetchRRTDetails(vinReq));
	}

}
