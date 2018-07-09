package com.learn.word.cache;

import java.util.concurrent.Callable;

import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.data.redis.cache.RedisCache;

import net.sf.ehcache.Element;
/**
 * ehcache&redis 二级缓存
 * @author chengcong
 *
 */

public class EhRedisCache implements Cache {
	
	/**
	 * ehcache缓存
	 */
	private EhCacheCache ehCacheCache;
	/**
	 * redis缓存
	 */
	private RedisCache redisCache;
	/**
	 * 此二级缓存名称
	 */
	private String name;
	
	private boolean l2IsDisable;
	
	/**
	 * EhCacheCache.cache.name
	 */
	private String ehcacheName;
	
	public EhRedisCache() {
	}
	
	public EhRedisCache(boolean l2IsDisable) {
		this.l2IsDisable = l2IsDisable;
	}
	
	public EhRedisCache(EhCacheCache ehCacheCache, RedisCache redisCache, String name, boolean l2IsDisable) {
		this.ehCacheCache = ehCacheCache;
		this.redisCache = redisCache;
		this.name = name;
		this.l2IsDisable = l2IsDisable;
	}
	
	public EhCacheCache getEhCacheCache() {
	
		return ehCacheCache;
	}
	
	public boolean isL2IsDisable() {
		return l2IsDisable;
	}

	public void setL2IsDisable(boolean l2IsDisable) {
		this.l2IsDisable = l2IsDisable;
	}

	/**
	 * set the ehCacheCache
	 * @param ehCacheCache
	 */
	public void setEhCacheCache(EhCacheCache ehCacheCache) {
		this.ehCacheCache = ehCacheCache;
	}

	public RedisCache getRedisCache() {
		return redisCache;
	}

	/**
	 * set the redisCache
	 * @param redisCache
	 */
	public void setRedisCache(RedisCache redisCache) {
		this.redisCache = redisCache;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getEhcacheName() {
		return ehcacheName;
	}

	public void setEhcacheName(String ehcacheName) {
		this.ehcacheName = ehcacheName;
	}

	public Object getNativeCache() {
		return this;
	}

	/**
	 * 优先从ehcache中取，如果不存在，从redis中取（redis中取到非空值，则更新到ehcache）
	 */
	public ValueWrapper get(Object key) {
		ValueWrapper valueWrapper = ehCacheCache.get(key);
		if (valueWrapper != null) {
			return valueWrapper;
		}
		
		//L2：二级缓存redis可以通过开关选择开启or关闭
		if(!l2IsDisable) {
			try {
				valueWrapper = redisCache.get(key);
				if (valueWrapper != null) {
					ehCacheCache.put(key, valueWrapper.get());
				}else {
				}
			} catch (Exception e) {
			}
		}
		return valueWrapper;
	}

	@SuppressWarnings("unchecked")
	public <T> T get(Object key, Class<T> type) {
		Object value = null;
		try {
			value = ehCacheCache.get(key, type);
		} catch(IllegalStateException e) {
			ehCacheCache.evict(key);//如果ehcache存在的值得类型不符合，直接从ehcache中删除
		}
		
		if (value != null) {
			return (T)value;
		}
		
		if(!l2IsDisable) {
			try {
				value = redisCache.get(key, type);
				if (value != null) {
					ehCacheCache.put(key, value);//将redis中获取的值更新至ehcache
				}
				return (T)value;
			} catch (Exception e) {
				return null;
			}
		}else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T get(Object key, Callable<T> valueLoader) {
		ValueWrapper valueWrapper = ehCacheCache.get(key);
		Element element = (Element)valueWrapper.get();
		if (element != null) {
			return (T) element.getObjectValue();
		} else {
			if(!l2IsDisable) {
				try {
					T value = redisCache.get(key, valueLoader);
					if (value != null) {
						ehCacheCache.get(key, valueLoader);
					}
					return value;
				} catch (Exception e) {
					return null;
				}
			}else {
				T value = ehCacheCache.get(key, valueLoader);
				return value;
			}
		}
	}

	public void put(Object key, Object value) {
		ehCacheCache.put(key, value);
		if(!l2IsDisable) {
			try {
				redisCache.put(key, value);
			} catch (Exception e) {
				//redis put fail
			}
		}
	}

	public ValueWrapper putIfAbsent(Object key, Object value) {
		ValueWrapper valueWra = ehCacheCache.putIfAbsent(key, value);
		if(!l2IsDisable) {
			redisCache.putIfAbsent(key, value);
		}
		return valueWra;
	}

	public void evict(Object key) {
		ehCacheCache.evict(key);
		if(!l2IsDisable) {
			try {
				redisCache.evict(key);
			} catch (Exception e) {
				//redis evict fail
			}
			
		}
	}

	public void clear() {
		ehCacheCache.clear();
		if(!l2IsDisable) {
			try {
				redisCache.clear();
			} catch (Exception e) {
				//redis clear fail
			}
			
		}
	}

}
