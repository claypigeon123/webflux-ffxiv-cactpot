package com.cp.minigames.minicactpot.domain.exception.impl;

import com.cp.minigames.minicactpot.domain.exception.MiniCactpotGameException;
import org.springframework.http.HttpStatus;

public class FieldAlreadyRevealedException extends MiniCactpotGameException {
    public FieldAlreadyRevealedException() {
        super("The given field is already revealed on the given ticket", HttpStatus.CONFLICT);
    }
}
