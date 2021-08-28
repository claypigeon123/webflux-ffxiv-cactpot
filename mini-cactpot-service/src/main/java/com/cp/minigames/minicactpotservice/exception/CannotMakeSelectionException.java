package com.cp.minigames.minicactpotservice.exception;

import com.cp.minigames.minicactpotservice.exception.base.MiniCactpotGameException;

public class CannotMakeSelectionException extends MiniCactpotGameException {
    public CannotMakeSelectionException() {
        super("Cannot make a selection on the given ticket yet");
    }
}
