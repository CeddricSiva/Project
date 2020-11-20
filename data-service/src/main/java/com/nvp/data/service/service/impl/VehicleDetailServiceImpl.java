package com.nvp.data.service.service.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;

import com.nvp.data.service.bean.VehicleResponseBean;
import com.nvp.data.service.bean.VinFull17RequestBean;
import com.nvp.data.service.config.DataServiceConfig;
import com.nvp.data.service.config.exception.GenericRestException;
import com.nvp.data.service.service.VehicleDetailService;
import com.nvp.data.service.util.Constants;
import com.nvp.data.service.util.Utility;

@Service
public class VehicleDetailServiceImpl implements VehicleDetailService{
	
	private static final Logger logger = LoggerFactory.getLogger(VehicleDetailServiceImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private DataServiceConfig dataServiceConfig;
	
	private boolean checkDuplicateVIN(String vinLast8, String market, String zone, String dealerCode) throws SQLException{
		
		logger.info("Inside checkDuplicateVIN ");
		HashSet<String> vins = new HashSet<>();
		
		logger.debug( "getCount() - STP call to check duplication for last 8 vin: {} " , vinLast8);
		logger.debug( "getCount() - Vehicle market: {} ", market);
		logger.debug( "getCount() - Vehicle zone: {} " , zone);
		logger.debug( "getCount() - Vehicle dealer code: {} " , dealerCode);
		
		Connection conn = null;
		CallableStatement cstmt = null;
		
		try {
			conn = jdbcTemplate.getDataSource().getConnection();
			cstmt = conn.prepareCall("{call GCD4100K (?,?,?,?,?,?,?,?,?,?,?)}");
			cstmt.setString(1, "DC"); //IN-APP-NAME            
			cstmt.setString(2, ""); //IN-VIN-FIRST9
			cstmt.setString(3, vinLast8.toUpperCase()); //IN-VIN-LAST-8
			cstmt.setString(4, "LAST8"); //IN-ACTION-PARM
			cstmt.setString(5, market);// IN-MARKET-CODE
			cstmt.setString(6, zone);// IN-MARKET-ZONE
			cstmt.setString(7, dealerCode);// IN-DEALER
			cstmt.setString(8, "");// IN-FILLER
			cstmt.registerOutParameter(9, java.sql.Types.VARCHAR); 
	        cstmt.registerOutParameter(10, java.sql.Types.CHAR);
	        cstmt.registerOutParameter(11, java.sql.Types.CHAR);
	        cstmt.execute();
	        String outputFirstResult = cstmt.getString(9);
	        String outputSecondResult = cstmt.getString(10);
	        String outputFiller = cstmt.getString(11);
	        logger.debug( "outputFirstResult :: {} ",outputFirstResult);
	        logger.debug("outputSecondResult :: {} ", outputSecondResult);
			if (outputSecondResult != null && !outputSecondResult.trim().isEmpty() && ("0000").equalsIgnoreCase(outputSecondResult.trim().substring(0,4))) {
				setVin(outputFirstResult, vins);
			}else{
				StringBuilder errorMsgBuilder =new StringBuilder();
				errorMsgBuilder.append("ORECORD of STP GCD4100K ::").append(outputFirstResult);
				errorMsgBuilder.append("OFILLER of STP GCD4100K ::").append(outputFiller).append("\n");
				errorMsgBuilder.append("Error In STP GCD4100K ::[ ");
				errorMsgBuilder.append("STP-RETURN-CODE-LIST ::").append(outputSecondResult != null ? outputSecondResult.substring(0,4) : "");
				
				errorMsgBuilder.append("STP-RETURN-SQLCODE ::").append(outputSecondResult != null ? outputSecondResult.substring(5,8): "");
				errorMsgBuilder.append("STP-CRITICAL-TABLE-LIST ::").append(outputSecondResult != null ? outputSecondResult.substring(9,17): "");
				errorMsgBuilder.append("STP-ERROR-PARA-NAME ::").append(outputSecondResult != null ? outputSecondResult.substring(18,58):"").append("]");
				logger.debug(" getCount() - Error in STP : {} " ,errorMsgBuilder);
				throw new GenericRestException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_RESPONSE_VIN_VALID_STP);
			}
		}catch(SQLException ex) {
			logger.error("Error occured while calling STP GCD4100K ");
			throw new GenericRestException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_RESPONSE_VIN_VALID_STP);
		}finally {
			if(cstmt != null) {
				try {
					cstmt.close();
				} catch (SQLException e) {
					logger.error("Error occured while closing statement");
				}
			}
			
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					logger.error("Error occured while closing connection");
				}
			}
		}
		logger.info("Exit checkDuplicateVIN ");
		return vins.size() <= 1;
	}
	
	private void setVin(String outputFirstResult, HashSet<String> vins){
		if (outputFirstResult != null && !outputFirstResult.trim().isEmpty() && outputFirstResult.indexOf('¦') > -1) {	
			String [] fetchedRecord=outputFirstResult.split("¦");				
			for (int counter = 0; counter < fetchedRecord.length; counter++) {
				if(fetchedRecord[counter] != null && fetchedRecord[counter].substring(0, 17) != null && !fetchedRecord[counter].substring(0, 17).trim().isEmpty() && ("SVEHW").equalsIgnoreCase(fetchedRecord[counter].substring(101,108).trim())){
					String vin = fetchedRecord[counter];
					logger.debug("Adding VIN:: {}", vin);
					vins.add(vin);
				}
			}				
		}
	}
	
	@Override
	public VehicleResponseBean getVehicleDetails(VinFull17RequestBean requestBean) {
		
		logger.info("Inside getVehicleDetails ");
		
		//check if VIN is null or empty
		if(requestBean == null || requestBean.getVin() == null || "".equals(requestBean.getVin().trim())) {
			throw new GenericRestException(HttpStatus.BAD_REQUEST, Constants.ERROR_RESPONSE_INVALID_ARGUMENT);
		}
		
		
		String vin = Utility.trimToEmpty(requestBean.getVin());
		String query = null;
		
		List<String> args = new ArrayList<>();
		
		if(vin.length() == 8) {
			
			//check for duplicate VIN
			try {
				if(!checkDuplicateVIN(vin,
						Utility.trimToEmpty(requestBean.getMarket()) , Utility.trimToEmpty(requestBean.getZone()), Utility.trimToEmpty(requestBean.getDealerCode()))) {
					logger.debug("false");
					throw new GenericRestException(HttpStatus.NOT_ACCEPTABLE, Constants.ERROR_RESPONSE_VIN_DUPLICATE);
				}
			} catch (SQLException | GenericRestException e) {
				throw new GenericRestException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_RESPONSE_VIN_VALID_STP);
			}
			
			query = dataServiceConfig.getPropertyData(Constants.GET_VEH_LAST8);
			args.add(vin);
		}else if(vin.length() == 17) {
			query = dataServiceConfig.getPropertyData(Constants.GET_VEH_FULL);
			args.add(vin.substring(9));
			args.add(vin.substring(0,9));
		}else {
			throw new GenericRestException(HttpStatus.BAD_REQUEST, Constants.ERROR_RESPONSE_VIN_LENGTH);
		}
		
		logger.debug("Query :: {} ",query);
		logger.debug("args :: {] ", args.toArray());
		
		List<VehicleResponseBean> detail = jdbcTemplate.query(query, args.toArray(), new ResultSetExtractor<List<VehicleResponseBean>>() {

			@Override
			public List<VehicleResponseBean> extractData(ResultSet rs) throws SQLException {
				VehicleResponseBean vehicleDetail = null;
				List<VehicleResponseBean> vehicleDetailList = new ArrayList<>();
				while(rs.next()) {
					vehicleDetail = new VehicleResponseBean();
					vehicleDetail.setPrtitn(rs.getString("I_PRTITN"));
					vehicleDetail.setFamilyCode(rs.getString("C_FAM"));
					vehicleDetail.setBodyStyle(rs.getString("C_BDY_STYLE"));
					vehicleDetail.setLine(rs.getString("C_LINE"));
					vehicleDetail.setMakeYear(rs.getString("I_MOD_YR"));
					vehicleDetail.setModelYear(rs.getString("I_VIN_MOD_YR"));
					vehicleDetail.setSeries(rs.getString("C_SERIES"));
					vehicleDetail.setVehicleSan(rs.getString("I_VHCL_SAN"));
					vehicleDetail.setVehicleSoldDate(rs.getString("D_VHCL_SOLD"));
					vehicleDetail.setVinFirst9(rs.getString("I_VIN_FIRST_9"));
					vehicleDetail.setVinLast8(rs.getString("I_VIN_LAST_8"));
					vehicleDetailList.add(vehicleDetail);
				}	
				return vehicleDetailList;
			}
		});
		logger.info("Exit getVehicleDetails ");
		return !detail.isEmpty() ? detail.get(0) : null;
	}

	
}
