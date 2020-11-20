package com.nvp.data.service.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.nvp.data.service.bean.RecallResponseBean;
import com.nvp.data.service.config.DataServiceConfig;
import com.nvp.data.service.config.exception.GenericRestException;
import com.nvp.data.service.mapper.SrclsMapper;
import com.nvp.data.service.mapper.Srcsdtlod5Mapper;
import com.nvp.data.service.request.bean.VinRequestBean;
import com.nvp.data.service.service.RecallDataService;
import com.nvp.data.service.util.Constants;

@Service
public class RecallDataServiceImpl implements RecallDataService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	DataServiceConfig dataServiceConfig;

	private static final int INTVALUE25 = 25;
	private static final String NO_PARTS_REQ = "NO PARTS REQUIRED";
	private static final String SEE_RECALL_NOTICE = "SEE RECALL NOTICE";
	boolean partflag = false;
	boolean recallflag = false;
	boolean nopartflag = false;
	String partnum = "";
	private static final Logger logger = LoggerFactory.getLogger(RecallDataServiceImpl.class);

	/**
	 * Provide Recall Data
	 */
	@Override
	public ArrayList<RecallResponseBean> getRecallData(VinRequestBean requestBean) {
		logger.info("Inside RecallDatsServiceImpl");
		int cnt = 0;
		ArrayList<RecallResponseBean> recallListtemp = new ArrayList<>();
		ArrayList<RecallResponseBean> recallList = new ArrayList<>();

		List<RecallResponseBean> recallSrcdtl = jdbcTemplate.query(
				dataServiceConfig.getPropertyData(Constants.GET_RECALL_SRCDTL05),
				new Object[] { requestBean.getVinLast8(), requestBean.getVinFirst9() }, new Srcsdtlod5Mapper());
		cnt += recallSrcdtl.size();
		recallSrcdtl.forEach(recallListtemp::add);

		List<RecallResponseBean> recallSrclc = jdbcTemplate.query(
				dataServiceConfig.getPropertyData(Constants.GET_RECALL_SRCLC),
				new Object[] { requestBean.getVinLast8(), requestBean.getVinFirst9() }, new SrclsMapper());
		cnt += recallSrclc.size();
		recallSrclc.forEach(recallListtemp::add);

		if (cnt <= INTVALUE25) {

			recallListtemp.forEach(recall -> {
				partflag = false;
				recallflag = false;
				nopartflag = false;

				if (!recall.isPartNumDtl05()) {
					jdbcTemplate.query(dataServiceConfig.getPropertyData(Constants.GET_RECALL_SRCPART),
							new Object[] { recall.getNumber(), recall.getPartType() }, (rs, rowNum) -> {
								partflag = true;
								partnum = rs.getString("I_PART").trim();
								recall.setNoPartFlag(nopartflag);
								recall.setRecallFlag(recallflag);
								setAllPartNumbers(recall, partnum);
								nopartflag = recall.isNoPartFlag();
								recallflag = recall.isRecallFlag();
								return partnum;
							});
				}
				if (!partflag) {
					if (("99").equals(recall.getPartType())) {
						recall.setPartNum1(NO_PARTS_REQ);
					} else {
						recall.setPartNum1(SEE_RECALL_NOTICE);
					}
				}
				recallList.add(recall);
			});
		}

		if (!recallList.isEmpty()) {
			logger.info("End of fetchCheckListData");
			return recallList;
		} else
			throw new GenericRestException(HttpStatus.NOT_FOUND,
					Constants.ERROR_RESPONSE_NVP_DATA_SERVICE_DATA_NOT_FOUND);

	}

	/**
	 * @param newRecall
	 * @param newPartNum
	 * @return Recall Data validation
	 */
	public RecallResponseBean setAllPartNumbers(RecallResponseBean recall, String partnum) {
		RecallResponseBean newRecall = recall;
		String newPartNum = partnum;
		if (("").equals(newPartNum) && ("99").equals(newRecall.getPartType())
				&& ("").equals(newRecall.getPartNum1().trim())) {
			newRecall.setNoPartFlag(true);
			newPartNum = NO_PARTS_REQ;
			newRecall.setPartNum1(newPartNum);
		} else if (("").equals(newPartNum) && ("").equals(newRecall.getPartNum1().trim())) {
			newRecall.setRecallFlag(true);
			newPartNum = SEE_RECALL_NOTICE;
			newRecall.setPartNum1(newPartNum);
		} else if (!("").equals(newPartNum)) {
			if (("").equals(newRecall.getPartNum1().trim())) {
				newRecall.setPartNum1(newPartNum);
			} else if (("").equals(newRecall.getPartNum2().trim())) {
				if (newRecall.isNoPartFlag() || newRecall.isRecallFlag()) {
					newRecall.setPartNum1(newPartNum);
					newRecall.setNoPartFlag(false);
					newRecall.setRecallFlag(false);
				} else {
					newRecall.setPartNum2(newPartNum);
				}
			} else if (("").equals(newRecall.getPartNum3().trim())) {
				newRecall.setPartNum3(newPartNum);
			}
		}
		return newRecall;
	}
}
