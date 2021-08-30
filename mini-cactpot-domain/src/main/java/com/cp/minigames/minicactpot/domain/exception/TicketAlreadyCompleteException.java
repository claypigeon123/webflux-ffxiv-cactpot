package com.cp.minigames.minicactpot.domain.exception;

import com.cp.minigames.minicactpot.domain.exception.base.MiniCactpotGameException;

public class TicketAlreadyCompleteException extends MiniCactpotGameException {
    public TicketAlreadyCompleteException() {
        super("The given mini cactpot ticket has already been completed");
    }
}
