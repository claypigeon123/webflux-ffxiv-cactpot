package com.cp.minigames.minicactpotservice.config.props;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Builder
@Validated
@ConfigurationProperties(prefix = "mini-cactpot.cleanup")
public record CleanupProperties(
    @NotBlank String aggregateCleanupCron,
    @NotNull Long cutoffHours
) {
}
