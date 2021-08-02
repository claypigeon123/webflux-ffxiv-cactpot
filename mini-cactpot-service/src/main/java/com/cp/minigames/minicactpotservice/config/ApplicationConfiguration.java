package com.cp.minigames.minicactpotservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public DateTimeFormatter dtf() {
        return DateTimeFormatter
            .ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .withZone(ZoneId.of(ZoneOffset.UTC.getId()));
    }

}
