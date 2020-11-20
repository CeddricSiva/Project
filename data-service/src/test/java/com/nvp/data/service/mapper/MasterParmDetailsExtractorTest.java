package com.nvp.data.service.mapper;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.junit4.SpringRunner;

import com.nvp.data.service.bean.ParmDTO;

@RunWith(SpringRunner.class)
class MasterParmDetailsExtractorTest {

	MasterParmDetailsExtractor mapper = new MasterParmDetailsExtractor();
	ResultSet rs = Mockito.mock(ResultSet.class);

	@Test
	void MpaaerTest() throws DataAccessException, SQLException {
		when(rs.next()).thenReturn(true);
		when(rs.getString(1)).thenReturn("6");
		when(rs.getString(2)).thenReturn("*");
		when(rs.getString(3)).thenReturn("*");
		when(rs.getString(4)).thenReturn("GS");
		ParmDTO actual = mapper.extractData(rs);
		assertNotNull(actual);
	}

}
