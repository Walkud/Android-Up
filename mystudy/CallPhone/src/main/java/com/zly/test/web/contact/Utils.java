package com.zly.test.web.contact;

import java.util.regex.Pattern;

public class Utils {
	
	public static final String REG = "^\\+{0,1}86|^17951|^12593|^17911|^17900|\\s+|-+";
	
	/**
	 * 去除电话号码 前缀和-
	 * 
	 * @return 处理后的电话号码
	 */
	public static String formatNumber(String number) {
		String returnStr;
		if (number == null || number.length() == 0) {
			return null;
		}
		
		returnStr = number.replaceAll(REG, "");

		return returnStr;
	}

	/**
	 * 处理首字母
	 * 
	 * @return 字符串的首字母，不是A~Z范围的返回#
	 */
	public static String formatAlpha(String str) {
		if (str == null) {
			return "#";
		}
		if (str.trim().length() == 0) {
			return "#";
		}

		char c = str.trim().substring(0, 1).charAt(0);
		Pattern pattern = Pattern.compile("^[A-Za-z]+$");
		if (pattern.matcher(c + "").matches()) {
			return (c + "").toUpperCase();
		} else {
			return "#";
		}
	}
}