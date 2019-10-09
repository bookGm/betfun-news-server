package com.guansuo.common;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @Copyright: Copyright (c)2017 by exiangjia <br>
 */
public final class JsonUtil {
	public static final SerializerFeature[] features = { 
			//保留map空的字段
            SerializerFeature.WriteMapNullValue,
            // 将String类型的NULL转化为""
            SerializerFeature.WriteNullStringAsEmpty,
            // 将Number类型的NULL转化为0
            SerializerFeature.WriteNullNumberAsZero,
            // 将List类型的NULL转成[]
            SerializerFeature.WriteNullListAsEmpty,
            // 将Boolean类型的NULL转化为false
            SerializerFeature.WriteNullBooleanAsFalse,
            // 避免循环引用
            SerializerFeature.DisableCircularReferenceDetect,
//			SerializerFeature.PrettyFormat,
			SerializerFeature.WriteDateUseDateFormat,
			SerializerFeature.WriteNonStringKeyAsString };
	public static final SerializerFeature[] nullFeatures = {
//			SerializerFeature.PrettyFormat,
			SerializerFeature.WriteDateUseDateFormat,
			SerializerFeature.WriteNonStringKeyAsString,
			SerializerFeature.DisableCircularReferenceDetect};
	
	private JsonUtil() {
		
	}

	/**
	 * json字符串转化为JSONObject
	 * @param json
	 * @return
	 * @author: zhuzhao
	 * @Createtime: 2017年10月25日
	 */
	public static JSONObject parseJSONObject(String json) {
		return JSON.parseObject(json);
	}

	/**
	 * json字符串转化为JSONArray
	 * @param json
	 * @return
	 * @author: zhuzhao
	 * @Createtime: 2017年10月25日
	 */
	public static JSONArray parseJSONArray(String json) {
		return JSON.parseArray(json);
	}

	/**
	 * json字符串转化为对象
	 * @param json
	 * @param clazz
	 * @return
	 * @author: zhuzhao
	 * @Createtime: 2017年10月25日
	 */
	public static <T> T parseObject(String json, Class<T> clazz) {
		return JSON.parseObject(json, clazz);
	}

	/**
	 * json字符串转化为List对象
	 * @param json
	 * @param clazz
	 * @return
	 * @author: zhuzhao
	 * @Createtime: 2017年10月25日
	 */
	public static <T> List<T> parseList(String json, Class<T> clazz) {
		return JSON.parseArray(json, clazz);
	}
	
	/**
	 * json字符串转化为Set对象
	 * @param json
	 * @param clazz
	 * @return
	 * @author: zhuzhao
	 * @Createtime: 2017年11月8日
	 */
	public static <T> Set<T> parseSet(String json, Class<T> clazz) {
		Set<T> set = new LinkedHashSet<>();
		List<T> list = parseList(json, clazz);
		if(list != null) {
			int size = list.size();
			T t = null;
			for(int i = 0;i < size;i++) {
				t = list.get(i);
				set.add(t);
			}
		}
		return set;
	}

	/**
	 * 对象转化为JSON字符串，包含为null的字段
	 * @param object
	 * @return
	 * @author: zhuzhao
	 * @Createtime: 2017年10月25日
	 */
	public static String toJSONString(Object object) {
		return JSON.toJSONString(object, features);
	}

	/**
	 * 对象转化为JSON字符串，不包含为null的字段
	 * @param object
	 * @return
	 * @author: zhuzhao
	 * @Createtime: 2017年10月25日
	 */
	public static String toJSONStringWithoutNullValue(Object object) {
		return JSON.toJSONString(object, nullFeatures);
	}
}
