package com.learn.word.redis;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 配置redis工作模式
 * 提供redisTemplate
 * @author PC
 *
 */
@Configuration
@EnableCaching
@PropertySource(value = "classpath:/redis-${spring.profiles.active}.properties")
public class RedisConf {
	
	enum MODE{
		stand_alone, cluster, sentinel
	}
	
	
	@Value("${redis.mode}")
	private String mode;
	
	@Value("${redis.cluster.nodes}")
	private String clusterNodes;
	
	@Value("${redis.sentinel.nodes}")
	private String sentinelNodes;
	
	@Value("${redis.sentinel.master}")
	private String master4Sentinel;
	
	@Value("${redis.max-total}")
	private String maxTotal;
	
	@Value("${redis.max-idle}")
	private String maxIdle;
	
	@Value("${redis.stand_alone.host-name}")
	private String hostName; 
	
	@Value("${redis.stand_alone.port}")
	private String port;
	
	@Value("${redis.db_idx}")
	private String database;
	
	@Bean
	public RedisConnectionFactory connectionFactory() throws Exception {
		return RedisUtil.connectionFactory(mode, getNodes(), master4Sentinel, Integer.parseInt(maxTotal), 
				Integer.parseInt(maxIdle), hostName, Integer.parseInt(port), Integer.parseInt(database));
	}

	/**
	 * 提供redisTemplate
	 * @param connectionFactory
	 * @return
	 */
	@Bean
	@Primary
	public RedisTemplate<String, ?> template(RedisConnectionFactory connectionFactory){
		RedisTemplate<String,?> template=new RedisTemplate<>();
		template.setConnectionFactory(connectionFactory);
		template.setKeySerializer(new StringRedisSerializer());
//		template.setValueSerializer(new StringRedisSerializer());

		return template;
	}
	

	
	
	private List<String> getNodes() {
		List<String> nodesList = new ArrayList<String>();
		
		if(MODE.stand_alone.name().equals(mode)){
			nodesList=null;
		}else if(MODE.cluster.name().equals(mode)){
			String[] nodeArray = clusterNodes.split(",");
			for(String str : nodeArray){
				nodesList.add(str);
			}
		}else if(MODE.sentinel.name().equals(mode)){
			String[] nodeArray = sentinelNodes.split(",");
			for(String str : nodeArray){
				nodesList.add(str);
			}
		}
		return nodesList;
	}
}
