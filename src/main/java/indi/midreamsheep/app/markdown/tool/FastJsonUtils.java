package indi.midreamsheep.app.markdown.tool;

import java.util.List;
import java.util.Map;
 
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class FastJsonUtils {
 
    private static final SerializeConfig config;
 
    static {
        config = new SerializeConfig();
    }
 
    private static final SerializerFeature[] features = {SerializerFeature.WriteMapNullValue, // 输出空置字段
            SerializerFeature.WriteNullListAsEmpty, // list字段如果为null，输出为[]，而不是null
            SerializerFeature.WriteNullNumberAsZero, // 数值字段如果为null，输出为0，而不是null
            SerializerFeature.WriteNullBooleanAsFalse, // Boolean字段如果为null，输出为false，而不是null
            SerializerFeature.WriteNullStringAsEmpty // 字符类型字段如果为null，输出为""，而不是null
    };
    
 
    public static String convertObjectToJSON(Object object) {
        return JSON.toJSONString(object, config, features);
    }
    
    public static String toJSONNoFeatures(Object object) {
        return JSON.toJSONString(object, config);
    }
    
 
 
    public static Object toBean(String text) {
        return JSON.parse(text);
    }
 
    public static <T> T toBean(String text, Class<T> clazz) {
        return JSON.parseObject(text, clazz);
    }
    
    public static <T> Object[] toArray(String text) {
        return toArray(text, null);
    }
    
    public static <T> Object[] toArray(String text, Class<T> clazz) {
        return JSON.parseArray(text, clazz).toArray();
    }
    
    public static <T> List<T> toList(String text, Class<T> clazz) {
        return JSON.parseArray(text, clazz);
    }
    
    public static Object textToJson(String text) {
        return JSON.parse(text);
    }
    
    public static <K, V> Map<K, V>  stringToCollect(String s) {
        return (Map<K, V>) JSONObject.parseObject(s);
    }
    
    public static Object convertJsonToObject(String jsonData, Class<?> clazz) {
        return JSONObject.parseObject(jsonData, clazz);
    }
    
    public static <K, V> String collectToString(Map<K, V> m) {
        return JSONObject.toJSONString(m);
    }
}