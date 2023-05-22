package com.cp.minigames.minicactpotservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public DateTimeFormatter dtf() {
        return DateTimeFormatter.ISO_OFFSET_DATE_TIME
            .withZone(ZoneId.of(ZoneOffset.UTC.getId()));
    }

    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }
}
