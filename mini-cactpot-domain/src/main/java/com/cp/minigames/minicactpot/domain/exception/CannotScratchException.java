package com.cp.minigames.minicactpot.domain.exception;

import com.cp.minigames.minicactpot.domain.exception.base.MiniCactpotGameException;

public class CannotScratchException extends MiniCactpotGameException {
    public CannotScratchException() {
        super("Cannot scratch any more fields on the given ticket");
    }
}
