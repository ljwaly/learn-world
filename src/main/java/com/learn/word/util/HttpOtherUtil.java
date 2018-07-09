package com.learn.word.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;




public class HttpOtherUtil {

	
	public static Log log = LogFactory.getLog(HttpOtherUtil.class);
	
	
	private static Map<String, CloseableHttpClient> clientMap =  new ConcurrentHashMap<String, CloseableHttpClient>();
	
	
	
	/**
	 * 
	 * 
	 * 
	 * @param uri
	 * @return
	 */
	private static CloseableHttpClient getClient(URI uri) {
		
		String host = "";
		int port = -1;
		host = uri.getHost();
		port = uri.getPort();
		
		CloseableHttpClient client = clientMap.get(host + ":" + port);//从内存中读取
		
		if (client == null) {
			HttpClientBuilder builder = HttpClientBuilder.create();
			client = builder
					.setMaxConnPerRoute(100)//路由最大连接
					.setMaxConnTotal(3)//最大连接数
					.build();
			clientMap.put(host + ":" + port, client);
		}
		
		return client;
	}
	
	
	/**
	 * 
	 * @param url
	 * @param requestXML
	 * @return
	 */
	public static String httpGet(String url, Map<String, String> headers){
		
		String result = "";
		HttpGet httpGet = new HttpGet();
		
		//设置连接参数
		RequestConfig requestConfig = 
				RequestConfig
				.custom() 
			    .setConnectionRequestTimeout(3000)
			    .setConnectTimeout(1000000)  
			    .setSocketTimeout(1000000)
			    .build();
		httpGet.setConfig(requestConfig);
		
		//设置url
		URI uri = null;
		try {
			uri = URI.create(url);
		} catch (Exception e1) {
			log.error(e1.getMessage());
			return result;
		}
		httpGet.setURI(uri);
		
		//设置header
	    if (headers != null) {
	    	Set<Entry<String, String>> entrySet = headers.entrySet();
	    	if (entrySet != null) {
	    		for (Entry<String, String> entryHead : entrySet) {
		    		httpGet.setHeader(entryHead.getKey(), entryHead.getValue());
				}
			}
		}
	    
	    //处理请求发送
	    BufferedReader bufferedReader = null;
		try {
			CloseableHttpResponse response = getClient(uri).execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			if (statusLine != null) {
				int statusCode = statusLine.getStatusCode();
				if (statusCode != 200) {
					log.error("HttpClient Error, statusCode:" + statusCode + ", uri :" + url );
				}
			}
			
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent(),"UTF-8"));
                String text = "";
                while ((text = bufferedReader.readLine()) != null) {
                    result += text;
                }
			}
			
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		return result;
		
	}
	
	
	/**
	 * 
	 * @param url
	 * @param headers
	 * @param json
	 * @return
	 */
	public static String httpJsonPost(String url, Map<String, String> headers, String json){
		String result = "";
		HttpPost httpPost = new HttpPost();
		
		RequestConfig requestConfig = 
				RequestConfig
				.custom() 
			    .setConnectionRequestTimeout(3000)
			    .setConnectTimeout(1000000)  
			    .setSocketTimeout(1000000)
			    .build();
		httpPost.setConfig(requestConfig);
		
		
		URI uri = null;
		try {
			uri = URI.create(url);
		} catch (Exception e1) {
			log.error(e1.getMessage());
			return result;
		}
		httpPost.setURI(uri);
		
		
		//设置header
	    if (headers != null) {
	    	Set<Entry<String, String>> entrySet = headers.entrySet();
	    	if (entrySet != null) {
	    		for (Entry<String, String> entryHead : entrySet) {
	    			httpPost.setHeader(entryHead.getKey(), entryHead.getValue());
				}
			}
		}
	    
	    //设置参数(jsonBody形式)
	    StringEntity paramsEntity = new StringEntity(json.toString(), ContentType.create("application/json", "utf-8"));    
	    paramsEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "utf-8"));  
	    httpPost.setEntity(paramsEntity);
	    
	    
	    
	    //处理请求发送
	    BufferedReader bufferedReader = null;
		try {
			CloseableHttpResponse response = getClient(uri).execute(httpPost);
			StatusLine statusLine = response.getStatusLine();
			if (statusLine != null) {
				int statusCode = statusLine.getStatusCode();
				if (statusCode != 200) {
					log.error("HttpClient Error, statusCode:" + statusCode + ", uri :" + url );
				}
			}
			
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent(),"UTF-8"));
                String text = "";
                while ((text = bufferedReader.readLine()) != null) {
                    result += text;
                }
			}
			
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		return result;
		
	}
	
	
	/**
	 * 
	 * @param url
	 * @param headers
	 * @param 是拼在url上的参数的那种post请求
	 * @return
	 */
	public static String httpNameValuePairPost(String url, Map<String, String> headers, Map<String, Object> params){
		String result = "";
		HttpPost httpPost = new HttpPost();
		
		RequestConfig requestConfig = 
				RequestConfig
				.custom() 
			    .setConnectionRequestTimeout(3000)
			    .setConnectTimeout(1000000)  
			    .setSocketTimeout(1000000)
			    .build();
		httpPost.setConfig(requestConfig);
		
		
		URI uri = null;
		try {
			uri = URI.create(url);
		} catch (Exception e1) {
			log.error(e1.getMessage());
			return result;
		}
		httpPost.setURI(uri);
		
		
		//设置header
	    if (headers != null) {
	    	Set<Entry<String, String>> entrySet = headers.entrySet();
	    	if (entrySet != null) {
	    		for (Entry<String, String> entryHead : entrySet) {
	    			httpPost.setHeader(entryHead.getKey(), entryHead.getValue());
				}
			}
		}
	    
	    if (params != null) {
	    	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(); 
	    	for (Entry<String, Object> entry : params.entrySet()) {
	    		String key = entry.getKey();
	    		String value = entry.getValue().toString();
	    		nameValuePairs.add(new BasicNameValuePair(key, value));  
			}
	        try {
				httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			} catch (UnsupportedEncodingException e1) {
				log.error(e1.getMessage());
				return result;
			}  
		}
	    
	    
	    
	    //处理请求发送
	    BufferedReader bufferedReader = null;
		try {
			CloseableHttpResponse response = getClient(uri).execute(httpPost);
			StatusLine statusLine = response.getStatusLine();
			if (statusLine != null) {
				int statusCode = statusLine.getStatusCode();
				if (statusCode != 200) {
					log.error("HttpClient Error, statusCode:" + statusCode + ", uri :" + url );
				}
			}
			
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent(),"UTF-8"));
                String text = "";
                while ((text = bufferedReader.readLine()) != null) {
                    result += text;
                }
			}
			
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		return result;
		
	}
}
