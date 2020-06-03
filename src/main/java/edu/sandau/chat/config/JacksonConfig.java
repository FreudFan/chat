package edu.sandau.chat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class JacksonConfig {

    @Bean
    public Converter<String, LocalDate> localDateConverter() {
        String datePattern = "yyyy-MM-dd";
        return new Converter<String, LocalDate>() {
            @Override
            public LocalDate convert(String source) {
                try {
                    return LocalDate.parse(source, DateTimeFormatter.ofPattern(datePattern));
                } catch (Exception e) {
                    throw new IllegalArgumentException("无效的日期格式，请使用这种格式:" + datePattern);
                }
            }
        };
    }

    @Bean
    public Converter<String, LocalDateTime> localDateTimeConverter() {
        String datePattern = "yyyy-MM-dd HH:mm:ss";
        return new Converter<String, LocalDateTime>() {
            @Override
            public LocalDateTime convert(String source) {
                try {
                    return LocalDateTime.parse(source, DateTimeFormatter.ofPattern(datePattern));
                } catch (Exception e) {
                    throw new IllegalArgumentException("无效的日期格式，请使用这种格式:" + datePattern);
                }
            }
        };
    }

}
