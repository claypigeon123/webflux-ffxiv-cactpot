package com.cp.minigames.minicactpotservice.exception;

import com.cp.minigames.minicactpotservice.exception.base.MiniCactpotGameException;

public class FieldAlreadyRevealedException extends MiniCactpotGameException {
    public FieldAlreadyRevealedException() {
        super("The given field is already revealed on the given ticket");
    }
}
