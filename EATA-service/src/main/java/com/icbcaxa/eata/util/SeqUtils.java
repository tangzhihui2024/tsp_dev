/**
 * 
 */
package com.icbcaxa.eata.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;

import java.security.MessageDigest;
import java.util.UUID;


public class SeqUtils {

	public SeqUtils() {}

	public static String getSeqNo()
	{
	  return getSeqNo(5);
	}





	public static String getSeqNo(int digit)
	{
	  int range = (int)(Math.pow(10.0D, digit) - 1.0D);
	  return String.valueOf(System.currentTimeMillis()) + StringUtils.leftPad(String.valueOf(RandomUtils.nextInt(range)), digit, "0");
	}




	public static String genShortUID(String param)
	{
	  return genShortUIDArray(param, "fkey")[0];
	}



	public static String[] genShortUIDArray(String param)
	{
	  return genShortUIDArray(param, "fkey");
	}

















	public static String[] genShortUIDArray(String param, String key)
	{
	  String[] chars = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
	  





	  String hex = md5ByHex(key + param);
	  
	  String[] resUrl = new String[4];
	  for (int i = 0; i < 4; i++)
	  {

	    String sTempSubString = hex.substring(i * 8, i * 8 + 8);
	    


	    long lHexLong = 0x3FFFFFFF & Long.parseLong(sTempSubString, 16);
	    String outChars = "";
	    for (int j = 0; j < 6; j++)
	    {
	      long index = 0x3D & lHexLong;
	      
	      outChars = outChars + chars[((int)index)];
	      
	      lHexLong >>= 5;
	    }
	    
	    resUrl[i] = outChars;
	  }
	  return resUrl;
	}





	private static String md5ByHex(String src)
	{
	  try
	  {
	    MessageDigest md = MessageDigest.getInstance("MD5");
	    byte[] b = src.getBytes();
	    md.reset();
	    md.update(b);
	    byte[] hash = md.digest();
	    String hs = "";
	    String stmp = "";
	    for (int i = 0; i < hash.length; i++) {
	      stmp = Integer.toHexString(hash[i] & 0xFF);
	      if (stmp.length() == 1) {
	        hs = hs + "0" + stmp;
	      } else {
	        hs = hs + stmp;
	      }
	    }
	    return hs.toUpperCase();
	  } catch (Exception e) {
	    throw new RuntimeException(e);
	  }
	}




	public static String getUUID()
	{
	  UUID uuid = UUID.randomUUID();
	  String id = uuid.toString();
	  id = id.replaceAll("-", "");
	  return id;
	}
}
