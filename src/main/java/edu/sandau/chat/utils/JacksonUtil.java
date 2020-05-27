package edu.sandau.chat.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Slf4j
public class JacksonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        JavaTimeModule timeModule = new JavaTimeModule();
        timeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        timeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        objectMapper.registerModule(timeModule);
    }

    /***
     * 对象序列化为json字符串
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> String toJSON(T obj) {
        String jsonStr;
        try {
            jsonStr = objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.error("Jackson序列化失败");
            throw new RuntimeException(e);
        }
        return jsonStr;
    }

    /***
     * 将json字符串序列化为对象
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T fromJSON(String json, Class<T> clazz) {
        T obj;
        try {
            if (clazz.isAssignableFrom(String.class)) {
                return (T) json;
            }
            obj = objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            log.error("Jackson反序列化失败");
            throw new RuntimeException(e);
        }
        return obj;
    }

    public static class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {
        @Override
        public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeNumber(localDateTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli());
        }
    }

    public static class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
        @Override
        public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            long timestamp = jsonParser.getLongValue();
            return LocalDateTime.ofEpochSecond(timestamp / 1000, 0, ZoneOffset.ofHours(8));
        }
    }

}
