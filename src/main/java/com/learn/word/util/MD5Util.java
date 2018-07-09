package com.learn.word.util;

import java.security.MessageDigest;

public class MD5Util {
	public final static String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = s.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str).toLowerCase();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	

	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", 
			"9", "a", "b", "c", "d", "e", "f" };
	/**
	 * 盐值
	 */
	private Object salt;
	private String algorithm;

	public MD5Util(Object salt, String algorithm) {
		this.salt = salt;
		this.algorithm = algorithm;
	}

	/**
	 * 带盐值加密 盐值在通过new创建对象时进行赋值
	 * 
	 * 
	 * @param rawPass
	 * @return
	 * 
	 */
	public String encode(String rawPass) {
		String result = null;
		try {
			MessageDigest md = MessageDigest.getInstance(algorithm);
			// 加密后的字符串
			result = byteArrayToHexString(md.digest(mergePasswordAndSalt(rawPass).getBytes("utf-8")));
		} catch (Exception ex) {
		}
		return result;
	}

	private String mergePasswordAndSalt(String password) {
		if (password == null) {
			password = "";
		}

		if ((salt == null) || "".equals(salt)) {
			return password;
		} else {
			return password + "{" + salt.toString() + "}";
		}
	}

	/**
	 * 转换字节数组为16进制字串
	 * 
	 * @param b
	 *            字节数组
	 * @return 16进制字串
	 */
	private static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}
	
	
	public static void main(String[] args) {
		System.out.println("无盐值加密测试：");
		System.out.println(MD5Util.MD5("20121221"));
		System.out.println(MD5Util.MD5("加密"));
		
		System.out.println("***********************************************************************");
		
		System.out.println("带有盐值加密测试：");
		
		String salt = "tongzhimenhao";//盐值
		
		MD5Util md1 = new MD5Util(salt, "MD5");
		String pass1 = md1.encode("test");
		System.out.println(pass1);

		MD5Util md2 = new MD5Util(salt, "MD5");
		String pass2 = md2.encode("test");
		System.out.println(pass2);
		
		System.out.println(pass2.equals(pass2));
	}
}