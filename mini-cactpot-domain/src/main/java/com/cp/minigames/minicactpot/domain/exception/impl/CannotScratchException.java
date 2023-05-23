package com.cp.minigames.minicactpot.domain.exception.impl;

import com.cp.minigames.minicactpot.domain.exception.MiniCactpotGameException;
import org.springframework.http.HttpStatus;

public class CannotScratchException extends MiniCactpotGameException {
    public CannotScratchException() {
        super("Cannot scratch any more fields on the given ticket", HttpStatus.CONFLICT);
    }
}
