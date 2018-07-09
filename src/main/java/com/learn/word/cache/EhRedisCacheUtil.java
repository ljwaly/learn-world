package com.learn.word.cache;

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.core.RedisTemplate;

public class EhRedisCacheUtil {

	Logger log = LoggerFactory.getLogger(EhRedisCacheUtil.class);
	
	private static final String EH_REDIS_CACHE_NAME_PREFIX = "EhRedis_";
	private static final String REDIS_CACHE_NAME_PREFIX = "Redis_";
	private static final Long REDIS_CACHE_DEFAULT_EXPIRE = 120L;

	public static EhRedisCache ehRedisCache(String ehcacheName, EhCacheCacheManager ehCacheCacheManager,
			RedisTemplate<String, ?> redisTemplate, Long expiration, boolean l2IsDisable) {

		EhRedisCache ehRedisCache = new EhRedisCache(l2IsDisable);

		// L1:一级缓存ehcache默认开启
		EhCacheCache ehCacheCache = new EhCacheCache(ehCacheCacheManager.getCacheManager().getEhcache(ehcacheName));

		// L2：二级缓存redis可以通过开关选择开启or关闭
		if (!l2IsDisable) {
			// redis
			if (expiration == null) {
				expiration = REDIS_CACHE_DEFAULT_EXPIRE;
			}
			RedisCache redisCache = new RedisCache(ehcacheName, REDIS_CACHE_NAME_PREFIX.getBytes(), redisTemplate,
					expiration);
			ehRedisCache.setRedisCache(redisCache);
		}

		ehRedisCache.setEhCacheCache(ehCacheCache);
		ehRedisCache.setName(EH_REDIS_CACHE_NAME_PREFIX + ehcacheName);

		return ehRedisCache;
	}

	public static SimpleCacheManager ehRedisCacheManager(EhRedisCache ehRedisCache) {
		SimpleCacheManager ehRedisCacheManager = new SimpleCacheManager();
		Collection<EhRedisCache> caches = new ArrayList<EhRedisCache>();
		caches.add(ehRedisCache);
		ehRedisCacheManager.setCaches(caches);
		return ehRedisCacheManager;
	}

}
