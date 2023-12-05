package com.art.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	// @Autowired
	// private GlobalInterceptor global;
	// @Autowired
	// private AuthInterceptor accessInterceptor;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**", "/templates/**").addResourceLocations("classpath:/static/",
				"classpath:/templates/");
		registry.addResourceHandler("/static/**").addResourceLocations("file:/static/");

	}

}
