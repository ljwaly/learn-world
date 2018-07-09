package com.learn.word.util;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;


import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


public class DESUtil {

	private final static Logger log = Logger.getLogger(DESUtil.class);
	

	/**
	 * 定义 加密算法,可用DES,DESede,Blowfish
	 */
	private static final String Algorithm = "DESede"; 
	private static final String CHARSET_UTF_8 = "UTF-8";
	/**
	 * 加密
	 * @param src	明文
	 * @param key	加密KEY 长度必须24位
	 * @return
	 */
	public static String encryptMode(String src,String key) {
		try {
			byte[] keyBytes = key.getBytes(CHARSET_UTF_8);
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(keyBytes, Algorithm);
			// 加密
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			return byte2data(c1.doFinal(src.getBytes()));
		} catch (NoSuchAlgorithmException e1) {
			log.error(e1.getMessage(),e1);
		} catch (NoSuchPaddingException e2) {
			log.error(e2.getMessage(),e2);
		} catch (Exception e3) {
			log.error(e3.getMessage(),e3);
		}	
		return null;
	}
	private static String byte2data(byte[] bytes){
		return new String(Base64.encodeBase64(bytes));
	}
	
	
	
	
	/**
	 * 解密
	 * @param src	密文
	 * @param key	加密KEY 长度必须24位	
	 * @return
	 */
	public static String decryptMode(String src,String key) {
		try {
			byte[] keyBytes = key.getBytes(CHARSET_UTF_8);
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(keyBytes, Algorithm);
			// 解密
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.DECRYPT_MODE, deskey);
			return new String(c1.doFinal(data2byte(src)));
		} catch (NoSuchAlgorithmException e1) {
			log.error(e1.getMessage(),e1);
		} catch (NoSuchPaddingException e2) {
			log.error(e2.getMessage(),e2);
		} catch (Exception e3) {
			log.error(e3.getMessage(),e3);
		}
		return null;
	}
	private static byte[] data2byte(String data) throws IOException{
		return Base64.decodeBase64(data.getBytes());
	}
}
