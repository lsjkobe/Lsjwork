package com.example.lsj.httplibrary.utils;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;


/**
 * 用来解析Json数据，返回的格式包括有bean格式，List格式等等
 * @author wan
 * 
 */
public class JackSonTools {
	/**
	 * 返回bean格式
	 * @param gsonString String格式的Json
	 * @param cls 解析所用的bean类
	 * @return bean类
	 */
	public static <T> T changeGsonToBean(String gsonString, Class<T> cls) {

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		T t;
		try {
			t = objectMapper.readValue(gsonString, cls);
			return t;
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static <T> String beantoString(Object cls){
		ObjectMapper objectMapper = new ObjectMapper();
		String result=null;
		try {
			result =objectMapper.writeValueAsString(cls);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
}
