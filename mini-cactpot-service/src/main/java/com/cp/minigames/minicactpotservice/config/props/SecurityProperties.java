package com.cp.minigames.minicactpotservice.config.props;

import lombok.Builder;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Builder
@ConfigurationProperties(prefix = "application.security")
public record SecurityProperties(
    List<String> allowedOrigins,
    List<String> allowedMethods,
    List<String> exposedHeaders,
    List<String> allowedHeaders,
    Boolean allowCredentials
) {
}
