package com.nvp.data.service.mapper;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import com.nvp.data.service.bean.ParmDTO;

@RunWith(SpringRunner.class)
class ParmDetailsMapperTest {

	ParmDetailsMapper parmDetailsMapper = new ParmDetailsMapper() ;
	ResultSet rs = Mockito.mock(ResultSet.class);
	
	@Test
	void parmDetailsMapperTest() throws SQLException {
		when(rs.getString("Sale_Code")).thenReturn("*");
		when(rs.getString("GROUP_ID")).thenReturn("1");
		when(rs.getInt("Order_Number")).thenReturn(0);
		when(rs.getString("Item_Type")).thenReturn("H");
		when(rs.getString("C_PARM_DATA")).thenReturn("VEHICLE READINESS");
		ParmDTO actual = parmDetailsMapper.mapRow(rs, 5);
		assertNotNull(actual);
	}

}
