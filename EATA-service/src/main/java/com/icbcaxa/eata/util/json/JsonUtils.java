package com.icbcaxa.eata.util.json;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.icbcaxa.eata.util.date.DateUtil;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Json解析工具
 */
public class JsonUtils {

    // 定义jackson对象
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 将对象转换成json字符串。
     * <p>Title: pojoToJson</p>
     * <p>Description: </p>
     * @param data
     * @return
     *
     */
    public static String objectToJson(Object data) {
    	try {
			String string = objectMapper.writeValueAsString(data);
			return string;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    /**
     * 将json结果集转化为对象
     * 
     * @param jsonData json数据
     * @param对象中的object类型
     * @return
     */
    public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
        try {
            T t = objectMapper.readValue(jsonData, beanType);
            return t;
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 将json数据转换成pojo对象list
     * <p>Title: jsonToList</p>
     * <p>Description: </p>
     * @param jsonData
     * @param beanType
     * @return
     */
    public static <T>List<T> jsonToList(String jsonData, Class<T> beanType) {
    	JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, beanType);
    	try {
    		List<T> list = objectMapper.readValue(jsonData, javaType);
    		return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return null;
    }

    /**
     * Json转化成Map
     *
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> jsonToMap(String jsonStr)
            throws JsonParseException, JsonMappingException, IOException {
        return objectMapper.readValue(jsonStr, Map.class);
    }

    /**
     * Json转化成指定Bean类型的Map
     *
     * @param jsonStr
     * @param clazz
     */
    public static <T> Map<String, T> jsonToMap(String jsonStr, Class<T> clazz)
            throws Exception {
        Map<String, Map<String, Object>> map = objectMapper.readValue(
                jsonStr, new TypeReference<Map<String, T>>() {
                });
        Map<String, T> result = new HashMap<String, T>();
        for (Map.Entry<String, Map<String, Object>> entry : map.entrySet()) {
            result.put(entry.getKey(), mapToBean(entry.getValue(), clazz));
        }
        return result;
    }

    /**
     * Map转化成Bean
     *
     * @param map
     * @param
     */
    public static <T> T mapToBean(Map<String, Object> map, Class<T> clazz) {
        return objectMapper.convertValue(map, clazz);
    }

    public static  <T> Map<String,List<T>>  listToMap(List<T> tList,String key) {
        Map<String, List<T>> map = new HashMap<>();
        try {
            for (T t : tList) {
                String mapKey = null;
                Class<?> aClass = t.getClass();
                Field field = aClass.getDeclaredField(key);
                if (field != null) {
                    field.setAccessible(true);
                    if (field.getType() == Date.class) {
                        mapKey = DateUtil.toDay((Date) field.get(t));
                    } else {
                        mapKey = field.get(t).toString();
                    }
                }
                List<T> tt;
                if (map.containsKey(mapKey)) {
                    tt = map.get(mapKey);
                    tt.add(t);
                } else {
                    tt = new ArrayList<>();
                    tt.add(t);
                }
                map.put(mapKey, tt);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return map;
    }

    public static <T> T handle(Object object,Class<T> tClass){
        T t = null;
        try {
            if(object != null) {
                t = jsonToPojo(objectToJson(object),tClass);
            }
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
