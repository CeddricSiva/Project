package com.nvp.data.service.util;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nvp.data.service.bean.ApiResponseBean;
import com.nvp.data.service.bean.ErrorDetail;

@Component
public class ErrorResponseUtil {

	public Object errorResponseHandler(HttpClientErrorException httpException) {
		ObjectMapper mapper = new ObjectMapper();

		if (httpException.getRawStatusCode() == 404) {
			try {
				ApiResponseBean apiResponseBean = mapper.readValue(httpException.getResponseBodyAsString(),
						ApiResponseBean.class);
				return null;
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}

		else if (httpException.getRawStatusCode() == 401 || httpException.getRawStatusCode() == 403) {
			ErrorDetail errorDetail = new ErrorDetail();
			errorDetail.setDetail(Constants.UNAUTHORIZED);
			errorDetail.setStatus(httpException.getRawStatusCode());
			errorDetail.setTimeStamp(LocalDateTime.now().toString());
			return errorDetail;
		}
		return null;
	}
}
