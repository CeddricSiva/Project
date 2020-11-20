package com.nvp.data.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
class DataServiceApplicationTests {

	@Autowired
	ObjectMapper mapper;
	
	@Test
	void contextLoads() {
	}

}
