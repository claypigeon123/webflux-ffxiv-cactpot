package com.cp.minigames.minicactpotservice.config.props;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.Map;

@Builder
@Validated
@ConfigurationProperties(prefix = "mini-cactpot")
public record MiniCactpotProperties(
    @NotEmpty Map<Integer, Integer> winningsMap
) {
}
