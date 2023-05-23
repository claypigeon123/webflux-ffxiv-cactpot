package com.cp.minigames.minicactpot.domain.exception.impl;

import com.cp.minigames.minicactpot.domain.exception.MiniCactpotGameException;
import org.springframework.http.HttpStatus;

public class CannotMakeSelectionException extends MiniCactpotGameException {
    public CannotMakeSelectionException() {
        super("Cannot make a selection on the given ticket yet", HttpStatus.CONFLICT);
    }
}
