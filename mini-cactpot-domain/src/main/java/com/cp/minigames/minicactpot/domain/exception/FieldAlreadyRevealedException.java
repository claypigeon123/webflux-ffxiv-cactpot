package com.cp.minigames.minicactpot.domain.exception;

import com.cp.minigames.minicactpot.domain.exception.base.MiniCactpotGameException;

public class FieldAlreadyRevealedException extends MiniCactpotGameException {
    public FieldAlreadyRevealedException() {
        super("The given field is already revealed on the given ticket");
    }
}
