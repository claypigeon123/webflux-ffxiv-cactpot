package com.cp.minigames.minicactpot.domain.model.error;

import lombok.*;

@Value
@Builder
@RequiredArgsConstructor
public class MiniCactpotFieldError {
    String field;
    String error;
}
