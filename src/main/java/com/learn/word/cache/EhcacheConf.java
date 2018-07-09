package com.learn.word.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;




/**
 * 创建ehcache管理器：EhCacheCacheManager
 * @author PC
 *
 */

@Configuration
@EnableCaching
public class EhcacheConf {
	Logger log = LoggerFactory.getLogger(EhcacheConf.class);
	
	

	/*
	 * ehcache 主要的管理器
	 */
	@Bean
	public EhCacheCacheManager ehCacheCacheManager(EhCacheManagerFactoryBean bean) {
		EhCacheCacheManager ehCacheCacheManager = new EhCacheCacheManager();
		ehCacheCacheManager.setCacheManager(bean.getObject());
		return ehCacheCacheManager;
	}

	/*
	 * 据shared与否的设置,Spring分别通过CacheManager.create()或new
	 * CacheManager()方式来创建一个ehcache基地.
	 */
	@Bean
	public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
		String path = "";//可以配置xml读取路径例如：path= classpath:/properties/dev/ehcache.xml
		EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean();
		cacheManagerFactoryBean.setShared(true);
		if (path != null && !"".equals(path)) {
			cacheManagerFactoryBean.setConfigLocation(new ClassPathResource(path));
		}
		return cacheManagerFactoryBean;
	}
	

	
}
