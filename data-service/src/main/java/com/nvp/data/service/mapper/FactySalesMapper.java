package com.nvp.data.service.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.nvp.data.service.bean.ParmDTO;

public class FactySalesMapper implements RowMapper<ParmDTO>{

	@Override
	public ParmDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		ParmDTO parmDTO = new ParmDTO();
		parmDTO.setVhclSalesOtC(rs.getString("C_VHCL_SALES_OT_C").trim());
		parmDTO.setVhclSalesDj(rs.getString("C_VHCL_SALES_D_J").trim());
		parmDTO.setVhclSalesKp(rs.getString("C_VHCL_SALES_K_P").trim());
		parmDTO.setVhclSalesQz(rs.getString("C_VHCL_SALES_Q_Z").trim());
		parmDTO.setVhclSales09(rs.getString("C_VHCL_SALES_0_9").trim());
		return parmDTO;
	}

}
