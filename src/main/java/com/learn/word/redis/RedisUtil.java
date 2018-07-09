package com.learn.word.redis;

import java.util.List;

import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {
	enum MODE {
		stand_alone, cluster, sentinel
	}
	
	public static RedisConnectionFactory connectionFactory(String mode, List<String> nodes, String master4Sentinel,
			int maxTotal, int maxIdle, String hostName, int port, int database) throws Exception {
		MODE _mode = MODE.valueOf(mode);
		if (_mode == null) {
			throw new IllegalArgumentException("property [redis.mode] wrong,allowed value is " + MODE.values());
		}
		switch (_mode) {
		case stand_alone:
			return buildConnectionFactory4StandAlone(maxTotal, maxIdle, hostName, port, database);
		case cluster:
			return buildConnectionFactory4Cluster(nodes, maxTotal, maxIdle, database);
		case sentinel:
			return buildConnectionFactory4Sentinel(nodes, master4Sentinel, maxTotal, maxIdle, database);
		default:
			throw new Exception("RedisConnectionFactory build exception");
		}
	}
	
	private static RedisConnectionFactory buildConnectionFactory4StandAlone(int maxTotal, int maxIdle, String hostName, int port, int database) {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(maxTotal);
		config.setMaxIdle(maxIdle);
		JedisConnectionFactory factory=new JedisConnectionFactory(config);
		factory.setHostName(hostName);
		factory.setPort(port);
		factory.setUsePool(true);
		factory.setDatabase(database);
		factory.afterPropertiesSet();
		return factory;
	}
	
	
	private static RedisConnectionFactory buildConnectionFactory4Cluster(List<String> nodes, int maxTotal, int maxIdle, int database) {
		RedisClusterConfiguration clusterConf=new RedisClusterConfiguration(nodes);
		JedisConnectionFactory factory=new JedisConnectionFactory(clusterConf);
		JedisPoolConfig poolConf=new JedisPoolConfig();
		poolConf.setMaxTotal(maxTotal);
		poolConf.setMaxIdle(maxIdle);
		factory.setUsePool(true);
		factory.setPoolConfig(poolConf);
		factory.setDatabase(database);
		return  factory;
	}

	private static RedisConnectionFactory buildConnectionFactory4Sentinel(List<String> nodes, String master4Sentinel, int maxTotal, int maxIdle, int database) {
		RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration().master(master4Sentinel);
		String host;
		int port;
		for(String sentinelNode:nodes){
			host = sentinelNode.split(":")[0];
			port = Integer.parseInt(sentinelNode.split(":")[1]);
			sentinelConfig.addSentinel(new RedisNode(host, port));
		}
		
		JedisConnectionFactory factory= new JedisConnectionFactory(sentinelConfig);
		factory.setUsePool(true);
		factory.setDatabase(database);
		
		JedisPoolConfig poolConf=new JedisPoolConfig();
		poolConf.setMaxTotal(maxTotal);
		poolConf.setMaxIdle(maxIdle);
		factory.setPoolConfig(poolConf);
		
		return factory;
	}
	
}
