package kg.zakharov.payments.service.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class JsonConverter {

    private final ObjectMapper objectMapper;
    private final Logger logger;

    public JsonConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.logger = LoggerFactory.getLogger(JsonConverter.class);
    }

    public String toJson(Object object){
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.error("Can't convert to Json object : {}", object);
            throw new JsonConverterException(e);
        }
    }
    public <T> T toModel(String content, Class<T> valueType){
        try {
            return objectMapper.readValue(content, valueType);
        } catch (JsonProcessingException e) {
            logger.error("Can't convert to Model content : {}", content);
            throw new JsonConverterException(e);
        }
    }

    public static class JsonConverterException extends RuntimeException{
        public JsonConverterException(Throwable cause) {
            super(cause);
        }
    }
}
