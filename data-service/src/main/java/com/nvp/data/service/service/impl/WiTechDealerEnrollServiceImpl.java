package com.nvp.data.service.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;

import com.nvp.data.service.bean.WiTechDealerRequestBean;
import com.nvp.data.service.bean.WiTechDealerResponseBean;
import com.nvp.data.service.config.DataServiceConfig;
import com.nvp.data.service.service.WiTechDealerEnrollService;
import com.nvp.data.service.util.Constants;

@Service
public class WiTechDealerEnrollServiceImpl implements WiTechDealerEnrollService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	DataServiceConfig dataServiceConfig;

	private static final Logger logger = LoggerFactory.getLogger(WiTechDealerEnrollServiceImpl.class);

	@Override
	public List<WiTechDealerResponseBean> getDealerEnrollment(WiTechDealerRequestBean requestBean) {
		logger.info("Inside getDealerEnrollment");
		String query = dataServiceConfig.getPropertyData(Constants.WITECH_ENROLLED_DEALER_QUERY);
		Object[] parameters = { requestBean.getDealerCode().trim().toUpperCase() };

		return jdbcTemplate.query(query, parameters, new ResultSetExtractor<List<WiTechDealerResponseBean>>() {

			@Override
			public List<WiTechDealerResponseBean> extractData(ResultSet rs) throws SQLException {
				List<WiTechDealerResponseBean> dataList = new ArrayList<>();
				while (rs.next()) {
					WiTechDealerResponseBean bean = new WiTechDealerResponseBean();
					bean.setBusinessCenter(rs.getString(1));
					bean.setEffectiveStartDate(rs.getString(2));
					bean.setEffectiveEndDate(rs.getString(3));
					bean.setAuthPlanMax(rs.getString(4));
					bean.setAuthPlanMin(rs.getString(5));
					bean.setStatus(rs.getString(6));
					dataList.add(bean);
				}
				logger.info("Eixt getDealerEnrollment");
				return dataList;
			}
		});
	}

}
