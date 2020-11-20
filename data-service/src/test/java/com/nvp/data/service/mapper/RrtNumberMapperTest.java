package com.nvp.data.service.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
class RrtNumberMapperTest {

	RRTNumberMapper rrtNumberMapper = new RRTNumberMapper();
	ResultSet rs = Mockito.mock(ResultSet.class);
	
	@Test
	void rrtNumberMapperMock() throws SQLException {
		when(rs.getString("C_RPD_RSP_NUM")).thenReturn("101");
		String actual = rrtNumberMapper.mapRow(rs, 1);
		assertEquals("101", actual);
	}

}
