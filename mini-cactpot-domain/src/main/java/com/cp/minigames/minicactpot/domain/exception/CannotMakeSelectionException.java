package com.cp.minigames.minicactpot.domain.exception;

import com.cp.minigames.minicactpot.domain.exception.base.MiniCactpotGameException;

public class CannotMakeSelectionException extends MiniCactpotGameException {
    public CannotMakeSelectionException() {
        super("Cannot make a selection on the given ticket yet");
    }
}
