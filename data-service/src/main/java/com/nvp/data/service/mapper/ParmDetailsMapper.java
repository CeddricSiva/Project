package com.nvp.data.service.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.nvp.data.service.bean.ParmDTO;
import com.nvp.data.service.util.Utility;

public class ParmDetailsMapper implements RowMapper<ParmDTO>{

	@Override
	public ParmDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		ParmDTO parmDto = new ParmDTO();
		parmDto.setSalescode(Utility.trimToEmpty(rs.getString("Sale_Code")));
		parmDto.setGroupID(Utility.trimToEmpty(rs.getString("GROUP_ID")));
		parmDto.setItemOrderNum(rs.getInt("Order_Number"));
		parmDto.setItemType(Utility.trimToEmpty(rs.getString("Item_Type")));
		parmDto.setText(Utility.trimToEmpty(rs.getString("C_PARM_DATA")));
		return parmDto;
	}

}
