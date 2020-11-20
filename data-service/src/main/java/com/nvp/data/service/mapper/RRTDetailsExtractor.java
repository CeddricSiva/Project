package com.nvp.data.service.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.nvp.data.service.bean.RRTResponseBean;
import com.nvp.data.service.util.Constants;
import com.nvp.data.service.util.Utility;

public class RRTDetailsExtractor implements ResultSetExtractor<List<RRTResponseBean>>{


	@Override
	public List<RRTResponseBean> extractData(ResultSet rs) throws SQLException, DataAccessException {
		List<RRTResponseBean> rrtFilteredList = new ArrayList<>();
		boolean isStatusFlag=false;
		boolean isRRTNumPresent = true;
		while(rs.next()) {
			String status = rs.getString("C_STAT");
			if("1".equals(status)) {
				isStatusFlag = true;
			}else if("0".equals(status) && !isStatusFlag && isRRTNumPresent) {
				RRTResponseBean rrtDTO = new RRTResponseBean();
				rrtDTO.setRrtNumber(Utility.trimToEmpty(rs.getString("C_RPD_RSP_NUM")));
				rrtDTO.setLop(
						Utility.trimToEmpty(rs.getString("C_OPER_FUNCL_GRP")) + 
			            Utility.trimToEmpty(rs.getString("C_OPER_COMP_GRP")) + 
			            Utility.trimToEmpty(rs.getString("C_LABR_OPER_COMP")) + 
			            Utility.trimToEmpty(rs.getString("C_LABR_OPER"))
				);
				
				String effectiveDate = rs.getString("D_EFFCTV");
				if(effectiveDate != null && !effectiveDate.isEmpty()) {
					rrtDTO.setEffectiveDate(Utility.getDateAsString
							(Utility.parseDate(effectiveDate, Constants.DB_DATE_FORMAT), Constants.DATE_FORMAT));
				}
				rrtDTO.setRrtDescription(rs.getString("X_VHCL_RSP"));
				
				rrtFilteredList.add(rrtDTO);
				isRRTNumPresent = false;
			}
		}
		return rrtFilteredList;
	
	}

}
