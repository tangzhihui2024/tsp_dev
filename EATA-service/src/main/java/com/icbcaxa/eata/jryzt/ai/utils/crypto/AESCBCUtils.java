package com.icbcaxa.eata.jryzt.ai.utils.crypto;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;

public class AESCBCUtils {

//	public static final Log logger = LogFactory.getLog(AESCBCUtils.class);
	public static final String KEY_ALGORITHM = "AES";
	// 加密模式为ECB，填充模式为NoPadding
	public static final String CIPHER_ALGORITHM = "AES/CBC/NoPadding";
	// 字符集
	public static final String ENCODING = "UTF-8";
	// 向量
	public static final String IV_SEED = "1234567812345678";

	/**
	 * AES加密算法
	 * 
	 * @param str密文
	 * @param key密key
	 * @return
	 */
	public static String encrypt(String str, String key) {
		try {
			if (str == null) {
//				logger.error("AES加密出错:Key为空null");
				System.out.println("AES加密出错:Key为空null");
				return null;
			}
			// 判断Key是否为16位
			if (key.length() != 16) {
//				logger.error("AES加密出错:Key长度不是16位");
				System.out.println("AES加密出错:Key长度不是16位");
				return null;
			}
			byte[] raw = key.getBytes(ENCODING);
			SecretKeySpec skeySpec = new SecretKeySpec(raw, KEY_ALGORITHM);
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			IvParameterSpec iv = new IvParameterSpec(IV_SEED.getBytes(ENCODING));
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
			byte[] srawt = str.getBytes(ENCODING);
			int len = srawt.length;
			/* 计算补空格后的长度 */
			while (len % 16 != 0)
				len++;
			byte[] sraw = new byte[len];
			/* 在最后空格 */
			for (int i = 0; i < len; ++i) {
				if (i < srawt.length) {
					sraw[i] = srawt[i];
				} else {
					sraw[i] = 32;
				}
			}
			byte[] encrypted = cipher.doFinal(sraw);
			return formatString(new String(Base64.encodeBase64(encrypted), "UTF-8"));
		} catch (Exception ex) {
//			logger.error("AES加密出错：" + ex.toString());
			System.out.println("AES加密出错：" + ex.toString());
			return null;
		}
	}

	/**
	 * AES解密算法
	 * 
	 * @param str密文
	 * @param key密key
	 * @return
	 */
	public static String decrypt(String str, String key) {
		try {
			// 判断Key是否正确
			if (key == null) {
//				logger.error("AES解密出错:Key为空null");
				System.out.println("AES解密出错:Key为空null");
				return null;
			}
			// 判断Key是否为16位
			if (key.length() != 16) {
//				logger.error("AES解密出错：Key长度不是16位");
				System.out.println("AES解密出错：Key长度不是16位");
				return null;
			}
			byte[] raw = key.getBytes(ENCODING);
			SecretKeySpec skeySpec = new SecretKeySpec(raw, KEY_ALGORITHM);
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			IvParameterSpec iv = new IvParameterSpec(IV_SEED.getBytes(ENCODING));
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] bytes = Base64.decodeBase64(str.getBytes("UTF-8"));
			bytes = cipher.doFinal(bytes);
			return new String(bytes, ENCODING);
		} catch (Exception ex) {
//			logger.error("AES解密出错：" + ex.toString());
			System.out.println("AES解密出错：" + ex.toString());
			return null;
		}
	}

	private static String formatString(String sourceStr) {
		if (sourceStr == null) {
			return null;
		}
		return sourceStr.replaceAll("\\r", "").replaceAll("\\n", "");
	}

	public static void main(String[] args) {
		String aes_key = "abcdefgh12345678";
		String source = "王成";
		// 加密
		String encrypt_str = encrypt(source, aes_key);
		System.out.println(encrypt_str);
		// 解密
		String decrypt_str = decrypt(encrypt_str, aes_key);
		System.out.println(decrypt_str);
		
		
		System.out.println(decrypt("AGMGjii6dJ0CLgDqp4hzPPAiGsLcU8NUpTD8hE87A8HPkWUEkCbzhbXMC9NfN7DIDUuR/4S6GJbXShGvSooHK0Osd1wrUxnccbAZzj03v+US6hNVIwEhSZqHOhmMy4jesZR20EBs7b54t2kZwLeSnPWOf5AcEkiDXw2RGktlfHI=", "abcdefghabcdefgh"));
	}

}