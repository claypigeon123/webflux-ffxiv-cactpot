package com.cp.minigames.minicactpotservice.exception;

import com.cp.minigames.minicactpotservice.exception.base.MiniCactpotGameException;

public class CannotScratchException extends MiniCactpotGameException {
    public CannotScratchException() {
        super("Cannot scratch any more fields on the given ticket");
    }
}
