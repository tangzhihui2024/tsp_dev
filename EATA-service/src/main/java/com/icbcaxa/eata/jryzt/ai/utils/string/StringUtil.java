package com.icbcaxa.eata.jryzt.ai.utils.string;

import org.apache.commons.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

public class StringUtil extends StringUtils {

//	private static Logger log = LoggerFactory.getLogger(StringUtil.class);

	/**
	 * 判断是否为null或空字符串
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}

	public static String getRandom() {
		StringBuilder sb = new StringBuilder();
		sb.append((int) ((Math.random() * 100000)));
		sb.append(System.currentTimeMillis());
		return sb.toString();
	}

	public static String getString(String str) {
		return str == null ? "" : str.toString().trim();
	}

	public static Boolean isNum(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher matcher = pattern.matcher(str);
		if (matcher.matches()) {
			return true;
		} else {
			return false;
		}
	}

	public static String toString(Object obj) {
		return obj == null ? null : obj.toString().trim();
	}

	public static String getUpperString(String str) {
		return str == null || "".equals(str) ? null : str.toUpperCase()
				.toString().trim();
	}

	/**
	 * Description 验证字符串对象是否为空
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean validStrObjIsEmpty(Object obj) {
		if (obj == null) {
			return true;
		}
		String str = String.valueOf(obj);
		if (StringUtils.isEmpty(str.trim())) {
			return true;
		}
		return false;
	}

	/**
	 * Description 判断字符串对象不为空，且长度在范围之内
	 * 
	 * @param obj
	 * @param len
	 *            必须大于0
	 * @return
	 */
	public static boolean isNotEmptyAndMaxLength(Object obj, int len) {
		try {
			if (obj == null || len <= 0) {
				return false;
			}
			String str = (String) obj;
			if (StringUtils.isEmpty(str.trim())) {
				return false;
			}
			if (str.length() > len) {
				return false;
			}
		} catch (Exception e) {
//			log.error("isNotEmptyAndMaxLength 字符串对象验证异常！{},{}。", obj, len);
			System.out.println("isNotEmptyAndMaxLength 字符串对象验证异常！{},{}。,"+ obj+","+ len);
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Description 判断字符串对象不为空，且长度等于len
	 * 
	 * @param obj
	 * @param len
	 *            必须大于0
	 * @return
	 */
	public static boolean isNotEmptyAndLength(Object obj, int len) {
		try {
			if (obj == null || len <= 0) {
				return false;
			}
			String str = (String) obj;
			if (StringUtils.isEmpty(str.trim())) {
				return false;
			}
			if (str.length() != len) {
				return false;
			}
		} catch (Exception e) {
//			log.error("isNotEmptyAndLength 字符串对象验证异常！{},{}。", obj, len);
			System.out.println("isNotEmptyAndLength 字符串对象验证异常！{},{}。,"+ obj+","+ len);
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Description 验证对象字符串是否为空，并返回字符串(去空格)
	 * 
	 * @param
	 */
	public static String valueOf(Object obj) {
		if (obj == null) {
			return "";
		}
		String str = (String) obj;
		if (StringUtils.isEmpty(str)) {
			return "";
		}
		return str.trim();
	}

	public static Boolean isCarNum(String carNum) {
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}$"); // 正则表达式
		Matcher m = p.matcher(carNum); // 操作的字符串
		return m.matches();
	}
	
	/**
	 * 姓名加*
	 * @param name
	 * @return
	 */
	public static String hideName(String name){
		if(null == name || name.length()<2){
			return name;
		}
		name = name.substring(0, name.length()-1)+"*";
		return name;
	}
	
	
	/**
	 * 身份证加*
	 * @param name
	 * @return
	 */
	public static String hideIdNo(String idNo){
		if(null == idNo){
			return idNo;
		}
		
		if(15==idNo.length()){
			idNo = idNo.substring(0, 3)+"********"+idNo.substring(11);
		}else if(18==idNo.length()){
			idNo = idNo.substring(0, 3)+"***********"+idNo.substring(14);
		}
		return idNo;
	}
	
	/**
	 * 手机号加*
	 * @param name
	 * @return
	 */
	public static String hideMobile(String mobile){
		if(null == mobile){
			return mobile;
		}
		
		if(11==mobile.length()){
			mobile = mobile.substring(0, 3)+"****"+mobile.substring(7);
		}
		return mobile;
	}
	
	public static boolean isNullOrBlank(String str) {
		if ((str == null) || (str.equals("")) || (str.equalsIgnoreCase("null"))) {
			return true;
		}
		return false;
	}
	

}
