package com.nvp.data.service.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.nvp.data.service.bean.ParmDTO;

public class MasterParmDetailsExtractor implements ResultSetExtractor<ParmDTO>{

	private static final Logger logger = LoggerFactory.getLogger(MasterParmDetailsExtractor.class);

	@Override
	public ParmDTO extractData(ResultSet rs) throws SQLException, DataAccessException {
		int resultCount = 0;
		int rowCount = 0;
		int starCount = 0;
		String masterParmId = null;
		String marketCode=null;
		String language = null;
		String zone = null;
		String parmId=null;
		
		while(rs.next()) {
			
			int wildCardCount = 0;
			resultCount++;
			
			logger.debug("getParmData() - wildCardCount: {} ",wildCardCount);
			logger.debug("getParmData() - resultCount: {} ",resultCount);
			
			masterParmId = rs.getString(1);
			logger.debug("getParmData() - masterParmId: {} ", masterParmId);
			
			marketCode = rs.getString(2);
			logger.debug("getParmData() - marketCode: {} ",marketCode);
			
			if("*".equalsIgnoreCase(marketCode.trim()))
			{
				wildCardCount++;
				logger.debug("getParmData() - wildCardCount for market: {} ",wildCardCount);
			}
			
			zone = rs.getString(3);
			logger.debug("getParmData() - zone: {} ",zone);
			
			if("*".equalsIgnoreCase(zone.trim()))
			{
				wildCardCount++;
				logger.debug("getParmData() - wildCardCount for zone: {} ",wildCardCount);
			}
			
			language = rs.getString(4);
			logger.debug("getParmData() - language: {} ",language);
			
			starCount = wildCardCount;
			logger.debug("getParmData() - starCount: {} ",starCount);
			
							
			if(!"010".equalsIgnoreCase(language.trim()))
			{
				parmId = masterParmId;
				logger.debug("getParmData() - parmId for preferred language: {} ", parmId);
				break;
			}
			
			if(resultCount == 1)
			{
				rowCount = wildCardCount;
				parmId = masterParmId;
				logger.debug("getParmData() - parmId for first row: {} ",parmId);
			}
			
			
			if(resultCount > 1)
			{
				logger.debug("getParmData() - starCount in if: {} ",starCount);
				logger.debug("getParmData() - rowCount: {} ",rowCount);
				
				if(rowCount > starCount)
				{
					rowCount = starCount;
					parmId = masterParmId;
					logger.debug("getParmData() - rowCount inside if: {} ", rowCount);
				}
			}
		}
		
		logger.debug("getParmData() - resultCount: {} ",resultCount);
		logger.debug("getParmData() - before default parmId: {} ",parmId);
		logger.debug("getParmData() - before default parmId language: {} ",language);
        
		if(resultCount == 0)
		{
			parmId = "NVC";
			language = "010";
		}
		logger.debug("getParmData() - final parmId: {} ",parmId);
		logger.debug("getParmData() - final language: {} ",language);
		ParmDTO parmDTO = new ParmDTO();
		parmDTO.setLanguage(language);
		parmDTO.setMarketCode(marketCode);
		parmDTO.setMasterParmId(parmId);
		parmDTO.setZone(zone);
		return parmDTO;
	}

}
