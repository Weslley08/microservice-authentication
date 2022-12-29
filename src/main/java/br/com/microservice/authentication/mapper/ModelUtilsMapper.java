package br.com.microservice.authentication.mapper;

import br.com.microservice.authentication.model.ResponseData;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

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


    public static ResponseData setResponseData(Object data) {
        ResponseData responseData = new ResponseData();
        responseData.setData(data);
        return responseData;
    }

}
