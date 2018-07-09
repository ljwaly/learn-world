package com.learn.word.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;


@SuppressWarnings("rawtypes")
public class IpNetUtil {
	
	/**
	 * 获取内网ip地址
	 * @return
	 */
	public static String getInnerIp(){
		String innerIp = "";
		
		Enumeration allNetInterfaces;
		try {
			allNetInterfaces = NetworkInterface.getNetworkInterfaces();
			InetAddress ip = null;
			while (allNetInterfaces.hasMoreElements()) {
				NetworkInterface netInterface = (NetworkInterface) allNetInterfaces
				.nextElement();
				Enumeration addresses = netInterface.getInetAddresses();
				while (addresses.hasMoreElements()) {
					ip = (InetAddress) addresses.nextElement();
					if (ip != null && ip instanceof Inet4Address ) {
						if(!"127.0.0.1".equals(ip.getHostAddress()) && !"127.0.0.2".equals(ip.getHostAddress())){
							innerIp = ip.getHostAddress();
							break;
						}
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return innerIp;
		
	}
	
	/**
	 * 获取外网ip
	 * @return
	 */
	public static String getIp(){
	    String netip = null;// 外网IP
	    try {
	        Enumeration netInterfaces = NetworkInterface.getNetworkInterfaces();
	        InetAddress ip = null;
	        boolean finded = false;// 是否找到外网IP
	        while (netInterfaces.hasMoreElements() && !finded) {
	            NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
	            Enumeration address = ni.getInetAddresses();
	            while (address.hasMoreElements()) {
	                ip = (InetAddress) address.nextElement();
	                if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {// 外网IP
	                    netip = ip.getHostAddress();
	                    finded = true;
	                    break;
	                } 
	            }
	        }
	    } catch (SocketException e) {
	        e.printStackTrace();
	    }
	    return netip;
	   
	}
	
	
}
