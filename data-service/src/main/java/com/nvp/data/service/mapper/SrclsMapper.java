package com.nvp.data.service.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.jdbc.core.RowMapper;

import com.nvp.data.service.bean.RecallResponseBean;

public class SrclsMapper implements RowMapper<RecallResponseBean> {

	@Override
	public RecallResponseBean mapRow(ResultSet rs, int rowNum) throws SQLException {
		RecallResponseBean dto = new RecallResponseBean();
		dto.setNumber(rs.getString("I_VHCL_RECAL").trim());
		dto.setDescription(rs.getString("X_VHCL_RECAL").trim());
		dto.setPartType(rs.getString("C_PART_TYP").trim());
		if ("Y".equalsIgnoreCase(rs.getString("L_MKT_ALL")))
			setLaunchDate(rs.getString("D_RECAL_LNCH_ALL"), dto);

		else if ("Y".equalsIgnoreCase(rs.getString("L_MKT_USA")))
			setLaunchDate(rs.getString("D_RECAL_LNCH_USA"), dto);

		else if ("Y".equalsIgnoreCase(rs.getString("L_MKT_CAND")))
			setLaunchDate(rs.getString("D_RECAL_LNCH_CAND"), dto);

		else if ("Y".equalsIgnoreCase(rs.getString("L_MKT_MEX")))
			setLaunchDate(rs.getString("D_RECAL_LNCH_MEX"), dto);

		else if ("Y".equalsIgnoreCase(rs.getString("L_MKT_INTNL")))
			setLaunchDate(rs.getString("D_RECAL_LNCH_INTNL"), dto);
		return dto;
	}

	private void setLaunchDate(String launchDateSrcdtl, RecallResponseBean dto) {
		if (!(("          ").equals(launchDateSrcdtl)) && (launchDateSrcdtl.length() != 0)) {
			int year = Integer.parseInt(launchDateSrcdtl.substring(0, 4));
			int month = Integer.parseInt(launchDateSrcdtl.substring(5, 7));
			int day = Integer.parseInt(launchDateSrcdtl.substring(8, 10));
			dto.setLaunchDate(LocalDate.of(year, month, day).toString());
		}
	}

}
