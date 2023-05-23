package com.cp.minigames.minicactpotservice.config.props;

import com.cp.minigames.minicactpotservice.validation.annotation.ValidWinningsMap;
import lombok.Builder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.Map;

@Builder
@Validated
@ConfigurationProperties(prefix = "mini-cactpot")
public record MiniCactpotProperties(
    @ValidWinningsMap Map<Integer, Integer> winningsMap
) {
}
