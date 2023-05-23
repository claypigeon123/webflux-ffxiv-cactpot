package com.cp.minigames.minicactpotservice.config;

import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Configuration
@SuppressWarnings("Convert2Lambda")
public class MongoConfiguration {

    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        Converter<OffsetDateTime, String> odtToString = new Converter<>() {
            @Override
            public String convert(@NonNull OffsetDateTime source) {
                return source.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of(ZoneOffset.UTC.getId())));
            }
        };

        Converter<String, OffsetDateTime> stringToOdt = new Converter<>() {
            @Override
            public OffsetDateTime convert(@NonNull String source) {
                return OffsetDateTime.parse(source);
            }
        };

        return new MongoCustomConversions(List.of(
            odtToString, stringToOdt
        ));
    }

}
