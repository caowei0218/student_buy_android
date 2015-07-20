package com.example.student_buy_android.util;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.util.Log;

@SuppressLint("SimpleDateFormat")
public class JsonBinder {
	private ObjectMapper mapper;

	@SuppressWarnings("deprecation")
	public JsonBinder(Inclusion inclusion) {
		mapper = new ObjectMapper();
		mapper.getSerializationConfig().setSerializationInclusion(inclusion);
		mapper.getDeserializationConfig()
				.set(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,
						false);
	}

	public static JsonBinder buildNormalBinder() {
		return new JsonBinder(Inclusion.ALWAYS);
	}

	public static JsonBinder buildNonNullBinder() {
		return new JsonBinder(Inclusion.NON_NULL);
	}
	
	public static JsonBinder buildNonDefaultBinder() {
		return new JsonBinder(Inclusion.NON_DEFAULT);
	}

	public <T> T fromJson(String jsonString, Class<T> clazz) {
		if (StringUtils.isEmpty(jsonString)) {
			return null;
		}
		try {
			return mapper.readValue(jsonString, clazz);
		} catch (IOException e) {
			return null;
		}
	}

	public <T> T jsonToObj(String json, TypeReference<T> typeReference) {
		try {
			return mapper.readValue(json, typeReference);
		} catch (JsonParseException e) {
			Log.w("JsonParseException: ", e);
		} catch (JsonMappingException e) {
			Log.w("JsonMappingException: ", e);
		} catch (IOException e) {
			Log.w("IOException: ", e);
		}
		return null;
	}

	public <T> T jsonToObj(String json, Class<T> valueType) {
		try {
			return mapper.readValue(json, valueType);
		} catch (JsonParseException e) {
			Log.w("JsonParseException: ", e);
		} catch (JsonMappingException e) {
			Log.w("JsonMappingException: ", e);
		} catch (IOException e) {
			Log.w("IOException: ", e);
		}
		return null;
	}

	/**
	 * 对象转Json
	 * */
	public String toJson(Object object) {
		try {
			return mapper.writeValueAsString(object);
		} catch (IOException e) {
			Log.w("write to json string error:" + object, e);
			return null;
		}
	}

	@SuppressWarnings("deprecation")
	public void setDateFormat(String pattern) {
		if (StringUtils.isNotBlank(pattern)) {
			DateFormat df = new SimpleDateFormat(pattern);
			mapper.getSerializationConfig().setDateFormat(df);
			mapper.getDeserializationConfig().setDateFormat(df);
		}
	}

	/**
	 * 字符串转json对象
	 * */
	public JSONObject stringToJSONObject(String str) throws JSONException {
		JSONObject dataJson = new JSONObject(str);
		return dataJson;
	}

	public JavaType getCollectionType(Class<?>... elementClasses) {
		return mapper.getTypeFactory().constructParametricType(ArrayList.class,
				elementClasses);
	}

	/**
	 * json字符串转对象集合
	 * */
	@SuppressWarnings("hiding")
	public <Object> Object stringToList(String jsonString,
			Class<?>... elementClasses) throws JsonParseException,
			JsonMappingException, IOException {
		JavaType javaType = getCollectionType(elementClasses);
		return mapper.readValue(jsonString, javaType);
	}

	public ObjectMapper getMapper() {
		return mapper;
	}

}
