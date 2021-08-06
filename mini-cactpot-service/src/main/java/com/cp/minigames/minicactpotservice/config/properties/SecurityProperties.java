package com.cp.minigames.minicactpotservice.config.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "application.security")
public class SecurityProperties {
    private List<String> allowedOrigins;
    private List<String> allowedMethods;
    private Boolean allowCredentials;
    private List<String> allowedHeaders;
    private List<String> exposedHeaders;
}
