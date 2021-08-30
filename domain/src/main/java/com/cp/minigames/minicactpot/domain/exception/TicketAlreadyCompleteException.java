package com.cp.minigames.minicactpotservice.exception;

import com.cp.minigames.minicactpotservice.exception.base.MiniCactpotGameException;

public class TicketAlreadyCompleteException extends MiniCactpotGameException {
    public TicketAlreadyCompleteException() {
        super("The given mini cactpot ticket has already been completed");
    }
}
