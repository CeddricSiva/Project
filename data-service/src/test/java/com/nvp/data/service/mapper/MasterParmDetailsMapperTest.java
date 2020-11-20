package com.nvp.data.service.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
class MasterParmDetailsMapperTest {

	MasterParmDetailsExtractor masterParmDetailsMapper = new MasterParmDetailsExtractor();
	ResultSet rs = Mockito.mock(ResultSet.class);
	
	@Test
	void masterParmDetailsMapperTest() throws SQLException {
//		when(rs.getString("NVP_PARM")).thenReturn("NVC");
//		when(rs.getString("MARKET")).thenReturn("U");
//		when(rs.getString("ZONE")).thenReturn("*");
//		when(rs.getString("LANGUAGE")).thenReturn("010");
//		masterParmDetailsMapper.extractData(rs, 4);
	}
}
