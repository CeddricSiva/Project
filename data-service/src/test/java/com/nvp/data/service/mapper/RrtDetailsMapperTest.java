package com.nvp.data.service.mapper;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import com.nvp.data.service.bean.RRTResponseBean;


@RunWith(SpringRunner.class)
class RrtDetailsMapperTest {

	RRTDetailsExtractor rrtDetailsExtractor = new RRTDetailsExtractor();
	ResultSet rs = Mockito.mock(ResultSet.class);
	
	@Test
	void rrtDetailsMapperTest() throws SQLException {
		
		RRTResponseBean rrtDtoExp = new RRTResponseBean();
		rrtDtoExp.setStatus("0");
		rrtDtoExp.setRrtNumber("18-032");
		rrtDtoExp.setLop("181947");
		rrtDtoExp.setRrtDescription("2016-2018 B1 FLASH: IPC ENHANCEMENT");
		rrtDtoExp.setEffectiveDate("03/13/2018");
		rrtDtoExp.setOperationFuncGroup("18");
		rrtDtoExp.setOperationCompGroup("19");
		rrtDtoExp.setLaberOperationGroup("47");
		rrtDtoExp.setLaberOperation("");
		
		when(rs.getString("C_STAT")).thenReturn("0");
		when(rs.getString("C_RPD_RSP_NUM")).thenReturn("18-032");
		when(rs.getString("X_VHCL_RSP")).thenReturn("2016-2018 B1 FLASH: IPC ENHANCEMENT");
		when(rs.getString("D_EFFCTV")).thenReturn("2018-03-13");
		when(rs.getString("C_OPER_FUNCL_GRP")).thenReturn("18");
		when(rs.getString("C_OPER_COMP_GRP")).thenReturn("19");
		when(rs.getString("C_LABR_OPER_COMP")).thenReturn("47");
		when(rs.getString("C_LABR_OPER")).thenReturn("");
		List<RRTResponseBean> result = rrtDetailsExtractor.extractData(rs);
		assertNotNull(result);
	}

}
