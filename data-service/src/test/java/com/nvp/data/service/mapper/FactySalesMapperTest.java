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
class FactySalesMapperTest {

	FactySalesMapper factySalesMapper = new FactySalesMapper();
	ResultSet rs = Mockito.mock(ResultSet.class);
	
	@Test
	void factySalesMapperTest() throws SQLException {
		when(rs.getString("C_VHCL_SALES_OT_C")).thenReturn("*M5 -D5 ADBPADCPAPASARNPAWHPBARSBCESBGKSCADPCFNSCGTSCKDPCKNSCKTPCKVPCLEPCSASCSCPCSRPCUFP");
		when(rs.getString("C_VHCL_SALES_D_J")).thenReturn("DGB DGSSDHR DJHSDMDSDRJSERH GBBSGCBSGEDSGFAPGNCPGRKPGSKPGTKPGVBCGXMPHAAPHGAPHGDPJAYSJCDSJHASJHBSJJASJJBSJPAPJPBP");
		when(rs.getString("C_VHCL_SALES_K_P")).thenReturn("KBXPKGESKRBSKTBSK4APK5APLAGPLBBSLBCSLBDSLCDSLDASLDBSLMBSMFZPMHASMWGPNAASNF5SNHMPPW7");
		when(rs.getString("C_VHCL_SALES_Q_Z")).thenReturn("QW7SRAACRASPRCDPSBASSCGPSFAPSGASSUAPTBB TBLSTRN TZAPWJ5PWLYCXEVSYAAAYALAYBBAYEPAYGGAZJQPZTFPZ6AS");
		when(rs.getString("C_VHCL_SALES_0_9")).thenReturn("1ASS115S2TEA26E 3AGA3VRA4AS 4BAA4HA 4M1P4XBA4XZ 4ZD");
		ParmDTO actual = factySalesMapper.mapRow(rs, 5);
		assertNotNull(actual);
	}

}
