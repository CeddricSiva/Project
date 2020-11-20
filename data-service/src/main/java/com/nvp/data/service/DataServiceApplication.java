package com.nvp.data.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class DataServiceApplication {

	private static final Logger logger = LoggerFactory.getLogger(DataServiceApplication.class);

	public static void main(String[] args) {
		logger.info("Starting application..");
		SpringApplication.run(DataServiceApplication.class, args);
	}

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

}
