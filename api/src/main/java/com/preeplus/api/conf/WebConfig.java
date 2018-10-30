package com.preeplus.api.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

	@Override
	protected void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowCredentials(true);
	}
}
