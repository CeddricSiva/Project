package com.nvp.data.service.util;

import org.springframework.http.HttpStatus;

import com.nvp.data.service.config.exception.GenericRestException;
import com.nvp.data.service.request.bean.VinRequestBean;

public class RequestValidator {

	RequestValidator() {
	}

	public static void validateVinRequest(VinRequestBean dto) {

		if (isEmptyOrNull(dto.getVinFirst9()) || isEmptyOrNull(dto.getVinLast8())) {
			throw new GenericRestException(HttpStatus.NOT_FOUND, Constants.ERROR_RESPONSE_INVALID_ARGUMENT,
					"Enter proper vin detail");
		}

		if (dto.getVinFirst9().length() != 9 || dto.getVinLast8().length() != 8) {
			throw new GenericRestException(HttpStatus.NOT_FOUND, Constants.ERROR_RESPONSE_INVALID_ARGUMENT,
					"Enter proper vin detail");
		}
	}

	public static boolean isEmptyOrNull(String parm) {
		return parm == null || parm.trim().isEmpty();
	}

}