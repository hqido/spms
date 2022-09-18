package cc.hqido.spms.infra.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yueli
 * @since 2022/9/2
 */
public class JacksonUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper().
            //如果为空则不输出
                    disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
            //多余字段忽略
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            //视空字符传为null
            .enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

    private static final ObjectMapper IGNORE_NULL_OBJECT_MAPPER = new ObjectMapper()
            .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    public static String json(Object obj) {
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * json 序列化，空对象不输出
     *
     * @param obj 序列化对象
     * @return 不含有空对象的 json
     */
    public static String jsonIgnoreNull(Object obj) {
        try {
            return IGNORE_NULL_OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, Object> map(String json) {
        return map(json, String.class, Object.class);
    }

    public static <K, V> Map<K, V> map(String json, Class<K> keyClass, Class<V> valueClass) {
        try {
            return MAPPER.readValue(json, MAPPER.getTypeFactory().constructMapType(HashMap.class, keyClass, valueClass));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T object(String json, Class<T> clazz) {
        try {
            return MAPPER.readValue(json, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T object(JsonNode jsonNode, Class<T> clazz) {
        return MAPPER.convertValue(jsonNode, clazz);
    }

    public static <T> List<T> list(String json, Class<T> clazz) {
        try {
            return MAPPER.readValue(json, MAPPER.getTypeFactory().constructCollectionType(ArrayList.class, clazz));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> list(JsonNode jsonNode, Class<T> clazz) {
        return MAPPER.convertValue(jsonNode, MAPPER.getTypeFactory().constructCollectionType(ArrayList.class, clazz));
    }

    /**
     * 自定义 json 反序列化类型
     *
     * @param json          json
     * @param typeReference 自定义类型
     * @param <T>           类型
     * @return 类型
     */
    public static <T> T object(String json, TypeReference<T> typeReference) {
        try {
            return MAPPER.readValue(json, typeReference);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static JsonNode jsonNode(String json) {
        try {
            return MAPPER.readTree(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
