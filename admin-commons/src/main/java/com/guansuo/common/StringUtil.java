package com.guansuo.common;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Filename: StringUtil.java <br>
 *
 * Description: <br>
 * 
 * @version: 1.0 <br>
 * @Createtime: 2018年11月2日 <br>
 * 
 */

public class StringUtil {
	/**
	 * 区域编码前缀
	 * 
	 * @param areaCode
	 * @return
	 */
	public static String getAreaPrefix(String areaCode) {
		Integer tempAreaCode = Integer.valueOf(new StringBuilder(areaCode).reverse().toString());
		String areaPrefix = areaCode.substring(0, tempAreaCode.toString().length());
		return areaPrefix;
	}

	/**
	 * 十进制转十六进制
	 * 
	 * @param decimalNumber
	 * @param length
	 *            长度
	 * @return
	 */
	public static String getHexString(int decimalNumber, int length) {
		// 将十进制数转为十六进制数
		String hex = Integer.toHexString(decimalNumber);
		// 转为大写
		hex = hex.toUpperCase();
		// 加长到四位字符，用0补齐
		hex = String.format("%" + length + "s", hex).replaceAll(" ", "0");
		return hex;
	}

	/**
	 * 十六进制转十进制
	 * 
	 * @param entCode
	 * @return
	 */
	public static Integer getHexStringToInt(String entCode) {
		return Integer.parseInt(entCode, 16);
	}

	/**
	 * 首字母变小写
	 * 
	 * @param str
	 * @return
	 */
	public static String firstCharToLowerCase(String str) {
		char firstChar = str.charAt(0);
		if (firstChar >= 'A' && firstChar <= 'Z') {
			char[] arr = str.toCharArray();
			arr[0] += ('a' - 'A');
			return new String(arr);
		}
		return str;
	}

	/**
	 * 首字母变大写
	 * 
	 * @param str
	 * @return
	 */
	public static String firstCharToUpperCase(String str) {
		char firstChar = str.charAt(0);
		if (firstChar >= 'a' && firstChar <= 'z') {
			char[] arr = str.toCharArray();
			arr[0] -= ('a' - 'A');
			return new String(arr);
		}
		return str;
	}

	/**
	 * 判断是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(final Object str) {
		return (null == str) || (str.toString().length() == 0);
	}

	public static boolean isNullOrEmpty(String s) {
		return s == null || s.isEmpty();
	}

	/**
	 * 判断是否不为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(final Object str) {
		return !isEmpty(str);
	}

	/**
	 * 判断是否空白
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isBlank(final Object str) {
		int strLen;
		if ((str == null) || ((strLen = str.toString().length()) == 0)||"null".equals(str))
			return true;
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(str.toString().charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断是否不是空白
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotBlank(final Object str) {
		return !isBlank(str);
	}

	/**
	 * 判断多个字符串全部是否为空
	 * 
	 * @param strings
	 * @return
	 */
	public static boolean isAllEmpty(String... strings) {
		if (strings == null) {
			return true;
		}
		for (String str : strings) {
			if (isNotEmpty(str)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断多个字符串其中任意一个是否为空
	 * 
	 * @param strings
	 * @return
	 */
	public static boolean isHasEmpty(String... strings) {
		if (strings == null) {
			return true;
		}
		for (String str : strings) {
			if (isEmpty(str)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * checkValue为 null 或者为 "" 时返回 defaultValue
	 * 
	 * @param checkValue
	 * @param defaultValue
	 * @return
	 */
	public static String isEmpty(String checkValue, String defaultValue) {
		return isEmpty(checkValue) ? defaultValue : checkValue;
	}

	/**
	 * 字符串不为 null 而且不为 "" 并且等于other
	 * 
	 * @param str
	 * @param other
	 * @return
	 */
	public static boolean isNotEmptyAndEquelsOther(String str, String other) {
		if (isEmpty(str)) {
			return false;
		}
		return str.equals(other);
	}

	/**
	 * 字符串不为 null 而且不为 "" 并且不等于other
	 * 
	 * @param str
	 * @param other
	 * @return
	 */
	public static boolean isNotEmptyAndNotEquelsOther(String str, String... other) {
		if (isEmpty(str)) {
			return false;
		}
		for (int i = 0; i < other.length; i++) {
			if (str.equals(other[i])) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 字符串不等于other
	 * 
	 * @param str
	 * @param other
	 * @return
	 */
	public static boolean isNotEquelsOther(String str, String... other) {
		for (int i = 0; i < other.length; i++) {
			if (other[i].equals(str)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断字符串不为空
	 * 
	 * @param strings
	 * @return
	 */
	public static boolean isNotEmpty(String... strings) {
		if (strings == null) {
			return false;
		}
		for (String str : strings) {
			if (str == null || "".equals(str.trim())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 比较字符相等
	 * 
	 * @param value
	 * @param equals
	 * @return
	 */
	public static boolean equals(String value, String equals) {
		if (isAllEmpty(value, equals)) {
			return true;
		}
		return value.equals(equals);
	}

	/**
	 * 比较字符串不相等
	 * 
	 * @param value
	 * @param equals
	 * @return
	 */
	public static boolean isNotEquals(String value, String equals) {
		return !equals(value, equals);
	}

	public static String[] split(String content, String separatorChars) {
		return splitWorker(content, separatorChars, -1, false);
	}

	public static String[] split(String str, String separatorChars, int max) {
		return splitWorker(str, separatorChars, max, false);
	}

	public static final String[] EMPTY_STRING_ARRAY = new String[0];

	private static String[] splitWorker(String str, String separatorChars, int max, boolean preserveAllTokens) {
		if (str == null) {
			return null;
		}
		int len = str.length();
		if (len == 0) {
			return EMPTY_STRING_ARRAY;
		}
		List<String> list = new ArrayList<String>();
		int sizePlus1 = 1;
		int i = 0, start = 0;
		boolean match = false;
		boolean lastMatch = false;
		if (separatorChars == null) {
			while (i < len) {
				if (Character.isWhitespace(str.charAt(i))) {
					if (match || preserveAllTokens) {
						lastMatch = true;
						if (sizePlus1++ == max) {
							i = len;
							lastMatch = false;
						}
						list.add(str.substring(start, i));
						match = false;
					}
					start = ++i;
					continue;
				}
				lastMatch = false;
				match = true;
				i++;
			}
		} else if (separatorChars.length() == 1) {
			char sep = separatorChars.charAt(0);
			while (i < len) {
				if (str.charAt(i) == sep) {
					if (match || preserveAllTokens) {
						lastMatch = true;
						if (sizePlus1++ == max) {
							i = len;
							lastMatch = false;
						}
						list.add(str.substring(start, i));
						match = false;
					}
					start = ++i;
					continue;
				}
				lastMatch = false;
				match = true;
				i++;
			}
		} else {
			while (i < len) {
				if (separatorChars.indexOf(str.charAt(i)) >= 0) {
					if (match || preserveAllTokens) {
						lastMatch = true;
						if (sizePlus1++ == max) {
							i = len;
							lastMatch = false;
						}
						list.add(str.substring(start, i));
						match = false;
					}
					start = ++i;
					continue;
				}
				lastMatch = false;
				match = true;
				i++;
			}
		}
		if (match || (preserveAllTokens && lastMatch)) {
			list.add(str.substring(start, i));
		}
		return (String[]) list.toArray(EMPTY_STRING_ARRAY);
	}

	/**
	 * 消除转义字符
	 * 
	 * @param str
	 * @return
	 */
	public static String escapeXML(String str) {
		if (str == null)
			return "";
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < str.length(); ++i) {
			char c = str.charAt(i);
			switch (c) {
			case '\u00FF':
			case '\u0024':
				break;
			case '&':
				sb.append("&amp;");
				break;
			case '<':
				sb.append("&lt;");
				break;
			case '>':
				sb.append("&gt;");
				break;
			case '\"':
				sb.append("&quot;");
				break;
			case '\'':
				sb.append("&apos;");
				break;
			default:
				if (c >= '\u0000' && c <= '\u001F')
					break;
				if (c >= '\uE000' && c <= '\uF8FF')
					break;
				if (c >= '\uFFF0' && c <= '\uFFFF')
					break;
				sb.append(c);
				break;
			}
		}
		return sb.toString();
	}

	/**
	 * 将字符串中特定模式的字符转换成map中对应的值
	 *
	 * @param s
	 *            需要转换的字符串
	 * @param map
	 *            转换所需的键值对集合
	 * @return 转换后的字符串
	 */
	public static String replace(String s, Map<String, Object> map) {
		StringBuilder ret = new StringBuilder((int) (s.length() * 1.5));
		int cursor = 0;
		for (int start, end; (start = s.indexOf("${", cursor)) != -1 && (end = s.indexOf("}", start)) != -1;) {
			ret.append(s.substring(cursor, start)).append(map.get(s.substring(start + 2, end)));
			cursor = end + 1;
		}
		ret.append(s.substring(cursor, s.length()));
		return ret.toString();
	}

	public static String replace(String s, Object... objs) {
		if (objs == null || objs.length == 0)
			return s;
		if (s.indexOf("{}") == -1)
			return s;
		StringBuilder ret = new StringBuilder((int) (s.length() * 1.5));
		int cursor = 0;
		int index = 0;
		for (int start; (start = s.indexOf("{}", cursor)) != -1;) {
			ret.append(s.substring(cursor, start));
			if (index < objs.length)
				ret.append(objs[index]);
			else
				ret.append("{}");
			cursor = start + 2;
			index++;
		}
		ret.append(s.substring(cursor, s.length()));
		return ret.toString();
	}

	/**
	 * 字符串格式化工具,参数必须以{0}之类的样式标示出来.大括号中的数字从0开始。
	 * 
	 * @param source
	 *            源字符串
	 * @param params
	 *            需要替换的参数列表,写入时会调用每个参数的toString().
	 * @return 替换完成的字符串。如果原始字符串为空或者参数为空那么将直接返回原始字符串。
	 */
	public static String replaceArgs(String source, Object... params) {
		if (params == null || params.length == 0 || source == null || source.isEmpty()) {
			return source;
		}
		StringBuilder buff = new StringBuilder(source);
		StringBuilder temp = new StringBuilder();
		int startIndex = 0;
		int endIndex = 0;
		String param = null;
		for (int count = 0; count < params.length; count++) {
			if (params[count] == null) {
				param = null;
			} else {
				param = params[count].toString();
			}

			temp.delete(0, temp.length());
			temp.append("{");
			temp.append(count);
			temp.append("}");
			while (true) {
				startIndex = buff.indexOf(temp.toString(), endIndex);
				if (startIndex == -1) {
					break;
				}
				endIndex = startIndex + temp.length();

				buff.replace(startIndex, endIndex, param == null ? "" : param);
			}
			startIndex = 0;
			endIndex = 0;
		}
		return buff.toString();
	}

	public static String substringBefore(final String s, final String separator) {
		if (isEmpty(s) || separator == null) {
			return s;
		}
		if (separator.isEmpty()) {
			return "";
		}
		final int pos = s.indexOf(separator);
		if (pos < 0) {
			return s;
		}
		return s.substring(0, pos);
	}

	public static String substringBetween(final String str, final String open, final String close) {
		if (str == null || open == null || close == null) {
			return null;
		}
		final int start = str.indexOf(open);
		if (start != -1) {
			final int end = str.indexOf(close, start + open.length());
			if (end != -1) {
				return str.substring(start + open.length(), end);
			}
		}
		return null;
	}

	public static String substringAfter(final String str, final String separator) {
		if (isEmpty(str)) {
			return str;
		}
		if (separator == null) {
			return "";
		}
		final int pos = str.indexOf(separator);
		if (pos == -1) {
			return "";
		}
		return str.substring(pos + separator.length());
	}

	/**
	 * 转换为字节数组
	 * 
	 * @param bytes
	 * @return
	 */
	public static String toString(byte[] bytes) {
		try {
			return new String(bytes, "utf-8");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	/**
	 * 转换为字节数组
	 * 
	 * @param str
	 * @return
	 */
	public static byte[] getBytes(String str) {
		if (str != null) {
			try {
				return str.getBytes("utf-8");
			} catch (UnsupportedEncodingException e) {
				return null;
			}
		} else {
			return null;
		}
	}

	public static void main(String[] args) {
		/*
		 * String num = getHexString(268480513, 9); System.out.println(num);
		 */
		/*
		 * Integer x = getHexStringToInt("0002B001000"); System.out.println(x);
		 * System.out.println(getHexString(720899, 9));
		 */
		/*
		 * System.out.println(String.format("%6s", "崔").replaceAll(" ", "0"));
		 * System.out.println(getHexString(268480516,5));
		 */
		System.out.println(Integer.valueOf("1004EFFF", 16));
		System.out.println(getHexStringToInt("1004E"));
	}

	/**
	 * 字符串是否为空，空的定义如下 1、为null <br>
	 * 2、为""<br>
	 * 
	 * @param str
	 *            被检测的字符串
	 * @return 是否为空
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}
}
