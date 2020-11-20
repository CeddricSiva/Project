package com.nvp.data.service.controller;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nvp.data.service.bean.ApiResponseBean;
import com.nvp.data.service.bean.RRTResponseBean;
import com.nvp.data.service.bean.RecallResponseBean;
import com.nvp.data.service.config.DataServiceConfig;
import com.nvp.data.service.config.JwtAuthenticationEntryPoint;
import com.nvp.data.service.request.bean.VinRequestBean;
import com.nvp.data.service.service.CheckListDataService;
import com.nvp.data.service.service.DealerService;
import com.nvp.data.service.service.RRTService;
import com.nvp.data.service.service.RecallDataService;
import com.nvp.data.service.service.VehicleDetailService;
import com.nvp.data.service.service.WiTechDealerEnrollService;
import com.nvp.data.service.service.WiTechVehicleCheckService;
import com.nvp.data.service.util.ErrorResponseUtil;
import com.nvp.data.service.util.JwtUtil;
import com.nvp.data.service.util.RSAUtil;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@WebMvcTest(value = { NVPDataServiceController.class, DataServiceConfig.class, RRTService.class,
		RecallDataService.class, CheckListDataService.class, JwtUtil.class })
@PropertySource(value = { "file:D:/NVP/NvpSourceCode/NVP-DataService_18Nov2020/props/application.properties", "file:D:/NVP/NvpSourceCode/NVP-DataService_18Nov2020/props/query.properties",
		"file:D:/NVP/NvpSourceCode/NVP-DataService_18Nov2020/props/messages.properties" })
class NVPDataServiceControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	ObjectMapper mapper;

	@Autowired
	JwtUtil jwtTokenUtil;

	@MockBean
	JwtAuthenticationEntryPoint auth;

	@MockBean
	RRTService rrtService;

	@MockBean
	RecallDataService recallDataService;

	@MockBean
	CheckListDataService parmService;

	@MockBean
	WiTechVehicleCheckService wiTechVehicleService;

	@MockBean
	WiTechDealerEnrollService wiTechDealerEnrollService;

	@MockBean
	ErrorResponseUtil errorResponseUtil;

	@MockBean
	private DealerService dealerService;;

	@MockBean
	VehicleDetailService VehicleDetailService;

	@MockBean
	JdbcTemplate jdbcTemplate;

	@Value("${rsa.request.private.key}")
	private String rsaPrivateKey;

	private static MessageDigest messageDigest = null;

	@Test
	void GetRecallControllerest() throws Exception {

		VinRequestBean requestBean = new VinRequestBean();
		requestBean.setVinLast8("RY041741");
		requestBean.setVinFirst9("JB3AM44H3");
		String checksum = generateChecksum(mapper.writeValueAsString(requestBean));
		String token = generateToken(checksum);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/nvpDataService/getRecall")
				.accept(MediaType.APPLICATION_JSON).content("{\"vinLast8\":\"RY041741\",\"vinFirst9\":\"JB3AM44H3\"}")
				.header("Authorization", "Bearer " + token).contentType(MediaType.APPLICATION_JSON);
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
		when(recallDataService.getRecallData(Mockito.any())).thenReturn(expected);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String ss = result.getResponse().getContentAsString();
		System.out.println(ss);
		ApiResponseBean actual = mapper.readValue(result.getResponse().getContentAsString(), ApiResponseBean.class);
		assertNotNull(actual);
	}

	@Test
	void getRrtDetailsTest() throws Exception {

		VinRequestBean requestBean = new VinRequestBean();
		requestBean.setVinLast8("JK132532");
		requestBean.setVinFirst9("988611415");
		String checksum = generateChecksum(mapper.writeValueAsString(requestBean));
		String token = generateToken(checksum);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/nvpDataService/getRRT")
				.accept(MediaType.APPLICATION_JSON).content("{\"ivinlast8\":\"JK132532\",\"ivinfirst9\":\"988611415\"}")
				.header("Authorization", "Bearer " + token).contentType(MediaType.APPLICATION_JSON);
		List<RRTResponseBean> expected = new ArrayList<>();
		RRTResponseBean rrtDto = new RRTResponseBean();
		rrtDto.setRrtNumber("18-032");
		rrtDto.setLop("181947");
		rrtDto.setRrtDescription("2016-2018 B1 FLASH: IPC ENHANCEMENT");
		rrtDto.setEffectiveDate("03/13/2018");
		expected.add(rrtDto);
		when(rrtService.fetchRRTDetails(Mockito.any())).thenReturn(expected);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		ApiResponseBean actual = mapper.readValue(result.getResponse().getContentAsString(), ApiResponseBean.class);
		assertNotNull(actual);
	}

	private static void initializeMessageDigest() {
		if (messageDigest == null) {
			try {
				messageDigest = MessageDigest.getInstance("SHA-256");
			} catch (NoSuchAlgorithmException e) {
			}
		}

	}

	public static String generateChecksum(String input) {
		initializeMessageDigest();
		byte[] digest = messageDigest.digest(input.getBytes());
		return DatatypeConverter.printHexBinary(digest);
	}

	public String generateToken(String checksum) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("payload", checksum);
		return createToken(claims);
	}

	private String createToken(Map<String, Object> claims) {
		return Jwts.builder().setClaims(claims).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 120))
				.signWith(SignatureAlgorithm.RS256, RSAUtil.getPrivateKey(rsaPrivateKey)).compact();
	}

}
