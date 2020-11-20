package com.nvp.data.service.util;

public interface Constants {

	String STR_Y = "Y";
	String STR_N = "N";

	String TEST_WCC_QUERY = "dataservice.query.test";

	String GET_SALES_CODE = "dataservice.query.get_salescode";
	String GET_WCC = "dataservice.query.get_wcc";
	String GET_RECALL_SRCDTL05 = "dataservice.query.get_recall_srcdtl05";
	String GET_RECALL_SRCLC = "dataservice.query.get_recall_srclc";
	String GET_RECALL_SRCPART = "dataservice.query.get_recall_srcpart";
	String RRT_NUMBER_QUERY = "dataservice.query.fetch.rttNumber";
	String RRT_DETAILS_QUERY = "dataservice.query.fetch.rttDetails";
	String GET_VEH_LAST8 = "dataservice.query.last8.vin";
	String GET_VEH_FULL = "dataservice.query.full.vin";

	String PARM_FACTY_DATA_NOT_FOUND = "response.error.parm.facty.data.not.found";
	String UNAUTHORIZED = "UnAuthorized";
	String ERROR_RESPONSE_TITLE = "response.error.title";
	String ERROR_RESPONSE_INVALID_ARGUMENT = "response.error.method.argument.invalid.msg";
	String ERROR_RESPONSE_NVP_DATA_SERVICE_QUERY_NOT_FOUND = "response.error.nvp.query.not.found";
	String ERROR_RESPONSE_NVP_DATA_SERVICE_DATA_NOT_FOUND = "response.error.nvp.data.not.found";
	String ERROR_RESPONSE_RRT_DATA_NOT_FOUND_FOR_VIN = "response.error.rrt.data.not.for.vin";
	String ERROR_RESPONSE_RRT_NUMBERLIST_NOT_FOUND_FOR_VIN = "response.error.rrt.number.list.not.for.vin";

	String ERROR_RESPONSE_DEALER_INFO = "response.error.dealer.info";

	String ERROR_RESPONSE_VIN_VALID_STP = "response.error.vin.valid.stp";

	String VEHICLE_DETAILS_QUERY = "dataservice.vehicle.details.query";
	String SALES_CODE_QUERY = "dataservice.query.get_salescode";
	String PARM_DATA_NOT_FOUND = "response.error.parm.data.not.found";
	String DEALER_GET_LANGUAGE_QUERY = "dataservice.dealer.language.query";
	String PARM_GET_MASTERPARM_QUERY = "dataservice.parm.query.fetch.masterparm";
	String PARM_LANGUAGE_CODE_PARM_QUERY = "dataservice.parm.query.fetch.languageCode";
	String PARM_GET_PARM_QUERY = "dataservice.parm.query.fetch.parm";
	String PARM_GET_FACTY_SALES_QUERY = "dataservice.parm.query.fetch.facty.sales";

	String WITECH_VEHICLE_CHECK_QUERY = "wiTech.vehicle.check.query";
	String WITECH_ENROLLED_DEALER_QUERY = "wiTech.enrolled.dealer.query";

	String ERROR_RESPONSE_VIN_LENGTH = "response.error.vin.length";
	String ERROR_RESPONSE_VIN_DUPLICATE = "response.error.vin.duplicate";

	String SALESCODEOTC = "salesCodeOtc";
	String SALESCODEDJ = "salesCodeDj";
	String SALESCODEKP = "salesCodeKp";
	String SALESCODEQZ = "salesCodeQz";
	String SALESCODE09 = "salesCode09";
	// Date format
	String DATE_FORMAT = "MM/dd/yyyy";
	String DATE_TIME_FORMAT = "MM/dd/yyyy HH:mm:ss";
	String DB_DATE_FORMAT = "yyyy-MM-dd";
	String DEFAULT_HH_MM_SS = " 01:01:01";

	String EMPTY_SPACE = " ";

	String US_MKT = "US_MKT";
	String CN_MKT = "CN_MKT";
	String MX_MKT = "MX_MKT";
	String IN_MKT = "IN_MKT";

	public static final long JWT_TOKEN_VALIDITY = 1000 * 60l;
}
