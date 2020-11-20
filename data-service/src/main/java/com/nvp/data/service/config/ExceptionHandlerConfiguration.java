package com.nvp.data.service.config;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.nvp.data.service.bean.ErrorDetail;
import com.nvp.data.service.config.exception.GenericRestException;
import com.nvp.data.service.util.Constants;


@RestControllerAdvice
public class ExceptionHandlerConfiguration extends ResponseEntityExceptionHandler{

	private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerConfiguration.class);
	
	@Autowired
	MessageSource messageSource;
	
	/**
	 * Method to handle resource not found exception.
	 * @param resourceNotFoundException
	 * @param locale
	 * @return ResponseEntity
	 * @throws Exception 
	 */
	@ExceptionHandler(GenericRestException.class)
	public ResponseEntity<?> handleGenericRestException(GenericRestException ex, Locale locale, WebRequest request) throws Exception{
		logger.info("Inside handleGenericRestException ");
		return Optional.of(ex)
				.map(c -> {
					
					ErrorDetail errorDetail = new ErrorDetail();
					errorDetail.setTitle(messageSource.getMessage(Constants.ERROR_RESPONSE_TITLE, null, locale));
					errorDetail.setStatus(ex.getHttpStatus().value());
					errorDetail.setTimeStamp(LocalDateTime.now().toString());
					errorDetail.setDetail(messageSource.getMessage(ex.getMessage(),ex.getPayloads(), locale));
					ex.printStackTrace();
					return new ResponseEntity<>(errorDetail, ex.getHttpStatus());
				})
				.orElseThrow(() -> new Exception(messageSource.getMessage(Constants.ERROR_RESPONSE_TITLE, null, locale)));
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		ErrorDetail errorDetail = new ErrorDetail();
		errorDetail.setTitle(messageSource.getMessage(Constants.ERROR_RESPONSE_TITLE, null, request.getLocale()));
		errorDetail.setStatus(status.value());
		errorDetail.setTimeStamp(LocalDateTime.now().toString());
		errorDetail.setDetail(messageSource.getMessage(Constants.ERROR_RESPONSE_INVALID_ARGUMENT,null, request.getLocale()));
		
		return new ResponseEntity<>(errorDetail, status);
	}
	
	//InvalidDataAccessApiUsageException
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
			try {
				return Optional.of(ex)
						.map(c -> {
							
							ErrorDetail errorDetail = new ErrorDetail();
							errorDetail.setTitle(messageSource.getMessage(Constants.ERROR_RESPONSE_TITLE, null, request.getLocale()));
							errorDetail.setStatus(HttpStatus.BAD_REQUEST.value());
							errorDetail.setTimeStamp(LocalDateTime.now().toString());
							errorDetail.setDetail(ex.getMessage());
							
							return new ResponseEntity<Object>(errorDetail, HttpStatus.BAD_REQUEST);
						})
						.orElseThrow(() -> new Exception(messageSource.getMessage(Constants.ERROR_RESPONSE_TITLE, null, request.getLocale())));
			} catch (Exception e) {
				logger.error("Exception While handling error :: {} ", e.getMessage());
			}
		return null;
	}

}
