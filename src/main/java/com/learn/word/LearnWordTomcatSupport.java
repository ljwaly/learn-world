package com.learn.word;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
/**
 * 部署于tomcat下的支持
 * @author PC
 *
 */
public class LearnWordTomcatSupport extends SpringBootServletInitializer{

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		SpringApplicationBuilder sources = builder.sources(LearnWordServer.class);
		return sources;
	}
}
