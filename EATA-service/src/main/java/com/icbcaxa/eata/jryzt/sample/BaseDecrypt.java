package com.icbcaxa.eata.jryzt.sample;


import com.icbcaxa.eata.jryzt.ai.utils.crypto.AESCBCUtils;

/**
 * 可对密文进行解密
 * @author EX-MAQINGNI002
 *
 */
public class BaseDecrypt {
	private static String aes_public_key = "abcdefghabcdefgh";

	public static void main(String[] args) {
		String a = "AGMGjii6dJ0CLgDqp4hzPPAiGsLcU8NUpTD8hE87A8HPkWUEkCbzhbXMC9NfN7DIwWNrs0AxmCGqks6tBa9tZXnFOdKW/l5TS6Fi9fvzqHXxTUaOtrj7ZMVIEOewejYl0nwkO8AUpIXq8Ky+GwiEwQ/0XdpLYGBV0ZrMfYLahr4=";
		System.out.println("解密结果：" + AESCBCUtils.decrypt(a, aes_public_key));
	}
}
