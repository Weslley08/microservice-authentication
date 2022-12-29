package br.com.microservice.authentication.mapper;

import br.com.microservice.authentication.model.ResponseData;
import br.com.microservice.authentication.model.dto.ErrorData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public final class ModelUtilsMapper {

    private static final ObjectMapper MAPPER = initMapper();

    private static ObjectMapper initMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return objectMapper;
    }

    public static <T> T fromObject(Object originClass, Class<T> targetClass) {
        return MAPPER.convertValue(originClass, targetClass);
    }

    public static <T> T convertJsonToObject(String originClass, Class<T> targetClass) {
        try {
            return MAPPER.readValue(originClass, targetClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.toString());
        }
    }

    public static <T> T convertJsonToObject(InputStream originClass, Class<T> targetClass) {
        try {
            return MAPPER.readValue(originClass, targetClass);
        } catch (IOException e) {
            throw new RuntimeException(e.toString());
        }
    }


    public static ResponseData setResponseData(Object data) {
        ResponseData responseData = new ResponseData();
        responseData.setData(data);
        return responseData;
    }

}
