package com.cp.minigames.minicactpotservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MiniCactpotServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiniCactpotServiceApplication.class, args);
    }
}
