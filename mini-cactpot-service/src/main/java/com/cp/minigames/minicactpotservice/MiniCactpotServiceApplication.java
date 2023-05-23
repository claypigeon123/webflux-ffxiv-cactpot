package com.cp.minigames.minicactpotservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@SpringBootApplication(exclude = ReactiveUserDetailsServiceAutoConfiguration.class)
public class MiniCactpotServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiniCactpotServiceApplication.class, args);
    }
}
