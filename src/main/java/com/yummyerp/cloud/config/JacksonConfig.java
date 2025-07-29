package com.yummyerp.cloud.config;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Jackson配置类
 * 全局配置JSON日期时间格式
 */
@Configuration
public class JacksonConfig {

    /**
     * 默认日期时间格式
     */
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    
    /**
     * 默认日期格式
     */
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    
    /**
     * 默认时间格式
     */
    private static final String TIME_FORMAT = "HH:mm:ss";

    /**
     * Jackson全局配置
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> {
            // 日期时间格式化
            JavaTimeModule javaTimeModule = new JavaTimeModule();
            
            // LocalDateTime序列化和反序列化配置
            javaTimeModule.addSerializer(LocalDateTime.class, 
                    new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)));
            javaTimeModule.addDeserializer(LocalDateTime.class, 
                    new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)));
            
            // LocalDate序列化和反序列化配置
            javaTimeModule.addSerializer(LocalDate.class, 
                    new LocalDateSerializer(DateTimeFormatter.ofPattern(DATE_FORMAT)));
            javaTimeModule.addDeserializer(LocalDate.class, 
                    new LocalDateDeserializer(DateTimeFormatter.ofPattern(DATE_FORMAT)));
            
            // LocalTime序列化和反序列化配置
            javaTimeModule.addSerializer(LocalTime.class, 
                    new LocalTimeSerializer(DateTimeFormatter.ofPattern(TIME_FORMAT)));
            javaTimeModule.addDeserializer(LocalTime.class, 
                    new LocalTimeDeserializer(DateTimeFormatter.ofPattern(TIME_FORMAT)));
            
//            // Long和BigInteger类型序列化为String，避免前端精度丢失
//            builder.serializerByType(Long.class, ToStringSerializer.instance);
//            builder.serializerByType(Long.TYPE, ToStringSerializer.instance);
//            builder.serializerByType(BigInteger.class, ToStringSerializer.instance);
            
            builder.modules(javaTimeModule);
        };
    }
} 