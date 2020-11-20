package com.nvp.data.service.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;

import com.nvp.data.service.bean.CheckListResponseBean;
import com.nvp.data.service.bean.ParmDTO;
import com.nvp.data.service.bean.ParmHeaderDTO;
import com.nvp.data.service.bean.ParmTextDTO;
import com.nvp.data.service.config.DataServiceConfig;
import com.nvp.data.service.config.exception.GenericRestException;
import com.nvp.data.service.mapper.MasterParmDetailsExtractor;
import com.nvp.data.service.mapper.ParmDetailsMapper;
import com.nvp.data.service.request.bean.RequestBean;
import com.nvp.data.service.service.CheckListDataService;
import com.nvp.data.service.util.Constants;

@Service
public class CheckListDataServiceImpl implements CheckListDataService {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	DataServiceConfig dataServiceConfig;

	private static final int INTVALUE48 = 48;
	private static final int INTVALUE57 = 57;
	private static final int INTVALUE68 = 68;
	private static final int INTVALUE74 = 74;
	private static final int INTVALUE75 = 75;
	private static final int INTVALUE80 = 80;
	private static final int INTVALUE81 = 81;
	private static final int INTVALUE90 = 90;

	private static final Logger logger = LoggerFactory.getLogger(CheckListDataServiceImpl.class);

	Boolean scPresent = false;
	String temp1SalesCode = "";
	String temp2SalesCode = "";

	@Override
	public CheckListResponseBean fetchCheckListData(RequestBean requestBean) {
		logger.info("Inside fetchCheckListData");
		String dealerLanguage = this.getDealerLanguage(requestBean.getDealerCode());
		ParmDTO masterParmDTO = this.getMasterParmDetails(requestBean, dealerLanguage);
		List<ParmDTO> parmlist = this.getParmDetails(masterParmDTO.getMasterParmId(), requestBean);
		logger.info("End of fetchCheckListData");
		return getParmDataResponseBean(parmlist);
	}

	public String getDealerLanguage(String dealerCode) {
		logger.debug("****Inside getLanguage");
		String query = dataServiceConfig.getPropertyData(Constants.DEALER_GET_LANGUAGE_QUERY);
		return jdbcTemplate.queryForObject(query, String.class, dealerCode);
	}

	public ParmDTO getMasterParmDetails(RequestBean requestBean, String languageCode) {
		logger.debug("****Inside getMasterParmDetails");
		String query = dataServiceConfig.getPropertyData(Constants.PARM_GET_MASTERPARM_QUERY);
		Object[] parameters = { requestBean.getMarket(), requestBean.getZone(), languageCode };

		/*** Fetching masterParmDetails via query PARM_GET_MASTERPARM_QUERY ***/
		return jdbcTemplate.query(query, parameters, new MasterParmDetailsExtractor());
	}

	public String getParmLanguage(String language) {
		logger.debug("****Inside getParmLanguage");
		String query = dataServiceConfig.getPropertyData(Constants.PARM_LANGUAGE_CODE_PARM_QUERY);
		return jdbcTemplate.queryForObject(query, String.class, language);
	}

	public List<ParmDTO> getParmDetails(String parmId, RequestBean requestBean) {
		logger.debug("****Inside getParmDetails");
		boolean scFlag = false;
		String query = dataServiceConfig.getPropertyData(Constants.PARM_GET_PARM_QUERY);
		Object[] parameters = { parmId, requestBean.getMarket(), requestBean.getModelYear(), requestBean.getModelYear(),
				requestBean.getFamCode(), requestBean.getZone(), requestBean.getState() };

		List<ParmDTO> fetchdParmDtoList = jdbcTemplate.query(query, parameters, new ParmDetailsMapper());
		List<ParmDTO> parmlist = new ArrayList<>();

		if (fetchdParmDtoList.isEmpty())
			throw new GenericRestException(HttpStatus.NOT_FOUND, Constants.PARM_DATA_NOT_FOUND);

		for (ParmDTO parmdto : fetchdParmDtoList) {
			String scUpper = parmdto.getSalescode();
			scUpper = scUpper.toUpperCase();
			parmdto.setSalescode(scUpper);
			parmdto.setItemType(parmdto.getItemType().toUpperCase());
			if (!("*".equals(scUpper)) && !("".equals(scUpper))) {
				scFlag = true;
			}
			parmlist.add(parmdto);
		}

		if (scFlag) {
			return this.filterSalesCodes(parmlist, this.getSalesCodes(requestBean));
		}

		return parmlist;
	}

	public Map<String, List<String>> getSalesCodes(RequestBean requestBean) {
		String query = dataServiceConfig.getPropertyData(Constants.SALES_CODE_QUERY);
		logger.debug(requestBean.getPrtitn());
		logger.debug(requestBean.getVehicleSan());
		return jdbcTemplate.query(query, new Object[] { requestBean.getPrtitn(), requestBean.getVehicleSan() },
				new ResultSetExtractor<Map<String, List<String>>>() {

					@Override
					public Map<String, List<String>> extractData(ResultSet rs) throws SQLException {
						Map<String, List<String>> salesCodeMap = new HashMap<>();
						while (rs.next()) {
							salesCodeMap.put(Constants.SALESCODEOTC,
									refactorSalesCodes(rs.getString("C_VHCL_SALES_OT_C")));
							salesCodeMap.put(Constants.SALESCODEDJ,
									refactorSalesCodes(rs.getString("C_VHCL_SALES_D_J")));
							salesCodeMap.put(Constants.SALESCODEKP,
									refactorSalesCodes(rs.getString("C_VHCL_SALES_K_P")));
							salesCodeMap.put(Constants.SALESCODEQZ,
									refactorSalesCodes(rs.getString("C_VHCL_SALES_Q_Z")));
							salesCodeMap.put(Constants.SALESCODE09,
									refactorSalesCodes(rs.getString("C_VHCL_SALES_0_9")));
						}
						return salesCodeMap;
					}
				});
	}

	public List<String> refactorSalesCodes(String salesCodeString) {
		logger.debug("refactor...");
		logger.debug(salesCodeString);
		List<String> salesCodeList = new ArrayList<>();
		if (salesCodeString != null) {

			String temp = null;
			int rem = (salesCodeString.length()) % 4;
			if (rem != 0) {
				rem = 4 - rem;
				for (int i = 0; i < rem; i++) {
					salesCodeString = salesCodeString + Constants.EMPTY_SPACE;
				}
			}
			for (int i = 0; i < salesCodeString.length(); i += 4) {
				temp = salesCodeString.substring(i, i + 4);
				temp = temp.trim();
				salesCodeList.add(temp);
			}
		}
		return salesCodeList;
	}

	private boolean isMatchPresent(List<String> salesCodeList, String salesCodeToMatch) {
		logger.debug("inside isMatch");
		logger.debug(salesCodeToMatch);
		return salesCodeList.stream().anyMatch(sc -> salesCodeToMatch.equals(String.valueOf(sc).substring(0, 3)));
	}

	private boolean matchSalesCode(String parmSalesCode, Map<String, List<String>> salesCodeMap) {

		char[] charArray = parmSalesCode.toCharArray();
		int firstChar = (int) charArray[0];
		logger.debug("First Char :: {} ", firstChar);

		if (firstChar >= INTVALUE68 && firstChar <= INTVALUE74) {
			return isMatchPresent(salesCodeMap.get(Constants.SALESCODEDJ), parmSalesCode);
		} else if (firstChar >= INTVALUE75 && firstChar <= INTVALUE80) {
			return isMatchPresent(salesCodeMap.get(Constants.SALESCODEKP), parmSalesCode);
		} else if (firstChar >= INTVALUE81 && firstChar <= INTVALUE90) {
			return isMatchPresent(salesCodeMap.get(Constants.SALESCODEQZ), parmSalesCode);
		} else if (firstChar >= INTVALUE48 && firstChar <= INTVALUE57) {
			return isMatchPresent(salesCodeMap.get(Constants.SALESCODE09), parmSalesCode);
		} else {
			return isMatchPresent(salesCodeMap.get(Constants.SALESCODEOTC), parmSalesCode);
		}
	}

	private List<ParmDTO> filterSalesCodes(List<ParmDTO> parmList, Map<String, List<String>> salesCodeMap) {

		List<ParmDTO> filteredParmList = new ArrayList<>();
		parmList.forEach(parm -> {
			boolean isSalesCodePresent = false;
			String parmSalesCode = parm.getSalescode();

			if ("*".equals(parmSalesCode)) {
				isSalesCodePresent = true;
			} else {
				isSalesCodePresent = matchSalesCode(parmSalesCode, salesCodeMap);
			}

			if (isSalesCodePresent) {
				filteredParmList.add(parm);
			}
		});
		return filteredParmList;
	}

	/**
	 * This method is used to generate the ParmDataResponseBean by adding the
	 * checkList header and section data.
	 * 
	 * @param parmList
	 * @return
	 */
	public CheckListResponseBean getParmDataResponseBean(List<ParmDTO> parmList) {

		CheckListResponseBean parmDataResponseBean = new CheckListResponseBean();
		Map<Integer, List<ParmHeaderDTO>> sectionHeaderMap = new HashMap<>();
		Map<Integer, List<ParmTextDTO>> sectionTextMap = new HashMap<>();
		List<String> sectionTextDuplicateList = new ArrayList<>();
		List<String> specialMessageList = new ArrayList<>();

		parmList.stream().forEach(parm -> {
			int groupId = Integer.parseInt(parm.getGroupID());
			String itemType = parm.getItemType();

			if ("H".equals(itemType)) {
				ParmHeaderDTO headerDTO = new ParmHeaderDTO();
				headerDTO.setOrderNum(parm.getItemOrderNum());
				headerDTO.setHeaderData(parm.getText());
				if (sectionHeaderMap.containsKey(groupId)) {
					sectionHeaderMap.get(groupId).add(headerDTO);
				} else {
					List<ParmHeaderDTO> headerList = new ArrayList<>();
					headerList.add(headerDTO);
					sectionHeaderMap.put(groupId, headerList);
				}
			} else if ("C".equals(itemType) || "T".equals(itemType)) {
				ParmTextDTO parmTextDTO = new ParmTextDTO();
				parmTextDTO.setOrderNum(parm.getItemOrderNum());
				parmTextDTO.setTextData(parm.getText());
				parmTextDTO.setTextType(itemType);
				String textId = groupId + "-" + itemType + "-" + parm.getText();
				List<ParmTextDTO> sectionTextList = null;
				if (sectionTextMap.containsKey(groupId)) {
					sectionTextList = sectionTextMap.get(groupId);
				} else {
					sectionTextList = new ArrayList<>();
				}
				checkDuplicateText(textId, itemType, sectionTextDuplicateList, sectionTextList, parmTextDTO);
				sectionTextMap.put(groupId, sectionTextList);

			} else if ("M".equals(itemType)) {
				specialMessageList.add(parm.getText());
			}
		});

		parmDataResponseBean.setHeader(sectionHeaderMap);
		parmDataResponseBean.setParmText(sectionTextMap);
		parmDataResponseBean.setSpecialMessage(specialMessageList);

		return parmDataResponseBean;
	}

	/**
	 * @param id
	 * @param check_typ
	 * @param sectionTextList
	 * @param texttdo
	 */
	private void checkDuplicateText(String id, String checktyp, List<String> sectionTextDuplicate,
			List<ParmTextDTO> sectionTextList, ParmTextDTO texttdo) {
		if ("C".equals(checktyp)) {
			if (!sectionTextDuplicate.contains(id)) {
				sectionTextDuplicate.add(id);
				sectionTextList.add(texttdo);
			}
		} else {
			sectionTextList.add(texttdo);
		}
	}
}
