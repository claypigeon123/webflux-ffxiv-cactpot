package com.cp.minigames.minicactpotservice;

import com.cp.minigames.minicactpotservice.config.model.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MiniCactpotServiceApplication implements CommandLineRunner {
    private final Logger log = LoggerFactory.getLogger(MiniCactpotServiceApplication.class);
    private final SecurityProperties securityProperties;

    public MiniCactpotServiceApplication(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    public static void main(String[] args) {
        SpringApplication.run(MiniCactpotServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Loaded properties: {}", securityProperties);
    }
}
