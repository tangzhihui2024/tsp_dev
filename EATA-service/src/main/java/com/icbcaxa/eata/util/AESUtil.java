package com.icbcaxa.eata.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;

/**
 * AES的加密和解密
 * @author libo
 */
public class AESUtil {
	//前端加密：v81v0fpmul4mj7o1h
	//前端解密：grtlgegor1ojr0rd1
    //密钥 (需要前端和后端保持一致)
    private static final String ENCODE_KEY = "grtlgegor1ojr0rd";
    private static final String DECODE_KEY = "v81v0fpmul4mj7o1";
    
    //融e联  加解密
    private static final String REL_CODE_KEY = "kshedO4IihPDYZs0";
    
    //算法
    private static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding";
    
    public static String getEncodeKey() {
    	return AESUtil.ENCODE_KEY;
    }
    
    public static String getDecodeKey() {
    	return AESUtil.DECODE_KEY;
    }
    
    public static String getRelCodeKey() {
		return REL_CODE_KEY;
	}

	/** 
     * aes解密 
     * @param encrypt   内容 
     * @return 
     * @throws Exception 
     */  
    public static String aesDecrypt(String encrypt) {  
        try {
            return aesDecrypt(encrypt, DECODE_KEY);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }  
    }  
      
    /** 
     * aes加密 
     * @param content 
     * @return 
     * @throws Exception 
     */  
    public static String aesEncrypt(String content) {  
        try {
            return aesEncrypt(content, ENCODE_KEY);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }  
    }  
  
    /** 
     * 将byte[]转为各种进制的字符串 
     * @param bytes byte[] 
     * @param radix 可以转换进制的范围，从Character.MIN_RADIX到Character.MAX_RADIX，超出范围后变为10进制 
     * @return 转换后的字符串 
     */  
    public static String binary(byte[] bytes, int radix){  
        return new BigInteger(1, bytes).toString(radix);// 这里的1代表正数  
    }  
  
    /** 
     * base 64 encode 
     * @param bytes 待编码的byte[] 
     * @return 编码后的base 64 code 
     */  
    public static String base64Encode(byte[] bytes){  
        return Base64.encodeBase64String(bytes);
    }  
  
    /** 
     * base 64 decode 
     * @param base64Code 待解码的base 64 code 
     * @return 解码后的byte[] 
     * @throws Exception 
     */  
    public static byte[] base64Decode(String base64Code) throws Exception{  
        return StringUtils.isEmpty(base64Code) ? null : new BASE64Decoder().decodeBuffer(base64Code);
    }  
  
      
    /** 
     * AES加密 
     * @param content 待加密的内容 
     * @param encryptKey 加密密钥 
     * @return 加密后的byte[] 
     * @throws Exception 
     */  
    public static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {  
        KeyGenerator kgen = KeyGenerator.getInstance("AES");  
        kgen.init(128);  
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);  
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));  
  
        return cipher.doFinal(content.getBytes("utf-8"));  
    }  
  
  
    /** 
     * AES加密为base 64 code 
     * @param content 待加密的内容 
     * @param encryptKey 加密密钥 
     * @return 加密后的base 64 code 
     * @throws Exception 
     */  
    public static String aesEncrypt(String content, String encryptKey) throws Exception {  
        return base64Encode(aesEncryptToBytes(content, encryptKey));  
    }  
  
    /** 
     * AES解密 
     * @param encryptBytes 待解密的byte[] 
     * @param decryptKey 解密密钥 
     * @return 解密后的String 
     * @throws Exception 
     */  
    public static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {  
        KeyGenerator kgen = KeyGenerator.getInstance("AES");  
        kgen.init(128);  
  
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);  
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));  
        byte[] decryptBytes = cipher.doFinal(encryptBytes);  
        return new String(decryptBytes);  
    }  
  
  
    /** 
     * 将base 64 code AES解密 
     * @param encryptStr 待解密的base 64 code 
     * @param decryptKey 解密密钥 
     * @return 解密后的string 
     * @throws Exception 
     */  
    public static String aesDecrypt(String encryptStr, String decryptKey) throws Exception {  
        return StringUtils.isEmpty(encryptStr) ? null : aesDecryptByBytes(base64Decode(encryptStr), decryptKey);
    }  
    
    /**
     * 测试
     */
    public static void main(String[] args) throws Exception {  
//        String content = "{\"questionList\":[{\"clientType\":\"004\",\"quesAdd\":\"{\\\"type\\\":\\\"mchoose\\\",\\\"clientAnswer\\\":{\\\"code\\\":\\\"\\\",\\\"text\\\":\\\"子女教育,\\\"},\\\"options\\\":[{\\\"code\\\":\\\"01\\\",\\\"text\\\":\\\"家庭保障\\\",\\\"isChoose\\\":\\\"0\\\"},{\\\"code\\\":\\\"02\\\",\\\"text\\\":\\\"子女教育\\\",\\\"isChoose\\\":\\\"1\\\"},{\\\"code\\\":\\\"03\\\",\\\"text\\\":\\\"退休保障\\\",\\\"isChoose\\\":\\\"0\\\"},{\\\"code\\\":\\\"99\\\",\\\"text\\\":\\\"其他（请详述）\\\",\\\"isChoose\\\":\\\"0\\\"}],\\\"subQuest\\\":[{\\\"isParentCode\\\":\\\"99\\\",\\\"type\\\":\\\"input\\\",\\\"quesDesc\\\":\\\"\\\",\\\"describe\\\":\\\"\\\",\\\"clientAnswer\\\":{\\\"code\\\":\\\"\\\",\\\"text\\\":\\\"\\\"}}]}\",\"quesSeq\":\"1\",\"policyNumber\":\"aaa\",\"clientAnswer\":\"\\\"\\\"\",\"id\":\"1037272024705138688\",\"questionnaireType\":\"1A1002\",\"quesDesc\":\"投保目的\"},{\"clientType\":\"004\",\"quesAdd\":\"{\\\"type\\\":\\\"schoose\\\",\\\"clientAnswer\\\":{\\\"code\\\":\\\"0\\\",\\\"text\\\":\\\"无\\\"},\\\"options\\\":[{\\\"text\\\":\\\"有\\\",\\\"code\\\":\\\"1\\\",\\\"isChoose\\\":\\\"0\\\"},{\\\"text\\\":\\\"无\\\",\\\"code\\\":\\\"0\\\",\\\"isChoose\\\":\\\"1\\\"}],\\\"subQuest\\\":[{\\\"isParentCode\\\":\\\"1\\\",\\\"quesDesc\\\":\\\"若是，其父母是否拥有个人人身保险计划？\\\",\\\"type\\\":\\\"schoose\\\",\\\"clientAnswer\\\":{\\\"code\\\":\\\"\\\",\\\"text\\\":\\\"\\\"},\\\"options\\\":[{\\\"text\\\":\\\"有\\\",\\\"code\\\":\\\"1\\\",\\\"isChoose\\\":\\\"0\\\"},{\\\"text\\\":\\\"无\\\",\\\"code\\\":\\\"0\\\",\\\"isChoose\\\":\\\"0\\\"}],\\\"subQuest\\\":[{\\\"isParentCode\\\":\\\"1\\\",\\\"quesDesc\\\":\\\"保险公司：\\\",\\\"type\\\":\\\"input\\\",\\\"describe\\\":\\\"\\\",\\\"clientAnswer\\\":{\\\"code\\\":\\\"\\\",\\\"text\\\":\\\"\\\"}},{\\\"isParentCode\\\":\\\"1\\\",\\\"quesDesc\\\":\\\"保险金额：\\\",\\\"type\\\":\\\"input\\\",\\\"describe\\\":\\\"\\\",\\\"clientAnswer\\\":{\\\"code\\\":\\\"\\\",\\\"text\\\":\\\"\\\"}},{\\\"isParentCode\\\":\\\"1\\\",\\\"quesDesc\\\":\\\"投保日期：\\\",\\\"type\\\":\\\"input\\\",\\\"describe\\\":\\\"\\\",\\\"clientAnswer\\\":{\\\"code\\\":\\\"\\\",\\\"text\\\":\\\"\\\"}}]}]}\",\"quesSeq\":\"2\",\"policyNumber\":\"aaa\",\"clientAnswer\":\"\\\"\\\"\",\"id\":\"1037272024713527296\",\"questionnaireType\":\"1A1002\",\"quesDesc\":\"被保险人是否未成年或是无收入的学生？\"},{\"clientType\":\"004\",\"quesAdd\":\"{\\\"type\\\":\\\"schoose\\\",\\\"clientAnswer\\\":{\\\"code\\\":\\\"0\\\",\\\"text\\\":\\\"无\\\"},\\\"options\\\":[{\\\"text\\\":\\\"有\\\",\\\"code\\\":\\\"1\\\",\\\"isChoose\\\":\\\"0\\\"},{\\\"text\\\":\\\"无\\\",\\\"code\\\":\\\"0\\\",\\\"isChoose\\\":\\\"1\\\"}],\\\"subQuest\\\":[{\\\"quesDesc\\\":\\\"若有，被保险人是否配偶？\\\",\\\"isParentCode\\\":\\\"1\\\",\\\"type\\\":\\\"schoose\\\",\\\"options\\\":[{\\\"text\\\":\\\"有\\\",\\\"code\\\":\\\"1\\\",\\\"isChoose\\\":\\\"0\\\"},{\\\"text\\\":\\\"无\\\",\\\"code\\\":\\\"0\\\",\\\"isChoose\\\":\\\"0\\\"}],\\\"clientAnswer\\\":{\\\"code\\\":\\\"\\\",\\\"text\\\":\\\"\\\"},\\\"subQuest\\\":[{\\\"quesDesc\\\":\\\"若有配偶，期配偶的有效保险金额是多少？\\\",\\\"isParentCode\\\":\\\"1\\\",\\\"type\\\":\\\"input\\\",\\\"describe\\\":\\\"\\\",\\\"clientAnswer\\\":{\\\"code\\\":\\\"\\\",\\\"text\\\":\\\"\\\"}}]}]}\",\"quesSeq\":\"3\",\"policyNumber\":\"aaa\",\"clientAnswer\":\"\\\"\\\"\",\"id\":\"1037272024713527297\",\"questionnaireType\":\"1A1002\",\"quesDesc\":\"被保险人是否有稳定收入?\"},{\"clientType\":\"004\",\"quesAdd\":\"{\\\"type\\\":\\\"ndanswer\\\",\\\"clientAnswer\\\":{\\\"code\\\":\\\"\\\",\\\"text\\\":\\\"\\\"},\\\"subQuest\\\":[{\\\"quesDesc\\\":\\\"投 保 人：\\\",\\\"type\\\":\\\"tinput\\\",\\\"tailText1\\\":\\\"年\\\",\\\"describe1\\\":\\\"2\\\",\\\"tailText2\\\":\\\"月\\\",\\\"describe2\\\":\\\"2\\\",\\\"clientAnswer\\\":{\\\"code\\\":\\\"\\\",\\\"text\\\":\\\"2年2月\\\"}},{\\\"quesDesc\\\":\\\"被保险人：\\\",\\\"type\\\":\\\"tinput\\\",\\\"tailText1\\\":\\\"年\\\",\\\"describe1\\\":\\\"2\\\",\\\"tailText2\\\":\\\"月\\\",\\\"describe2\\\":\\\"2\\\",\\\"clientAnswer\\\":{\\\"code\\\":\\\"\\\",\\\"text\\\":\\\"2年2月\\\"}}]}\",\"quesSeq\":\"4\",\"policyNumber\":\"aaa\",\"clientAnswer\":\"\\\"\\\"\",\"id\":\"1037272024713527298\",\"questionnaireType\":\"1A1002\",\"quesDesc\":\"你和他/她认识多久？\"},{\"clientType\":\"004\",\"quesAdd\":\"{\\\"type\\\":\\\"ndanswer\\\",\\\"clientAnswer\\\":{\\\"code\\\":\\\"\\\",\\\"text\\\":\\\"\\\"},\\\"subQuest\\\":[{\\\"quesDesc\\\":\\\"投 保 人：\\\",\\\"type\\\":\\\"input\\\",\\\"describe\\\":\\\"dss\\\",\\\"clientAnswer\\\":{\\\"code\\\":\\\"\\\",\\\"text\\\":\\\"dss\\\"}},{\\\"quesDesc\\\":\\\"被保险人：\\\",\\\"type\\\":\\\"input\\\",\\\"describe\\\":\\\"sdcds\\\",\\\"clientAnswer\\\":{\\\"code\\\":\\\"\\\",\\\"text\\\":\\\"sdcds\\\"}}]}\",\"quesSeq\":\"5\",\"policyNumber\":\"aaa\",\"clientAnswer\":\"{\\\"code\\\":\\\"\\\",\\\"text\\\":\\\"\\\"}\",\"id\":\"1037272024713527299\",\"questionnaireType\":\"1A1002\",\"quesDesc\":\"你和他/她的关系是？\"},{\"clientType\":\"004\",\"quesAdd\":\"{\\\"type\\\":\\\"ndanswer\\\",\\\"clientAnswer\\\":{\\\"code\\\":\\\"\\\",\\\"text\\\":\\\"\\\"},\\\"subQuest\\\":[{\\\"quesDesc\\\":\\\"投 保 人：\\\",\\\"type\\\":\\\"schoose\\\",\\\"options\\\":[{\\\"text\\\":\\\"是\\\",\\\"code\\\":\\\"1\\\",\\\"isChoose\\\":\\\"0\\\"},{\\\"text\\\":\\\"否\\\",\\\"code\\\":\\\"0\\\",\\\"isChoose\\\":\\\"1\\\"}],\\\"clientAnswer\\\":{\\\"code\\\":\\\"0\\\",\\\"text\\\":\\\"否\\\"}},{\\\"quesDesc\\\":\\\"被保险人：\\\",\\\"type\\\":\\\"schoose\\\",\\\"options\\\":[{\\\"text\\\":\\\"是\\\",\\\"code\\\":\\\"1\\\",\\\"isChoose\\\":\\\"0\\\"},{\\\"text\\\":\\\"否\\\",\\\"code\\\":\\\"0\\\",\\\"isChoose\\\":\\\"1\\\"}],\\\"clientAnswer\\\":{\\\"code\\\":\\\"0\\\",\\\"text\\\":\\\"否\\\"}}]}\",\"quesSeq\":\"6\",\"policyNumber\":\"aaa\",\"clientAnswer\":\"\\\"\\\"\",\"id\":\"1037272024713527300\",\"questionnaireType\":\"1A1002\",\"quesDesc\":\"你是否听闻他/她患病或向任何医生求诊？(不包含本次投保客户已告知的情况)\"},{\"clientType\":\"004\",\"quesAdd\":\"{\\\"type\\\":\\\"ndanswer\\\",\\\"clientAnswer\\\":{\\\"code\\\":\\\"\\\",\\\"text\\\":\\\"\\\"},\\\"subQuest\\\":[{\\\"quesDesc\\\":\\\"投 保 人：\\\",\\\"type\\\":\\\"schoose\\\",\\\"options\\\":[{\\\"text\\\":\\\"是\\\",\\\"code\\\":\\\"1\\\",\\\"isChoose\\\":\\\"0\\\"},{\\\"text\\\":\\\"否\\\",\\\"code\\\":\\\"0\\\",\\\"isChoose\\\":\\\"1\\\"}],\\\"clientAnswer\\\":{\\\"code\\\":\\\"0\\\",\\\"text\\\":\\\"否\\\"}},{\\\"quesDesc\\\":\\\"被保险人：\\\",\\\"type\\\":\\\"schoose\\\",\\\"options\\\":[{\\\"text\\\":\\\"是\\\",\\\"code\\\":\\\"1\\\",\\\"isChoose\\\":\\\"0\\\"},{\\\"text\\\":\\\"否\\\",\\\"code\\\":\\\"0\\\",\\\"isChoose\\\":\\\"1\\\"}],\\\"clientAnswer\\\":{\\\"code\\\":\\\"0\\\",\\\"text\\\":\\\"否\\\"}}]}\",\"quesSeq\":\"7\",\"policyNumber\":\"aaa\",\"clientAnswer\":\"\\\"\\\"\",\"id\":\"1037272024713527301\",\"questionnaireType\":\"1A1002\",\"quesDesc\":\"他/她是否面带病容，有任何身体或智力缺陷或精神 问题（如四肢五官残疾障碍或畸形，或任何对核保有 影响而未于投保单内清晰列明的问题？）\"},{\"clientType\":\"004\",\"quesAdd\":\"{\\\"type\\\":\\\"ndanswer\\\",\\\"clientAnswer\\\":{\\\"code\\\":\\\"\\\",\\\"text\\\":\\\"\\\"},\\\"subQuest\\\":[{\\\"quesDesc\\\":\\\"投 保 人：\\\",\\\"type\\\":\\\"input\\\",\\\"describe\\\":\\\"sdsd\\\",\\\"clientAnswer\\\":{\\\"code\\\":\\\"\\\",\\\"text\\\":\\\"sdsd\\\"}},{\\\"quesDesc\\\":\\\"被保险人：\\\",\\\"type\\\":\\\"input\\\",\\\"describe\\\":\\\"sdsds\\\",\\\"clientAnswer\\\":{\\\"code\\\":\\\"\\\",\\\"text\\\":\\\"sdsds\\\"}}]}\",\"quesSeq\":\"8\",\"policyNumber\":\"aaa\",\"clientAnswer\":\"\\\"\\\"\",\"id\":\"1037272024713527302\",\"questionnaireType\":\"1A1002\",\"quesDesc\":\"是否有其他情况需告知？\"},{\"clientType\":\"004\",\"quesAdd\":\"{\\\"type\\\":\\\"schoose\\\",\\\"options\\\":[{\\\"text\\\":\\\"是\\\",\\\"code\\\":\\\"1\\\",\\\"isChoose\\\":\\\"0\\\"},{\\\"text\\\":\\\"否\\\",\\\"code\\\":\\\"0\\\",\\\"isChoose\\\":\\\"1\\\"}],\\\"clientAnswer\\\":{\\\"code\\\":\\\"0\\\",\\\"text\\\":\\\"否\\\"}}\",\"quesSeq\":\"9\",\"policyNumber\":\"aaa\",\"clientAnswer\":\"\\\"\\\"\",\"id\":\"1037272024713527303\",\"questionnaireType\":\"1A1002\",\"quesDesc\":\"是否已经向客户清晰讲解并确认客户已明确知晓所需填写的所有投保资料上的相关内容。\"}]}\r\n";
        String content = "{\r\n" + 
        		"  \"flag\": \"string\",\r\n" + 
        		"  \"policyNumber\": \"FJ140667959\"\r\n" + 
        		"}";
//        System.out.println("加密前：" + content);  
//        System.out.println("加密密钥和解密密钥：" + ENCODE_KEY + "," + DECODE_KEY);  
        String encrypt = aesEncrypt(content, DECODE_KEY);  
//        System.out.println("加密后：" + encrypt); 
        encrypt = "Mm/hMIAY+IAWt7GxW4P4OFwtHxHGox81wK7mzTYnhCiDDntwZB1uCKLi50Xk37Rig5OmYwxoRBjUB93RjJnkiXpRCMNFfQy/gVQxvnBzdV4TJpR+ofN2wXZ6q1N/t+TuQNOBlAG24r3PBbVSW20/OQ==";
        String decrypt = aesDecrypt(encrypt, ENCODE_KEY);  
//        System.out.println("解密后：" + decrypt);
        
    } 
}
