package vn.uni.medico.shared.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ObjectMapperUtils {

    private static ObjectMapper objectMapper;

    @Autowired
    public ObjectMapperUtils(ObjectMapper objectMapper) {
        ObjectMapperUtils.objectMapper = objectMapper;
    }

    public static <T> T convertToClass(String jsonValue, Class<T> clazz) {
        try {
            return objectMapper.readValue(jsonValue, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> convertToList(String jsonValue) {
        try {
            return objectMapper.readValue(jsonValue, new TypeReference<List<T>>() {
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String convertToString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
