package com.nvp.data.service.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.springframework.jdbc.core.RowMapper;

import com.nvp.data.service.bean.RecallResponseBean;

public class Srcsdtlod5Mapper implements RowMapper<RecallResponseBean> {

	private static final String SEE_RECALL_NOTICE = "SEE RECALL NOTICE";

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
		dto.setPartNumDtl05(true);

		setRecallNumbers(dto, rs.getString("I_PART"),
				partNumDtl05 -> (ArrayList<String>) setPartNum(partNumDtl05, new ArrayList<>()));
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

	private void setRecallNumbers(RecallResponseBean dto, String partNumDtl05, Function<String, ArrayList<String>> f) {
		ArrayList<String> partNums = f.apply(partNumDtl05);
		dto.setPartNum1(partNums.get(0));
		dto.setPartNum2(partNums.get(1));
		dto.setPartNum3(partNums.get(2));
	}

	public List<String> setPartNum(String partNumDtl05, List<String> partnums) {
		String partnum;
		String partNumTemp;
		if (null != partNumDtl05 && !partNumDtl05.trim().isEmpty()) {
			partNumTemp = partNumDtl05.trim();
			String[] parts = partNumTemp.split(",");
			int count = 0;
			for (String partTemp : parts) {
				partnum = partTemp;
				int partIndex = partnum.indexOf('(');
				partnum = partnum.substring(0, partIndex);
				count++;
				if (count <= 3)
					partnums.add(partnum);
				else
					break;

			}
			int noOfParts = partnums.size();
			for (int i = 1; i <= 3; i++)
				if (i > noOfParts)
					partnums.add("");

		} else {
			partnum = SEE_RECALL_NOTICE;
			partnums.add(partnum);
			partnums.add("");
			partnums.add("");
		}

		return partnums;

	}
}
