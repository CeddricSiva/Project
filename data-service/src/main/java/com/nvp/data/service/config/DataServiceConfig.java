package com.nvp.data.service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
//@PropertySource(value = { "file:D:/NVP/NvpSourceCode/NVP-DataService_18Nov2020/props/application.properties", "file:D:/NVP/NvpSourceCode/NVP-DataService_18Nov2020/props/query.properties",
//"file:D:/NVP/NvpSourceCode/NVP-DataService_18Nov2020/props/messages.properties" })
@PropertySource("classpath:query.properties")
public class DataServiceConfig {

	@Autowired
	Environment env;

	public String getPropertyData(String key) {
		return env.getProperty(key);
	}
}
