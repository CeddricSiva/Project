package com.nvp.data.service.service.impl;

import static org.junit.Assert.assertEquals;
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
import com.nvp.data.service.bean.WiTechDealerRequestBean;
import com.nvp.data.service.bean.WiTechDealerResponseBean;
import com.nvp.data.service.config.DataServiceConfig;
import com.nvp.data.service.config.JwtAuthenticationEntryPoint;
import com.nvp.data.service.util.Constants;
import com.nvp.data.service.util.JwtUtil;

@RunWith(SpringRunner.class)
@WebMvcTest(value = { WiTechDealerEnrollServiceImpl.class, DataServiceConfig.class })
class WiTechDealerEnrollServiceImplTest {

	@Autowired
	WiTechDealerEnrollServiceImpl wiTechDealerEnrollServiceImpl;
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
	void GetDealerEnrollmentTest() {

		WiTechDealerRequestBean requestBean = new WiTechDealerRequestBean();
		requestBean.setDealerCode("23401");
		WiTechDealerResponseBean res = new WiTechDealerResponseBean();
		res.setBusinessCenter("53");
		res.setEffectiveStartDate("2020-11-02");
		res.setEffectiveEndDate("9999-12-31");
		res.setAuthPlanMax("0");
		res.setAuthPlanMin("0");
		res.setStatus("A");
		List<WiTechDealerResponseBean> dataList = new ArrayList<WiTechDealerResponseBean>();
		dataList.add(res);
		when(jdbcTemplate.query(
				ArgumentMatchers.contains(queryPropertyConfig.getPropertyData(Constants.WITECH_ENROLLED_DEALER_QUERY)),
				ArgumentMatchers.<Object[]>any(), ArgumentMatchers.<ResultSetExtractor>any())).thenReturn(dataList);
		List<WiTechDealerResponseBean> actual = wiTechDealerEnrollServiceImpl.getDealerEnrollment(requestBean);
		assertEquals(res.getBusinessCenter(), actual.get(0).getBusinessCenter());

	}

}
