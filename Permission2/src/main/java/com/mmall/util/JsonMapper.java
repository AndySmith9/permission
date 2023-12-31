package com.mmall.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class JsonMapper {

    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
		/*
		 * // config
		 * objectMapper.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES
		 * ); objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS,
		 * false); objectMapper.setFilters(new
		 * SimpleFilterProvider().setFailOnUnknownId(false));
		 * objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_EMPTY);
		 */
      
        //忽略未知的json字段
        objectMapper.configure(
        	    DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //ObjectMapper默认会处理json值为null的情况,以下设置为true,遇到json字段值为null,会抛异常
        objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true);
        
    }

    
    
    public static <T> String obj2String(T src) {
        if (src == null) {
            return null;
        }
        try {
            return src instanceof String ? (String) src : objectMapper.writeValueAsString(src);
        } catch (Exception e) {
            log.warn("parse object to String exception, error:{}", e);
            return null;
        }
    }

    public static <T> T string2Obj(String src, TypeReference<T> typeReference) {
        if (src == null || typeReference == null) {
            return null;
        }
        try {
            return (T) (typeReference.getType().equals(String.class) ? src : objectMapper.readValue(src, typeReference));
        } catch (Exception e) {
            log.warn("parse String to Object exception, String:{}, TypeReference<T>:{}, error:{}", src, typeReference.getType(), e);
            return null;
        }
    }
}
