package com.learn.word.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@EnableCaching
@PropertySource(value = "classpath:/ehredis-${spring.profiles.active}.properties")
public class EhCacheRedisConf {

	@Autowired
	private RedisTemplate<String, ?> redisTemplate;

	@Value("${application.l2IsDisable}")
	private boolean l2IsDisable;

	@Primary
	@Bean(name = "assetDotEhRedisCacheManager")
	public SimpleCacheManager assetDotEhRedisManagerFactoryBean(EhCacheCacheManager ehCacheCacheManager,
			@Value(value = "${assetDot.ehcache.name}") String ehcacheName,
			@Value(value = "${assetDot.redis.expiration}") Long expiration) {
		EhRedisCache ehRedisCache = EhRedisCacheUtil.ehRedisCache(ehcacheName, ehCacheCacheManager, redisTemplate,
				expiration, l2IsDisable);
		return EhRedisCacheUtil.ehRedisCacheManager(ehRedisCache);
	}

	@Bean(name = "channelPkgEhRedisCacheManager")
	public SimpleCacheManager channelPkgEhRedisManagerFactoryBean(EhCacheCacheManager ehCacheCacheManager,
			@Value(value = "${channelpkg.ehcache.name}") String ehcacheName,
			@Value(value = "${channelpkg.redis.expiration}") Long expiration) {
		EhRedisCache ehRedisCache = EhRedisCacheUtil.ehRedisCache(ehcacheName, ehCacheCacheManager, redisTemplate,
				expiration, l2IsDisable);
		return EhRedisCacheUtil.ehRedisCacheManager(ehRedisCache);
	}
	
	
	
}
