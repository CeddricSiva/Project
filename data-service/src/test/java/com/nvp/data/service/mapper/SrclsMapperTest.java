package com.nvp.data.service.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import com.nvp.data.service.bean.RecallResponseBean;



@RunWith(SpringRunner.class)
class SrclsMapperTest {
//	@Autowired
	SrclsMapper mapper = new SrclsMapper();
	
	
	ResultSet rs = Mockito.mock(ResultSet.class);

	@Test
	void Mappertest() throws SQLException {
		when(rs.getString("I_VHCL_RECAL")).thenReturn("847");
		when(rs.getString("X_VHCL_RECAL")).thenReturn("FRONT BRAKE HOSES");
		when(rs.getString("C_PART_TYP")).thenReturn("01");
		when(rs.getString("L_MKT_ALL")).thenReturn("Y");
		when(rs.getString("D_RECAL_LNCH_ALL")).thenReturn("1998-05-10");
		when(rs.getString("L_MKT_USA")).thenReturn("");
		when(rs.getString("D_RECAL_LNCH_USA")).thenReturn("");
		when(rs.getString("L_MKT_CAND")).thenReturn("");
		when(rs.getString("D_RECAL_LNCH_CAND")).thenReturn("");
		when(rs.getString("L_MKT_MEX")).thenReturn("");
		when(rs.getString("D_RECAL_LNCH_MEX")).thenReturn("");
		when(rs.getString("L_MKT_INTNL")).thenReturn("");
		when(rs.getString("D_RECAL_LNCH_INTNL")).thenReturn("");
		RecallResponseBean actual = mapper.mapRow(rs, 1);
		assertEquals("FRONT BRAKE HOSES", actual.getDescription());
	}
	
	@Test
	void MapperBranchtest() throws SQLException {
		when(rs.getString("I_VHCL_RECAL")).thenReturn("847");
		when(rs.getString("X_VHCL_RECAL")).thenReturn("FRONT BRAKE HOSES");
		when(rs.getString("C_PART_TYP")).thenReturn("01");
		when(rs.getString("L_MKT_ALL")).thenReturn("");
		when(rs.getString("D_RECAL_LNCH_ALL")).thenReturn("");
		when(rs.getString("L_MKT_USA")).thenReturn("y");
		when(rs.getString("D_RECAL_LNCH_USA")).thenReturn("1998-05-10");
		when(rs.getString("L_MKT_CAND")).thenReturn("");
		when(rs.getString("D_RECAL_LNCH_CAND")).thenReturn("");
		when(rs.getString("L_MKT_MEX")).thenReturn("");
		when(rs.getString("D_RECAL_LNCH_MEX")).thenReturn("");
		when(rs.getString("L_MKT_INTNL")).thenReturn("");
		when(rs.getString("D_RECAL_LNCH_INTNL")).thenReturn("");
		RecallResponseBean actual = mapper.mapRow(rs, 1);
		assertEquals("FRONT BRAKE HOSES", actual.getDescription());
	}
	
	@Test
	void MapperBranch1test() throws SQLException {
		when(rs.getString("I_VHCL_RECAL")).thenReturn("847");
		when(rs.getString("X_VHCL_RECAL")).thenReturn("FRONT BRAKE HOSES");
		when(rs.getString("C_PART_TYP")).thenReturn("01");
		when(rs.getString("L_MKT_ALL")).thenReturn("");
		when(rs.getString("D_RECAL_LNCH_ALL")).thenReturn("");
		when(rs.getString("L_MKT_USA")).thenReturn("");
		when(rs.getString("D_RECAL_LNCH_USA")).thenReturn("");
		when(rs.getString("L_MKT_CAND")).thenReturn("Y");
		when(rs.getString("D_RECAL_LNCH_CAND")).thenReturn("1998-05-10");
		when(rs.getString("L_MKT_MEX")).thenReturn("");
		when(rs.getString("D_RECAL_LNCH_MEX")).thenReturn("");
		when(rs.getString("L_MKT_INTNL")).thenReturn("");
		when(rs.getString("D_RECAL_LNCH_INTNL")).thenReturn("");
		RecallResponseBean actual = mapper.mapRow(rs, 1);
		assertEquals("FRONT BRAKE HOSES", actual.getDescription());
	}
	
	@Test
	void MapperBranch2test() throws SQLException {
		when(rs.getString("I_VHCL_RECAL")).thenReturn("847");
		when(rs.getString("X_VHCL_RECAL")).thenReturn("FRONT BRAKE HOSES");
		when(rs.getString("C_PART_TYP")).thenReturn("01");
		when(rs.getString("L_MKT_ALL")).thenReturn("");
		when(rs.getString("D_RECAL_LNCH_ALL")).thenReturn("");
		when(rs.getString("L_MKT_USA")).thenReturn("");
		when(rs.getString("D_RECAL_LNCH_USA")).thenReturn("");
		when(rs.getString("L_MKT_CAND")).thenReturn("");
		when(rs.getString("D_RECAL_LNCH_CAND")).thenReturn("");
		when(rs.getString("L_MKT_MEX")).thenReturn("Y");
		when(rs.getString("D_RECAL_LNCH_MEX")).thenReturn("1998-05-10");
		when(rs.getString("L_MKT_INTNL")).thenReturn("");
		when(rs.getString("D_RECAL_LNCH_INTNL")).thenReturn("");
		RecallResponseBean actual = mapper.mapRow(rs, 1);
		assertEquals("FRONT BRAKE HOSES", actual.getDescription());
	}
	
	@Test
	void MapperBranch3test() throws SQLException {
		when(rs.getString("I_VHCL_RECAL")).thenReturn("847");
		when(rs.getString("X_VHCL_RECAL")).thenReturn("FRONT BRAKE HOSES");
		when(rs.getString("C_PART_TYP")).thenReturn("01");
		when(rs.getString("L_MKT_ALL")).thenReturn("");
		when(rs.getString("D_RECAL_LNCH_ALL")).thenReturn("");
		when(rs.getString("L_MKT_USA")).thenReturn("");
		when(rs.getString("D_RECAL_LNCH_USA")).thenReturn("");
		when(rs.getString("L_MKT_CAND")).thenReturn("");
		when(rs.getString("D_RECAL_LNCH_CAND")).thenReturn("");
		when(rs.getString("L_MKT_MEX")).thenReturn("");
		when(rs.getString("D_RECAL_LNCH_MEX")).thenReturn("");
		when(rs.getString("L_MKT_INTNL")).thenReturn("Y");
		when(rs.getString("D_RECAL_LNCH_INTNL")).thenReturn("1998-05-10");
		RecallResponseBean actual = mapper.mapRow(rs, 1);
		assertEquals("FRONT BRAKE HOSES", actual.getDescription());
	}

}
