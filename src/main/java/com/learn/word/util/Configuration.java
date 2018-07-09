package com.learn.word.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 读取配置文件
 * 
 * 用于在静态类中配置属性的全局状态信息
 * 可以在初始化静态类中的静态属性public static Configuration conf;，通过初始化conf，
 * 用conf调用getProperty("stringName")得到对应配置文件的字段
 *  
 * @author lijinwu
 *
 */
public class Configuration {
	
	private Properties props;
	
	private Configuration(String propConfigFile){
		
		props = new Properties();
		try {
			BufferedReader is = new BufferedReader(
					new InputStreamReader(this.getClass().getResourceAsStream(propConfigFile), "UTF-8"));//使用字节转转化为字符流；1字符=2字节
			props.load(is);
		} catch (IOException e) {
			System.out.println("no file: "+propConfigFile+" -->"+e.getMessage());
			
		}
	}
	
	private static Configuration configuration = null;
	
	/**
	 * 得到Configuration的实例，可以用在util包中
	 * @return
	 */
	public static Configuration getInstance(){
		
		if (configuration == null) {
			configuration=new Configuration("/config.properties");//在resource资源路径下的config.properties配置文件的内部信息
		}
		
		return configuration;
	} 
	
	/**
	 * 获取指定配置文件的内容
	 * @param key
	 * @return
	 */
	public String getProperty(String key){
		return props.getProperty(key);
		
	}
	
}
