package com.nvp.data.service.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.nvp.data.service.bean.RRTResponseBean;
import com.nvp.data.service.config.DataServiceConfig;
import com.nvp.data.service.mapper.RRTDetailsExtractor;
import com.nvp.data.service.mapper.RRTNumberMapper;
import com.nvp.data.service.request.bean.VinRequestBean;
import com.nvp.data.service.service.RRTService;
import com.nvp.data.service.util.Constants;

@Service
public class RRTServiceImpl implements RRTService{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	DataServiceConfig dataServiceConfig;
	
	boolean cStatFlag = false;
	boolean rrtNumDisplay = true;
	
	private static final Logger logger = LoggerFactory.getLogger(RRTServiceImpl.class);

	/**
	 * Method to fetch the list of RRT numbers
	 * @param requestBean
	 * @return
	 */
	public List<String> fetchRRTNumberList(VinRequestBean requestBean) {
		logger.info("Inside fetchRRTNumberList ");
		
		String query = dataServiceConfig.getPropertyData(Constants.RRT_NUMBER_QUERY);
		Object[] parameters = {requestBean.getVinLast8(), requestBean.getVinFirst9()};
		
		logger.debug("Query :: {}", query);
		
		return jdbcTemplate.query(query, parameters, new RRTNumberMapper());
	}

	/**
	 * Method to fetch the RRT Details list
	 */
	@Override
	public List<RRTResponseBean> fetchRRTDetails(VinRequestBean requestBean) {
		logger.info("Inside fetchRRTDetails");
		
		final List<RRTResponseBean> rrtFilteredList = new ArrayList<>();
		
		List<String> rrtNumberList = this.fetchRRTNumberList(requestBean);
		
		String query = dataServiceConfig.getPropertyData(Constants.RRT_DETAILS_QUERY);
		logger.debug("Query :: {}", query);
		
		if(rrtNumberList != null && !rrtNumberList.isEmpty()) {
			rrtNumberList.stream().forEach(rrtNumber ->{
				Object[] parameters = {requestBean.getVinLast8(), requestBean.getVinFirst9(), rrtNumber};
				rrtFilteredList.addAll(jdbcTemplate.query(query, parameters, new RRTDetailsExtractor()));
				
			});
		}
		logger.info("Exit fetchRRTDetails");
		return rrtFilteredList;
	}
}
