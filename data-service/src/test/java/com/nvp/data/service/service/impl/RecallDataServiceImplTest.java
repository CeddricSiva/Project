package com.nvp.data.service.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nvp.data.service.bean.CheckListResponseBean;
import com.nvp.data.service.bean.ParmDTO;
import com.nvp.data.service.bean.ParmHeaderDTO;
import com.nvp.data.service.bean.RecallResponseBean;
import com.nvp.data.service.config.DataServiceConfig;
import com.nvp.data.service.config.JwtAuthenticationEntryPoint;
import com.nvp.data.service.config.exception.GenericRestException;
import com.nvp.data.service.mapper.SrclsMapper;
import com.nvp.data.service.mapper.Srcsdtlod5Mapper;
import com.nvp.data.service.request.bean.VinRequestBean;
import com.nvp.data.service.util.Constants;
import com.nvp.data.service.util.ErrorResponseUtil;
import com.nvp.data.service.util.JwtUtil;
import com.nvp.data.service.util.RequestValidator;

@RunWith(SpringRunner.class)
@WebMvcTest(value = { RecallDataServiceImpl.class, DataServiceConfig.class })
class RecallDataServiceImplTest {

	@Autowired
	private RecallDataServiceImpl recallDatService;

	@Autowired
	DataServiceConfig queryPropertyConfig;

	@Autowired
	ObjectMapper mapper;

	@MockBean
	private JdbcTemplate jdbcTemplate;
	@MockBean
	HttpClientErrorException httpException;

	@MockBean
	JwtUtil util;
	@MockBean
	JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Test
	void getRecallDatatest() throws JsonProcessingException {
		VinRequestBean requestBean = new VinRequestBean();

		requestBean.setVinLast8("RY041741");
		requestBean.setVinFirst9("JB3AM44H3");
		RecallResponseBean dto = new RecallResponseBean();
		dto.setDescription("FRONT BRAKE HOSES");
		dto.setLaunchDate(LocalDate.of(1998, 6, 4).toString());
		dto.setNoPartFlag(false);
		dto.setNumber("699");
		dto.setPartNum1("SEE RECALL NOTICE");
		dto.setPartNum2("");
		dto.setPartNum3("");
		dto.setPartType("01");
		dto.setRecallFlag(true);
		List<RecallResponseBean> expected = new ArrayList<>();
		expected.add(dto);
		expected.add(new RecallResponseBean());
		ErrorResponseUtil errorUtil = new ErrorResponseUtil();
		when(httpException.getRawStatusCode()).thenReturn(401);
		errorUtil.errorResponseHandler(httpException);
		when(jdbcTemplate.query(
				ArgumentMatchers.contains(queryPropertyConfig.getPropertyData(Constants.GET_RECALL_SRCDTL05)),
				ArgumentMatchers.<Object[]>any(), ArgumentMatchers.<Srcsdtlod5Mapper>any())).thenReturn(expected);
		dto.setPartNumDtl05(false);
		when(jdbcTemplate.query(
				ArgumentMatchers.contains(queryPropertyConfig.getPropertyData(Constants.GET_RECALL_SRCLC)),
				ArgumentMatchers.<Object[]>any(), ArgumentMatchers.<SrclsMapper>any())).thenReturn(expected);
		ArrayList<RecallResponseBean> actual = recallDatService.getRecallData(requestBean);
		assertEquals(expected.get(0).getNumber(), actual.get(0).getNumber());
		assertEquals(expected.get(0).getDescription(), actual.get(0).getDescription());
		assertEquals(expected.get(0).getPartType(), actual.get(0).getPartType());
		assertEquals(expected.get(0).getLaunchDate(), actual.get(0).getLaunchDate());

	}

	@Test
	void getRecallDataErrortest() throws JsonProcessingException {
		VinRequestBean requestBean = new VinRequestBean();

		requestBean.setVinLast8("RY041741");
		requestBean.setVinFirst9("JB3AM44H3");
		RecallResponseBean dto = new RecallResponseBean();
		dto.setDescription("FRONT BRAKE HOSES");
		dto.setLaunchDate(LocalDate.of(1998, 6, 4).toString());
		dto.setNoPartFlag(false);
		dto.setNumber("699");
		dto.setPartNum1("SEE RECALL NOTICE");
		dto.setPartNum2("");
		dto.setPartNum3("");
		dto.setPartType("01");
		dto.setRecallFlag(true);
		List<RecallResponseBean> expected = new ArrayList<>();
		expected.add(dto);
		expected.add(new RecallResponseBean());
		ErrorResponseUtil errorUtil = new ErrorResponseUtil();
		when(httpException.getRawStatusCode()).thenReturn(401);
		errorUtil.errorResponseHandler(httpException);
		dto.setPartNumDtl05(false);
		Assertions.assertThrows(GenericRestException.class, () -> recallDatService.getRecallData(requestBean));

	}

	@Test
	void setAllPartNumbersTest() {
		RecallResponseBean dto = new RecallResponseBean();
		dto.setDescription("FRONT BRAKE HOSES");
		dto.setLaunchDate(LocalDate.of(1998, 6, 4).toString());
		dto.setNoPartFlag(false);
		dto.setNumber("699");
		dto.setPartNum1("");
		dto.setPartNum2("");
		dto.setPartNum3("");
		dto.setPartType("99");
		ParmHeaderDTO dummy = new ParmHeaderDTO();
		dummy.getHeaderData();
		dummy.getOrderNum();
		dto.setRecallFlag(true);
		RecallResponseBean actual = recallDatService.setAllPartNumbers(dto, "");
		assertEquals("NO PARTS REQUIRED", actual.getPartNum1());
	}

	@Test
	void setAllPartNumbersTest1() {
		RecallResponseBean dto = new RecallResponseBean();
		dto.setDescription("FRONT BRAKE HOSES");
		dto.setLaunchDate(LocalDate.of(1998, 6, 4).toString());
		dto.setNoPartFlag(false);
		dto.setNumber("699");
		dto.setPartNum1("");
		CheckListResponseBean dummy = new CheckListResponseBean();
		dummy.getHeader();
		dummy.getSpecialMessage();
		dummy.getFlagDetails();
		dummy.setFlagDetails("flagDetails");
		dto.setPartNum2("");
		dto.setPartNum3("");
		dto.setPartType("");
		dto.setRecallFlag(true);
		RecallResponseBean actual = recallDatService.setAllPartNumbers(dto, "");
		assertEquals("SEE RECALL NOTICE", actual.getPartNum1());
	}

	@Test
	void setAllPartNumbersTest2() {
		RecallResponseBean dto = new RecallResponseBean();
		dto.setDescription("FRONT BRAKE HOSES");
		dto.setLaunchDate(LocalDate.of(1998, 6, 4).toString());
		dto.setNoPartFlag(false);
		dto.setNumber("699");
		dto.setPartNum1("");
		dto.setPartNum2("");
		dto.setPartNum3("");
		dto.setPartType("");
		dto.setRecallFlag(true);
		RecallResponseBean actual = recallDatService.setAllPartNumbers(dto, "99");
		assertEquals("99", actual.getPartNum1());
	}

	@Test
	void setAllPartNumbersTest3() {
		RecallResponseBean dto = new RecallResponseBean();
		dto.setDescription("FRONT BRAKE HOSES");
		dto.setLaunchDate(LocalDate.of(1998, 6, 4).toString());
		dto.setNoPartFlag(false);
		dto.setNumber("699");
		dto.setPartNum1("SEE RECALL NOTICE");
		dto.setPartNum2("");
		dto.setPartNum3("");
		dto.setPartType("");
		dto.setRecallFlag(true);
		RecallResponseBean actual = recallDatService.setAllPartNumbers(dto, "99");
		assertEquals("99", actual.getPartNum1());
	}

	@Test
	void setAllPartNumbersTest4() {
		RecallResponseBean dto = new RecallResponseBean();
		dto.setDescription("FRONT BRAKE HOSES");
		dto.setLaunchDate(LocalDate.of(1998, 6, 4).toString());
		dto.setNoPartFlag(false);
		dto.setNumber("699");
		dto.setPartNum1("SEE RECALL NOTICE");
		dto.setPartNum2("");
		dto.setPartNum3("");
		dto.setPartType("");
		dto.setRecallFlag(false);
		RecallResponseBean actual = recallDatService.setAllPartNumbers(dto, "99");
		assertEquals("SEE RECALL NOTICE", actual.getPartNum1());
	}

	@Test
	void setAllPartNumbersTest5() {
		RecallResponseBean dto = new RecallResponseBean();
		dto.setDescription("FRONT BRAKE HOSES");
		dto.setLaunchDate(LocalDate.of(1998, 6, 4).toString());
		dto.setNoPartFlag(false);
		dto.setNumber("699");
		dto.setPartNum1("SEE RECALL NOTICE");
		dto.setPartNum2("99");
		dto.setPartNum3("");
		dto.setPartType("");
		dto.setRecallFlag(false);
		RecallResponseBean actual = recallDatService.setAllPartNumbers(dto, "99");
		assertEquals("SEE RECALL NOTICE", actual.getPartNum1());
	}
}
