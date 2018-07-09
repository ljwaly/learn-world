package com.learn.word.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


/**
 * 提供ApplicationContext对象实例
 * 
 * 通过实现接口ApplicationContextAware，在启动程序的时候 自动初始化appContext;
 * 
 * 在外部通过ServerContextUtil.getAppContext()得到ApplicationContext对象appContext
 * 然后用appContext.getBean("BeanName")得到配置的bean的实例（即使用Service,Compontent,Controller,Repository注解自动加载的服务类）
 * 
 * @author lijinwu
 *
 */
@Component
public class ServerApplictionContext implements ApplicationContextAware{
	
	private static ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext appContext) throws BeansException {
		applicationContext=appContext;
	}

	public static ApplicationContext getAppContext(){
		return applicationContext;
	}

}
