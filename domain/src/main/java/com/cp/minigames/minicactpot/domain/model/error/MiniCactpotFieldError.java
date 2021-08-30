package com.cp.minigames.minicactpotservice.model.error;

import lombok.*;

@Value
@Builder
@RequiredArgsConstructor
public class MiniCactpotFieldError {
    String field;
    String error;
}
