package com.nvp.data.service.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.nvp.data.service.bean.CheckListResponseBean;
import com.nvp.data.service.bean.DealerRequestBean;
import com.nvp.data.service.bean.DealerResponseBean;
import com.nvp.data.service.bean.RRTResponseBean;
import com.nvp.data.service.bean.RecallResponseBean;
import com.nvp.data.service.bean.VehicleResponseBean;
import com.nvp.data.service.bean.VinFull17RequestBean;
import com.nvp.data.service.bean.WiTechDealerRequestBean;
import com.nvp.data.service.bean.WiTechDealerResponseBean;
import com.nvp.data.service.bean.WiTechVehicleRequestBean;
import com.nvp.data.service.bean.WiTechVehicleResponseBean;
import com.nvp.data.service.request.bean.RequestBean;
import com.nvp.data.service.request.bean.VinRequestBean;
import com.nvp.data.service.service.CheckListDataService;
import com.nvp.data.service.service.DealerService;
import com.nvp.data.service.service.RRTService;
import com.nvp.data.service.service.RecallDataService;
import com.nvp.data.service.service.VehicleDetailService;
import com.nvp.data.service.service.WiTechDealerEnrollService;
import com.nvp.data.service.service.WiTechVehicleCheckService;
import com.nvp.data.service.util.ErrorResponseUtil;
import com.nvp.data.service.util.RequestValidator;

@RestController
@RequestMapping("/nvpDataService")
public class NVPDataServiceController {
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	WiTechVehicleCheckService wiTechVehicleCheckService;

	@Autowired
	WiTechDealerEnrollService wiTechDealerEnrollService;

	@Autowired
	RRTService rrtService;

	@Autowired
	ErrorResponseUtil errorResponseUtil;

	@Autowired
	RecallDataService recallDataService;

	@Autowired
	CheckListDataService checkListDataService;

	@Autowired
	VehicleDetailService vehicleDetailService;

	@Autowired
	private DealerService dealerService;

	/**
	 * Method to get fetch vehicle details
	 * 
	 * @param requestBean
	 * @return
	 */
	private static final Logger logger = LoggerFactory.getLogger(NVPDataServiceController.class);

	@PostMapping("/getVehicleDetails")
	public ResponseEntity<VehicleResponseBean> getVehicle(@RequestBody VinFull17RequestBean requestBean) {
		logger.info("getVehicle  called..");
		return new ResponseEntity<>(vehicleDetailService.getVehicleDetails(requestBean), HttpStatus.OK);
	}

	@PostMapping("/getDealerInfo")
	public ResponseEntity<DealerResponseBean> getDealerDetails(@RequestBody DealerRequestBean requestBean) {
		logger.info("getVehicle  called..");
		return new ResponseEntity<>(dealerService.getDealerInfo(requestBean), HttpStatus.OK);
	}

	@PostMapping("/getCheckListData")
	public ResponseEntity<CheckListResponseBean> getCheckListData(@RequestBody RequestBean requestBean) {
		logger.info("getCheckListData called..");
		return new ResponseEntity<>(checkListDataService.fetchCheckListData(requestBean), HttpStatus.OK);
	}

	@PostMapping("/getRRT")
	public ResponseEntity<List<RRTResponseBean>> getRRTDetails(@RequestBody VinRequestBean requestBean) {
		logger.info("getRRT  called..");
		RequestValidator.validateVinRequest(requestBean);
		List<RRTResponseBean> rrtDtoList = rrtService.fetchRRTDetails(requestBean);
		return new ResponseEntity<>(rrtDtoList, HttpStatus.OK);
	}

	@PostMapping("/isWiTechVehicle")
	public ResponseEntity<WiTechVehicleResponseBean> isWiTechVehicle(
			@RequestBody WiTechVehicleRequestBean requestBean) {
		logger.info("isWiTechVehicle  called..");
		WiTechVehicleResponseBean responseBean = wiTechVehicleCheckService.isWiTechVehicle(requestBean);
		return new ResponseEntity<>(responseBean, HttpStatus.OK);
	}

	@PostMapping("/getEnrollment")
	public ResponseEntity<List<WiTechDealerResponseBean>> getEnrollment(
			@RequestBody WiTechDealerRequestBean requestBean) {
		logger.info("getEnrollment  called..");
		List<WiTechDealerResponseBean> dataList = wiTechDealerEnrollService.getDealerEnrollment(requestBean);
		return new ResponseEntity<>(dataList, HttpStatus.OK);
	}

	@PostMapping("/getRecall")
	public ResponseEntity<List<RecallResponseBean>> getRecallDetails(@RequestBody VinRequestBean requestBean) {
		logger.info("getRecall called..");
		RequestValidator.validateVinRequest(requestBean);
		return new ResponseEntity<>(recallDataService.getRecallData(requestBean), HttpStatus.OK);
	}
}
