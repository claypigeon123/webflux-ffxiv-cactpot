package com.cp.minigames.minicactpotservice.config.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "application.cleanup")
public class CleanupProperties {
    private String aggregateCleanupCron;
    private long cutoffHours;
}
