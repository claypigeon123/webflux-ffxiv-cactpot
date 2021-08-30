package com.cp.minigames.minicactpot.domain.exception;

import com.cp.minigames.minicactpot.domain.exception.base.MiniCactpotGameException;

public class TicketNotFoundException extends MiniCactpotGameException {
    public TicketNotFoundException(String id) {
        super(String.format("Mini cactpot ticket with ID %s not found", id));
    }
}
