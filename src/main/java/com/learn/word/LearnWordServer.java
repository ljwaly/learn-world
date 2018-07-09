package com.learn.word;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;


/**
 * 启动类
 * @author PC
 *
 */
@SpringBootApplication
public class LearnWordServer {
	
	private static final Logger log = LoggerFactory.getLogger(LearnWordServer.class);
	
	public static void main(String[] args) {
		SpringApplication springApplication =  new SpringApplication(LearnWordServer.class);
		//启动springboot
		ConfigurableApplicationContext run = springApplication.run(args);
		
		//获取一些环境参数
		ConfigurableEnvironment environment = run.getEnvironment();
		String programName = environment.getProperty("spring.application.name");
		String port = environment.getProperty("server.port");
		
		log.info("\n----------------------------------------------------------\n\t"
				+ "Application {} is running! Access URLs:\n\t" // 大括号1
				+ "Local: \t\thttp://localhost:{}\n\t" // 大括号2
				+ "\n\t" 
				+ "\n----------------------------------------------------------"
				
				,programName
				,port);
		
		
	}
}
